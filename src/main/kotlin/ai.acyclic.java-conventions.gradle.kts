plugins {
    base

    java
    `java-test-fixtures`

//    kotlin("jvm")

    `project-report`

    idea
    eclipse

    id("com.github.ben-manes.versions")
}

val vs = versions()

allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")

    apply(plugin = "project-report")
    apply(plugin = "idea")
    apply(plugin = "eclipse")

    group = vs.rootGroupID
    version = vs.rootV

    repositories {
        mavenLocal()
        mavenCentral()

//        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")

        // for Scala formless
        maven {
            name = "bondlink-maven-repo"
            url = uri("https://raw.githubusercontent.com/mblink/maven-repo/main")
        }
    }

    tasks.withType<AbstractArchiveTask> {
        archiveBaseName.set(getModuleID(project))
    }

    java {

        val jvmTarget = vs.jvmTarget

        withSourcesJar()
        withJavadocJar()

        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }

    dependencies {

        val jUnitV = "5.11.4"

//        testImplementation("org.junit.jupiter:junit-jupiter-api:${jUnitV}")
        testImplementation("org.junit.jupiter:junit-jupiter:${jUnitV}")
//        testImplementation(platform("org.junit:junit-bom:${jUnitV}"))

//        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jUnitV}")
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

            excludeDirs = excludeDirs + files(
                "gradle",
            )

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
