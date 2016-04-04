<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign need_enum = false/>
<#list fields?values as field>
    <#if (!field.internal && !field.hidden && field.harmony_type?lower_case == "enum") >
        <#assign need_enum = true/>
    </#if>
</#list>
<!-- <@header?interpret /> -->

<UserControl
    x:Class="${project_namespace}.View.${curr.name?cap_first}.UsersControls.${curr.name?cap_first}CreateUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name?cap_first}.UsersControls"
<#if need_enum>
    xmlns:util="using:${project_namespace}.Harmony.Util"
</#if>
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

<#if need_enum>
    <UserControl.Resources>
        <util:EnumTypeToListConverter x:Key="enumConverter"/>
    </UserControl.Resources>
</#if>

    <ScrollViewer>
        <StackPanel x:Name="root_stackpanel">
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                        <#if (field.harmony_type?lower_case == "boolean")>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <CheckBox x:Name="checkbox_${field.name}"/>
                        <#elseif (field.harmony_type?lower_case == "enum")>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <ComboBox x:Name="combobox_${field.name}" HorizontalAlignment="Stretch" ItemsSource="{Binding ${field.name?cap_first}s, Converter={StaticResource enumConverter}, Mode=OneTime}" SelectedItem="{Binding ${field.name?cap_first}, Mode=TwoWay}">
                <ComboBox.ItemTemplate>
                    <DataTemplate>
                        <Grid>
                            <TextBlock Text="{Binding }" FontSize="14"/>
                        </Grid>
                    </DataTemplate>
                </ComboBox.ItemTemplate>
            </ComboBox>
                        <#elseif (field.harmony_type?lower_case == "date" || field.harmony_type?lower_case == "datetime")>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <CalendarDatePicker x:Name="calendar_${field.name}"/>
                        <#else>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name?cap_first} : ${field.name}"/>
            <TextBox x:Name="text_box_${field.name}"/>
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
                <Button x:Name="btn_add_${field.relation.targetEntity?lower_case}" Content="Add ${field.relation.targetEntity?cap_first}" HorizontalAlignment="Stretch" Tapped="btn_add_${field.relation.targetEntity?lower_case}_Tapped"/>
                    </#if>
                </#if>
            </#list>
                <Button x:Name="btn_validate" Content="Validate" HorizontalAlignment="Stretch" Tapped="btn_validate_Tapped"/>
            </StackPanel>
        </StackPanel>
    </ScrollViewer>
</UserControl>
