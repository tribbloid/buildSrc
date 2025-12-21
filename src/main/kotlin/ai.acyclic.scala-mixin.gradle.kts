import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.kotlin.dsl.named

plugins {

    id("ai.acyclic.java-conventions")

    scala
    id("io.github.cosmicsilence.scalafix")
}

val vs = versions()

// TODO: remove after https://github.com/ben-manes/gradle-versions-plugin/issues/816 resolved
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    filterConfigurations = Spec<Configuration> {
        !it.name.startsWith("incrementalScalaAnalysis")
    }
}

val scalametaV = "4.14.2"

allprojects {

    if (!plugins.hasPlugin("bloop")) {
        apply(plugin = "bloop")
        // DO NOT apply directly! In VSCode it will cause the conflict:
        // Cannot add extension with name 'bloop', as there is an extension already registered with that name
    }

    dependencies {
        // Don't delete, used for auto version upgrade
        testImplementation("org.scalameta:scalameta_${vs.scala.artifactSuffix}:$scalametaV")

        var semanticdbJavacV = "0.11.2"

        compileOnly("com.sourcegraph:semanticdb-javac:${semanticdbJavacV}")
        annotationProcessor("com.sourcegraph:semanticdb-javac:${semanticdbJavacV}")
    }

    apply(plugin = "scala")

    BuildMapping.getAll().forEach { this.jointScala(it) }

    scala {
//        withSourcesJar()
//        withJavadocJar()
    }

    tasks {

        withType<ScalaCompile> {

            val jvmTarget = vs.jvmTarget.toString()

            sourceCompatibility = jvmTarget
            targetCompatibility = jvmTarget


            // expose java src to scala compiler and cut the dependency of ScalaCompile on JavaCompile
//            classpath = sourceSets.main.get().compileClasspath

            scalaCompileOptions.apply {

                loggingLevel = "verbose"

                forkOptions.apply {

                    memoryInitialSize = "1g"
                    memoryMaximumSize = "4g"

                    jvmArgs = listOf(
                        // this may be over the top but the test code in macro & core frequently run implicit search on church encoded Nat type
                        "-Xss256m",
                        // circumventing Java 17 + Apache Spark test runner
                        //
                    )
                }

                additionalParameters.addAll(
                    listOf(
                        "-encoding", "UTF-8",
                    )
                )
            }

            apply(plugin = "io.github.cosmicsilence.scalafix")
            scalafix {
                semanticdb.autoConfigure.set(true)
                semanticdb.version.set(scalametaV)
            }
        }
    }

    idea {

        module {

            excludeDirs.addAll(
                files(
                    ".bloop",
                    ".bsp",
                    ".metals",
                    ".ammonite"
                )
            )
        }
    }
}
