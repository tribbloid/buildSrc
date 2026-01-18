plugins {

    id("ai.acyclic.scala-mixin")
    id("io.github.cosmicsilence.scalafix")
}

val vs = versions()
val vScala = vs.scala.v
//val vScala = "3.3.6"

allprojects {

    dependencies {

        bothImpl("org.scala-lang:scala3-library_3:${vScala}")
        bothImpl("org.scala-lang:scala3-staging_3:${vScala}")
    }

    tasks {

        withType<ScalaCompile> {

            scalaCompileOptions.apply {

                additionalParameters.addAll(
                    listOf(
//                        "-verbose", // enable in case of compiler bug
//                        "-explain",

                        "-feature",

//                        "-rewrite",
                        "-source:3.3-migration",
//                        "-source:future-migration",
//                        "-source:future",

                        "-explain-cyclic",

                        "-Wunused:all",// demand by scalafix

                        "-language:experimental.modularity",
//                        "-language:experimental.dependent",

//                        "-Ydebug",
                    )
                )
            }
        }
    }
}
