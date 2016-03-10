<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<Page
    x:Class="${curr.controller_namespace}.${curr.name}ListPage"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${curr.controller_namespace}"
    xmlns:usercontrol="using:${project_namespace}.View.Navigation.UsersControls"
    xmlns:localusercontrol="using:${curr.controller_namespace}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d">

    <!--LayoutRoot is the root grid where all page content is placed-->
    <ScrollViewer>
        <Grid x:Name="LayoutRoot" Background="Transparent">
            <Grid.RowDefinitions>
                <RowDefinition Height="30"/>
                <RowDefinition Height="*"/>
            </Grid.RowDefinitions>
            <usercontrol:NavigationBrowser x:Name="navigation_broswer" Grid.Row="0"/>
            <localusercontrol:${curr.name}ListUserControl x:Name="${curr.name}_items_list" Grid.Row="1"/>
        </Grid>
    </ScrollViewer>
</Page>