<?xml version="1.0" encoding="UTF-8"?>
<project name="${bundle_name?lower_case}-bundle" default="buildall">
	<description>
		Harmony Bundle ${bundle_name?cap_first} build file.
	</description>

	<!-- load environment variables as properties -->
	<property environment="env" />

	<!-- load properties files -->
	<property file="build.properties" />
	<property file="../build.properties" />
	<property file="${r"${user.home}"}/build.properties" />

	<!-- default folder location properties -->
	<property name="src.rel-dir" value="src" />
	<!-- <property name="etc.rel-dir" value="etc" /-->
	<property name="build.rel-dir" value="bin" />
	<property name="core-lib.rel-dir" value="../tact-core/lib" />
	<property name="lib.rel-dir" value="lib" />
	<property name="ant-lib.rel-dir" value="${r"${core-lib.rel-dir}"}/ant" />
	<property name="tmp.rel-dir" value="tmp" />
	<property name="dist.rel-dir" value="./" />
	<property name="doc.rel-dir" value="${r"${dist.rel-dir}"}/javadoc" />
	<property name="jar.rel-file" value="${r"${dist.rel-dir}"}/${r"${ant.project.name}"}.jar" />
	<property name="jar-annotations.rel-file" value="${r"${dist.rel-dir}"}/lib/${r"${ant.project.name}"}-annotations.jar" />


	<property name="compile.debug" value="true" />
	<property name="compile.debuglevel" value="lines,vars,source" />

	<!-- jar file from where the tasks are loaded -->
    <path id="android.antlibs">
        <pathelement path="${r"${ant-lib.rel-dir}"}/anttasks.jar" />
    </path>

    <!-- Custom tasks -->
    <taskdef resource="anttasks.properties" classpathref="android.antlibs" />

	<path id="project.classpath">
		<pathelement location="../tact-core/harmony.jar" />
		<!-- compiled classes -->
		<pathelement location="${r"${build.rel-dir}"}" />
		<!-- libraries -->
		<fileset dir="${r"${lib.rel-dir}"}">
			<include name="*.jar" />
		</fileset>
	</path>

	<!-- basic -->
	<target name="init">
		<mkdir dir="${r"${build.rel-dir}"}" />
		<mkdir dir="${r"${dist.rel-dir}"}" />
		<mkdir dir="${r"${tmp.rel-dir}"}" />
		<mkdir dir="${r"${lib.rel-dir}"}" />
	</target>

	<target name="clean" description="Delete temporary folders">
		<delete dir="${r"${build.rel-dir}"}" failonerror="false" deleteonexit="true" />
		<delete dir="${r"${tmp.rel-dir}"}" failonerror="false" deleteonexit="true" />
	</target>

	<!-- javadoc -->

	<target name="javadoc" depends="init" description="Generate Java classes documentation">
		<echo message="Generating javadocs to directory ${r"${doc.rel-dir}"}" />
		<delete dir="${r"${doc.rel-dir}"}" />
		<javadoc destdir="${r"${doc.rel-dir}"}" sourcepath="" excludepackagenames="${bundle_namespace?lower_case}.test" defaultexcludes="yes">
			<fileset dir="${r"${src.rel-dir}"}" includes="**/*.java" />
			<classpath refid="project.classpath" />
		</javadoc>
	</target>

	<!-- compile -->

	<target name="prepare-resources" depends="init">
		<!-- description="Prepare application resource files" -->
		<copy todir="${r"${build.rel-dir}"}" overwrite="true">
			<fileset dir="${r"${src.rel-dir}"}" includes="**/*.properties,**/*.xml" excludes="" />
		</copy>
		<replace dir="${r"${build.rel-dir}"}" includes="**/*.properties,**/*.xml" excludes="" summary="true">
			<replacefilter token="@example-token@" value="${r"${example-property}"}" />
		</replace>
	</target>

	<target name="compile" depends="init,prepare-resources">
		<!-- description="Compile source code" excludes="**/com/tactfactory/harmony/test/**" -->
		<javac srcdir="${r"${src.rel-dir}"}" destdir="${r"${build.rel-dir}"}" debug="${r"${compile.debug}"}" debuglevel="${r"${compile.debuglevel}"}" includeantruntime="false" excludes="**/${bundle_namespace?replace(".","/")}/test/**" target="1.6" source="1.6" bootclasspath="jdk1.6.0/lib/rt.jar">
			<compilerarg line="" />
			<!-- "-Xlint:all", "-Xlint:all,-path", "-Xlint:all,-path,-unchecked" -->
			<classpath refid="project.classpath" />
		</javac>
	</target>

	<!-- tests -->

	<target name="compile-tests" depends="init,prepare-resources" description="Compile tests">
		<javac srcdir="${r"${src.rel-dir}"}" destdir="${r"${build.rel-dir}"}" debug="${r"${compile.debug}"}" debuglevel="${r"${compile.debuglevel}"}" includeantruntime="false" target="1.6" source="1.6">
			<compilerarg line="" />
			<!-- "-Xlint:all", "-Xlint:all,-path", "-Xlint:all,-path,-unchecked" -->
			<classpath refid="project.classpath" />
		</javac>
	</target>

	<target name="run-tests" depends="compile-tests" 
		description="Run tests">

		<if condition="${r"${emma.enabled}"}">
            <then>
		    	<emma enabled="${r"${emma.enabled}"}" >
				    <instr instrpathref="emma.coverage.classes"
				         destdir="${r"${emma.instr-dir}"}"
				         metadatafile="${r"${emma.out-dir}"}/metadata.emma"
				         merge="true">
 				        <filter value="${r"${emma.filter}"}"/>
				    </instr>
				</emma>
				
		    	<path id="emma.test.classpath">
					
					<pathelement location="${r"${emma.instr-dir}"}"/>
					<path refid="project.classpath"/>
<!-- 					<pathelement location="${r"${build.rel-dir}"}"/>					 -->
					<pathelement location="${r"${emma.dir}"}"/>
					<fileset dir="${r"${emma.dir}"}">
						<include name="*.jar" />
					</fileset>
					<fileset dir="${r"${lib.rel-dir}"}">
						<include name="*.jar" />
					</fileset>
					<fileset dir="${r"${dist.rel-dir}"}">
						<include name="*.jar" />
					</fileset>
				</path>
				
		    	<property name="test.classpath.id" value="emma.test.classpath" />
			</then>
			<else>
			    <property name="test.classpath.id" value="project.classpath" />
			</else>
		</if>

		<junit haltonfailure="no" fork="yes">
            <!-- <if condition="${r"${emma.enabled}"}">
	            <then> -->
		            <jvmarg value="-Demma.coverage.out.file=${r"${emma.out-dir}"}/coverage.emma" />
					<jvmarg value="-Demma.coverage.out.merge=false" />
					<!-- <jvmarg value="-XX:-UseSplitVerifier" /> -->
           		<!-- </then>
      		</if>	 -->	

			<classpath refid="${r"${test.classpath.id}"}" />
			<formatter type="xml" />
            <formatter classname="org.apache.tools.ant.taskdefs.optional.junit.TearDownOnVmCrash" usefile="false"/>
			<test name="${bundle_namespace}.tests.AllTests" todir="${r"${tmp.rel-dir}"}/" failureproperty="tests.failure" />
		</junit>

 		<if condition="${r"${emma.enabled}"}">
            <then>
		        <emma enabled="${r"${emma.enabled}"}" >
				    <report sourcepath="src">
					    <!-- collect all EMMA data dumps (metadata and runtime)
					         [this can be done via nested <fileset> fileset elements
					         or <file> elements pointing to a single file]:
					    -->
					    <fileset dir="${r"${emma.out-dir}"}" >
					        <include name="*.emma" />
					    </fileset>
					    
					    <!-- for every type of report desired, configure a nested
					         element; various report parameters
					         can be inherited from the parent <report>
					         and individually overridden for each report type:
					    -->
					    <xml outfile="${r"${emma.out-dir}"}/coverage.xml"
						columns="name,class,method,block,line" 
                              			sort="+line, +name"/>
					    <html outfile="${r"${emma.out-dir}"}/coverage.html" />
				    </report>
				</emma>
			</then>
		</if>
		
        <fail message="Tests failed!!!">
       		<condition>
       		    <isset property="tests.failure" />
       		</condition>
   		</fail>
	</target>
    
<!-- 	<target name="run-tests" depends="compile-tests" description="Run tests"> -->
<!-- 		<property name="test.classpath.id" value="project.classpath" /> -->

<!-- 		<junit haltonfailure="no" fork="yes"> -->
<!-- 			<classpath refid="${r"${test.classpath.id}"}" /> -->
<!-- 			<formatter type="xml" /> -->
<!-- 			<test name="com.tactfactory.harmony.bundles.rest.test.AllTests" todir="${r"${tmp.rel-dir}"}/" /> -->
<!-- 		</junit> -->
<!-- 	</target> -->

	<target name="run-findbugs" depends="compile-tests" description="Run code analysis over code to check for problems.">

		<!-- Fail this target if FindBugs is not installed. -->
		<available file="${r"${env.FINDBUGS_HOME}"}/lib/findbugs.jar" property="findbugs.available" />
		<fail unless="findbugs.available" message="Error: FINDBUGS_HOME not set or findbugs.jar not found." />

		<!-- Run this target if FindBugs is installed. -->
		<taskdef name="findbugs" classpath="${r"${env.FINDBUGS_HOME}"}/lib/findbugs-ant.jar" classname="edu.umd.cs.findbugs.anttask.FindBugsTask" />
		<findbugs home="${r"${env.FINDBUGS_HOME}"}" workHard="true" output="xml:withMessages" outputFile="${r"${tmp.rel-dir}"}/findbugs.xml" jvmargs="-Xmx1024m">
			<auxClasspath refid="project.classpath" />
			<sourcePath path="${r"${src.rel-dir}"}" />
			<class location="${r"${jar.rel-file}"}" />
		</findbugs>

		<!-- Report -->
		<xslt in="${r"${tmp.rel-dir}"}/findbugs.xml" out="${r"${tmp.rel-dir}"}/findbugs.html" style="${r"${env.FINDBUGS_HOME}"}/src/xsl/fancy.xsl" />
	</target>

	<target name="run-checkstyle" depends="compile-tests" description="Report of code convention violations.">

		<!-- Fail this target if CheckStyle is not installed. -->
		<available file="${r"${env.CHECKSTYLE_HOME}"}/checkstyle-5.6-all.jar" property="checkstyle.available" />
		<fail unless="checkstyle.available" message="Error: CHECKSTYLE_HOME not set or checkstyle-5.6-all.jar not found." />

		<!-- Run this target if CheckStyle is installed. -->
		<taskdef resource="checkstyletask.properties" classpath="${r"${env.CHECKSTYLE_HOME}"}/checkstyle-5.6-all.jar" />

		<!-- run analysis-->
		<checkstyle config="${r"${env.CHECKSTYLE_HOME}"}/sun_checks.xml" failureProperty="checkstyle.failure" failOnViolation="false">
			<formatter type="xml" tofile="${r"${tmp.rel-dir}"}/checkstyle_report.xml" />
			<fileset dir="${r"${src.rel-dir}"}" includes="**/*.java" />
		</checkstyle>

		<!-- Report -->
		<xslt in="${r"${tmp.rel-dir}"}/checkstyle_report.xml" out="${r"${tmp.rel-dir}"}/checkstyle_report.html" style="${r"${env.CHECKSTYLE_HOME}"}/contrib/checkstyle-noframes.xsl" />
	</target>

	<target name="run-pmd" depends="compile-tests" description="Run pmd">

		<!-- Fail this target if Pmd is not installed. -->
		<available file="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar" property="pmd.available" />
		<fail unless="pmd.available" message="Error: PMD_HOME not set or pmd-5.0.2.jar not found." />

		<path id="pmd.classpath">
			<fileset dir="${r"${env.PMD_HOME}"}/lib/">
				<include name="**/*.jar" />
			</fileset>
		</path>

		<!-- Run this target if Pmd is installed. -->
		<taskdef name="pmd" classpathref="pmd.classpath" classpath="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar" classname="net.sourceforge.pmd.ant.PMDTask" />
		<pmd shortFilenames="true">
			<ruleset>${r"${env.PMD_HOME}"}/rules.xml</ruleset>
			<formatter type="xml" toFile="${r"${tmp.rel-dir}"}/pmd.xml" />
			<fileset dir="${r"${src.rel-dir}"}">
				<include name="**/*.java" />
				<exclude name="**/*Test*" />
			</fileset>
		</pmd>

		<taskdef name="cpd" classpathref="pmd.classpath" classpath="${r"${env.PMD_HOME}"}/lib/pmd-5.0.2.jar" classname="net.sourceforge.pmd.cpd.CPDTask" />
		<cpd minimumTokenCount="100" language="java" format="xml" outputFile="${r"${tmp.rel-dir}"}/cpd.xml">
			<fileset dir="${r"${src.rel-dir}"}">
				<include name="**/*.java" />
				<exclude name="**/*Test*" />
			</fileset>
		</cpd>

		<!-- Report -->
		<xslt in="${r"${tmp.rel-dir}"}/pmd.xml" out="${r"${tmp.rel-dir}"}/pmd.html" style="${r"${env.PMD_HOME}"}/etc/xslt/pmd-report.xslt" />
		<xslt in="${r"${tmp.rel-dir}"}/cpd.xml" out="${r"${tmp.rel-dir}"}/cpd.html" style="${r"${env.PMD_HOME}"}/etc/xslt/cpdhtml.xslt" />
	</target>

	<target name="run-jdepend" depends="compile-tests" description="Run jdepend">
		<jdepend outputfile="docs/jdepend.xml" fork="yes" format="xml">
			<sourcespath>
				<pathelement location="${r"${src.rel-dir}"}" />
			</sourcespath>
			<classpath refid="project.classpath" />
			<!--pathelement location="classes"/>
			        <pathelement location="/usr/share/java/jdepend-2.9.jar"/>
			    </classpath-->
		</jdepend>
	</target>

	<!-- build -->

	<target name="build" depends="compile" description="Build the project">
	</target>

	<target name="rebuild" depends="clean,build" description="Rebuild the project">
	</target>

	<target name="buildall" depends="rebuild,jar,post_build" description="Build the project">
	</target>

	<target name="tests" depends="run-tests" />
	
	<target name="reports" depends="run-checkstyle,run-findbugs,run-pmd" />

	<target name="jar" depends="compile">
		<!-- description="Create a jar file" -->
		<pathconvert property="libs.project" pathsep=" ">
			<mapper>
				<chainedmapper>

					<!-- remove absolute path -->
					<flattenmapper />

					<!-- add lib/ prefix -->
					<globmapper from="*" to="../../lib/*" />
				</chainedmapper>
			</mapper>

			<path>

				<!-- lib.home contains all jar files, in several subdirectories -->
				<fileset dir="${r"${lib.rel-dir}"}">
					<include name="**/*.jar" />
				</fileset>
			</path>
		</pathconvert>
		<jar destfile="${r"${jar.rel-file}"}" compress="true" filesetmanifest="merge">
			<zipfileset dir="${r"${build.rel-dir}"}" includes="" excludes="" />
			<!-- zipfileset dir="${r"${lib.rel-dir}"}"
			prefix="${r"${lib.rel-dir}"}"
			includes=""
			excludes="" />
			<zipfileset dir="tpl"
			prefix="tpl"
			includes=""
			excludes="" /-->
			<manifest>
				<attribute name="Class-Path" value="${r"${libs.project}"}" />
			</manifest>

		</jar>
		<jar destfile="${r"${jar-annotations.rel-file}"}" compress="true" filesetmanifest="merge">
			<zipfileset dir="${r"${build.rel-dir}"}" includes="**/${bundle_namespace?replace(".","/")}/annotation/**"/>
			<!-- zipfileset dir="${r"${lib.rel-dir}"}"
			prefix="${r"${lib.rel-dir}"}"
			includes=""
			excludes="" />
			<zipfileset dir="tpl"
			prefix="tpl"
			includes=""
			excludes="" /-->
			<manifest>
				<attribute name="Class-Path" value="${r"${libs.project}"}" />
			</manifest>

		</jar>
	</target>
	
	<target name="post_build" depends="compile" description="Build the project">
		<!-- build a temporary lib dir, and flatten out the jars into one folder -->
		<!--copy todir="${r"${dist.rel-dir}"}/${r"${lib.rel-dir}"}/${r"${sherlock.rel-dir}"}">
		  <fileset dir="${r"${lib.rel-dir}"}/${r"${sherlock.rel-dir}"}"/>
		</copy-->
		<delete dir="${r"${build.rel-dir}"}" failonerror="false" deleteonexit="true" />
	</target>

	<!-- emma -->   
	<property name="emma.dir" value="../tact-core/lib/emma" />
	<property name="emma.out-dir" value="${r"${tmp.rel-dir}"}/coverage" />
	<property name="emma.instr-dir" value="${r"${tmp.rel-dir}"}/emmainstr" />
	
	<path id="emma.lib" >
	    <pathelement location="${r"${emma.dir}"}/emma.jar" />
	    <pathelement location="${r"${emma.dir}"}/emma_ant.jar" />
	</path>
	
	<taskdef resource="emma_ant.properties" classpathref="emma.lib" />
	
	<path id="emma.coverage.classes" >
	    <pathelement location="${r"${build.rel-dir}"}" />
	</path>
	
	<target name="emma"> <!-- enable code coverage -->
        <property name="emma.enabled" value="true" />
        <property name="emma.filter" value="" />
        <mkdir dir="${r"${emma.instr-dir}"}" />        
    </target>
</project>

