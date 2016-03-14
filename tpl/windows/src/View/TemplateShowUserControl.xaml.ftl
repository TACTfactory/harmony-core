<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

<UserControl
    x:Class="com.tactfactory.demact.View.JockeyView.UsersControls.JockeyDetailUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:com.tactfactory.demact.View.JockeyView.UsersControls"
    xmlns:entity="using:com.tactfactory.demact.Entity"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

    <ScrollViewer>
        <StackPanel x:Name="root_stackpanel">
            <TextBlock x:Name="text_block_name" Text="Jockey : name"/>
            <TextBlock x:Name="text_box_name" Text="{Binding JockeyItem.Name}"/>
            <TextBlock x:Name="text_block_surname" Text="Jockey : surname"/>
            <TextBlock x:Name="text_box_surname" Text="{Binding JockeyItem.Surname}"/>
            <StackPanel x:Name="stackpanel_btn">
                <Button x:Name="btn_show_poney" Content="Show Poneys" HorizontalAlignment="Stretch" Tapped="btn_show_poney_Tapped"/>
            </StackPanel>
        </StackPanel>
    </ScrollViewer>
</UserControl>
