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
	//public interface ${curr.name}Columns<#if InheritanceUtils.isExtended(curr)> extends ${curr.inheritance.superclass.name}Columns</#if> {
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		/** Identifier for inheritance. */
		public static final String DISCRIMINATOR_IDENTIFIER = "${curr.inheritance.discriminatorIdentifier}";
		</#if>
		<#list curr_fields as field><#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>${ContractUtils.getFieldsDeclarations(field, curr)}</#if></#list>
		<#if (singleTabInheritance && isTopMostSuperClass)>
			/** Discriminator column. */
			public static final String ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
					"${curr.inheritance.discriminatorColumn.columnName}";
			/** Alias. */
			public static final String ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
					${curr.name}Contract.TABLE_NAME + "." + ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
		</#if>
	//}

	
		/** Constant for parcelisation/serialization. */
		public static final String PARCEL = "${curr.name}";
		/** Table name of SQLite database. */
		public static final String TABLE_NAME = <#if singleTabInheritance && !isTopMostSuperClass>${curr.inheritance.superclass.name?cap_first}Contract.TABLE_NAME<#else>"${curr.name}"</#if>;
		/** Global Fields. */
		public static final String[] COLS = new String[] {
		<#assign wholeFields = curr_fields />
		<#if singleTabInheritance && !isTopMostSuperClass>
			<#assign wholeFields = wholeFields + curr.inheritance.superclass.fields?values />
		</#if>
		<#list wholeFields as field>
			<#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>
				<#assign fieldNames = ContractUtils.getFieldsNames(field) />
				<#list fieldNames as name>
				${name}<#if field_has_next || name_has_next>,</#if>
				</#list>
			</#if>
		</#list>
		};

		/** Global Fields. */
		public static final String[] ALIASED_COLS = new String[] {
		<#list ViewUtils.getAllFields(curr)?values as field>
			<#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>
				<#assign fieldNames = ContractUtils.getFieldsNames(field, true) />
				<#list fieldNames as name>
				${name}<#if field_has_next || name_has_next>,</#if>
				</#list>
			</#if>
		</#list>
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
			final ContentValues result = ${curr.name?cap_first}Contract.itemToContentValues(item);
		<#list curr_fields as field>
			<#if (field.internal)>
				<#assign fieldNames = ContractUtils.getFieldsNames(field) />
					<#list field.relation.field_ref as refField>
			result.put(${fieldNames[refField_index]},
					String.valueOf(${field.name?uncap_first}Id));
					</#list>
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
			result.putAll(${curr.inheritance.superclass.name?cap_first}Contract.itemToContentValues(item));
			</#if>

<#list curr_fields as field>${AdapterUtils.itemToContentValuesFieldAdapter("item", field, 3)}</#list>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
			result.put(${curr.inheritance.superclass.name?cap_first}Contract.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)},
						${curr.name?cap_first}Contract.DISCRIMINATOR_IDENTIFIER);
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
			${curr.name?cap_first}Contract.cursorToItem(cursor, result);
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
				${curr.inheritance.superclass.name?cap_first}Contract.cursorToItem(cursor, result);

				</#if>
				int index;

<#list curr_fields as field>${AdapterUtils.cursorToItemFieldAdapter("result", field, 4)}</#list>
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
					item = ${curr.name?cap_first}Contract.cursorToItem(cursor);
					result.add(item);
				} while (cursor.moveToNext());
			}

			return result;
		}
	</#if>
	</#if>
}
