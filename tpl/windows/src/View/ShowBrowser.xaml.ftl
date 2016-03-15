<@header?interpret />

<UserControl
    x:Class="${project_namespace}.View.Navigation.UsersControls.ShowBrowser"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.Navigation.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="40"
    d:DesignWidth="400">

    <StackPanel HorizontalAlignment="Stretch" VerticalAlignment="Stretch" Orientation="Horizontal">
        <Button x:Name="btn_back" Content="Back" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
        <Button x:Name="btn_edit" Content="Edit" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
        <Button x:Name="btn_delete" Content="Delete" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
    </StackPanel>
</UserControl>
