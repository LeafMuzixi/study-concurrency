plugins {
    java
    kotlin("jvm") version "1.3.72"
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    repositories {
        maven("http://maven.aliyun.com/nexus/content/groups/public/")
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.gradle.java")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        testImplementation("org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}")

        implementation("com.google.guava:guava:${Version.guava}")
    }


    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}

tasks.wrapper {
    gradleVersion = "6.4"
    distributionType = Wrapper.DistributionType.ALL
}