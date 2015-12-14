<#-- Refer to documentation for change https://support.tactfactory.com/projects/harmony/wiki/Metadata_Map -->
<?xml version="1.0" encoding="UTF-8"?>
<project name="harmony-rules">
    <property name="findbug.analyze.package" value="" />
    <property name="checkstyle.includes" value="" />
    <property name="checkstyle.excludes" value="" />
    <property name="tmp.rel-dir" value="tmp" />
    <property name="sdk.extra.dir" value="${r"${sdk.dir}"}/extras/android/support/v4" />
    <property name="supportv4.jar" value="android-support-v4.jar" />
    
    <path id="findbugs.dir.jars">
    <fileset dir="${r"${env.FINDBUGS_HOME}"}/lib">
        <include name="*.jar" />
    </fileset>
    </path>
    
    <target name="-setup" depends="android_rules.-setup">
        <condition property="android_executable" value="android.bat" else="android">
            <os family="windows"/>
        </condition> 
    </target>
    
<!-- basic -->
    <target name="init">
        <mkdir dir="${r"${out.dir}"}"/>
        <mkdir dir="${r"${tmp.rel-dir}"}"/>
    </target>
    
    <target name="test-all" depends="test-all-debug,test-all-release"/>
    <target name="test-all-debug" depends="uninstall,emma,clean,test-all-sub,reports"/>
    <target name="test-all-release" depends="uninstall,clean,release,install,monkey"/>

    <target name="test-all-sub">
        <subant target="test-all">
            <fileset dir="./test" includes="build.xml"/>
        </subant>
    </target>

    <target name="monkey">
        <subant target="monkey">
            <fileset dir="./test" includes="build.xml"/>
        </subant>
    </target>
    
    <target name="run-findbugs" depends="init">
        <taskdef name="findbugs" classname="edu.umd.cs.findbugs.anttask.FindBugsTask" description="classes+findbugs=magic" classpathref="findbugs.dir.jars"/>
        <findbugs home="${r"${env.FINDBUGS_HOME}"}"
                output="xml"
                outputFile="${r"${tmp.rel-dir}"}/findbugs.xml"
                onlyAnalyze="${r"${findbug.analyze.package}"}"
                excludefilter="./findbugs_excludes.xml">
            <sourcePath path="${r"${source.dir}"}" />
            <class location="${r"${out.dir}"}" />
            <auxClasspath refid="classpath" /> <!-- so we dont get hundrets of "The following classes needed for analysis were missing" warnings. -->
        </findbugs>
        <xslt
            in="${r"${tmp.rel-dir}"}/findbugs.xml"
                   out="${r"${tmp.rel-dir}"}/findbugs.html"
            style="${r"${env.FINDBUGS_HOME}"}/src/xsl/fancy.xsl"
           />
    </target>
    
    <target name="run-checkstyle" depends="init"
        description="Report of code convention violations.">

        <!-- Fail this target if CheckStyle is not installed. -->
           <available file="${r"${env.CHECKSTYLE_HOME}"}/checkstyle-5.6-all.jar"
            property="checkstyle.available"/>
           <fail unless="checkstyle.available"
               message="Error: CHECKSTYLE_HOME not set or checkstyle-5.6-all.jar not found." />

        <!-- Run this target if CheckStyle is installed. -->
        <taskdef resource="checkstyletask.properties"
               classpath="${r"${env.CHECKSTYLE_HOME}"}/checkstyle-5.6-all.jar" />

           <!-- run analysis-->
           <checkstyle config="checkstyle_rules_lvl1.xml"
                       failureProperty="checkstyle.failure"
                       failOnViolation="false" >
               <formatter type="xml" tofile="${r"${tmp.rel-dir}"}/checkstyle-result.xml" />
               <fileset dir="${r"${source.dir}"}"
                    includes="${r"${checkstyle.includes}"}"
                    excludes="${r"${checkstyle.excludes}"}" />
           </checkstyle>

        <!-- Report -->
           <xslt
               in="${r"${tmp.rel-dir}"}/checkstyle-result.xml"
               out="${r"${tmp.rel-dir}"}/checkstyle-result.html"
               style="${r"${env.CHECKSTYLE_HOME}"}/contrib/checkstyle-noframes.xsl"
           />
    </target>

    <target name="run-pmd" depends="init"
        description="Run pmd">

        <!-- Fail this target if Pmd is not installed. -->
        <available file="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar"
            property="pmd.available" />
        <fail unless="pmd.available"
            message="Error: PMD_HOME not set or pmd-5.0.2.jar not found." />

        <path id="pmd.classpath">
            <fileset dir="${r"${env.PMD_HOME}"}/lib/">
                <include name="**/*.jar" />
            </fileset>
        </path>

        <!-- Run this target if Pmd is installed. -->
        <taskdef name="pmd"
            classpathref="pmd.classpath"
            classpath="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar"
            classname="net.sourceforge.pmd.ant.PMDTask" />
        <pmd shortFilenames="true">
            <ruleset>./pmd_rules_lvl1.xml</ruleset>
            <formatter type="xml" toFile="${r"${tmp.rel-dir}"}/pmd.xml"/>
            <fileset dir="${r"${source.dir}"}">
                <include name="**/*.java"/>
                <exclude name="**/*Test*"/>
            </fileset>
        </pmd>

        <taskdef name="cpd"
            classpathref="pmd.classpath"
            classpath="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar"
            classname="net.sourceforge.pmd.cpd.CPDTask" />
        <cpd
            minimumTokenCount="100"
            language="java"
            format="xml"
            outputFile="${r"${tmp.rel-dir}"}/cpd.xml">
            <fileset dir="${r"${source.dir}"}">
                <include name="**/*.java"/>
                <exclude name="**/*Test*"/>
            </fileset>
        </cpd>

        <!-- Report -->
        <xslt
            in="${r"${tmp.rel-dir}"}/pmd.xml"
            out="${r"${tmp.rel-dir}"}/pmd.html"
            style="${r"${env.PMD_HOME}"}/etc/xslt/pmd-report.xslt" />
        <xslt
            in="${r"${tmp.rel-dir}"}/cpd.xml"
            out="${r"${tmp.rel-dir}"}/cpd.html"
            style="${r"${env.PMD_HOME}"}/etc/xslt/cpdhtml.xslt" />
    </target>

    <target name="reports" depends="run-checkstyle,run-findbugs,run-pmd" />
    
    <!-- Code obfuscation check  -->
    <target name="proguard" >
        <property name="debug.proguard.enable" value="true" />
    </target>
    
	<target name="-debug-obfuscation-check">
		<!-- proguard is never enabled in debug mode -->
        <condition property="proguard.enabled" value="true" else="false">
            <isset property="debug.proguard.enable"/>
        </condition>
	</target>
	
    <target name="-update-library">
        <condition property="library.target" value="${r"${target}"}">
           <not>
              <isset property="library.target"/>
           </not>
        </condition>

		<!-- Manage library naming -->
		<condition property="library.target" value="${r"${nameSetter}"}">
           <not>
              <isset property="${r"${nameSetter}"}"/>
           </not>
        </condition>
        
        <echo level="info">Update ${r"${library.update}"} Android library</echo>
        <exec executable="${r"${sdk.dir}/tools/${android_executable}"}" failonerror="true">
            <arg value="update"/>
            <arg value="lib-project"/>
            <arg value="-p"/>
            <arg value="${r"${library.update}"}"/>
            <arg value="--target"/>
            <arg value="${r"${library.target}"}"/>
            <arg value="--name" />
            <arg value="${r"${nameSetter}"}"/>
        </exec>
    </target>
    
    <target name="-update-library-add">
        <echo level="info">Update ${r"${library.update}"} Android library with library</echo>
        <exec executable="${r"${sdk.dir}"}/tools/${r"${android_executable}"}" failonerror="true">
            <arg value="update"/>
            <arg value="project"/>
            <arg value="-p"/>
            <arg value="${r"${library.update}"}"/>
            <arg value="--library"/>
            <arg value="${r"${library.param.library}"}"/>
        </exec>
    </target>
</project>
