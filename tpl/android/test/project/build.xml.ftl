<?xml version="1.0" encoding="UTF-8"?>
<project name="${project_name}-test" default="help">
	<property name="reports.dir" value="tmp" />
    <property name="tested.manifest.package" value="${project_namespace}" />

    <!-- The local.properties file is created and updated by the 'android' tool.
         It contains the path to the SDK. It should *NOT* be checked into
         Version Control Systems. -->
    <property file="local.properties" />

    <!-- The ant.properties file can be created by you. It is only edited by the
         'android' tool to add properties to it.
         This is the place to change some Ant specific build properties.
         Here are some properties you may want to change/update:

         source.dir
             The name of the source directory. Default is 'src'.
         out.dir
             The name of the output directory. Default is 'bin'.

         For other overridable properties, look at the beginning of the rules
         files in the SDK, at tools/ant/build.xml

         Properties related to the SDK location or the project target should
         be updated using the 'android' tool with the 'update' action.

         This file is an integral part of the build system for your
         application and should be checked into Version Control Systems.

         -->
    <property file="ant.properties" />

    <!-- if sdk.dir was not set from one of the property file, then
         get it from the ANDROID_HOME env var.
         This must be done before we load project.properties since
         the proguard config can use sdk.dir -->
    <property environment="env" />

    <!-- The project.properties file is created and updated by the 'android'
         tool, as well as ADT.

         This contains project specific properties such as project target, and library
         dependencies. Lower level build properties are stored in ant.properties
         (or in .classpath for Eclipse projects).

         This file is an integral part of the build system for your
         application and should be checked into Version Control Systems. -->
    <loadproperties srcFile="project.properties" />

    <!-- quick check on sdk.dir -->
    <fail
            message="sdk.dir is missing. Make sure to generate local.properties using 'android update project' or to inject it through the ANDROID_HOME environment variable."
            unless="sdk.dir"
    />

    <!--
        Import per project custom build rules if present at the root of the project.
        This is the place to put custom intermediary targets such as:
            -pre-build
            -pre-compile
            -post-compile (This is typically used for code obfuscation.
                           Compiled code location: $.{out.classes.absolute.dir}
                           If this is not done in place, override $.{out.dex.input.absolute.dir})
            -post-package
            -post-build
            -pre-clean
    -->
    <import file="custom_rules.xml" optional="true" />

    <!-- Import the actual build file.

         To customize existing targets, there are two options:
         - Customize only one target:
             - copy/paste the target into this file, *before* the
               <import> task.
             - customize it to your needs.
         - Customize the whole content of build.xml
             - copy/paste the content of the rules files (minus the top node)
               into this file, replacing the <import> task.
             - customize to your needs.

         ***********************
         ****** IMPORTANT ******
         ***********************
         In all cases you must update the value of version-tag below to read 'custom' instead of an integer,
         in order to avoid having your file be overridden by tools such as "android update project"
    -->
    <!-- version-tag: 1 -->
    <import file="${sdk.dir}/tools/ant/build.xml" />

    <target name="test-report">
    	<echo>Downloading XML test report...</echo>
    	<mkdir dir="tmp"/>
    	<exec executable="${sdk.dir}/platform-tools/adb" failonerror="true">
			<arg value="pull" />
			<arg value="/data/data/${project_namespace}/files/junit-report.xml" />
			<arg value="tmp/junit-report.xml" />
   		</exec>
	</target>

    <target name="test" depends="-test-project-check" 
                description="Runs tests from the package defined in test.package property">
        <property name="test.runner" value="android.test.InstrumentationTestRunner" />

        <if condition="${r"${project.is.test}"}">
        <then>
            <property name="tested.project.absolute.dir" location="${r"${tested.project.dir}"}" />

            <!-- Application package of the tested project extracted from its manifest file -->
            <xpath input="${r"${tested.project.absolute.dir}"}/AndroidManifest.xml" 
                    expression="/manifest/@package" output="tested.project.app.package" />

            <if condition="${r"${emma.enabled}"}">
                <then>
                    <getprojectpaths projectPath="${r"${tested.project.absolute.dir}"}" 
                            binOut="tested.project.out.absolute.dir" 
                            srcOut="tested.project.source.absolute.dir" />

                    <getlibpath projectPath="${r"${tested.project.absolute.dir}"}" 
                            libraryFolderPathOut="tested.project.lib.source.path" 
                            leaf="@{source.dir}" />

                </then>
            </if>

        </then>
        <else>
            <!-- this is a test app, the tested package is the app's own package -->
            <property name="tested.project.app.package" value="${r"${project.app.package}"}" />

            <if condition="${r"${emma.enabled}"}">
                <then>
                    <property name="tested.project.out.absolute.dir" value="${r"${out.absolute.dir}"}" />
                    <property name="tested.project.source.absolute.dir" value="${r"${source.absolute.dir}"}" />

                    <getlibpath
                            libraryFolderPathOut="tested.project.lib.source.path" 
                            leaf="@{source.dir}" />

                </then>
            </if>

        </else>
        </if>

        <property name="emma.dump.file" 
                value="/data/data/${r"${tested.project.app.package}"}/coverage.ec" />

        <if condition="${r"${emma.enabled}"}">
            <then>
                <echo>Running tests...</echo>
                <run-tests-helper emma.enabled="true">
                    <extra-instrument-args>
                        <arg value="-e" />
                           <arg value="coverageFile" />
                           <arg value="${r"${emma.dump.file}"}" />
                    </extra-instrument-args>
                </run-tests-helper>

                <echo level="info">Setting permission to download the coverage file...</echo>
                <exec executable="${r"${adb}"}" failonerror="true">
                    <arg line="${r"${adb.device.arg}"}" />
                    <arg value="shell" />
                    <arg value="run-as" />
                    <arg value="${r"${tested.project.app.package}"}" />
                    <arg value="chmod" />
                    <arg value="644" />
                    <arg value="${r"${emma.dump.file}"}" />
                </exec>
                <echo level="info">Downloading coverage file into project directory...</echo>
                <exec executable="${r"${adb}"}" failonerror="true">
                    <arg line="${r"${adb.device.arg}"}" />
                    <arg value="pull" />
                    <arg value="${r"${emma.dump.file}"}" />
                    <arg path="${r"${out.absolute.dir}"}/coverage.ec" />
                </exec>

                <pathconvert property="tested.project.lib.source.path.value" refid="tested.project.lib.source.path">
                    <firstmatchmapper>
                        <regexpmapper from='^([^ ]*)( .*)$$' to='"\1\2"'/>
                        <identitymapper/>
                    </firstmatchmapper>
                </pathconvert>

                <echo level="info">Extracting coverage report...</echo>
                <emma>
                    <property name="report.html.out.encoding" value="UTF-8" />
                    <report sourcepath="${r"${tested.project.source.absolute.dir}"}:${r"${tested.project.lib.source.path.value}"}" 
                            verbosity="${r"${verbosity}"}">
                        <!-- TODO: report.dir or something like should be introduced if necessary -->
                        <infileset file="${r"${out.absolute.dir}"}/coverage.ec" />
                        <infileset file="${r"${tested.project.out.absolute.dir}"}/coverage.em" />
                        <!-- TODO: reports in other, indicated by user formats -->
                        <html outfile="${r"${out.absolute.dir}"}/coverage.html" />
                        <txt outfile="${r"${out.absolute.dir}"}/coverage.txt" />
                        <xml outfile="${r"${out.absolute.dir}"}/coverage.xml" 
                            columns="name,class,method,block,line" 
                              sort="+line, +name"/>
                   </report>
                </emma>
                <echo level="info">Cleaning up temporary files...</echo>
                <delete file="${r"${out.absolute.dir}"}/coverage.ec" />
                <delete file="${r"${tested.project.out.absolute.dir}"}/coverage.em" />
                <exec executable="${r"${adb}"}" failonerror="true">
                    <arg line="${r"${adb.device.arg}"}" />
                    <arg value="shell" />
                    <arg value="run-as" />
                    <arg value="${r"${tested.project.app.package}"}" />
                    <arg value="rm" />
                    <arg value="${r"${emma.dump.file}"}" />
                </exec>
                <echo level="info">Saving the coverage reports in ${r"${out.absolute.dir}"}</echo>
            </then>
            <else>
                <run-tests-helper />
            </else>
        </if>
    </target>

</project>
