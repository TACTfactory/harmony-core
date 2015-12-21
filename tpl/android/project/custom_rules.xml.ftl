<#-- Refer to documentation for change https://support.tactfactory.com/projects/harmony/wiki/Metadata_Map -->
<?xml version="1.0" encoding="UTF-8"?>
<project name="${project_name}" default="help">
    <import file="build.rules.xml"/>

    <!-- Load keystore params for release mode -->
    <property file="${r"${env.KEYSTORE_PATH}"}/${r"${ant.project.name}"}.properties" />

    <!-- Override prebuild target to update library dependencies -->
    <target name="-pre-build" depends="android_rules.-pre-build">
    </target>
</project>