buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.3.1'
    }
}

apply plugin: 'com.android.application'
apply from: "gradle/quality_rules.gradle"

repositories {
    jcenter()
}

dependencies {
    compile 'com.android.support:support-v4:13.0.+'
    compile 'com.actionbarsherlock:actionbarsherlock:4.4.0@aar'
    
    compile fileTree(dir: 'libs', include: '*.jar')

    androidTestCompile "com.android.support.test:runner:0.3"
    androidTestCompile 'junit:junit:4.12'
    androidTestCompile fileTree(dir: 'test/libs', include: '*.jar')
}

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.1"

    defaultConfig {
        //multiDexEnabled true 

        applicationId "${project_namespace}"
        minSdkVersion 23
        targetSdkVersion 23

        //testApplicationId "${project_namespace}.test"
        testInstrumentationRunner "com.zutubi.android.junitreport.JUnitReportTestRunner"
    }

    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-project.txt'
        }
        debug {
            testCoverageEnabled true
        }
    }

    sourceSets {
        main {
            manifest.srcFile 'AndroidManifest.xml'
            java.srcDirs = ['src']
            resources.srcDirs = ['src']
            aidl.srcDirs = ['src']
            renderscript.srcDirs = ['src']
            res.srcDirs = ['res']
            assets.srcDirs = ['assets']
        }

        androidTest {
            java.srcDirs = ['test/src']
            manifest.srcFile file('test/AndroidManifest.xml')
            resources.srcDirs = ['test/src']
            res.srcDirs = ['test/res']
            assets.srcDirs = ['test/assets']
        }
    }

    packagingOptions {
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/LICENSE.txt'
      }

    lintOptions {
          abortOnError false
      }
}