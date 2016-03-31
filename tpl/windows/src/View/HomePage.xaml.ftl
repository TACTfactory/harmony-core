<!-- <@header?interpret /> -->

<Page
    x:Class="${project_namespace}.View.HomePage"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:local="using:${project_namespace}.View"
    xmlns:d="http://schemas.microsoft.com/expression/blend/2008"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    mc:Ignorable="d">

    <!--LayoutRoot is the root grid where all page content is placed-->
    <Grid x:Name="LayoutRoot" Background="Transparent">
        <Grid.RowDefinitions>
            <RowDefinition Height="Auto"/>
            <RowDefinition Height="*"/>
        </Grid.RowDefinitions>

        <!-- LOCALIZATION NOTE:
            To localize the displayed strings copy their values to appropriately named
            keys in the app's neutral language resource file (AppResources.resx) then
            replace the hard-coded text value between the attributes' quotation marks
            with the binding clause whose path points to that string name.

            For example:

                Text="{Binding Path=LocalizedResources.ApplicationTitle, Source={StaticResource LocalizedStrings}}"

            This binding points to the template's string resource named "ApplicationTitle".

            Adding supported languages in the Project Properties tab will create a
            new resx file per language that can carry the translated values of your
            UI strings. The binding in these examples will cause the value of the
            attributes to be drawn from the .resx file that matches the
            CurrentUICulture of the app at run time.
         -->

        <!--TitlePanel contains the name of the application and page title-->
        <StackPanel x:Name="TitlePanel" Grid.Row="0" Margin="12,17,0,28">
            <TextBlock Text="${project_name?upper_case}" Style="{StaticResource BaseTextBlockStyle}" Margin="12,0"/>
            <TextBlock Text="What to do next" Margin="9,-7,0,0" Style="{StaticResource TitleTextBlockStyle}"/>
        </StackPanel>

        <!--ContentPanel - place additional content here-->
        <ScrollViewer Grid.Row="1" Margin="12,0,12,0">
            <StackPanel x:Name="ContentPanel">
                <TextBlock
                    TextWrapping="Wrap"
                    Margin="0,0,0,15"
                    Text="You have generated your first Windows Phone project with Harmony."/>
                <TextBlock
                    TextWrapping="Wrap"
                    Foreground="{StaticResource TextBoxButtonPressedForegroundThemeBrush}"
                    FontWeight="Bold"
                    Text="Step 1"/>
                <TextBlock
                    TextWrapping="Wrap"
                    Margin="0,0,0,4"
                    Text="Now you can create your models in the entity package of the Android project."/>
                <TextBlock
                    TextWrapping="Wrap"
                    FontSize="17"
                    Foreground="#FFCBCBCB"
                    Text="File : /src/com/compagny/android/entity/Post"/>
                <TextBlock
                    TextWrapping="Wrap"
                    FontSize="17"
                    Foreground="#FFCBCBCB"
                    Text="Package com.compagny.android.entity; public class Post { }"/>
                <TextBlock
                    TextWrapping="Wrap"
                    FontWeight="Bold"
                    Foreground="{StaticResource TextBoxButtonPressedForegroundThemeBrush}"
                    Margin="0,8,0,0"
                    Text="Step 2"/>
                <TextBlock
                    TextWrapping="Wrap"
                    Margin="0,0,0,4"
                    Text="After creating your models, run Harmony CLI Entities and CRUD view generator."/>
                <TextBlock
                    TextWrapping="Wrap"
                    FontSize="17"
                    Foreground="#FFCBCBCB"
                    Text="script/console orm:generate:entities"/>
                <TextBlock
                    TextWrapping="Wrap"
                    Foreground="#FFCBCBCB"
                    FontSize="17"
                    Text="script/console orm:generate:crud"/>
                <TextBlock
                    TextWrapping="Wrap"
                    FontWeight="Bold"
                    Foreground="{StaticResource TextBoxButtonPressedForegroundThemeBrush}"
                    Margin="0,8,0,0"
                    Text="Step 3"/>
                <TextBlock
                    TextWrapping="Wrap"
                    Margin="0,0,0,24"
                    Text="Refresh, clean and run your project again..."/>
                <TextBlock
                    TextWrapping="Wrap"
                    Text="More documentation at :"/>
                <TextBlock
                    TextWrapping="Wrap"
                    Margin="0,0,0,20"
                    Text="https://support.tactfactory.com/projects/harmony/wiki"/>
                <#list entities?values as entity>
                    <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
                <Button Name="Button${entity.name}" Content="List all ${entity.name}" Click="Button${entity.name}_Click"/>
                    </#if>
                </#list>
            </StackPanel>
        </ScrollViewer>

        <!--Uncomment to see an alignment grid to help ensure your controls are
            aligned on common boundaries.  The image has a top margin of -32px to
            account for the System Tray. Set this to 0 (or remove the margin altogether)
            if the System Tray is hidden.

            Before shipping remove this XAML and the image itself.-->
        <!--<Image Source="/Assets/AlignmentGrid.png" VerticalAlignment="Top" Height="800" Width="480" Margin="0,-32,0,0" Grid.Row="0" Grid.RowSpan="2" IsHitTestVisible="False" />-->
    </Grid>

</Page>
