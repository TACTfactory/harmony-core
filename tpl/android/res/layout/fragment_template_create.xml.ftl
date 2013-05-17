<#assign curr = entities[current_entity] />
<#assign currname=curr.name?lower_case />
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout 
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >
<#list curr.fields?values as field>
	<#if (!field.internal && !field.hidden)>
	<#assign m_id="${currname?lower_case}_${field.name?lower_case}" />
	<#assign m_id_label="${m_id}_label" />
		<#if (!field.relation??)>
    <TextView 
    	android:id="@+id/${m_id_label}"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
		    	<#if (lastField??)>
	    android:layout_below="@+id/${lastField}"
		    	</#if>
	    android:text="@string/${m_id_label}"/>
			<#if (field.type?lower_case=="boolean")>
    <CheckBox
	    android:id="@+id/${m_id}"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:layout_below="@+id/${m_id_label}" />
	    		<#elseif (field.type?lower_case == "datetime")>
	<LinearLayout
        android:id="@+id/${m_id}"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/${m_id_label}"
        android:animateLayoutChanges="false"
        android:orientation="horizontal" >
			<#if (field.harmony_type == "datetime" || field.harmony_type == "date")>
        <EditText
            android:id="@+id/${m_id}_date"
            android:layout_height="wrap_content"
            			<#if (field.harmony_type == "datetime")>
            android:layout_width="0px"
            android:paddingRight="4dp"
            android:layout_weight="7"
            			<#else>
            android:layout_width="match_parent"	
            			</#if>
            android:focusable="false"
            android:singleLine="true"
            style="@style/TextAppearance.Edit_Spinner" />
			</#if>
			<#if (field.harmony_type == "datetime" || field.harmony_type == "time")>
        <EditText
            android:id="@+id/${m_id}_time"
            android:layout_height="wrap_content"
            			<#if (field.harmony_type == "datetime")>
            android:layout_width="0px"
            android:layout_weight="4"
            			<#else>
            android:layout_width="match_parent"	
            			</#if>
            android:focusable="false"
            style="@style/TextAppearance.Edit_Spinner" />
            		</#if>
    </LinearLayout>
		<#else>
    <EditText
		android:id="@+id/${m_id}"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
			<#if (field.harmony_type??)>
			    	<#if (field.harmony_type == "text")>
	    android:inputType="textCapWords|textAutoComplete|textMultiLine"
			    	<#elseif (field.harmony_type=="password")>
	    android:inputType="textPassword"
			    	<#elseif (field.harmony_type=="email")>
	    android:inputType="textEmailAddress"
			    	<#elseif (field.harmony_type=="phone")>
	    android:inputType="phone"
			    	<#elseif (field.harmony_type=="zipcode")>
	    android:inputType="textPostalAddress"
			    	<#elseif (field.harmony_type=="integer" || field.harmony_type=="int" || field.harmony_type=="ean")>
	    android:inputType="number"
				<#elseif (field.harmony_type=="float")>
	    android:inputType="numberDecimal"
				<#elseif (field.harmony_type=="long")>
	    android:inputType="number"
				<#elseif (field.harmony_type=="double")>
	    android:inputType="numberDecimal"
				<#elseif (field.harmony_type=="short")>
	    android:inputType="number"
				<#elseif (field.harmony_type=="char")>
	    android:inputType="text"
				<#elseif (field.harmony_type=="byte")>
	    android:inputType="number"
				<#else>
	    android:inputType="text"
				</#if>
			<#else>
	    android:inputType="text"
			</#if>
		android:layout_below="@+id/${m_id_label}" />
		</#if>
		<#assign lastField="${m_id}" />
		<#else>
    <TextView 
    	android:id="@+id/${m_id_label}"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
		    	<#if (lastField??)>
	    android:layout_below="@+id/${lastField}"
		    	</#if>
	    android:text="@string/${m_id_label}"/>
    <Button
        android:id="@+id/${m_id}_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
	    android:layout_below="@+id/${m_id_label}" />
			<#assign lastField="${m_id}_button" />
		</#if>
	</#if>
</#list>

	<Button
    	android:id="@+id/${currname?lower_case}_btn_save"
    	android:layout_width="match_parent"
    	android:layout_height="wrap_content"
    	<#if (curr.relations?size!=0)>
    	android:layout_below="@+id/${lastField}"
    	<#else>
    	android:layout_below="@+id/${currname?lower_case}_${curr.fields?last.name?lower_case}"
    	</#if>
    	android:text="@string/common_create" />
</RelativeLayout>
