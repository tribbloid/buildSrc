import org.gradle.api.JavaVersion
import org.gradle.api.Project

class Versions(private val self: Project) {

    // TODO : how to group them?
    val rootGroup = self.properties["rootGroup"]?.toString() ?: "ai.acyclic"

    val rootID = self.properties["rootID"]?.toString() ?: "scaffold"

    val rootGroupID = "$rootGroup.$rootID"

    val rootV = self.properties["rootVersion"]?.toString() ?: "1.0.0-SNAPSHOT"
    val rootVMajor = rootV.removeSuffix("-SNAPSHOT")

    inner class Scala {
        val group: String = self.properties["scalaGroup"]?.toString() ?: "org.scala-lang"

        val v: String = self.properties["scalaVersion"].toString()
        protected val vParts: List<String> = v.split('.')

        val majorV: String = vParts[0]
        val binaryV: String = vParts.subList(0, 2).joinToString(".")
        val patchV: String = vParts[2]
    }
    val scala: Scala by lazy { Scala() }

    val jvmTarget = JavaVersion.VERSION_11

    val scalaTestV = "3.2.17"
    val splainV: String = self.properties["splainVersion"]?.toString() ?: ""

    val scalajsV: String? = self.properties.get("scalaJSVersion")?.toString()


    inner class Spark {

        val v: String = self.properties["sparkVersion"].toString()
        protected val vParts: List<String> = v.split('.')

        val majorV: String = vParts[0]
        val binaryV: String = vParts.subList(0, 2).joinToString(".")
        val patchV: String = vParts[2]
    }
    val spark: Spark by lazy { Spark() }
}
