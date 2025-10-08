plugins {

    id("ai.acyclic.scala-mixin")
}

val vs = versions()

allprojects {

    dependencies {

        testFixturesApi("org.scalatest:scalatest_${vs.scala.artifactSuffix}:${vs.scalaTestV}")
        testImplementation("org.scalatest:scalatest_${vs.scala.artifactSuffix}:${vs.scalaTestV}")
//        testFixturesApi("org.scalatest:scalatest-core_${vs.scala.artifactSuffix}:${vs.scalaTestV}")

        val jUnitV = "5.13.4"
        val jUnitPlatformV = "1.13.4"

        testRuntimeOnly("org.junit.platform:junit-platform-engine:$jUnitPlatformV")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher:$jUnitPlatformV")
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitV}")
//        testRuntimeOnly("org.scalatestplus:junit-5-13_${vs.scala.artifactSuffix}:3.2.19.0")
        testRuntimeOnly("ai.acyclic.scalatestplus:junit-5-13_${vs.scala.artifactSuffix}:3.2.19.2")
//        testRuntimeOnly("ai.acyclic.scalatestplus:junit-5-13_${vs.scala.artifactSuffix}:3.3.0.0")
    }

    tasks {

        test {

            minHeapSize = "1024m"
            maxHeapSize = "4096m"

            useJUnitPlatform {
                includeEngines("scalatest")
            }

            /*
in IDE:
--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.invoke=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.net=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens=java.base/jdk.internal.ref=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/sun.nio.cs=ALL-UNNAMED --add-opens=java.base/sun.security.action=ALL-UNNAMED --add-opens=java.base/sun.util.calendar=ALL-UNNAMED
working directory:
$MODULE_WORKING_DIR$
             */
            val addOpens =
                """
                        java.base/java.lang
                        java.base/java.lang.invoke
                        java.base/java.lang.reflect
                        java.base/java.io
                        java.base/java.net
                        java.base/java.nio
                        java.base/java.util
                        java.base/java.util.concurrent
                        java.base/java.util.concurrent.atomic
                        java.base/jdk.internal.ref
                        java.base/sun.nio.ch
                        java.base/sun.nio.cs
                        java.base/sun.security.action
                        java.base/sun.util.calendar
                    """.trimIndent().split("\n")
            // from apache spark test runner

            var allArgs = listOf(
                "-Xmx2048m",  // Maximum heap size
                "-XX:MaxMetaspaceSize=512m",  // Maximum metaspace size
                "-XX:+HeapDumpOnOutOfMemoryError",  // Dump heap on OOM
                "-Dfile.encoding=UTF-8",  // Set file encoding
            )
            for (pkg in addOpens) {
                allArgs += "--add-opens=$pkg=ALL-UNNAMED"
            }

            jvmArgs(allArgs)
        }

    }
}
