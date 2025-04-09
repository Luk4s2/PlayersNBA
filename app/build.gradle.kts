import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.hilt)
	kotlin("kapt")
}


android {
	namespace = "eu.playersnba"
	compileSdk = 35

	defaultConfig {
		applicationId = "eu.playersnba"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		val localProperties = Properties().apply {
			load(rootProject.file("local.properties").inputStream())
		}
		val apiKey = localProperties.getProperty("NBA_API_KEY") ?: ""
		buildConfigField("String", "NBA_API_KEY", "\"$apiKey\"")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
	kapt {
		correctErrorTypes = true
	}
	hilt {
		enableAggregatingTask = false
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	implementation (libs.androidx.constraintlayout.compose)

	// Paging 3
	implementation(libs.androidx.paging.runtime)
	implementation(libs.androidx.paging.compose)
	implementation(libs.androidx.paging.compose.v330alpha01)

	// Hilt
	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)
	implementation(libs.androidx.hilt.navigation.compose)

	// Retrofit
	implementation(libs.retrofit)
	implementation(libs.converter.gson)

	// Core Glide
	implementation(libs.glide)
	kapt(libs.compiler)

	// Lifecycle & Compose
	implementation(libs.androidx.lifecycle.runtime.ktx.v262)
	implementation(libs.androidx.lifecycle.viewmodel.compose)

	// Glide for Jetpack Compose (still experimental)
	implementation(libs.compose)
	implementation(libs.compose.v100beta01)

	implementation (libs.accompanist.placeholder.material3)

	// JUnit
	testImplementation (libs.junit)

	// Coroutine testing
	testImplementation (libs.kotlinx.coroutines.test.v173)

	// Mockito + Kotlin
	testImplementation (libs.mockito.kotlin)

	// Turbine
	testImplementation (libs.turbine)
}