<@header?interpret />

<UserControl
    x:Class="${project_namespace}.View.Navigation.UsersControls.NavigationBrowser"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.Navigation.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="40"
    d:DesignWidth="400">

    <StackPanel x:Name="stackpanel_btn" HorizontalAlignment="Stretch" VerticalAlignment="Stretch" Orientation="Horizontal">
        <Button x:Name="btn_back" Content="Back" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
        <Button x:Name="btn_new" Content="Create New" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
        <Button x:Name="btn_existing" Content="Use Existing" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
        <Button x:Name="btn_erase_all" Content="Erase All" HorizontalAlignment="Stretch" VerticalAlignment="Stretch"/>
    </StackPanel>
</UserControl>
