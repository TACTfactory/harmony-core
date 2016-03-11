<#assign curr = entities[current_entity] />
<@header?interpret />

<UserControl
    x:Class="${project_namespace}.View.${curr.name}.UsersControls.${curr.name}CreateUserControl"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View.${curr.name}.UsersControls"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d"
    d:DesignHeight="300"
    d:DesignWidth="400">

    <ScrollViewer>
        <StackPanel x:Name="root_stackpanel">
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                        <#if (field.harmony_type?lower_case == "boolean")>
             <TextBlock x:Name="text_block_${field.name}" Text="${curr.name} : ${field.name}"/>
             <CheckBox x:Name="checkbox_${field.name}"/>
                        <#else>
            <TextBlock x:Name="text_block_${field.name}" Text="${curr.name} : ${field.name}"/>
            <TextBox x:Name="text_box_${field.name}"/>
                        </#if>
                    </#if>
                </#if>
            </#list>

            <StackPanel x:Name="stackpanel_btn">
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if (field.relation.type=="OneToMany" && field.relation.type=="ManyToMany")>
                <Button x:Name="btn_add_${field.relation.targetEntity}" Content="List related ${field.relation.targetEntity?cap_first}" HorizontalAlignment="Stretch" Tapped="btn_list_${field.relation.targetEntity}_Tapped"/>
                    <#else>
                <Button x:Name="btn_add_${field.relation.targetEntity}" Content="Add ${field.relation.targetEntity?cap_first}" HorizontalAlignment="Stretch" Tapped="btn_add_${field.relation.targetEntity}_Tapped"/>
                    </#if>
                </#if>
            </#list>
                <Button x:Name="btn_validate" Content="Validate" HorizontalAlignment="Stretch" Tapped="btn_validate_Tapped"/>
            </StackPanel>
        </StackPanel>
    </ScrollViewer>
</UserControl>



