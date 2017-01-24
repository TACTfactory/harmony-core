buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.3.1'
    }
}

apply plugin: 'com.android.application'
apply from: "gradle_script/quality_rules.gradle"

repositories {
    jcenter()
}

dependencies {
    //Configure appcompat version to match your compileSdkVersion
    //Support V4 is embeded in appcompat-v7
    compile 'com.android.support:appcompat-v7:22.2.0'

    compile fileTree(dir: 'libs', include: '*.jar', exclude:'android-support-v4.jar')

    androidTestCompile "com.android.support.test:runner:0.3"
    androidTestCompile 'junit:junit:4.12'
    androidTestCompile fileTree(dir: 'test/libs', include: '*.jar')
}

android {
    compileSdkVersion 21
    buildToolsVersion "23.0.1"

    defaultConfig {
        //multiDexEnabled true

        applicationId "${project_namespace}"
        minSdkVersion 8
        targetSdkVersion 21

        //Default test project is set with projectName.test
        //If you defined another test project set it here
        //testApplicationId "${project_namespace}.test"
        testInstrumentationRunner "com.zutubi.android.junitreport.JUnitReportTestRunner"
    }

    signingConfigs {
        release {
            if(new File("$System.env.KEYSTORE_PATH" + "/" + project.name + ".properties").exists()) {
                def props = new Properties()

                props.load(new FileInputStream("$System.env.KEYSTORE_PATH" + "/" + project.name + ".properties"))

                storeFile rootProject.file(props.keyStore)
                storePassword props.keyStorePassword
                keyAlias props.keyAlias
                keyPassword props.keyAliasPassword
            }
        }
    }

    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-project.txt'
            signingConfig signingConfigs.release
        }
        debug {
            testCoverageEnabled true
        }
    }

    //SourceSets make correct setup path to use gradle with eclipse architecture
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

    //Exclude file when using Dex to setup one package for build
    //We need to exclude remanente files from all embeded .jar
    packagingOptions {
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/LICENSE.txt'
      }

    //Because of some compatibilities issues between lint and eclipse projects
    //we need to continue build process even if lint report issues
    lintOptions {
          abortOnError false
      }
}

tasks.all {
    // Disable application signing if no configurations found
    task -> if (task.name.equals('packageRelease')
            && !new File("$System.env.KEYSTORE_PATH" + "/" + project.name + ".properties").exists()) {
        task.enabled = false
    }
}