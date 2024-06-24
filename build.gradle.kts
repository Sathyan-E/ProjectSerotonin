import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
val sourceCompatibility by extra(VERSION_11)
val targetCompatibility by extra(VERSION_11)
