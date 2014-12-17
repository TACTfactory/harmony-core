<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@android:color/white"
    android:baselineAligned="false"
    tools:ignore="InconsistentLayout" >

    <include layout="@layout/toolbar" />
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:baselineAligned="false"
        android:orientation="horizontal">

        <fragment
            android:id="@+id/fragment_list"
            android:name="${curr.controller_namespace}.${curr.name}ListFragment"
            android:layout_width="0dp"
            android:layout_weight="33"
            android:layout_height="match_parent"
            tools:layout="@layout/fragment_${curr.name?lower_case}_list" >
        </fragment>
        
        <#if curr.showAction>
        <fragment
            android:id="@+id/fragment_show"
            android:name="${curr.controller_namespace}.${curr.name}ShowFragment"
            android:layout_width="0dp"
            android:layout_weight="67"
            android:layout_height="match_parent"
            tools:layout="@layout/fragment_${curr.name?lower_case}_show" />
        
        </#if>
    </LinearLayout>
</LinearLayout>
