<#include utilityPath + "all_imports.ftl" />
<#assign sync = curr.options.sync?? />
<#assign isRecursiveJoinTable = (curr.internal) && (!curr.relations[1]??) && (curr.relations[0].relation.targetEntity == entities[curr.relations[0].relation.targetEntity].fields[curr.relations[0].relation.inversedBy].relation.targetEntity) />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#assign hasInternalFields = false />
<#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />

<#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />
<@header?interpret />
package ${data_namespace}.base;

import java.util.ArrayList;
<#if hasDate || hasTime || hasDateTime>
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

<#if (MetadataUtils.hasToOneRelations(curr) || curr.internal)>
import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
</#if>
import ${data_namespace}.SQLiteAdapter;
${ImportUtils.importRelatedSQLiteAdapters(curr, false, true)}
${ImportUtils.importRelatedEntities(curr)}
${ImportUtils.importRelatedEnums(curr)}<#if !(curr_ids?size>0)>import ${project_namespace}.harmony.exception.NotImplementedException;</#if>
<#if hasDate || hasTime || hasDateTime>import ${curr.namespace}.harmony.util.DateUtils;</#if>
import ${project_namespace}.${project_name?cap_first}Application;
<#if (curr.internal)>
	<#assign extendType = "Void" />
<#else>
	<#assign extendType = curr.name />
</#if>
<#if sync>
	<#assign extend="SyncSQLiteAdapterBase<" +extendType+ ">" />
<#else>
	<#assign extend="SQLiteAdapter<" +extendType+ ">" />
</#if>

<#if curr.internal>
import ${project_namespace}.criterias.base.CriteriaExpression;
import ${project_namespace}.criterias.base.Criterion;
import ${project_namespace}.criterias.base.Criterion.Type;
import ${project_namespace}.criterias.base.CriteriaExpression.GroupType;
import ${project_namespace}.criterias.base.value.SelectValue;
</#if>
<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.harmony.util.DatabaseUtil;
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
</#if>

import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#list curr_relations as relation>
	<#if (relation.relation.type == "ManyToMany")>
import ${project_namespace}.provider.contract.${relation.relation.joinTable?cap_first}Contract;
	</#if>
import ${project_namespace}.provider.contract.${relation.relation.targetEntity?cap_first}Contract;
</#list>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase
						extends ${extend} {

	/** TAG for debug purpose. */
	protected static final String TAG = "${curr.name}DBAdapter";

<#if (InheritanceUtils.isExtended(curr))>
	/** Mother Adapter. */
	private final ${curr.inheritance.superclass.name}SQLiteAdapter motherAdapter;
</#if>

	/**
	 * Get the table name used in DB for your ${curr.name} entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return ${ContractUtils.getContractTableName(curr)};
	}

	/**
	 * Get the joined table name used in DB for your ${curr.name} entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = ${ContractUtils.getContractTableName(curr)};
		<#if (joinedInheritance)>
		result += " INNER JOIN ";
		result += this.motherAdapter.getJoinedTableName();
		result += " <#if InheritanceUtils.isExtended(entities[curr.inheritance.superclass.name])>AND<#else>ON</#if> ";
		<#list entities[curr.inheritance.superclass.name].ids as id>
		result += ${ContractUtils.getContractClass(curr)}.ALIASED_${NamingUtils.alias(curr.ids[id_index].name)} + " = " + ${ContractUtils.getContractCol(id, true)};<#if id_has_next>
		result += " AND ";</#if>
		</#list>
		</#if>
		return result;
	}

	/**
	 * Get the column names from the ${curr.name} entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return ${ContractUtils.getContractCols(curr, true)};
	}

<#if (singleTabInheritance && !isTopMostSuperClass)>
	public static String getSchemaColumns() {
		return ""
<#list curr_fields as field>
	<#if (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
		<#assign fieldNames = ContractUtils.getFieldsNames(field) />
		<#list fieldNames as fieldName>
		<#if (lastLine??)>${lastLine},"</#if>
			<#if (field.relation?? && field.relation.field_ref?size > 1)>
				<#assign lastLine=" + ${fieldName}	+ \"" + field.relation.field_ref[fieldName_index].schema?replace(" NOT NULL", "") />
			<#else>
				<#assign lastLine=" + ${fieldName}	+ \"" + field.schema?replace(" NOT NULL", "") />
			</#if>
		</#list>
	</#if>
</#list>
		${lastLine}"
		<#list curr.inheritance.subclasses as subclass>+ ${subclass.name}SQLiteAdapter.getSchemaColumns()<#if subclass_has_next> + ","</#if></#list>
;
	}

	public static String getSchemaConstraints() {
		return ""
<#if (curr.relations??)>
	<#list (curr.relations) as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
		<#assign fieldNames = ContractUtils.getFieldsNames(relation) />
		<#list fieldNames as fieldName>
		<#assign refId = relation.relation.field_ref[fieldName_index] />
		<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation=" + \"FOREIGN KEY(\" + ${fieldName}"
			+ " + \") REFERENCES \" \n\t\t\t + "
			+ "${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])} \n\t\t\t\t+ \" (\" + ${ContractUtils.getFieldsNames(refId)[0]} + \")">
		</#list>
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}"</#if>
</#if>
<#list curr_fields as field>
	<#if (field.unique?? && field.unique)>
		+ ", UNIQUE(" + ${ContractUtils.getContractCol(field)} + ")"
	</#if>
</#list>
<#if (curr.indexes?? && curr.indexes?size > 0)>
	<#list curr.indexes?keys as indexKey>
		+ "CREATE UNIQUE INDEX IF NOT EXISTS ${indexKey} ON ${curr.name}(<#list curr.indexes[indexKey] as indexColumn>${indexColumn}<#if indexColumn_has_next>, </#if></#list>);"
	</#list>
</#if>
<#list curr.inheritance.subclasses as subclass>+ ${subclass.name}SQLiteAdapter.getSchemaConstraints()<#if subclass_has_next> + ","</#if></#list>
;
	}
<#else>
	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
		return "CREATE TABLE "
		+ ${ContractUtils.getContractTableName(curr)}	+ " ("
<#list curr_fields as field>
	<#if (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
		<#assign fieldNames = ContractUtils.getFieldsNames(field) />
		<#list fieldNames as fieldName>
		<#if (lastLine??)>${lastLine},"</#if>
			<#if (field.relation?? && field.relation.field_ref?size > 1)>
				<#if field.nullable>
					<#assign lastLine=" + ${fieldName}	+ \"" + field.relation.field_ref[fieldName_index].schema?replace("NOT NULL", "") />
				<#else>
					<#assign lastLine=" + ${fieldName}	+ \"" + field.relation.field_ref[fieldName_index].schema />
				</#if>
			<#else>
				<#assign lastLine=" + ${fieldName}	+ \"" + field.schema />
			</#if>
		</#list>
	</#if>
</#list>
		${lastLine}<#if (singleTabInheritance && isTopMostSuperClass)>,"
		+ ${ContractUtils.getContractCol(curr.inheritance.discriminatorColumn)} + " ${curr.inheritance.discriminatorColumn.schema}<#if (curr.inheritance.subclasses?size > 0)>,</#if>"<#elseif MetadataUtils.hasRelationOrIds(curr, false)>,"<#else>"</#if>
		<#if (singleTabInheritance)><#list curr.inheritance.subclasses as subclass>
		+ ${subclass.name}SQLiteAdapter.getSchemaColumns() + ","
		</#list></#if>
		<#if (singleTabInheritance)><#list curr.inheritance.subclasses as subclass><#if (subclass.relations?size > 0)>
		+ ${subclass.name}SQLiteAdapter.getSchemaConstraints()<#if subclass_has_next || MetadataUtils.hasRelationOrIds(curr, false)> + ","
		</#if></#if></#list></#if>

<#if (curr.relations??)>
	<#list (curr.relations) as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
		<#assign fieldNames = ContractUtils.getFieldsNames(relation) />
		<#list fieldNames as fieldName>
		<#assign refId = relation.relation.field_ref[fieldName_index] />
		<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation=" + \"FOREIGN KEY(\" + ${fieldName}"
			+ " + \") REFERENCES \" \n\t\t\t + "
			+ "${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])} \n\t\t\t\t+ \" (\" + ${ContractUtils.getFieldsNames(refId)[0]} + \")">
		</#list>
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}"</#if>
</#if>
<#if (!((singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??) && curr_ids?size>1)>
		+ ", PRIMARY KEY (" + <#list curr_ids as id><#assign fieldNames = ContractUtils.getFieldsNames(id) /><#list fieldNames as fieldName>${fieldName}<#if (id_has_next || fieldName_has_next)> + "," + </#if></#list></#list> + ")"
</#if>
<#if (joinedInheritance)>
		+ ", FOREIGN KEY (" + ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])} + ") REFERENCES " + ${ContractUtils.getContractTableName(curr.inheritance.superclass)} + "(" + ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])} + ") ON DELETE CASCADE"
</#if>
<#list curr_fields as field>
	<#if (field.unique?? && field.unique)>
		+ ", UNIQUE(" + ${ContractUtils.getContractCol(field)} + ")"
	</#if>
</#list>
<#if !(singleTabInheritance && !isTopMostSuperClass)>
		+ ");"
</#if>
<#if (curr.indexes?? && curr.indexes?size > 0)>
	<#list curr.indexes?keys as indexKey>
		+ "CREATE UNIQUE INDEX IF NOT EXISTS ${indexKey} ON ${curr.name}(<#list curr.indexes[indexKey] as indexColumn>${indexColumn}<#if indexColumn_has_next>, </#if></#list>);"
	</#list>
</#if>
;
	}

</#if>
	<#if (InheritanceUtils.isExtended(curr))>
	@Override
	public SQLiteDatabase open() {
		SQLiteDatabase db = super.open();
		this.motherAdapter.open(db);
		return db;
	}

	@Override
	public SQLiteDatabase open(SQLiteDatabase db) {
		this.motherAdapter.open(db);
		return super.open(db);
	}
	</#if>
	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapterBase(final Context ctx) {
		super(ctx);
		<#if (InheritanceUtils.isExtended(curr))>
		this.motherAdapter = new ${curr.inheritance.superclass.name}SQLiteAdapter(ctx);
		</#if>
	}

<#if (!curr.internal)>
	// Converters

	/**
	 * Convert ${curr.name} entity to Content Values for database.
	 * @param item ${curr.name} entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item) {
		return ${ContractUtils.getContractItemToContentValues(curr)}(item);
	}

	/**
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @return ${curr.name} entity
	 */
	public ${curr.name} cursorToItem(final Cursor cursor) {
		return ${ContractUtils.getContractCursorToItem(curr)}(cursor);
	}

	/**
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @param result ${curr.name} entity
	 */
	public void cursorToItem(final Cursor cursor, final ${curr.name} result) {
		${ContractUtils.getContractCursorToItem(curr)}(cursor, result);
	}

	//// CRUD Entity ////
	/**
	 * Find & read ${curr.name} by id in database.
	 *
	 * @param id Identify of ${curr.name}
	 * @return ${curr.name} entity
	 */
	public ${curr.name} getByID(<#list curr_ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
							,</#if></#list>) {
	<#if (curr_ids?size>0)>
		final Cursor cursor = this.getSingleCursor(<#list curr_ids as id>${id.name}<#if (id_has_next)>,
										</#if></#list>);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}

		final ${curr.name} result = this.cursorToItem(cursor);
		cursor.close();

		<#list (curr_relations) as relation>
			<#if (!relation.internal)>
				<#if (relation.relation.type=="OneToMany")>
		final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter =
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		Cursor ${relation.name?lower_case}Cursor = ${relation.name?uncap_first}Adapter
					.getBy${relation.relation.mappedBy?cap_first}(<#list IdsUtils.getAllIdsGetters(curr) as id>
							result${id},</#list>
							${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)},
							null,
							null,
							null);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.cursorToItems(${relation.name?lower_case}Cursor));
				<#elseif (relation.relation.type=="ManyToMany")>
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter =
				new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		Cursor ${relation.name?lower_case}Cursor = ${relation.relation.joinTable?lower_case}Adapter.getBy${relation.relation.mappedBy?cap_first}(<#list IdsUtils.getAllIdsGetters(curr) as id>
							result${id},</#list>
							${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)},
							null,
							null,
							null);
		result.set${relation.name?cap_first}(new ${relation.relation.targetEntity}SQLiteAdapter(ctx).cursorToItems(${relation.name?lower_case}Cursor));
				<#else>
		if (result.get${relation.name?cap_first}() != null) {
			final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter =
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			
			result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.getByID(<#list IdsUtils.getAllIdsGetters(entities[relation.relation.targetEntity]) as id>
							result.get${relation.name?cap_first}()${id}<#if id_has_next>,</#if></#list>));
		}
				</#if>
			</#if>
		</#list>
		return result;
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}

	<#if (curr_relations??)>
		<#list (curr_relations) as relation>
			<#if (relation.relation.type=="ManyToOne" || relation.relation.type=="OneToOne")>
	/**
	 * Find & read ${curr.name} by ${relation.name}.
	 * @param ${relation.name?lower_case}Id ${relation.name?lower_case}Id
	 * @param orderBy Order by string (can be null)
	 * @return List of ${curr.name} entities
	 */
	 public Cursor getBy${relation.name?cap_first}(final int ${relation.name?lower_case}Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		String idSelection = <#list ContractUtils.getFieldsNames(relation) as relName>${relName} + "= ?"<#if relName_has_next>
				+ " AND "
				+</#if></#list>;
		String idSelectionArgs = String.valueOf(${relation.name?lower_case}Id);
		if (!Strings.isNullOrEmpty(selection)) {
			selection += " AND " + idSelection;
			selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
		} else {
			selection = idSelection;
			selectionArgs = new String[]{idSelectionArgs};
		}
		final Cursor cursor = this.query(
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);

		return cursor;
	 }
			</#if>
		</#list>
	</#if>

	/**
	 * Read All ${curr.name}s entities.
	 *
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
	<#if (relations??)>
		<#list (curr_relations) as relation>
			<#if (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter adapt${relation.relation.targetEntity} =
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		adapt${relation.relation.targetEntity}.open(this.mDatabase);
		for (${curr.name} ${curr.name?lower_case} : result) {
			${curr.name?lower_case}.set${relation.name?cap_first}(
					adapt${relation.relation.targetEntity}.getBy${curr.name}(
							${curr.name?lower_case}.get${curr_ids[0].name?cap_first}()));
		}

			</#if>
		</#list>
	</#if>

		return result;
	}

	<#if (singleTabInheritance && !isTopMostSuperClass)>
	@Override
	protected Cursor getAllCursor() {
		return this.query(${ContractUtils.getContractCols(curr, true)},
				${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn)} + " = ?",
				new String[]{${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER},
				null,
				null,
				null);
	}
	</#if>


	/**
	 * Insert a ${curr.name} entity into database.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(final ${curr.name} item) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + ${ContractUtils.getContractTableName(curr)} + ")");
		}

		final ContentValues values =
				${ContractUtils.getContractItemToContentValues(curr)}(item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#list curr_ids as id>
		<#if id.strategy == "IDENTITY">
			<#assign fieldNames = ContractUtils.getFieldsNames(id) />
			<#list fieldNames as fieldName>
		values.remove(${fieldName});
			</#list>
		</#if>
	</#list>
	<#if (singleTabInheritance && !isTopMostSuperClass)>
		long insertResult = this.motherAdapter.insert(null, values);		
	<#else>
	<#if !InheritanceUtils.isExtended(curr)>
		int insertResult;
	<#else>
		this.motherAdapter.open(this.mDatabase);
		final ContentValues currentValues =
				DatabaseUtil.extractContentValues(values, ${ContractUtils.getContractCols(curr)});
		<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
		values.put(${id},
				currentValues.getAsString(
						${id}));
		</#list>
		int insertResult = (int) this.motherAdapter.insert(null, values);
		<#list entities[curr.inheritance.superclass.name].ids as id>
			<#assign fieldNames = ContractUtils.getFieldsNames(id) />
			<#list fieldNames as fieldName>
				<#if id.strategy == "IDENTITY">
			currentValues.put(${fieldName}, insertResult);
				<#else>
			currentValues.put(${fieldName}, item.get${id.name?cap_first}());
				</#if>
			</#list>
		</#list>
	</#if>
		if (values.size() != 0) {
			<#if !InheritanceUtils.isExtended(curr)>insertResult = (int) </#if>this.insert(
					null,
					<#if InheritanceUtils.isExtended(curr)>currentValues<#else>values</#if>);
		} else {
			<#if !InheritanceUtils.isExtended(curr)>insertResult = (int) </#if>this.insert(
					${ContractUtils.getFieldsNames(curr_ids[0])[0]},
					<#if InheritanceUtils.isExtended(curr)>currentValues<#else>values</#if>);
		}
		<#list curr_ids as id><#if id.strategy == "IDENTITY">
		item.set${id.name?cap_first}((int) insertResult);
		</#if></#list>
	<#list (curr_relations) as relation>
		<#if (relation.relation.type=="ManyToMany")>
		if (item.get${relation.name?cap_first}() != null) {
			${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter =
					new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
				<#if curr.name != entities[relation.relation.joinTable].ids[0].relation.targetEntity>
				${relation.name?uncap_first}Adapter.insert(
						<#list relation.relation.field_ref as ref>i.get${ref.name?cap_first}()<#if ref_has_next>,
						</#if></#list>,
						<#list curr_ids as id><#if id.strategy == "IDENTITY">insertResult<#else>item.get${id.name?cap_first}()</#if><#if id_has_next>,
						</#if></#list>);
				<#else>
				${relation.name?uncap_first}Adapter.insert(<#list curr_ids as id><#if id.strategy == "IDENTITY">insertResult<#else>item.get${id.name?cap_first}()</#if><#if id_has_next>,
						</#if></#list>,
						<#list relation.relation.field_ref as ref>i.get${ref.name?cap_first}()<#if ref_has_next>,
						</#if></#list>);
				</#if>
			}
		}
		<#elseif (relation.relation.type=="OneToMany")>
		if (item.get${relation.name?cap_first}() != null) {
			${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter =
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case}
						: item.get${relation.name?cap_first}()) {
			<#if (relation.relation.mappedBy?? && !MetadataUtils.getMappedField(relation).internal)>
				${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
				${relation.name?uncap_first}Adapter.insertOrUpdate(${relation.relation.targetEntity?lower_case});
			<#else>
				${relation.name?uncap_first}Adapter.insertOrUpdateWith${curr.name?cap_first}${relation.name?cap_first}(
									${relation.relation.targetEntity?lower_case},
									insertResult);
			</#if>
			}
		}
		</#if>
	</#list>
	</#if>
		return insertResult;
	}

	/**
	 * Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final ${curr.name} item) {
		<#if (curr_ids?? && curr_ids?size > 0)>
		int result = 0;
		if (this.getByID(<#list curr_ids as id>item.get${id.name?cap_first}()<#if id_has_next>, </#if></#list>) != null) {
			// Item already exists => update it
			result = this.update(item);
		} else {
			// Item doesn't exist => create it
			final long id = this.insert(item);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
		<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
		</#if>
	}

	/**
	 * Update a ${curr.name} entity into database.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @return count of updated entities
	 */
	public int update(final ${curr.name} item) {
	<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + ${ContractUtils.getContractTableName(curr)} + ")");
		}

		final ContentValues values =
				${ContractUtils.getContractItemToContentValues(curr)}(item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause =
				<#list curr_ids as id><#list ContractUtils.getFieldsNames(id) as fieldName>${fieldName}
				+ " = ? "<#if id_has_next || fieldName_has_next> + " AND "
				+ </#if></#list></#list> + " AND "
				+ ${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn)} 
				+ " = ?";
		final String[] whereArgs =
				new String[] {<#list curr_ids as id>
						String.valueOf(item.get${id.name?cap_first}()),</#list>
						${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER};
		
		return this.motherAdapter.update(values, whereClause, whereArgs);
		<#else>
		final String whereClause =
				<#list curr_ids as id><#list ContractUtils.getFieldsNames(id) as fieldName> ${fieldName}
				 + " = ?"<#if id_has_next || fieldName_has_next>
				 + " AND "
				 +</#if></#list></#list>;
		final String[] whereArgs =
				new String[] {<#list curr_ids as id>String.valueOf(item.get${id.name?cap_first}()) <#if id_has_next>,
							  </#if></#list>};

		<#if (InheritanceUtils.isExtended(curr))>
		final ContentValues currentValues =
				DatabaseUtil.extractContentValues(values, ${ContractUtils.getContractCols(curr)});
		this.motherAdapter.update(values, whereClause, whereArgs);

		return this.update(
				currentValues,
				whereClause,
				whereArgs);
		<#else>
		return this.update(
				values,
				whereClause,
				whereArgs);
		</#if>
		</#if>
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}

	<#list (curr_relations) as relation>
		<#assign fieldNames = ContractUtils.getFieldsNames(relation) />
		<#if (relation.relation.type=="ManyToOne" && relation.internal)>

	/**
	 * Update a ${curr.name} entity into database.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return count of updated entities
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
					${curr.name} item,
					<#list relation.relation.field_ref as refField>int ${relation.name?uncap_first}${refField.name?cap_first}<#if refField_has_next>,
					</#if></#list>) {
			<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + ${ContractUtils.getContractTableName(curr)} + ")");
		}

		ContentValues values =
				${ContractUtils.getContractItemToContentValues(curr)}(item);
		<#list relation.relation.field_ref as refField>
		values.put(
				${fieldNames[refField_index]},
				${relation.name?uncap_first}${refField.name?cap_first});
		</#list>
		String whereClause =
				<#list curr_ids as id> ${ContractUtils.getContractCol(id)}
				 + "=?"<#if id_has_next>
				 + " AND "
				 +</#if></#list>;
		String[] whereArgs =
				new String[] {<#list curr_ids as id><#if !id.relation??>String.valueOf(item.get${id.name?cap_first}())<#else><#list entities[id.relation.targetEntity].ids as refId>String.valueOf(item.get${id.name?cap_first}())<#if refId_has_next>,
						</#if></#list></#if><#if id_has_next>,
						</#if></#list>};

		return this.update(
				values,
				whereClause,
				whereArgs);
			<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
			</#if>
	}


	/**
	 * Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
		int result = 0;
		<#assign id = curr_ids[0] />
		if (this.getByID(item.get${id.name?cap_first}()) != null) {
			// Item already exists => update it
			result = this.updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item,
					${relation.relation.targetEntity?lower_case}Id);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item,
					${relation.relation.targetEntity?lower_case}Id);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}


	/**
	 * Insert a ${curr.name} entity into database.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return Id of the ${curr.name} entity
	 */
	public long insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + ${ContractUtils.getContractTableName(curr)} + ")");
		}

		ContentValues values = ${ContractUtils.getContractItemToContentValues(curr)}(item<#list (curr_relations) as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>,
				${relation.relation.targetEntity?lower_case}Id<#else>,
				0</#if></#if></#list>);
	<#list curr_ids as id>
		<#list ContractUtils.getFieldsNames(id) as fieldName>
			<#if id.strategy == "IDENTITY">
		values.remove(${fieldName});
			</#if>
		</#list>
	</#list>
		int newid = (int) this.insert(
			null,
			values);

	<#list (curr_relations) as relation>
		<#if (relation.relation.type=="ManyToMany")>

		${relation.relation.joinTable}SQLiteAdapter ${relation.name?uncap_first}Adapter =
				new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0].name?cap_first}());
		}
		<#elseif (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter =
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		if (item.get${relation.name?cap_first}() != null) {
			for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()) {
				<#if (relation.relation.mappedBy?? && !MetadataUtils.getMappedField(relation).internal)>
				${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
				${relation.name?uncap_first}Adapter.update(
						${relation.relation.targetEntity?lower_case});
				<#else>
				${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(
						${relation.relation.targetEntity?lower_case}, newid);
				</#if>
			}
		}
		</#if>
	</#list>

		return newid;
	}

		</#if>
	</#list>

	/**
	 * Delete a ${curr.name} entity of database.
	 *
	 <#list curr_ids as id>* @param ${id.name} ${id.name}
	 </#list>
	 * @return count of updated entities
	 */
	public int remove(<#list curr_ids as id>final ${id.type} ${id.name}<#if (id_has_next)>
			,</#if></#list>) {
	<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(
				TAG,
				"Delete DB(" 
					+ ${ContractUtils.getContractTableName(curr)} 
					+ ")"<#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id>
					+ " id : "+ ${id}</#list>);
		}
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause =
				<#list curr_ids as id><#list ContractUtils.getFieldsNames(id) as fieldName>${fieldName}
				+ " = ? "<#if id_has_next || fieldName_has_next> + " AND "
				+ </#if></#list></#list> + " AND "
				+ ${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn)} 
				+ " = ?";

		final String[] whereArgs = new String[] {<#list curr_ids as id>
					String.valueOf(${id.name}),</#list>
					${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER};

		return this.motherAdapter.delete(
				whereClause,
				whereArgs);
		<#else>
		
		final String whereClause =<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
				<#if (id_index > 0)>+ </#if>${id} 
				+ " = ?<#if id_has_next> AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id>
					String.valueOf(${id})<#if (id_has_next)>,</#if></#list>};

		return this.delete(
				whereClause,
				whereArgs);
		</#if>
	<#else>
		throw new NotImplementedException("An entity with no ID can't implement this method.");
	</#if>
	}

	/**
	 * Deletes the given entity.
	 * @param ${curr.name?uncap_first} The entity to delete
	 * @return count of updated entities
	 */
	public int delete(final ${curr.name?cap_first} ${curr.name?uncap_first}) {
		return this.remove(<#list curr_ids as id><#if (curr_ids?size > 1)>
				</#if>${curr.name?uncap_first}.get${id.name?cap_first}()<#if id_has_next>,</#if></#list>);
	}

	/**
	 *  Internal Cursor.
	 <#list curr_ids as id>* @param ${id.name} ${id.name}
	 </#list>
	 *  @return A Cursor pointing to the ${curr.name} corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(<#list curr_ids as id><#if (curr_ids?size > 1)>
						</#if>final ${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
	<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + <#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id>${id}<#if id_has_next>
					 + " id : " + </#if></#list>);
		}

		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause =<#list IdsUtils.getAllIdsColsFromArray(curr_ids, true) as id> ${id}
					 + " = ?"<#if id_has_next>
					 + " AND "
					 +</#if></#list>
					 + " AND " + ${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn)} + " = ?";

		final String[] whereArgs = new String[] {<#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id><#if (curr_ids?size > 1)>
					</#if>String.valueOf(${id}),</#list>
					${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER};

		return this.motherAdapter.query(${ContractUtils.getContractCols(curr, true)},
				whereClause,
				whereArgs,
				null,
				null,
				null);
		<#else>
		final String whereClause =<#list IdsUtils.getAllIdsColsFromArray(curr_ids, true) as id>
				<#if (id_index > 0)>+ </#if>${id} 
				+ " = ?<#if id_has_next> AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id><#if (curr_ids?size > 1)>
				</#if>String.valueOf(${id})<#if id_has_next>,</#if></#list>};

		return this.query(
				${ContractUtils.getContractCols(curr, true)},
				whereClause,
				whereArgs,
				null,
				null,
				null);
		</#if>
	<#else>
		throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
	</#if>
	}

</#if>

	/**
	 * Query the DB to find a ${curr.name} entity.
	 *
	 * <#list curr_ids as id>@param ${id.name} The ${id.name} of the entity to get from the DB<#if id_has_next>
	 * </#if></#list>
	 *
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(<#list curr_ids as id>final ${id.type} ${id.name}<#if id_has_next>,
				</#if></#list>) {
		<#if curr_ids?size==0>
			throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
		<#else>

			<#list IdsUtils.getAllIdsColsFromArray(curr_ids, true) as id>
		<#if (id_index == 0)>String </#if>selection <#if (id_index > 0)>+</#if>= ${id} + " = ?";
		<#if id_has_next>selection += " AND ";</#if>
			</#list>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
		selection += ${ContractUtils.getContractCol(curr.inheritance.superclass.inheritance.discriminatorColumn)} + " = ?";
			</#if>

		String[] selectionArgs = new String[<#if (singleTabInheritance && !isTopMostSuperClass)>${IdsUtils.getAllIdsColsFromArray(curr_ids)?size + 1}<#else>${IdsUtils.getAllIdsColsFromArray(curr_ids)?size}</#if>];
			<#list IdsUtils.getAllIdsGettersFromArray(curr_ids) as id>
		selectionArgs[${id_index}] = String.valueOf(${id});
			</#list>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
		selectionArgs[${curr_ids?size}] = ${ContractUtils.getContractClass(curr)}.DISCRIMINATOR_IDENTIFIER;
			</#if>

		return this.query(
				${ContractUtils.getContractCols(curr, true)},
				selection,
				selectionArgs,
				null,
				null,
				null);
		</#if>
	}

<#if sync>
	@Override
	public void completeEntityRelationsServerId(${curr.name} item) {
		<#list (curr_relations) as relation>
			<#if !relation.internal>
				<#if relation.relation.type == "ManyToMany">
		${relation.relation.joinTable}SQLiteAdapter ${relation.name}Adapter =
					new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		
		${relation.name}Adapter.open(this.mDatabase);
		
		Cursor ${relation.name}Cursor = ${relation.name}Adapter.getBy${curr.name}InternalId(
				item.getId(),
				${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)},
				null, null, null);
		
		item.set${relation.name?cap_first}(
				new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx)
						.cursorToItems(${relation.name}Cursor));
		
		${relation.name}Cursor.close();
				<#elseif relation.relation.type == "OneToMany">
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter =
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
					
		${relation.name}Adapter.open(this.mDatabase);
		
		Cursor ${relation.name}Cursor = ${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(
				item.getId(),
				${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)},
				null, null, null);
		
		item.set${relation.name?cap_first}(
				${relation.name}Adapter.cursorToItems(${relation.name}Cursor));
		
		${relation.name}Cursor.close();
				<#else>
		if (item.get${relation.name?cap_first}() != null) {
			${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter =
						new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name}Adapter.open(this.mDatabase);
			item.set${relation.name?cap_first}(${relation.name}Adapter
					.getByID(item.get${relation.name?cap_first}().getId()));
		}
				</#if>
			</#if>
		</#list>
	}
</#if>

<#if (curr.internal)>
	<#assign leftRelation = curr.relations[0] />
	<#if isRecursiveJoinTable>
		<#assign rightRelation = curr.relations[0] />
	<#else>
		<#assign rightRelation = curr.relations[1] />
	</#if>
	<#assign rightRelationFieldsNames = ContractUtils.getFieldsNames(rightRelation) />
	<#assign leftRelationFieldsNames = ContractUtils.getFieldsNames(leftRelation) />

	/**
	 * Insert a ${curr.name} entity into database.
	 *
	 * @param ${leftRelation.name?lower_case} ${leftRelation.name?lower_case}
	 * @param ${rightRelation.name?lower_case} ${rightRelation.name?lower_case}
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(<#list leftRelation.relation.field_ref as refField>final ${refField.type} ${leftRelation.name?uncap_first}${refField.name?cap_first},
			</#list><#list rightRelation.relation.field_ref as refField>final ${refField.type} ${rightRelation.name?uncap_first}${refField.name?cap_first}<#if refField_has_next>,
			</#if></#list>) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + ${ContractUtils.getContractTableName(curr)} + ")");
		}

		ContentValues values = new ContentValues();
		<#list leftRelation.relation.field_ref as refField>
		values.put(${leftRelationFieldsNames[refField_index]},
				${leftRelation.name?uncap_first}${refField.name?cap_first});
		</#list>
		<#list rightRelation.relation.field_ref as refField>		
		values.put(${rightRelationFieldsNames[refField_index]},
				${rightRelation.name?uncap_first}${refField.name?cap_first});
		</#list>

		return this.mDatabase.insert(
				${ContractUtils.getContractTableName(curr)},
				null,
				values);
	}


	<#list 1..2 as i>	
	/**
	 * Find & read ${curr.name} by ${leftRelation.name}.
     * @param ${leftRelation.name?lower_case} ${rightRelation.name?lower_case}
	 * @param orderBy Order by string (can be null)
	 * @return ArrayList of ${rightRelation.relation.targetEntity} matching ${leftRelation.name?lower_case}
	 */
	public Cursor getBy${leftRelation.name?cap_first}(
			<#list leftRelation.relation.field_ref as refField>
			final ${refField.type} ${leftRelation.name?uncap_first}${refField.name?cap_first},
			</#list>
			final String[] projection,
			String selection,
			String[] selectionArgs,
			final String orderBy) {

		Cursor ret = null;
		CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
		<#list leftRelation.relation.field_ref as refField>
		crit.add(${leftRelationFieldsNames[refField_index]},
				String.valueOf(${leftRelation.name?uncap_first}${refField.name?cap_first}),
				Type.EQUALS);
		</#list>
		SelectValue value = new SelectValue();
		value.setRefKey(<#list rightRelation.relation.field_ref as refField>${rightRelationFieldsNames[refField_index]}<#if refField_has_next> + " || '::dirtyHack::' ||" + </#if></#list>);
		value.setRefTable(${ContractUtils.getContractTableName(curr)});
		value.setCriteria(crit);
		CriteriaExpression ${rightRelation.relation.targetEntity?lower_case}Crit = new CriteriaExpression(GroupType.AND);
		Criterion ${rightRelation.relation.targetEntity?lower_case}SelectCrit = new Criterion();
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.setKey(<#list entities[rightRelation.relation.targetEntity].ids as id>${ContractUtils.getContractCol(id, true)}<#if id_has_next>+ " || '::dirtyHack::' || " + </#if></#list>);
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.setType(Type.IN);
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.addValue(value);
		${rightRelation.relation.targetEntity?lower_case}Crit.add(${rightRelation.relation.targetEntity?lower_case}SelectCrit);
		
		if (Strings.isNullOrEmpty(selection)) {
			selection = ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelection();
			selectionArgs = ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelectionArgs();
		} else {
			selection += " AND " + ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelection();
			selectionArgs = ObjectArrays.concat(
						${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelectionArgs(),
						selectionArgs,
						String.class);
		}

		ret = this.mDatabase.query(${ContractUtils.getContractTableName(entities[rightRelation.relation.targetEntity])},
				projection,
				selection,
				selectionArgs,
				null,
				null,
				orderBy);
		return ret;
	}

	<#if isRecursiveJoinTable>
		<#assign leftRelation = curr.relations[0] />
	<#else>
		<#assign leftRelation = curr.relations[1] />
	</#if>
	<#assign rightRelation = curr.relations[0] />

	<#assign rightRelationFieldsNames = ContractUtils.getFieldsNames(rightRelation) />
	<#assign leftRelationFieldsNames = ContractUtils.getFieldsNames(leftRelation) />
	</#list>

	@Override
	public Void cursorToItem(Cursor c) {
		return null;
	}

	@Override
	public ContentValues itemToContentValues(Void item) {
		return null;
	}

	@Override
	public long insert(Void item) {
		return -1;
	}

	@Override
	public int update(Void item) {
		return 0;
	}

	@Override
	public int delete(Void item) {
		return 0;
	}
</#if>
}
