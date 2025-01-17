plugins {

    id("ai.acyclic.scala-mixin")
    id("io.github.cosmicsilence.scalafix")
}

val vs = versions()

allprojects {

    dependencies {

        bothImpl("org.scala-lang:scala3-library_3:${vs.scala.v}")
        bothImpl("org.scala-lang:scala3-staging_3:${vs.scala.v}")
    }

    tasks {

        withType<ScalaCompile> {

            scalaCompileOptions.apply {

                additionalParameters.addAll(
                    listOf(

                        "-encoding", "UTF-8",

                        "-verbose", "-explain",

                        "-experimental",
                        "-source:future",
                        "-language:experimental.modularity",
                        "-explain-cyclic"

//                        "-language:experimental.dependent"

//                        "-verbose", // enable in case of compiler bug
//                        "-Ydebug",
                    )
                )
            }
        }
    }
}
