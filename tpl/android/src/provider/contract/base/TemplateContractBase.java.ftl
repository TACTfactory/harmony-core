<#assign curr = entities[current_entity] />
<#include utilityPath + "all_imports.ftl" />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<@header?interpret />
package ${project_namespace}.provider.contract.base;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

<#if hasDate || hasTime || hasDateTime>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
${ImportUtils.importRelatedEntities(curr, false)}
${ImportUtils.importRelatedEnums(curr, false)}

<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
import ${project_namespace}.provider.contract.base.${curr.inheritance.superclass.name?cap_first}ContractBase.${curr.inheritance.superclass.name?cap_first}Columns;
</#if>

import ${project_namespace}.provider.contract.${curr.name}Contract;
<#if hasDate || hasTime || hasDateTime>
import ${project_namespace}.harmony.util.DateUtils;
</#if>

/** ${project_name?cap_first} contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ${curr.name}ContractBase {

	<#if (curr.fields?size > 0 || curr.inheritance??)>
			<#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />
			<#assign hasInternalFields = false /><#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
	/**
	 * Columns names and aliases for ${curr.name} entity.
	 */
	public interface ${curr.name}Columns<#if InheritanceUtils.isExtended(curr)> extends ${curr.inheritance.superclass.name}Columns</#if> {
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		/** Identifier for inheritance. */
		public static final String DISCRIMINATOR_IDENTIFIER = "${curr.inheritance.discriminatorIdentifier}";
		</#if>
		<#list curr_fields as field>
			<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
		/** ${field.columnName}. */
		public static final String ${NamingUtils.alias(field.name)} =
				"${field.columnName}";
		/** Alias. */
		public static final String ALIASED_${NamingUtils.alias(field.name)} =
				<#if !field.columnResult>${curr.name?cap_first}Contract.${curr.name}.TABLE_NAME + "." + </#if>${NamingUtils.alias(field.name)};
			</#if>
		</#list>
		<#if (singleTabInheritance && isTopMostSuperClass)>
			/** userGroup. */
			public static final String ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
					"${curr.inheritance.discriminatorColumn.columnName}";
			/** Alias. */
			public static final String ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
					${curr.name}Contract.${curr.name}.TABLE_NAME + "." + ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
		</#if>
	}

	/**
	 * Contract base class for ${curr.name} Entity.
	 */
	public static class ${curr.name}Base implements ${curr.name}Columns {
		/** Constant for parcelisation/serialization. */
		public static final String PARCEL = "${curr.name}";
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = <#if singleTabInheritance && !isTopMostSuperClass>${curr.inheritance.superclass.name?cap_first}Contract.${curr.inheritance.superclass.name}.TABLE_NAME<#else>"${curr.name}"</#if>;
		/** Global Fields. */
		public static final String[] COLS = new String[] {
	<#assign firstFieldDone=false />
	<#list curr_fields as field>
		<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
			${field.owner?cap_first}Columns.${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	<#if singleTabInheritance && !isTopMostSuperClass>
		<#list curr.inheritance.superclass.fields?values as field>
			<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
			${field.owner?cap_first}Columns.${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	</#if>
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
	<#assign firstFieldDone=false />
	<#assign allFields = ViewUtils.getAllFields(curr)?values />
	<#list allFields as field>
		<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	<#if (firstFieldDone)>,</#if>
			${field.owner?cap_first}Columns.ALIASED_${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
		};

	<#if !curr.internal>
		<#if (hasInternalFields)>
		/** Convert ${curr.name} entity to Content Values for database.
		 *
		 * @param item ${curr.name} entity object<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>
		 * @param ${relation.relation.targetEntity?lower_case}Id ${relation.relation.targetEntity?lower_case} id</#if></#list>
		 * @return ContentValues object
		 */
		public static ContentValues itemToContentValues(final ${curr.name} item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>,
					final int ${relation.name?uncap_first}Id</#if></#list>) {
			final ContentValues result = ${curr.name?cap_first}Contract.${curr.name}.itemToContentValues(item);
		<#list curr_fields as field>
			<#if (field.internal)>
			result.put(${NamingUtils.alias(field.name)},
					String.valueOf(${field.name?uncap_first}Id));
			</#if>
		</#list>
			return result;
		}
		</#if>

		/**
		 * Converts a ${curr.name} into a content values.
		 *
		 * @param item The ${curr.name} to convert
		 *
		 * @return The content values
		 */
		public static ContentValues itemToContentValues(final ${curr.name} item) {
			final ContentValues result = new ContentValues();
			<#if (InheritanceUtils.isExtended(curr))>
			result.putAll(${curr.inheritance.superclass.name?cap_first}Contract.${curr.inheritance.superclass.name}.itemToContentValues(item));
			</#if>

<#list curr_fields as field>${AdapterUtils.itemToContentValuesFieldAdapter("item", field, 3)}</#list>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
			result.put(${curr.inheritance.superclass.name?cap_first}Contract.${curr.inheritance.superclass.name}.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)},
						${curr.name?cap_first}Contract.${curr.name}.DISCRIMINATOR_IDENTIFIER);
			</#if>
			return result;
		}

		/**
		 * Converts a Cursor into a ${curr.name}.
		 *
		 * @param cursor The cursor to convert
		 *
		 * @return The extracted ${curr.name} 
		 */
		public static ${curr.name} cursorToItem(final Cursor cursor) {
			${curr.name} result = new ${curr.name}();
			${curr.name?cap_first}Contract.${curr.name}.cursorToItem(cursor, result);
			return result;
		}

		/**
		 * Convert Cursor of database to ${curr.name} entity.
		 * @param cursor Cursor object
		 * @param result ${curr.name} entity
		 */
		public static void cursorToItem(final Cursor cursor, final ${curr.name} result) {
			if (cursor.getCount() != 0) {
				<#if (InheritanceUtils.isExtended(curr))>
				${curr.inheritance.superclass.name?cap_first}Contract.${curr.inheritance.superclass.name}.cursorToItem(cursor, result);

				</#if>
				int index;

	<#list curr_fields as field>${AdapterUtils.cursorToItemFieldAdapter("result", field, 3)}</#list>
			}
		}

		/**
		 * Convert Cursor of database to Array of ${curr.name} entity.
		 * @param cursor Cursor object
		 * @return Array of ${curr.name} entity
		 */
		public static ArrayList<${curr.name}> cursorToItems(final Cursor cursor) {
			final ArrayList<${curr.name}> result = new ArrayList<${curr.name}>(cursor.getCount());

			if (cursor.getCount() != 0) {
				cursor.moveToFirst();

				${curr.name} item;
				do {
					item = ${curr.name?cap_first}Contract.${curr.name}.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	</#if>
	}
	</#if>
}
