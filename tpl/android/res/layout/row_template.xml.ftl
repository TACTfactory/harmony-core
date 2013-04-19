<#assign curr = entities[current_entity] />
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingBottom="2.5dp"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    android:paddingTop="2.5dp" >
	<#list curr.fields as field>
		<#assign m_id="${curr.name?lower_case}_${field.name?lower_case}" />
		<#assign m_id_label="${m_id}_label" />
		<#if (!field.internal && !field.hidden)>	
			<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
				<#if (field.type=="boolean")>
	    <CheckBox
		android:id="@+id/row_${m_id}"
	    	android:layout_width="match_parent"
	    	android:layout_height="wrap_content"
		android:focusable="false"
		android:enabled="false"
				<#else>
	    <TextView
		android:id="@+id/row_${m_id}"
	    	android:layout_width="match_parent"
	    	android:layout_height="wrap_content"
				</#if>
				<#if (!lastFieldName??)>
	        android:layout_alignParentTop="true" />
				<#else>
		android:layout_below="@+id/row_${curr.name?lower_case}_${lastFieldName}" />
				</#if>
				<#assign lastFieldName=field.name?lower_case />
			</#if>
		</#if>
	</#list>
	<Button
		android:id="@+id/row_${curr.name?lower_case}_delete_btn"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:layout_alignParentRight="true"
		android:layout_centerVertical="true"
		android:text="Supprimer" />
	<Button
		android:id="@+id/row_${curr.name?lower_case}_edit_btn"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:layout_toLeftOf="@id/row_${curr.name?lower_case}_delete_btn"
		android:layout_centerVertical="true"
		android:text="Editer" />

</RelativeLayout>
