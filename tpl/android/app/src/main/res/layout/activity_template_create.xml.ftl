<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <include layout="@layout/toolbar" />
    
    <fragment
        android:id="@+id/fragment"
        android:name="${curr.controller_namespace}.${curr.name}CreateFragment"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:layout="@layout/fragment_${curr.name?lower_case}_create" >
    </fragment>
    
</LinearLayout>
