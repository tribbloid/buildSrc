plugins {

    id("ai.acyclic.scala-mixin")
}

val vs = versions()

allprojects {

    dependencies {

        bothImpl("org.scala-lang:scala3-library_3:${vs.scala.v}")
        bothImpl("org.scala-lang:scala3-staging_3:${vs.scala.v}")

        testFixturesApi("org.scalatest:scalatest_3:${vs.scalaTestV}")
    }

    tasks {

        withType<ScalaCompile> {

            scalaCompileOptions.apply {

                additionalParameters.addAll(
                    listOf(

                        "-encoding", "UTF-8",

                        "-verbose", "-explain",

//                        "-language:experimental.dependent"
                    )
                )
            }
        }
    }
}
