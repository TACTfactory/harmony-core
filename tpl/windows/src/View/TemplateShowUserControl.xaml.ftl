<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

<UserControl
    x:Class="${project_namespace}.View.${curr.name?cap_first}.UsersControls.${curr.name?cap_first}ShowUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name?cap_first}.UsersControls"
    xmlns:entity="using:${project_namespace}.Entity"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

    <ScrollViewer>
        <StackPanel x:Name="root_stackpanel">
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                        <#if (field.harmony_type?lower_case == "boolean")>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <CheckBox x:Name="checkbox_${field.name}" IsChecked="{Binding ${curr.name?cap_first}Item.${field.name?cap_first}}"/>
                        <#else>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <TextBox x:Name="text_box_${field.name}" Text="{Binding ${curr.name?cap_first}Item.${field.name?cap_first}}"/>
                        </#if>
                    </#if>
                </#if>
            </#list>
            <StackPanel x:Name="stackpanel_btn">
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden && field.relation??)>
                    <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
                <Button x:Name="btn_list_related_${field.relation.targetEntity?lower_case}" Content="List related ${field.relation.targetEntity?cap_first}" HorizontalAlignment="Stretch" Tapped="btn_list_related_${field.relation.targetEntity?lower_case}_Tapped"/>
                    <#else>
                <Button x:Name="btn_show_${field.relation.targetEntity?lower_case}" Content="Show ${field.relation.targetEntity?cap_first}" HorizontalAlignment="Stretch" Tapped="btn_show_${field.relation.targetEntity?lower_case}_Tapped"/>
                    </#if>
                </#if>
            </#list>
            </StackPanel>
        </StackPanel>
    </ScrollViewer>
</UserControl>
