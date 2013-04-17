<?xml version="1.0" encoding="UTF-8"?>
<project name="${project_name}" default="help">

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
    
    <!-- If sdk.dir was not set from one of the property file, then
		 get it from the ANDROID_HOME env var.
	     This must be done before we load project.properties since
	     the proguard config can use sdk.dir 
	-->
	<property environment="env"/>

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
            message="sdk.dir is missing. Make sure to generate local.properties using 'android update project' or to inject it through an env var"
            unless="sdk.dir"
    />

    <!--
        Import per project custom build rules if present at the root of the project.
        This is the place to put custom intermediary targets such as:
            -pre-build
            -pre-compile
            -post-compile (This is typically used for code obfuscation.
                           Compiled code location: ${out_classes_absolute_dir}
                           If this is not done in place, override ${out_dex_input_absolute_dir})
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
    
    <target name="run-findbugs" depends="jar"
	description="Run code analysis over code to check for problems.">

	<!-- Fail this target if FindBugs is not installed. -->
	<available file="${env.FINDBUGS_HOME}/lib/findbugs.jar" 
			property="findbugs.available" />
		<fail unless="findbugs.available"
			message="Error: FINDBUGS_HOME not set or findbugs.jar not found." />
	
		<!-- Run this target if FindBugs is installed. -->
		<taskdef name="findbugs" 
			classpath="${env.FINDBUGS_HOME}/lib/findbugs-ant.jar"
			classname="edu.umd.cs.findbugs.anttask.FindBugsTask" />
	    <findbugs home="${env.FINDBUGS_HOME}"
	    	workHard="true"
	    	output="xml:withMessages"
	        outputFile="${tmp.rel-dir}/findbugs.xml"
	    	jvmargs="-Xmx1024m" >
	    	<auxClasspath refid="project.classpath" />
	    	<sourcePath path="${src.rel-dir}" />
	    	<class location="${jar.rel-file}" />
	    </findbugs>
		
		<!-- Report -->
		<xslt 
			in="${tmp.rel-dir}/findbugs.xml"
	       	out="${tmp.rel-dir}/findbugs.html"
			style="${env.FINDBUGS_HOME}/src/xsl/fancy.xsl"
	       />
	</target>
	
	<target name="run-checkstyle" depends="compile-tests" 
		description="Report of code convention violations.">
		
		<!-- Fail this target if CheckStyle is not installed. -->
	       <available file="${env.CHECKSTYLE_HOME}/checkstyle-5.6-all.jar"
			property="checkstyle.available"/>
	       <fail unless="checkstyle.available"
	       	message="Error: CHECKSTYLE_HOME not set or checkstyle-5.6-all.jar not found." />
	       
		<!-- Run this target if CheckStyle is installed. -->
		<taskdef resource="checkstyletask.properties"
	       	classpath="${env.CHECKSTYLE_HOME}/checkstyle-5.6-all.jar" />
	
	       <!-- run analysis-->
	       <checkstyle config="${env.CHECKSTYLE_HOME}/sun_checks.xml"
	                   failureProperty="checkstyle.failure"
	                   failOnViolation="false" >
	           <formatter type="xml" tofile="${tmp.rel-dir}/checkstyle_report.xml" />
	           <fileset dir="${src.rel-dir}" includes="**/*.java" />
	       </checkstyle>
	
		<!-- Report -->
	       <xslt 
	       	in="${tmp.rel-dir}/checkstyle_report.xml"
	           out="${tmp.rel-dir}/checkstyle_report.html"
	           style="${env.CHECKSTYLE_HOME}/contrib/checkstyle-noframes.xsl"
	       />
	</target>
	
	<target name="run-pmd" depends="compile-tests"
		description="Run pmd">
		
		<!-- Fail this target if Pmd is not installed. -->
		<available file="${env.PMD_HOME}/lib/pmd-5.0.2.jar" 
			property="pmd.available" />
		<fail unless="pmd.available"
			message="Error: PMD_HOME not set or pmd-5.0.2.jar not found." />
		
		<path id="pmd.classpath">
	    	<fileset dir="${env.PMD_HOME}/lib/">
	            <include name="**/*.jar" />
	        </fileset>
	    </path>
		
		<!-- Run this target if Pmd is installed. -->
		<taskdef name="pmd" 
			classpathref="pmd.classpath"
			classpath="${env.PMD_HOME}/lib/pmd-5.0.2.jar"
			classname="net.sourceforge.pmd.ant.PMDTask" />
		<pmd shortFilenames="true">
			<ruleset>${env.PMD_HOME}/rules.xml</ruleset>
			<formatter type="xml" toFile="${tmp.rel-dir}/pmd.xml"/>
			<fileset dir="${src.rel-dir}">
				<include name="**/*.java"/>
				<exclude name="**/*Test*"/>
			</fileset>
		</pmd>
		
		<taskdef name="cpd" 
			classpathref="pmd.classpath"
			classpath="${env.PMD_HOME}/lib/pmd-5.0.2.jar"
			classname="net.sourceforge.pmd.cpd.CPDTask" />
	    <cpd 
	    	minimumTokenCount="100" 
	    	language="java" 
	    	format="xml" 
	    	outputFile="${tmp.rel-dir}/cpd.xml">
	        <fileset dir="${src.rel-dir}">
	            <include name="**/*.java"/>
	        	<exclude name="**/*Test*"/>
	        </fileset>
	    </cpd>
		
		<!-- Report -->
		<xslt 
			in="${tmp.rel-dir}/pmd.xml" 
			out="${tmp.rel-dir}/pmd.html"
			style="${env.PMD_HOME}/etc/xslt/pmd-report.xslt" />
		<xslt 
			in="${tmp.rel-dir}/cpd.xml" 
			out="${tmp.rel-dir}/cpd.html"
			style="${env.PMD_HOME}/etc/xslt/cpdhtml.xslt" />
	</target>
	
	<target name="run-jdepend" depends="compile-tests"
		description="Run jdepend">
		<jdepend outputfile="docs/jdepend.xml" fork="yes" format="xml">
		    <sourcespath>
		        <pathelement location="${src.rel-dir}"/>
		    </sourcespath>
		    <classpath refid="project.classpath" />
		        <!--pathelement location="classes"/>
		        <pathelement location="/usr/share/java/jdepend-2.9.jar"/>
		    </classpath-->
		</jdepend>
	</target>
	
	<target name="reports" depends="run-checkstyle,run-findbugs,run-pmd" />
    
    <!-- Add Harmony support -->
	<target name="test-report">
		<echo>Downloading XML test report...</echo>
		<mkdir dir="tmp"/>
		<exec executable="${sdk.dir}/platform-tools/adb" failonerror="true">
			<arg value="pull"/>
			<arg value="/data/data/com.tactfactory.mda.test.demact/files/junit-report.xml"/>
			<arg value="tmp/junit-report.xml"/>
		</exec>
	</target>
</project>
