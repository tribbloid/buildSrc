import io.github.gradlenexus.publishplugin.NexusRepository
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

plugins {

    signing
    `maven-publish`

    id("io.github.gradle-nexus.publish-plugin")// version "1.3.0"
}

val vs = versions()

val sonatypeApiUser = providers.gradleProperty("sonatypeApiUser")
val sonatypeApiKey = providers.gradleProperty("sonatypeApiKey")
if (sonatypeApiUser.isPresent && sonatypeApiKey.isPresent) {
    nexusPublishing {
        repositories {

            withType<NexusRepository> {

                username.set(sonatypeApiUser)
                password.set(sonatypeApiKey)
            }
        }
    }
} else {
    logger.warn("Sonatype API key not defined, skipping configuration of Maven Central publishing repository")
}

subprojects {

    apply(plugin = "signing")
    apply(plugin = "maven-publish")

    // https://stackoverflow.com/a/66352905/1772342

//    val signingKeyID = providers.gradleProperty("signing.gnupg.keyID")
    val signingSecretKey = providers.gradleProperty("signing.gnupg.secretKey")
    val signingKeyPassphrase = providers.gradleProperty("signing.gnupg.passphrase")

    signing {
        useGpgCmd()
        if (signingSecretKey.isPresent) {
            useInMemoryPgpKeys(signingSecretKey.get(), signingKeyPassphrase.get())
//            useInMemoryPgpKeys(signingKeyID.get(), signingSecretKey.get(), signingKeyPassphrase.get())
            sign(extensions.getByType<PublishingExtension>().publications)
        } else {
            logger.warn("PGP signing key not defined, skipping signing configuration")
        }
    }

    apply(plugin = "maven-publish")
    publishing {

        val rootID = vs.rootID
        val suffix = "_" + vs.scala.binaryV

        val moduleID =
            if (project.name.equals(rootID)) throw UnsupportedOperationException("root project should not be published")
            else rootID + project.path.replace(':','-') + suffix

        logger.info("publishing module `${project.path}` as `$moduleID`")

        publications {
            withType<MavenPublication> {
                artifactId = moduleID
                groupId = vs.rootGroupID
                // Lightweight Gradle Submodules use different groupID to avoid name collision
                //  which should be reverted when publishing

                val javaComponent = components["java"] as AdhocComponentWithVariants
                from(javaComponent)

                javaComponent.withVariantsFromConfiguration(configurations["testFixturesApiElements"]) { skip() }
                javaComponent.withVariantsFromConfiguration(configurations["testFixturesRuntimeElements"]) { skip() }
            }
        }
    }
}