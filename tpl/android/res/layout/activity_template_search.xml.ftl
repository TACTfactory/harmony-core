<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<ScrollView 
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <fragment
        android:id="@+id/fragment"
        android:name="${curr.controller_namespace}.${curr.name}SearchFragment"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:layout="@layout/fragment_${curr.name?lower_case}_search" >
    </fragment>

</ScrollView>
