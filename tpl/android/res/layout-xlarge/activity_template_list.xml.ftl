<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
  	android:background="@android:color/white" >

    <fragment
        android:id="@+id/fragment_list"
        android:name="${curr.controller_namespace}.${curr.name}ListFragment"
		android:layout_width="0dp"
		android:layout_weight="33"
        android:layout_height="match_parent"
        tools:layout="@layout/fragment_${curr.name?lower_case}_list" >
    </fragment>

	<fragment
        android:id="@+id/fragment_show"
        android:name="${curr.controller_namespace}.${curr.name}ShowFragment"
        android:layout_width="0dp"
		android:layout_weight="67"
        android:layout_height="match_parent"
		android:layout_toRightOf="@id/fragment_list"/>

</LinearLayout>
