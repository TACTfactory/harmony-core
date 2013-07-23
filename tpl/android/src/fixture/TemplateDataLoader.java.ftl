<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasLocaleTime = MetadataUtils.hasLocaleTime(curr) />
<@header?interpret />
package ${fixture_namespace};

<#list curr.relations as relation>
	<#if relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany" || (relation.relation.type == "ManyToOne" && MetadataUtils.getInversingField(relation)??) >
import java.util.ArrayList;
	<#break>
	</#if>
</#list>
<#list curr.relations as relation>
	<#if fixtureType=="xml" && (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") >
import java.util.List;
	<#break>
	</#if>
</#list>
<#if fixtureType=="yml">
	<#if (hasTime || hasDate || hasDateTime)>
import java.util.Date;
	</#if>
import java.util.Map;
</#if>

<#if fixtureType=="xml">
import org.jdom2.Element;

<#elseif fixtureType=="yml">
	<#if hasLocaleTime>
import org.joda.time.DateTimeZone;
	</#if>
	<#if (hasTime || hasDate || hasDateTime)>
import org.joda.time.DateTime;

	</#if>
</#if>
import android.content.Context;

import ${curr.namespace}.entity.${curr.name};
${ImportUtils.importRelatedEntities(curr)}${ImportUtils.importRelatedEnums(curr)}
<#if ((fixtureType=="xml" && (hasDate || hasDateTime)) || hasTime)>import ${project_namespace}.harmony.util.DateUtils;</#if>

/**
 * ${curr.name?cap_first}DataLoader.
 */
public class ${curr.name?cap_first}DataLoader 
						extends FixtureBase<${curr.name?cap_first}> {
	/** ${curr.name?cap_first}DataLoader name. */
	private static final String NAME = "${curr.name}";
	
	/** ${curr.name?cap_first}DataLoader instance (Singleton). */
	private static ${curr.name?cap_first}DataLoader instance;
	
	/**
	 * Get the ${curr.name?cap_first}DataLoader singleton.
	 * @param ctx The context
	 */
	public static ${curr.name?cap_first}DataLoader getInstance(
											final Context ctx) {
		if (instance == null) {
			instance = new ${curr.name?cap_first}DataLoader(ctx); 
		}
		return instance;
	}
	
	/**
	 * Constructor.
	 * @param ctx The context
	 */
	private ${curr.name?cap_first}DataLoader(final Context ctx) {
		super(ctx);
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
		
		<#list curr.fields?values as field>
			<#if (!field.internal)>
		if (element.getChildText("${field.name?uncap_first}") != null) {
				<#if !field.relation??>
					<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					Integer.parseInt(element.getChildText(
							"${field.name?uncap_first}")));
					<#elseif (field.type?lower_case=="datetime")>
						<#if field.harmony_type=="date">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternDate, 
							element.getChildText("${field.name?uncap_first}")));
						<#elseif field.harmony_type=="datetime">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternDateTime, 
							element.getChildText("${field.name?uncap_first}")));
						<#elseif field.harmony_type=="time">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.format<#if field.is_locale>Local</#if>Pattern(
							patternTime, 
							element.getChildText("${field.name?uncap_first}")));
						</#if>
					<#elseif field.type=="boolean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					Boolean.parseBoolean(
							element.getChildText("${field.name?uncap_first}")));		
					<#elseif field.type?lower_case=="string">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					element.getChildText("${field.name?uncap_first}"));
					<#elseif (field.harmony_type == "enum")>
						<#assign enumType = enums[field.type] />
						<#if (enumType.id??)>
							<#assign idEnum = enumType.fields[enumType.id] />
							<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${field.type}.fromValue(
							Integer.parseInt(
									element.getChildText(
											"${field.name?uncap_first}"))));
							<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${field.type}.fromValue(
							element.getChildText(
									"${field.name?uncap_first}")));
							</#if>
						<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					${field.type}.valueOf(
							element.getChildText(
									"${field.name?uncap_first}")));
						</#if>
					</#if>
				<#else>
					<#if (field.relation.type=="OneToOne")>
			${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.ctx).getModelFixture(
									element.getChildText("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(
								${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = MetadataUtils.getInversingField(field) />
				${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(
								${curr.name?uncap_first});
						</#if>								
			}
					<#elseif (field.relation.type=="ManyToOne")>
			${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.ctx).getModelFixture(element.getChildText("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(
						${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = MetadataUtils.getInversingField(field) />
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
				if (${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.ctx).items.containsKey(${field.relation.targetEntity?uncap_first}Name.getText()))
					${field.relation.targetEntity?uncap_first}s.add(
							${field.relation.targetEntity?cap_first}DataLoader.getInstance(
									this.ctx).getModelFixture(
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
		<#list curr.fields?values as field>
			<#if (!field.internal)>
		if (columns.get("${field.name?uncap_first}") != null) {
				<#if !field.relation??>
					<#if (field.type?lower_case=="int" || field.type?lower_case=="integer" || field.type?lower_case=="zipcode" || field.type?lower_case=="ean")>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Integer) columns.get("${field.name?uncap_first}"));
					<#elseif field.type?lower_case=="double">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Double) columns.get("${field.name?uncap_first}"));
					<#elseif field.type?lower_case=="float">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					((Double) columns.get("${field.name?uncap_first}")).floatValue());
					<#elseif field.type?lower_case=="datetime">
						<#if field.harmony_type=="date">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					new DateTime(((Date) columns.get("${field.name?uncap_first}"))<#if field.is_locale>, 
							DateTimeZone.UTC</#if>));
						<#elseif field.harmony_type=="datetime">		
			${curr.name?uncap_first}.set${field.name?cap_first}(
					new DateTime(((Date) columns.get("${field.name?uncap_first}"))<#if field.is_locale>, 
							DateTimeZone.UTC</#if>));
						<#elseif field.harmony_type=="time">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					DateUtils.formatPattern(patternTime, 
							(String) columns.get("${field.name?uncap_first}")));
						</#if>
					<#elseif field.type?lower_case=="boolean">
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(Boolean) columns.get("${field.name?uncap_first}"));		
					<#elseif (field.type?lower_case == "string")>
			${curr.name?uncap_first}.set${field.name?cap_first}(
					(String) columns.get("${field.name?uncap_first}"));
					<#elseif (field.harmony_type == "enum")>
						<#assign enumType = enums[field.type] />
						<#if (enumType.id??)>
							<#assign idEnum = enumType.fields[enumType.id] />
							<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.type}.fromValue(
					(Integer) columns.get("${field.name?uncap_first}")));
							<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.type}.fromValue(
					(String) columns.get("${field.name?uncap_first}")));
							</#if>
						<#else>
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.type}.valueOf(
					(String) columns.get("${field.name?uncap_first}")));
						</#if>
					</#if>
				<#else>
					<#if field.relation.type=="ManyToOne" || field.relation.type=="OneToOne">			
			final ${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = 
					${field.relation.targetEntity?cap_first}DataLoader.getInstance(
							this.ctx).items.get(
									(String) columns.get("${field.name?uncap_first}"));
			if (${field.relation.targetEntity?uncap_first} != null) {
				${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first});
						<#if field.relation.inversedBy??>
							<#assign invField = MetadataUtils.getInversingField(field) />
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
						this.ctx).items.containsKey(
								(String) ${field.relation.targetEntity?uncap_first}Name)) {
					${field.relation.targetEntity?uncap_first}s.add(
							${field.relation.targetEntity?cap_first}DataLoader.getInstance(
									this.ctx).items.get((String) ${field.relation.targetEntity?uncap_first}Name));
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
	 * Loads ${curr.name?cap_first}s into the DataManager.
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
