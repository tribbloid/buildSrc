import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.scala.ScalaCompile
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Define BuildOvrd locally for this subproject (matching parent definition)
data class BuildMapping(
    val taskPrefix: String,
    val moduleName: String
) {

    companion object {
        fun getAll(): List<BuildMapping> {

            return listOf(
                BuildMapping("compile", "main"),
                BuildMapping("compileTest", "test"),
                BuildMapping("compileTestFixtures", "testFixtures"),
            )
        }
    }
}

fun Project.jointScala(m: BuildMapping) {
    val javaTaskName = "${m.taskPrefix}Java"
    val scalaTaskName = "${m.taskPrefix}Scala"

    tasks.named<JavaCompile>(javaTaskName) {
        enabled = false
    }

    val javaTask = tasks.named<JavaCompile>(javaTaskName).get()

    // cut dependency of scalaCompile to JavaCompile
    tasks.named<ScalaCompile>(scalaTaskName) {

        source = source.plus(javaTask.source)

        val sourceSets = project.extensions.getByType<SourceSetContainer>()
        val sourceSet = sourceSets.findByName(m.moduleName)

        if (sourceSet != null) {
            classpath = sourceSet.compileClasspath
//            doFirst {
//                println("Configuring task: ${name}")
//                println("  SourceSet: ${sourceSet.name}")
//                println("  CompileClasspath: ${sourceSet.compileClasspath.files}")
//            }
        } else {
            classpath = classpath.minus(files(javaTask.destinationDirectory))
        }
    }


}

fun Project.scalaThenKotlin(m: BuildMapping) {

    val kotlinTaskName = "${m.taskPrefix}Kotlin"
    val scalaTaskName = "${m.taskPrefix}Scala"

    afterEvaluate {
        tasks.named<KotlinCompile>(kotlinTaskName).configure {
            val scalaCompile = tasks.named<ScalaCompile>(scalaTaskName)
            dependsOn(scalaCompile)
            libraries.from(scalaCompile.flatMap { it.destinationDirectory })
        }
    }
}

// Configure Scala compilation
fun Project.kotlinThenScala(m: BuildMapping) {
    val kotlinTaskName = "${m.taskPrefix}Kotlin"
    val scalaTaskName = "${m.taskPrefix}Scala"

    val kotlinCompile = tasks.named<KotlinCompile>(kotlinTaskName)

    tasks.named<ScalaCompile>(scalaTaskName) {
        dependsOn(kotlinCompile)

        classpath = classpath +
                files(kotlinCompile.get().destinationDirectory)
    }
}
