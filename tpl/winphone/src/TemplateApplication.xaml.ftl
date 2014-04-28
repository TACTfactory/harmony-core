<local:${project_name?cap_first}ApplicationBase
    x:Class="${project_namespace}.${project_name?cap_first}Application"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:shell="clr-namespace:Microsoft.Phone.Shell;assembly=Microsoft.Phone"
    xmlns:local="clr-namespace:${project_namespace}">

    <!--Application Resources-->
    <Application.Resources>
        <local:LocalizedStrings xmlns:local="clr-namespace:${project_namespace}.Resources" x:Key="LocalizedStrings"/>
        <Style x:Key="ButtonStyle1" TargetType="Button">
            <Setter Property="Background" Value="Transparent"/>
            <Setter Property="BorderBrush" Value="{StaticResource PhoneForegroundBrush}"/>
            <Setter Property="Foreground" Value="{StaticResource PhoneForegroundBrush}"/>
            <Setter Property="BorderThickness" Value="{StaticResource PhoneBorderThickness}"/>
            <Setter Property="FontFamily" Value="{StaticResource PhoneFontFamilySemiBold}"/>
            <Setter Property="FontSize" Value="{StaticResource PhoneFontSizeMedium}"/>
            <Setter Property="Padding" Value="10,5,10,6"/>
            <Setter Property="Template">
                <Setter.Value>
                    <ControlTemplate TargetType="Button">
                        <Grid Background="Transparent" Width="67" Height="67">
                            <VisualStateManager.VisualStateGroups>
                                <VisualStateGroup x:Name="CommonStates">
                                    <VisualState x:Name="Normal"/>
                                    <VisualState x:Name="MouseOver"/>
                                    <VisualState x:Name="Pressed">
                                        <Storyboard>
                                            <!--<ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Foreground" Storyboard.TargetName="ContentContainer">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneButtonBasePressedForegroundBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>-->
                                            <!--<ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Background" Storyboard.TargetName="ButtonBackground">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneAccentBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>-->
                                        </Storyboard>
                                    </VisualState>
                                    <VisualState x:Name="Disabled">
                                        <Storyboard>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Foreground" Storyboard.TargetName="ContentContainer">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneDisabledBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="BorderBrush" Storyboard.TargetName="ButtonBackground">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneDisabledBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Background" Storyboard.TargetName="ButtonBackground">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="Transparent"/>
                                            </ObjectAnimationUsingKeyFrames>
                                        </Storyboard>
                                    </VisualState>
                                </VisualStateGroup>
                            </VisualStateManager.VisualStateGroups>
                            <Border x:Name="ButtonBackground" BorderBrush="{TemplateBinding BorderBrush}" BorderThickness="{TemplateBinding BorderThickness}" Background="{TemplateBinding Background}" CornerRadius="0">
                                <ContentControl x:Name="ContentContainer" ContentTemplate="{TemplateBinding ContentTemplate}" Content="{TemplateBinding Content}" Foreground="{TemplateBinding Foreground}" HorizontalContentAlignment="{TemplateBinding HorizontalContentAlignment}" Padding="{TemplateBinding Margin}" VerticalContentAlignment="{TemplateBinding VerticalContentAlignment}" UseLayoutRounding="False"/>
                            </Border>
                        </Grid>
                    </ControlTemplate>
                </Setter.Value>
            </Setter>
        </Style>
        <Style x:Key="TextBoxStyle1" TargetType="TextBox">
            <Setter Property="FontFamily" Value="{StaticResource PhoneFontFamilyNormal}"/>
            <Setter Property="FontSize" Value="{StaticResource PhoneFontSizeMediumLarge}"/>
            <Setter Property="Background" Value="{StaticResource PhoneTextBoxBrush}"/>
            <Setter Property="Foreground" Value="{StaticResource PhoneTextBoxForegroundBrush}"/>
            <Setter Property="BorderBrush" Value="{StaticResource PhoneTextBoxBrush}"/>
            <Setter Property="SelectionBackground" Value="{StaticResource PhoneAccentBrush}"/>
            <Setter Property="SelectionForeground" Value="{StaticResource PhoneTextBoxSelectionForegroundBrush}"/>
            <Setter Property="BorderThickness" Value="{StaticResource PhoneBorderThickness}"/>
            <Setter Property="Padding" Value="2"/>
            <Setter Property="Template">
                <Setter.Value>
                    <ControlTemplate TargetType="TextBox">
                        <Grid Background="Transparent">
                            <VisualStateManager.VisualStateGroups>
                                <VisualStateGroup x:Name="CommonStates">
                                    <VisualState x:Name="Normal"/>
                                    <VisualState x:Name="MouseOver"/>
                                    <VisualState x:Name="Disabled">
                                        <Storyboard>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Background" Storyboard.TargetName="MainBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="Transparent"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="BorderBrush" Storyboard.TargetName="MainBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneDisabledBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Foreground" Storyboard.TargetName="ContentElement">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneDisabledBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                        </Storyboard>
                                    </VisualState>
                                    <VisualState x:Name="ReadOnly">
                                        <Storyboard>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Visibility" Storyboard.TargetName="MainBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0">
                                                    <DiscreteObjectKeyFrame.Value>
                                                        <Visibility>Collapsed</Visibility>
                                                    </DiscreteObjectKeyFrame.Value>
                                                </DiscreteObjectKeyFrame>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Visibility" Storyboard.TargetName="ReadonlyBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0">
                                                    <DiscreteObjectKeyFrame.Value>
                                                        <Visibility>Visible</Visibility>
                                                    </DiscreteObjectKeyFrame.Value>
                                                </DiscreteObjectKeyFrame>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Background" Storyboard.TargetName="ReadonlyBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneTextBoxBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="BorderBrush" Storyboard.TargetName="ReadonlyBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneTextBoxBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Foreground" Storyboard.TargetName="ContentElement">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneTextBoxReadOnlyBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                        </Storyboard>
                                    </VisualState>
                                </VisualStateGroup>
                                <VisualStateGroup x:Name="FocusStates">
                                    <VisualState x:Name="Focused">
                                        <Storyboard>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="Background" Storyboard.TargetName="MainBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneTextBoxEditBackgroundBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                            <ObjectAnimationUsingKeyFrames Storyboard.TargetProperty="BorderBrush" Storyboard.TargetName="MainBorder">
                                                <DiscreteObjectKeyFrame KeyTime="0" Value="{StaticResource PhoneTextBoxEditBorderBrush}"/>
                                            </ObjectAnimationUsingKeyFrames>
                                        </Storyboard>
                                    </VisualState>
                                    <VisualState x:Name="Unfocused"/>
                                </VisualStateGroup>
                            </VisualStateManager.VisualStateGroups>
                            <Border x:Name="MainBorder" BorderBrush="{TemplateBinding BorderBrush}" BorderThickness="{TemplateBinding BorderThickness}" Background="{TemplateBinding Background}"/>
                            <Border x:Name="ReadonlyBorder" BorderBrush="{StaticResource PhoneDisabledBrush}" BorderThickness="{TemplateBinding BorderThickness}" Background="Transparent" Margin="{StaticResource PhoneTouchTargetOverhang}" Visibility="Collapsed"/>
                            <Border BorderBrush="Transparent" BorderThickness="{TemplateBinding BorderThickness}" Background="Transparent">
                                <ContentControl x:Name="ContentElement" BorderThickness="0" HorizontalContentAlignment="Stretch" Padding="{TemplateBinding Padding}" VerticalContentAlignment="Stretch"/>
                            </Border>
                        </Grid>
                    </ControlTemplate>
                </Setter.Value>
            </Setter>
        </Style>
    </Application.Resources>

    <Application.ApplicationLifetimeObjects>
        <!--Required object that handles lifetime events for the application-->
        <shell:PhoneApplicationService
            Launching="Application_Launching" Closing="Application_Closing"
            Activated="Application_Activated" Deactivated="Application_Deactivated"/>
    </Application.ApplicationLifetimeObjects>

</local:${project_name?cap_first}ApplicationBase>