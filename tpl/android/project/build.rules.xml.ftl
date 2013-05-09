<?xml version="1.0" encoding="UTF-8"?>
<project name="harmony-rules">	    
	<property name="findbug.analyze.package" value="" />
	<property name="checkstyle.includes" value="" />
	<property name="android-jar" value="${sdk.dir}/platforms/android-14/android.jar" />    
    <property name="tmp.rel-dir" value="tmp" />
	<path id="findbugs.dir.jars">
	<fileset dir="${r"${env.FINDBUGS_HOME}"}/lib">
	    <include name="*.jar" />
	</fileset>
    </path>
    <path id="classpath">
	<fileset dir="${r"${jar.libs.dir}"}">
	    <include name="*.jar" />
	</fileset>
	<pathelement path="${r"${android-jar}"}" />
	<pathelement path="${r"${jar.libs.dir}"}/sherlock/library/bin/classes.jar" />
	<pathelement path="${r"${jar.libs.dir}"}/sherlock/library/libs/android-support-v4.jar" />
    </path>
	
<!-- basic -->
    <target name="init">
        <mkdir dir="${r"${out.dir}"}"/>
    	<mkdir dir="${r"${tmp.rel-dir}"}"/>
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
	       <checkstyle config="checkstyle_rules.xml"
	                   failureProperty="checkstyle.failure"
	                   failOnViolation="false" >
	           <formatter type="xml" tofile="${r"${tmp.rel-dir}"}/checkstyle-result.xml" />
	           <fileset dir="${r"${source.dir}"}" includes="${r"${checkstyle.includes}"}" />
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
			<ruleset>${r"${env.PMD_HOME}"}/rules.xml</ruleset>
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
</project>