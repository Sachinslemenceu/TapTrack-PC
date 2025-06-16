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

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.slemenceu.taptrackpc"
            packageVersion = "1.0.0"
        }
    }
}
tasks.withType<JavaExec>().matching { it.name == "desktopRunHot" }.configureEach {
    mainClass.set("com.slemenceu.taptrackpc.MainKt")
}
