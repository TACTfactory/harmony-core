<#function getInversingField field>
	<#assign entityT = entities[field.relation.targetEntity] />
	<#list entityT.relations as f>
		<#if f.name == field.relation.inversedBy>
			<#return f />
		</#if>
	</#list>
	<#return "">
</#function>
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;
<#assign isToMany=false />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#list curr.relations as relation>
	<#if relation.relation.type=="ManyToMany" || relation.relation.type=="OneToMany">
		<#assign isToMany=true />
	</#if>
</#list>
<#list curr.fields as field>
	<#if field.type=="date">
		<#assign hasDate=true />
	<#elseif field.type=="time">
		<#assign hasTime=true />
	<#elseif field.type="datetime">
		<#assign hasDateTime=true />
	</#if>
</#list>

<#assign hasLocaleTime = false />
<#list curr.fields as field>
	<#if field.is_locale?? && field.is_locale>
		<#assign hasLocaleTime = true />
	</#if>
</#list>
<#if hasLocaleTime>
import org.joda.time.DateTimeZone;
</#if>

import ${project_namespace}.entity.*;
import java.util.ArrayList;
import java.util.List;

<#if fixtureType=="xml">
	<#if (hasTime || hasDate || hasDateTime)>
import ${project_namespace}.harmony.util.DateUtils;
	</#if>
import org.jdom2.Element;
<#elseif fixtureType=="yml">
	<#if (hasTime)>
import ${project_namespace}.harmony.util.DateUtils;
	</#if>
	<#if (hasTime || hasDate || hasDateTime)>
import java.util.Date;
import org.joda.time.DateTime;
	</#if>
import org.yaml.snakeyaml.Yaml;
import java.util.Map;

</#if>

import ${curr.namespace}.entity.${curr.name};

/**
 * ${curr.name?cap_first}DataLoader.
 */
public class ${curr.name?cap_first}DataLoader 
						extends FixtureBase<${curr.name?cap_first}> {
	private static final String NAME = "${curr.name}";
	
	private static ${curr.name?cap_first}DataLoader instance;
	
	/**
	 * Get the ${curr.name?cap_first}DataLoader singleton.
	 * @param context The context
	 */
	public static ${curr.name?cap_first}DataLoader getInstance(
											final Context context) {
		if (instance == null) {
			instance = new ${curr.name?cap_first}DataLoader(context); 
		}
		return instance;
	}
	
	/**
	 * Constructor.
	 * @param context The context
	 */
	private ${curr.name?cap_first}DataLoader(final Context context) {
		super(context);
	}

	
	<#if fixtureType=="xml">
	/**
	 * Extract an entity from a fixture element (XML).
	 * @param element The element to be extracted
	 * @return A ${curr.name} entity
	 */
	@Override
	protected ${curr.name} extractItem(final Element element) {
		final ${curr.name?cap_first} ${curr.name?uncap_first} = 
				new ${curr.name?cap_first}();
		
		<#list curr.fields as field>
			<#if (!field.internal)>
		if (element.getChildText("${field.name?uncap_first}") != null) {
				<#if !field.relation??>
					<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					Integer.parseInt(element.getChildText(
							"${field.name?uncap_first}")));
					<#elseif field.type=="date">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternDate, 
							element.getChildText("${field.name?uncap_first}")));
					<#elseif field.type=="datetime">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternDateTime, 
							element.getChildText("${field.name?uncap_first}")));
					<#elseif field.type=="time">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternTime, 
							element.getChildText("${field.name?uncap_first}")));
					<#elseif field.type=="boolean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					Boolean.parseBoolean(
							element.getChildText("${field.name?uncap_first}")));		
					<#elseif field.type?lower_case=="string">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					element.getChildText("${field.name?uncap_first}"));
					<#else>
						<#if field.columnDefinition?lower_case=="integer" || field.columnDefinition?lower_case=="int">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${curr.name}.${field.type}.fromValue(
							Integer.parseInt(
									element.getChildText(
											"${field.name?uncap_first}"))));
						<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${curr.name}.${field.type}.fromValue(
							element.getChildText(
									"${field.name?uncap_first}")));
						</#if>
					</#if>
				<#else>
					<#if (field.relation.type=="OneToOne")>
			${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.context).getModelFixture(
									element.getChildText("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(
								${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = getInversingField(field) />
				${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(
								${curr.name?uncap_first});
						</#if>								
			}
					<#elseif (field.relation.type=="ManyToOne")>
			${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.context).getModelFixture(element.getChildText("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(
						${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = getInversingField(field) />
				ArrayList<${curr.name?cap_first}> ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s = 
						${field.relation.targetEntity?uncap_first}.get${invField.name?cap_first}();
				if (${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s == null) {
					${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s = 
							new ArrayList<${curr.name?cap_first}>();
				}							
				${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s.add(${curr.name?uncap_first});
				${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s);
						</#if>
			}	
					<#else>
			ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s = 
					new ArrayList<${field.relation.targetEntity?cap_first}>();
			List<Element> ${field.relation.targetEntity?uncap_first}sMap = 
					element.getChild("${field.name?uncap_first}").getChildren();
			for (Element ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap) {
				if (${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.context).items.containsKey(${field.relation.targetEntity?uncap_first}Name.getText()))
					${field.relation.targetEntity?uncap_first}s.add(
							${field.relation.targetEntity?cap_first}DataLoader.getInstance(
									this.context).getModelFixture(
											${field.relation.targetEntity?uncap_first}Name.getText()));
			}
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${field.relation.targetEntity?uncap_first}s);		
					</#if>
				</#if>
		}
			</#if>
		</#list>

	<#elseif fixtureType=="yml">
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @return A ${curr.name} entity
	 */
	@Override
	protected ${curr.name} extractItem(final Map<?, ?> columns) {
		final ${curr.name?cap_first} ${curr.name?uncap_first} = 
				new ${curr.name?cap_first}();
		<#list curr.fields as field>
			<#if (!field.internal)>
		if (columns.get("${field.name?uncap_first}") != null) {
				<#if !field.relation??>
					<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Integer) columns.get("${field.name?uncap_first}"));
					<#elseif field.type=="double">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Double) columns.get("${field.name?uncap_first}"));
					<#elseif field.type=="float">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					((Double) columns.get("${field.name?uncap_first}")).floatValue());
					<#elseif field.type=="date">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					new DateTime(((Date) columns.get("${field.name?uncap_first}"))<#if field.is_locale>, 
							DateTimeZone.UTC</#if>));
					<#elseif field.type=="datetime">		
			${curr.name?uncap_first}.set${field.name?cap_first}(
					new DateTime(((Date) columns.get("${field.name?uncap_first}"))<#if field.is_locale>, 
							DateTimeZone.UTC</#if>));
					<#elseif field.type=="time">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.formatPattern(patternTime, 
							(String) columns.get("${field.name?uncap_first}")));
					<#elseif field.type=="boolean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Boolean) columns.get("${field.name?uncap_first}"));		
					<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(String) columns.get("${field.name?uncap_first}"));
					</#if>
				<#else>
					<#if field.relation.type=="ManyToOne" || field.relation.type=="OneToOne">			
			final ${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.context).items.get(
									(String) columns.get("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = getInversingField(field) />
				ArrayList<${curr.name?cap_first}> ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s = 
						${field.relation.targetEntity?uncap_first}.get${invField.name?cap_first}();
				if (${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s == null) {
					${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s = 
							new ArrayList<${curr.name?cap_first}>();
				}							
				${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s.add(${curr.name?uncap_first});
				${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(
						${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s);
						</#if>
			}

					<#else>
			ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s = 
					new ArrayList<${field.relation.targetEntity?cap_first}>();
			final Map<?, ?> ${field.relation.targetEntity?uncap_first}sMap = 
					(Map<?, ?>) columns.get("${field.name?uncap_first}");
			for (final Object ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap.values()) {
				if (${field.relation.targetEntity?cap_first}DataLoader.getInstance(
						this.context).items.containsKey(
								(String) ${field.relation.targetEntity?uncap_first}Name)) {
					${field.relation.targetEntity?uncap_first}s.add(
							${field.relation.targetEntity?cap_first}DataLoader.getInstance(
									this.context).items.get((String) ${field.relation.targetEntity?uncap_first}Name));
				}
			}
			${curr.name?uncap_first}.set${field.name?cap_first}(
						${field.relation.targetEntity?uncap_first}s);		
					</#if>
				</#if>
		}
			</#if>
		</#list>
	</#if>

		return ${curr.name?uncap_first};
	}
	/**
	 * Loads ${curr.name?cap_first}s into the DataManager
	 * @param manager The DataManager
	 */
	@Override
	public void load(final DataManager manager) {
		for (final ${curr.name?cap_first} ${curr.name?uncap_first} : this.items.values()) {
			${curr.name?uncap_first}.setId(
					manager.persist(${curr.name?uncap_first}));
		}
		manager.flush();
	}
	
	/**
	 * Give priority for fixtures insertion in database.
	 * 0 is the first.
	 * @return The order
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * Get the fixture file name.
	 * @return A String representing the file name 
	 */
	@Override
	public String getFixtureFileName() {
		return NAME;
	}
}
