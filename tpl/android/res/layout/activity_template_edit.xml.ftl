<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content">
        
        <include layout="@layout/toolbar" />
            
        <fragment
            android:id="@+id/fragment"
            android:name="${curr.controller_namespace}.${curr.name}EditFragment"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            tools:layout="@layout/fragment_${curr.name?lower_case}_edit" >
        </fragment>
        
    </LinearLayout>

</ScrollView>
