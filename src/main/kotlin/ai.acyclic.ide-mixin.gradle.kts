plugins {
    base

    `project-report`

    idea
    eclipse

    id("com.github.ben-manes.versions")
    id("com.dorongold.task-tree")
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "8.14.3"
}

val vs = versions()

allprojects {

    apply(plugin = "project-report")
    apply(plugin = "idea")
    apply(plugin = "eclipse")

    group = vs.rootGroupID
    version = vs.rootV

    repositories {
        mavenLocal()
        mavenCentral()

        // for Scala formless
        maven {
            name = "bondlink-maven-repo"
            url = uri("https://raw.githubusercontent.com/mblink/maven-repo/main")
        }
    }


    eclipse {

        classpath {

            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }


    idea {

        targetVersion = "2023"


        module {

            isDownloadJavadoc = true
            isDownloadSources = true

            excludeDirs.addAll(
                files(
                    "build",
                    "target",
                    "out",
                    "bin",


                    ".gradle",
                    ".idea",
                    ".vscode",
                    ".cache",
                    ".history",
                    ".lib",

                    "logs",

                    ".classpath",
                    ".project"
                )
            )
        }
    }

}

idea {

    module {
        isDownloadSources = true
        isDownloadJavadoc = true

        excludeDirs.addAll(
            files(
//                "buildSrc",
                "buildSrc/gradle",
                "buildSrc/build",
                "buildSrc/build/generated-sources/kotlin-dsl-accessors/kotlin", // TODO: this should be fully covered by previous line, need to report the bug
                "gradle/wrapper",
            )
        )
    }
}