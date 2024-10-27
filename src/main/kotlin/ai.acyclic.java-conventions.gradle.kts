plugins {
//    base
    java
    `java-test-fixtures`

    `project-report`
    idea

    id("com.github.ben-manes.versions")
}

val vs = versions()

allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")

    apply(plugin = "project-report")
    apply(plugin = "idea")

    group = vs.rootGroupID
    version = vs.rootV

    repositories {
        mavenLocal()
        mavenCentral()
//        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
    }

    tasks.withType<AbstractArchiveTask> {
        archiveBaseName = getModuleID(project)
    }

    java {

        val jvmTarget = vs.jvmTarget

        withSourcesJar()
        withJavadocJar()

        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }


    dependencies {

        val jUnitV = "5.11.3"

//        testImplementation("org.junit.jupiter:junit-jupiter-api:${jUnitV}")
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitV}")
//        testImplementation(platform("org.junit:junit-bom:${jUnitV}"))

//        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jUnitV}")
    }

    idea {

        targetVersion = "2023"

        module {

            excludeDirs = excludeDirs + files(

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

                "logs"
            )

            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    task("dependencyTree") {

        dependsOn("dependencies", "htmlDependencyReport")
    }

    tasks {

        htmlDependencyReport {

            reports.html.outputLocation.set(File("build/reports/dependencyTree/htmlReport"))
        }
    }
}

idea {

    module {

        excludeDirs = excludeDirs + files(
            "gradle",
        )
    }
}