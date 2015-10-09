<#-- Refer to documentation for change https://support.tactfactory.com/projects/harmony/wiki/Metadata_Map -->
<!-- 6628 -->
<?xml version="1.0" encoding="UTF-8"?>
<project name="${project_name}" default="help">
    <import file="build.rules.xml"/>
    
    <!-- Override prebuild target to update library dependencies -->
    <target name="-pre-build" depends="android_rules.-pre-build">
    </target>
</project>
<!-- END6628 -->