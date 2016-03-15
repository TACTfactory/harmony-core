<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />

<Page
    x:Class="${project_namespace}.View.${curr.name?cap_first}.${curr.name?cap_first}ShowPage"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name?cap_first}"
    xmlns:usercontrol="using:${project_namespace}.View.Navigation.UsersControls"
    xmlns:localusercontrol="using:${project_namespace}.View.${curr.name?cap_first}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d">

    <ScrollViewer>
        <Grid x:Name="LayoutRoot" Background="Transparent">
            <Grid.RowDefinitions>
                <RowDefinition Height="30"/>
                <RowDefinition Height="*"/>
            </Grid.RowDefinitions>
            <usercontrol:ShowBrowser x:Name="show_broswer" Grid.Row="0"/>
            <localusercontrol:${curr.name?cap_first}ShowUserControl x:Name="${curr.name?lower_case}_show_usercontrol" Grid.Row="1"/>
        </Grid>
    </ScrollViewer>
</Page>
