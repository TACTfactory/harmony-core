<#assign curr = entities[current_entity] />

<Page
    x:Class="${project_namespace}.View.${curr.name}.${curr.name}CreatePage"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name}"
    xmlns:usercontrol="using:${project_namespace}.View.Navigation.UsersControls"
    xmlns:localusercontrol="using:${project_namespace}.View.${curr.name}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d">

    <ScrollViewer>
        <!--LayoutRoot is the root grid where all page content is placed-->
        <Grid x:Name="LayoutRoot" Background="Transparent">
            <Grid.RowDefinitions>
                <RowDefinition Height="30"/>
                <RowDefinition Height="*"/>
            </Grid.RowDefinitions>
            <usercontrol:BackBrowser x:Name="back_broswer" Grid.Row="0"/>
            <localusercontrol:${curr.name}CreateUserControl x:Name="${curr.name?lower_case}_create_usercontrol" Grid.Row="1"/>
        </Grid>
    </ScrollViewer>
</Page>