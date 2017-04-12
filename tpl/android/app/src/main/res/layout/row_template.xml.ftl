<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingBottom="2.5dp"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    android:paddingTop="2.5dp"
    android:background="@drawable/list_item_activated_background" >
    <#list fields?values as field>
        <#assign m_id="${curr.name?lower_case}_${field.name?lower_case}" />
        <#assign m_id_label="${m_id}_label" />
        <#if (!field.internal && !field.hidden)>
            <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                <#if (field.harmony_type?lower_case=="boolean")>
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
                    <#if (field.harmony_type?lower_case == "password")>
            android:inputType="textPassword"
            android:focusable="false"
            android:focusableInTouchMode="false"
                    </#if>
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

</RelativeLayout>
