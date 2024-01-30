plugins {

    id("ai.acyclic.scala-mixin")
    id("io.github.cosmicsilence.scalafix")
}

val vs = versions()

val scalametaV = "4.8.15"

allprojects {

    dependencies {

        bothImpl("${vs.scala.group}:scala-library:${vs.scala.v}")
        bothImpl("${vs.scala.group}:scala-reflect:${vs.scala.v}")
//        bothImpl("${vs.scala.group}:scala-compiler:${vs.scala.v}") // enable if low-level mutli-stage programming is required

        testFixturesApi("org.scalatest:scalatest_${vs.scala.binaryV}:${vs.scalaTestV}")

        // Don't delete, used for auto version upgrade
        testImplementation("org.scalameta:scalameta_${vs.scala.binaryV}:$scalametaV")

        if (vs.splainV.isNotEmpty()) {
            val splainD = "io.tryp:splain_${vs.scala.v}:${vs.splainV}"
            logger.warn("using ${splainD} ...\t in ${project.displayName} / scalaCompilerPlugins")

            scalaCompilerPlugins(splainD)
        }
    }

    tasks {

        withType<ScalaCompile> {

            scalaCompileOptions.apply {

                additionalParameters.addAll(
                    listOf(
                        "-encoding", "UTF-8",

                        "-g:vars", // demand by json4s

                        "-deprecation",
                        "-unchecked",
                        "-feature",
                        "-language:higherKinds",
                        "-language:existentials",

                        "-Ywarn-value-discard",

                        "-Ywarn-unused",

                        "-Wconf:cat=unused-params:i,cat=unused-locals:i",

                        "-Xlint:poly-implicit-overload",
                        "-Xlint:option-implicit",
                    )
                )

                if (vs.splainV.isNotEmpty()) {
                    additionalParameters.addAll(
                        listOf(
                            "-Vimplicits", "-Vimplicits-verbose-tree", "-Vtype-diffs",
                            "-P:splain:Vimplicits-diverging",
                            "-P:splain:Vtype-detail:4",
                            "-P:splain:Vtype-diffs-detail:3",
//                            "-P:splain:Vdebug"
                        )
                    )
                }
            }

            apply(plugin = "io.github.cosmicsilence.scalafix")
            scalafix {
                semanticdb.autoConfigure.set(true)
                semanticdb.version.set(scalametaV)
            }
        }
    }
}
