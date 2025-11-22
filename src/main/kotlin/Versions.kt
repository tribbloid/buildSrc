import org.gradle.api.JavaVersion
import org.gradle.api.Project

class Versions(private val project: Project) {

    // TODO : how to group them?
    val rootGroup = project.properties["rootGroup"]?.toString() ?: "ai.acyclic"

    val rootID = project.properties["rootID"]?.toString() ?: "scaffold"

    val rootGroupID = "$rootGroup.$rootID"

    val rootV = project.properties["rootVersion"]?.toString() ?: "1.0.0-SNAPSHOT"
    val rootVMajor = rootV.removeSuffix("-SNAPSHOT")

    inner class Scala {
        val group: String = project.properties["scalaGroup"]?.toString() ?: "org.scala-lang"

        val v: String = project.properties["scalaVersion"].toString()
        protected val vParts: List<String> = v.split('.').also { parts ->
            require(parts.size == 3) { "Scala version must be in format 'X.Y.Z' but was: $v" }
        }

        val majorV: String = vParts[0]
        val binaryV: String = vParts.subList(0, 2).joinToString(".")
        val patchV: String = vParts[2]

        val artifactSuffix = run {
            if (majorV == "3") majorV
            else binaryV
        }

        val jsV: String? = project.properties.get("scalaJSVersion")?.toString()
    }

    val scala: Scala by lazy { Scala() }

    val javaVersion = project.properties["javaVersion"]?.toString()?.let { JavaVersion.toVersion(it) }
        ?: JavaVersion.VERSION_17

    val jvmTarget = javaVersion

    val scalaTestV = "3.2.19"
    val splainV: String = project.properties["splainVersion"]?.toString() ?: ""


    inner class Spark {

        val v: String = project.properties["sparkVersion"].toString()
        protected val vParts: List<String> = v.split('.')

        val majorV: String = vParts[0]
        val binaryV: String = vParts.subList(0, 2).joinToString(".")
        val patchV: String = vParts[2]
    }

    val spark: Spark by lazy { Spark() }
}
