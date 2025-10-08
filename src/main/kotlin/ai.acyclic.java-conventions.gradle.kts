plugins {
    id("ai.acyclic.ide-mixin")

    java
    `java-test-fixtures`

    `project-report`
}

val vs = versions()

allprojects {

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")


    java {
        val jvmTarget = vs.jvmTarget

        withSourcesJar()
        withJavadocJar()

        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }

    tasks.withType<Test> {

        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showStackTraces = true
            showCauses = true
            showExceptions = true
            showStandardStreams = true
        }

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (result.exception != null) {
                    println("\n=== Test Suite Failed: ${suite.name} ===")
                    result.exception?.printStackTrace()
                }
            }

            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                if (result.exception != null) {
                    println("\n=== Test Failed: ${testDescriptor.className}.${testDescriptor.name} ===")
                    result.exception?.printStackTrace()
                }
            }
        })
    }
}
