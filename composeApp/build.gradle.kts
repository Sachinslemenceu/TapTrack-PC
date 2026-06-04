import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm("desktop") {
        compilations["main"].defaultSourceSet {
            resources.srcDir("src/desktopMain/composeResources")
        }
    }
    jvmToolchain(21)
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("com.google.zxing:core:3.5.2")
            api(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.9.2") // or mac/linux depending on platform
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.9.0")
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.slemenceu.taptrackpc.MainKt"
        
        jvmArgs += listOf(
            "-Xmx1024m",
            "-XX:+UseG1GC"
        )

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Exe)
            packageName = "TapTrack PC"
            packageVersion = "1.0.0"
            description = "QR Code Tap Track PC Application"
            copyright = "2026 TapTrack. All rights reserved."
            vendor = "TapTrack"
            
            // Ensure JVM is bundled
            includeAllModules = true
            modules(
                "java.base",
                "java.desktop",
                "java.logging",
                "java.xml",
                "java.net.http",
                "jdk.unsupported"
            )
            
            // Windows-specific configuration
            windows {
                // Create menu shortcuts
                menu = true
                menuGroup = "TapTrack"
                // Create desktop shortcut
                shortcut = true
                // Upgrade code for Windows Installer (enables upgrades)
                upgradeUuid = "4ff14216-79ad-4efc-a321-d5d63c572aff"
                dirChooser = true
                perUserInstall = false
            }
        }
    }
}
tasks.withType<JavaExec>().matching { it.name == "desktopRunHot" }.configureEach {
    mainClass.set("com.slemenceu.taptrackpc.MainKt")
}
