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
import java.util.List;
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

${ImportUtils.importRelatedEntities(curr)}
${ImportUtils.importRelatedEnums(curr)}
<#if (hasDate || hasDateTime || hasTime)>import ${project_namespace}.harmony.util.DateUtils;</#if>

/**
 * ${curr.name?cap_first}DataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ${curr.name?cap_first}DataLoader
						extends FixtureBase<${curr.name?cap_first}> {
	/** ${curr.name?cap_first}DataLoader name. */
	private static final String FILE_NAME = "${curr.name}";

<#list curr_fields as field>${AdapterUtils.fixtureConstantsFieldAdapter(field, 1)}</#list>

	/** ${curr.name?cap_first}DataLoader instance (Singleton). */
	private static ${curr.name?cap_first}DataLoader instance;

	/**
	 * Get the ${curr.name?cap_first}DataLoader singleton.
	 * @param ctx The context
	 * @return The dataloader instance
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
	@Override
	protected ${curr.name} extractItem(final Element element) {
		final ${curr.name?cap_first} ${curr.name?uncap_first} =
				new ${curr.name?cap_first}();

		return this.extractItem(element, ${curr.name?uncap_first});
	}

	/**
	 * Extract an entity from a fixture element (XML).
	 * @param element The element to be extracted
	 * @param ${curr.name?uncap_first} Item in which to store the extracted data
	 * @return A ${curr.name} entity
	 */
	protected ${curr.name} extractItem(final Element element,
								final ${curr.name} ${curr.name?uncap_first}) {
<#list curr_fields as field>${AdapterUtils.xmlExtractFieldAdapter(curr.name?uncap_first, field, curr, 2)}</#list>
		<#if InheritanceUtils.isExtended(curr)>
		${curr.inheritance.superclass.name}DataLoader inheritanceDataLoader =
				${curr.inheritance.superclass.name}DataLoader.getInstance(this.ctx);
		inheritanceDataLoader.extractItem(element, ${curr.name?uncap_first});
		</#if>

	<#elseif fixtureType=="yml">
	@Override
	protected ${curr.name} extractItem(final Map<?, ?> columns) {
		final ${curr.name?cap_first} ${curr.name?uncap_first} =
				new ${curr.name?cap_first}();

		return this.extractItem(columns, ${curr.name?uncap_first});
	}
	/**
	 * Extract an entity from a fixture element (YML).
	 * @param columns Columns to extract
	 * @param ${curr.name?uncap_first} Entity to extract
	 * @return A ${curr.name} entity
	 */
	protected ${curr.name} extractItem(final Map<?, ?> columns,
				${curr.name?cap_first} ${curr.name?uncap_first}) {
		<#if InheritanceUtils.isExtended(curr)>
		${curr.inheritance.superclass.name}DataLoader.getInstance(this.ctx).extractItem(columns, ${curr.name?uncap_first});

		</#if>
<#list curr_fields as field>${AdapterUtils.ymlExtractFieldAdapter(curr.name?uncap_first, field, curr, 2)}</#list>
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
			${curr.name?uncap_first}.set${curr_ids[0].name?cap_first}(
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
		return FILE_NAME;
	}

	@Override
	protected ${curr.name} get(final String key) {
		final ${curr.name} result;
		if (this.items.containsKey(key)) {
			result = this.items.get(key);
		}
		<#if (curr.inheritance?? && curr.inheritance.subclasses??)>
			<#list curr.inheritance.subclasses as subclass>
		else if (${subclass.name}DataLoader.getInstance(this.ctx).items.containsKey(key)) {
			result = ${subclass.name}DataLoader.getInstance(this.ctx).items.get(key);
		}
			</#list>
		</#if>
		else {
			result = null;
		}
		return result;
	}
}
