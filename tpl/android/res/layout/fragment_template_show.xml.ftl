<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign currname=curr.name?lower_case />
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
	<ScrollView 
	    android:layout_width="match_parent"
	    android:layout_height="match_parent" >
		<RelativeLayout
			android:id="@+id/${curr.name?lower_case}_data_layout"
			android:layout_width="match_parent"
			android:layout_height="match_parent"
			android:layout_margin="10dp" >
		<#list fields?values as field>
			<#if (!field.internal && !field.hidden)>
			<#assign m_id="${currname?lower_case}_${field.name?lower_case}" />
			<#assign m_id_label="${m_id}_label" />
			<#assign m_string_label="${field.owner?lower_case}_${field.name?lower_case}_label" />
			<TextView
				android:id="@+id/${m_id_label}"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				<#if (lastFieldName??)>
				android:layout_below="@+id/${currname?lower_case}_${lastFieldName?lower_case}"
				</#if>
				<#assign lastFieldName=field.name />
				android:text="@string/${m_string_label}"/>
				<#if (field.type=="boolean")>
			<CheckBox
				android:id="@+id/${m_id}"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				<#else>
			<TextView
				android:id="@+id/${m_id}"
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				android:textIsSelectable="true"
				</#if>
				android:layout_below="@+id/${m_id_label}" />
				<#assign lastField="${m_id}" />
			</#if>
		</#list>
		</RelativeLayout>
	</ScrollView>
	<TextView
	    android:id="@+id/${curr.name?lower_case}_empty"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:gravity="center"
	    android:visibility="gone"
	    android:text="No ${curr.name} to display" />
</RelativeLayout>
