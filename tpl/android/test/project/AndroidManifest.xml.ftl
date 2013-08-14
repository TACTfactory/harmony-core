<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="${project_namespace}.test"
    android:versionCode="1"
    android:versionName="1.0" >

    <uses-sdk
    	android:minSdkVersion="7"
    	android:targetSdkVersion="17" />

    <instrumentation
        android:name="com.zutubi.android.junitreport.JUnitReportTestRunner"
        android:targetPackage="${project_namespace}" />

    <application
        android:label="@string/app_name"
        android:allowBackup="true">
        <uses-library android:name="android.test.runner" />
    </application>

</manifest>
