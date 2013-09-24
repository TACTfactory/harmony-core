<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
	android:orientation="vertical"
	android:layout_width="fill_parent"
	android:layout_height="fill_parent" >
	<RelativeLayout
	    android:id="@+id/${curr.name?lower_case}ListContainer"
		android:layout_width="fill_parent"
	    android:layout_height="fill_parent" >
		<TextView
		    android:id="@android:id/empty"
		    android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:padding="10dp"
			android:gravity="center"
			android:text="@string/${curr.name?lower_case}_empty_list" />
	    <${project_namespace}.harmony.widget.pinnedheader.headerlist.PinnedHeaderListView
			android:id="@android:id/list"
			android:isScrollContainer="true"
			android:scrollbars="vertical"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:layout_marginBottom="8dp"
			android:paddingBottom="8dp"
			android:clickable="true"
			android:drawSelectorOnTop="false"
			android:fadingEdge="none"
			android:divider="@android:color/transparent" />
	</RelativeLayout>

	<LinearLayout
	    android:id="@+id/${curr.name?lower_case}ProgressLayout"
	    android:layout_width="fill_parent"
		android:layout_height="fill_parent"
		android:orientation="vertical"
		android:visibility="gone"
		android:gravity="center" >
	    <ProgressBar
	        android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			style="?android:attr/progressBarStyleLarge" />
	</LinearLayout>
</LinearLayout>
