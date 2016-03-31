<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<!-- <@header?interpret /> -->

<UserControl
    x:Class="${project_namespace}.View.${curr.name?cap_first}.UsersControls.${curr.name?cap_first}ListUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name?cap_first}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

    <ListView x:Name="itemsList" Margin="0" ItemClick="itemsList_ItemClick" IsItemClickEnabled="True">
        <ListView.ItemTemplate>
            <DataTemplate>
                <StackPanel x:Name="ContentPanel">
                    <#list fields?values as field>
                        <#if (!field.internal && !field.hidden)>
                            <#if (!field.relation??)>
                                <#if (field.harmony_type?lower_case == "boolean")>
                    <CheckBox IsChecked="{Binding ${field.name?cap_first}}"/>
                                <#else>
                    <TextBlock Text="{Binding ${field.name?cap_first}}"/>
                                </#if>
                            </#if>
                        </#if>
                    </#list>
                </StackPanel>
            </DataTemplate>
        </ListView.ItemTemplate>
    </ListView>
</UserControl>
