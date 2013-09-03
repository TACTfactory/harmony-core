buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:0.5.+'
        classpath files(['libs'])
    }
}

apply plugin: 'android'

repositories {
    mavenCentral()
}

dependencies {
    compile 'com.android.support:support-v4:13.0.+'
    compile 'com.actionbarsherlock:actionbarsherlock:4.4.0@aar'
    compile fileTree(dir: 'libs', include: '*.jar')
}

android {
    compileSdkVersion 17
    buildToolsVersion "17.0.0" 

    defaultConfig {
        minSdkVersion 14
        targetSdkVersion 17
        testPackageName "${project_namespace}" 
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

        instrumentTest.setRoot('test')
    }
}
