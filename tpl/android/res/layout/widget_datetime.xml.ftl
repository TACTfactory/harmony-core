<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:animateLayoutChanges="false"
    android:orientation="horizontal">

	<EditText
	        android:id="@+id/date"
	        android:layout_height="wrap_content"
	        android:layout_width="0dp"
	        android:layout_weight="7"
	        android:focusable="false"
	        android:singleLine="true"
	        style="@style/TextAppearance.Edit_Spinner" />

	<EditText
	        android:id="@+id/time"
	        android:layout_height="wrap_content"
	        android:layout_width="0dp"
	        android:layout_weight="4"
	        android:focusable="false"
	        android:singleLine="true"
	        style="@style/TextAppearance.Edit_Spinner" />
</LinearLayout>
