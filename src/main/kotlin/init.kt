import gradle.kotlin.dsl.accessors._46b0ca6400a65b9754358ead47fee5d2.implementation
import gradle.kotlin.dsl.accessors._46b0ca6400a65b9754358ead47fee5d2.testFixturesImplementation
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 */
fun Project.versions(): Versions {

    return Versions(this)
}


// see https://github.com/gradle/gradle/issues/13067
// TODO: how do I move it into upstream plugins?
fun DependencyHandler.bothImpl(dependencyNotation: Any): Unit {
    implementation(dependencyNotation)
    testFixturesImplementation(dependencyNotation)
}