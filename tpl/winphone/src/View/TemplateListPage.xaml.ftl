<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<phone:PhoneApplicationPage
    x:Class="${project_namespace}.View.${curr.name}.${curr.name}ListPage"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:phone="clr-namespace:Microsoft.Phone.Controls;assembly=Microsoft.Phone"
    xmlns:shell="clr-namespace:Microsoft.Phone.Shell;assembly=Microsoft.Phone"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    FontFamily="{StaticResource PhoneFontFamilyNormal}"
    FontSize="{StaticResource PhoneFontSizeNormal}"
    Foreground="{StaticResource PhoneForegroundBrush}"
    SupportedOrientations="Portrait" Orientation="Portrait"
    mc:Ignorable="d">
    
    <!--LayoutRoot is the root grid where all page content is placed-->
    <Grid x:Name="LayoutRoot" Background="Transparent">
        <phone:LongListSelector Name="itemsList" Margin="0">
            <phone:LongListSelector.ItemTemplate>
                <DataTemplate>
                        <StackPanel x:Name="ContentPanel">
                        <#list fields?values as field>
                            <#assign m_id="${curr.name?lower_case}_${field.name?lower_case}" />
                            <#assign m_id_label="${m_id}_label" />
                            <#if (!field.internal && !field.hidden)>
                                <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
                                    <#if (field.harmony_type?lower_case=="boolean")>
                        <TextBlock Text="{Binding ${field.name?cap_first}}"/>
                                    <#else>
                        <TextBlock Text="{Binding ${field.name?cap_first}}"/>
                                        <#if (field.harmony_type?lower_case == "password")>
                        
                                        </#if>
                                    </#if>
                                    <#if (!lastFieldName??)>
                                    
                                    <#else>
                        
                                    </#if>
                                    <#assign lastFieldName=field.name?lower_case />
                                </#if>
                            </#if>
                        </#list>
                    </StackPanel>
                </DataTemplate>
            </phone:LongListSelector.ItemTemplate>
        </phone:LongListSelector>
    </Grid>
    
</phone:PhoneApplicationPage>