<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<!-- <@header?interpret /> -->

<UserControl
    x:Class="${project_namespace}.View.${curr.name?cap_first}.UsersControls.${curr.name?cap_first}CheckListUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name?cap_first}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

    <Grid>
        <Grid.RowDefinitions>
            <RowDefinition Height="*"/>
            <RowDefinition Height="40"/>
            <RowDefinition Height="*"/>
        </Grid.RowDefinitions>
        <ListView x:Name="itemsList" Margin="0" IsItemClickEnabled="True" Grid.Row="0" ItemClick="itemsList_ItemClick">
            <ListView.ItemTemplate>
                <DataTemplate>
                    <Grid>
                        <Grid.ColumnDefinitions>
                            <ColumnDefinition Width="20"/>
                            <ColumnDefinition Width="20"/>
                            <ColumnDefinition Width="*"/>
                        </Grid.ColumnDefinitions>
                        <CheckBox IsChecked="{Binding Check, Mode=TwoWay}" Grid.Column="0"/>
                        <StackPanel x:Name="ContentPanel" Grid.Column="2">
                            <#list fields?values as field>
                                <#if (!field.internal && !field.hidden)>
                                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                        <#if (field.harmony_type?lower_case == "boolean")>
                            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name} : ${field.name}"/>
                            <CheckBox x:Name="checkbox_${field.name}" IsChecked="{Binding ${curr.name?cap_first}.${field.name?cap_first}}"/>
                                        <#else>
                            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name} : ${field.name}"/>
                            <TextBlock x:Name="text_box_${field.name}" Text="{Binding ${curr.name?cap_first}.${field.name?cap_first}}"/>
                                        </#if>
                                    </#if>
                                </#if>
                            </#list>
                        </StackPanel>
                    </Grid>
                </DataTemplate>
            </ListView.ItemTemplate>
        </ListView>
        <Button x:Name="btn_validate" Content="Validate" HorizontalAlignment="Center" Grid.Row="1" Tapped="btn_validate_Tapped"/>
    </Grid>
</UserControl>
