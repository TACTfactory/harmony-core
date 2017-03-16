apply plugin: 'com.android.application'
//apply from: "gradle_script/quality_rules.gradle"

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"

    defaultConfig {
        applicationId "${project_namespace}"

        minSdkVersion 19
        targetSdkVersion 25

        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }

    lintOptions {
        abortOnError false
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

//    //Exclude file when using Dex to setup one package for build
//    //We need to exclude remanente files from all embeded .jar
//    packagingOptions {
//        exclude 'META-INF/NOTICE.txt'
//        exclude 'META-INF/LICENSE.txt'
//    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    compile 'com.android.support:appcompat-v7:25.3.0'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'

    compile 'joda-time:joda-time:2.9.7'
    compile 'com.google.guava:guava:21.0'
    compile 'com.google.code.findbugs:jsr305:2.0.1'
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'

    androidTestCompile fileTree(dir: 'test/libs', include: '*.jar')

     androidTestCompile 'com.android.support:support-annotations:25.3.0'
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    androidTestCompile "com.android.support.test:runner:0.5"
    androidTestCompile 'junit:junit:4.12'

    testCompile 'junit:junit:4.12'
}

tasks.all {
    // Disable application signing if no configurations found
    task -> if (task.name.equals('packageRelease')
            && !new File("$System.env.KEYSTORE_PATH" + "/" + project.name + ".properties").exists()) {
        task.enabled = false
    }
}

