<#-- Refer to documentation for change https://support.tactfactory.com/projects/harmony/wiki/Metadata_Map -->
<?xml version="1.0" encoding="UTF-8"?>
<project name="harmony-rules-test" default="help">
    <property name="reports.dir" value="tmp" />
    <property name="junit.report.dir" value="/sdcard/" />
    <property name="monkey.numberevents" value="5000" />
    <property name="monkey.seed" value="0" />
    
    <!-- 6628 -->
    <!-- generic setup -->
    <target name="-setup" depends="android_rules.-setup">
        <property name="junit.report.file" value="${r"${project.app.package}"}.junit-report.xml" />
    </target>
    <!-- END6628 -->
    
    <target name="test-report" depends="-setup">
        <echo>Downloading XML test report...</echo>
        <mkdir dir="${r"${reports.dir}"}"/>
        <exec executable="${r"${sdk.dir}"}/platform-tools/adb" failonerror="true">
            <arg value="pull" />
            <arg value="${r"${junit.report.dir}"}${r"${junit.report.file}"}" />
            <arg value="${r"${reports.dir}"}/junit-report.xml" />
           </exec>
           
           <fail message="Tests failed!!!">
             <condition>
                 <contains string="${r"${tests.output}"}" substring="FAILURES" />
             </condition>
        </fail>
    </target>
    
    <!-- 6628 -->
    <target name="test-all" depends="emma,clean,debug,install,test,test-report,monkey"/>

    <target name="monkey" depends="-setup">
        <echo level="info">Launch monkey on ${tested.manifest.package}"</echo>
        <exec executable="${r"${sdk.dir}"}/platform-tools/adb" failonerror="true">
            <arg value="shell"/>
            <arg value="monkey"/>
            <arg value="-p"/>
            <arg value="${r"${tested.manifest.package}"}"/>
            <arg value="-v"/>
            <arg value="${r"${monkey.numberevents}"}"/>
            <arg value="-s"/>
            <arg value="${r"${monkey.seed}"}"/>
        </exec>
    </target>
    <!-- END6628 -->
    
    <target name="test" 
            depends="-test-project-check"
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
    
        <if condition="${r"${emma.enabled}"}">
            <then>
                <echo>Running tests with emma...</echo>
                
                <property name="emma.dump.file" value="/sdcard/${r"${tested.project.app.package}"}/coverage.ec" />
                
                <run-tests-helper emma.enabled="true">
                    <extra-instrument-args>
                        <arg value="-e" />
                        <arg value="coverageFile"/>
                        <arg value="${r"${emma.dump.file}"}"/>
                        <arg value="-e"/>
                        <arg value="reportDir"/>
                        <arg value="${r"${junit.report.dir}"}"/>
                        <arg value="-e"/>
                        <arg value="reportFile"/>
                        <arg value="${r"${junit.report.file}"}"/>
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
                    <arg value="rm" />
                    <arg value="${r"${emma.dump.file}"}" />
                </exec>
                
                <echo level="info">Saving the coverage reports in ${r"${out.absolute.dir}"}</echo>
            </then>
            <else>
                <run-tests-helper>
                    <extra-instrument-args>
                        <arg value="-e"/>
                        <arg value="reportDir"/>
                        <arg value="${r"${junit.report.dir}"}"/>
                        <arg value="-e"/>
                        <arg value="reportFile"/>
                        <arg value="${r"${junit.report.file}"}"/>
                    </extra-instrument-args>
                </run-tests-helper>
            </else>
        </if>
    </target>
    
</project>