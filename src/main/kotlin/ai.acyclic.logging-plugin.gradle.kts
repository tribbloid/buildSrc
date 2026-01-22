import com.dorongold.gradle.tasktree.TaskTreeTask
import org.gradle.api.reporting.dependencies.HtmlDependencyReportTask
import org.gradle.kotlin.dsl.register

val logDir = project.rootProject.layout.projectDirectory.dir("logs/__LAST")

tasks {

// Register dependency reporting task
    register<HtmlDependencyReportTask>("logCriticalDependencies") {
        setConfiguration("testRuntimeClasspath")
        outputFile = logDir.file("dependencies.txt").asFile
        reports.html.required.set(true)
        reports.html.outputLocation.set(logDir.dir("dependencies"))
    }

// Register task tree generation task
    register<TaskTreeTask>("taskTreeToFile") {
        outputFile = logDir.file("tasks.txt").asFile
    }

// Register aggregated task
    register("logCriticalTasks") {
        dependsOn("test", "taskTreeToFile")
    }
}
