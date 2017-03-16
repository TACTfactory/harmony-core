<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <include layout="@layout/toolbar" />
    
    <fragment
        android:id="@+id/fragment"
        android:name="${project_namespace}.view.${viewPackage}.${viewName}Fragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:layout="@layout/fragment_${viewName?lower_case}" >
    </fragment>

</FrameLayout>
