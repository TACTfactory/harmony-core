<?xml version="1.0"?>
<ruleset name="rule set"
    xmlns="http://pmd.sf.net/ruleset/1.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sf.net/ruleset/1.0.0 http://pmd.sf.net/ruleset_xml_schema.xsd"
    xsi:noNamespaceSchemaLocation="http://pmd.sf.net/ruleset_xml_schema.xsd">

    <description>
        rules
    </description>

    <!-- imports -->
    <rule ref="rulesets/java/basic.xml"></rule>
    <rule ref="rulesets/java/braces.xml"></rule>
    <rule ref="rulesets/java/codesize.xml"></rule>
    <rule ref="rulesets/java/clone.xml"></rule>

    <rule ref="rulesets/java/javabeans.xml">
        <exclude name="BeanMembersShouldSerialize"/>
    </rule>

    <rule ref="rulesets/java/strings.xml"></rule>

    <!-- CyclomaticComplexity with a threshold of 12 -->
    <!-- rule ref="rulesets/java/codesize.xml/CyclomaticComplexity">
        <priority>1</priority>
        <properties>
            <property name="reportLevel" value="12" />
        </properties>
    </rule -->

    <rule ref="rulesets/java/controversial.xml">
        <!-- exclude name="UnnecessaryConstructor"/ -->
        <!-- exclude name="NullAssignment"/ -->

        <exclude name="AtLeastOneConstructor"/>
        <exclude name="DataflowAnomalyAnalysis"/>
        <exclude name="AvoidFinalLocalVariable"/>
   </rule>

	<exclude-pattern>${project_namespace}.criterias.base.*</exclude-pattern>
	<exclude-pattern>${project_namespace}.data.base.*</exclude-pattern>
	<exclude-pattern>${project_namespace}.harmony.*</exclude-pattern>
	<exclude-pattern>${project_namespace}.provider.base.*</exclude-pattern>
	<exclude-pattern>${project_namespace}.provider.utils.base.*</exclude-pattern>
	<exclude-pattern>${project_namespace}.${project_name?cap_first}ApplicationBase.java</exclude-pattern>
</ruleset>
