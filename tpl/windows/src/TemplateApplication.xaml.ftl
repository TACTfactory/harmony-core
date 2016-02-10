<local:${project_name?cap_first}ApplicationBase
    x:Class="${project_namespace}.${project_name?cap_first}Application"
    xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
    xmlns:x="http://schemas.microsoft.com/winfx/2006/xaml"
    xmlns:shell="clr-namespace:Microsoft.Phone.Shell;assembly=Microsoft.Phone"
    xmlns:local="clr-namespace:${project_namespace}">

    <!--Application Resources-->
    <Application.Resources>
        <local:LocalizedStrings xmlns:local="clr-namespace:${project_namespace}.Resources" x:Key="LocalizedStrings"/>
    </Application.Resources>

    <Application.ApplicationLifetimeObjects>
        <!--Required object that handles lifetime events for the application-->
        <shell:PhoneApplicationService
            Launching="Application_Launching" Closing="Application_Closing"
            Activated="Application_Activated" Deactivated="Application_Deactivated"/>
    </Application.ApplicationLifetimeObjects>

</local:${project_name?cap_first}ApplicationBase>