<#include utilityPath + "all_imports.ftl" />
<#assign sync = curr.options.sync?? />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#assign hasInternalFields = false />
<#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />

<#assign isTopMostSuperClass = (curr.inheritance?? && !curr.inheritance.superclass??) />
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

${ImportUtils.importRelatedSQLiteAdapters(curr, true, true)}
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
	<#assign extend="SQLiteAdapterBase<" +extendType+ ">" />
</#if>

<#if curr.internal>
import ${project_namespace}.criterias.${curr.relations[0].relation.targetEntity}Criterias;
import ${project_namespace}.criterias.${curr.relations[1].relation.targetEntity}Criterias;
import ${project_namespace}.criterias.${curr.name}Criterias;
import ${project_namespace}.criterias.base.Criteria;
import ${project_namespace}.criterias.base.Criteria.Type;
import ${project_namespace}.criterias.base.CriteriasBase.GroupType;
import ${project_namespace}.criterias.base.value.SelectValue;
</#if>
<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.harmony.util.DatabaseUtil;
</#if>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase
						extends ${extend} {
	<#if (singleTabInheritance && !isTopMostSuperClass)>
	/** Identifier for inheritance. */
	public static final String DISCRIMINATOR_IDENTIFIER = "${curr.inheritance.discriminatorIdentifier}";
	</#if>
	<#if ((joinedInheritance || singleTabInheritance) && curr.inheritance.superclass??)>
	/** Mother Adapter. */
	private final ${curr.inheritance.superclass.name}SQLiteAdapter motherAdapter;
	</#if>

	/** TAG for debug purpose. */
	protected static final String TAG = "${curr.name}DBAdapter";

	/** Table name of SQLite database. */
	public static final String TABLE_NAME = <#if singleTabInheritance && !isTopMostSuperClass>${curr.inheritance.superclass.name}SQLiteAdapter.TABLE_NAME<#else>"${curr.name}"</#if>;

	/**
	 *  Columns constants fields mapping.
	 */
<#list curr_fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	/** ${field.columnName}. */
	public static final String ${NamingUtils.alias(field.name)} =
			"${field.columnName}";
	/** Alias. */
	public static final String ALIASED_${NamingUtils.alias(field.name)} =
			<#if !field.columnResult>TABLE_NAME + "." + </#if>${NamingUtils.alias(field.name)};
	</#if>
</#list>
<#if (singleTabInheritance && isTopMostSuperClass)>
	/** userGroup. */
	public static final String ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
			"${curr.inheritance.discriminatorColumn.columnName}";
	/** Alias. */
	public static final String ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = 
			TABLE_NAME + "." + ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
</#if>

	/** Global Fields. */
	public static final String[] COLS = new String[] {
<#assign firstFieldDone=false />
<#list curr_fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${field.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
<#if singleTabInheritance && !isTopMostSuperClass>
	<#list curr.inheritance.superclass.fields?values as field>
		<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	<#if (firstFieldDone)>,</#if>
		${field.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
</#if>
	};

	/** Global Fields. */
	public static final String[] ALIASED_COLS = new String[] {
<#assign firstFieldDone=false />
<#assign allFields = ViewUtils.getAllFields(curr)?values />
<#list allFields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${field.owner?cap_first}SQLiteAdapter.ALIASED_${NamingUtils.alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	};

	/**
	 * Get the table name used in DB for your ${curr.name} entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Get the joined table name used in DB for your ${curr.name} entity
	 * and its parents.
	 * @return A String showing the joined table name
	 */
	public String getJoinedTableName() {
		String result = TABLE_NAME;
		<#if (joinedInheritance)>
		result += " INNER JOIN ";
		result += this.motherAdapter.getJoinedTableName();
		result += " <#if InheritanceUtils.isExtended(entities[curr.inheritance.superclass.name])>AND<#else>ON</#if> ";
		result += ALIASED_${NamingUtils.alias(entities[curr.inheritance.superclass.name].ids[0].name)} + " = " + ${curr.inheritance.superclass.name}SQLiteAdapter.ALIASED_${NamingUtils.alias(entities[curr.inheritance.superclass.name].ids[0].name)};
		</#if>
		return result;
	}

	/**
	 * Get the column names from the ${curr.name} entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return ALIASED_COLS;
	}

	/**
	 * Generate Entity Table Schema.
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static String getSchema() {
<#if (singleTabInheritance && !isTopMostSuperClass)>
		return ""
<#else>
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
</#if>
<#list curr_fields as field>
	<#if (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
		<#if (lastLine??)>${lastLine},"</#if>
		<#assign lastLine=" + " + NamingUtils.alias(field.name) + "	+ \"" + field.schema />
	</#if>
</#list>
		${lastLine}<#if (singleTabInheritance && isTopMostSuperClass)>,"
		+ ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} + " ${curr.inheritance.discriminatorColumn.schema}<#if (curr.inheritance.subclasses?size > 0)>,</#if>"<#elseif MetadataUtils.hasRelationOrIds(curr, false)>,"<#else>"</#if>
		<#if (singleTabInheritance)><#list curr.inheritance.subclasses as subclass>+ ${subclass.name}SQLiteAdapter.getSchema()<#if subclass_has_next || MetadataUtils.hasRelationOrIds(curr, false)> + ","</#if></#list></#if>
<#if (curr.relations??)>
	<#list (curr.relations) as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
		<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation=" + \"FOREIGN KEY(\" + " + NamingUtils.alias(relation.name)
			+ " + \") REFERENCES \" \n\t\t\t + " + relation.relation.targetEntity
			+ "SQLiteAdapter.TABLE_NAME \n\t\t\t\t+ \" (\" + " + relation.relation.targetEntity
			+ "SQLiteAdapter." + NamingUtils.alias(relation.relation.field_ref[0].name) + " + \")">
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}<#if (curr_ids?size>1)>,</#if>"</#if>
</#if>
<#if (curr_ids?size>1)>
		+ "PRIMARY KEY (" + <#list curr_ids as id>${NamingUtils.alias(id.name)}<#if (id_has_next)> + "," + </#if></#list> + ")"
</#if>
<#if (joinedInheritance)>
		+ ", FOREIGN KEY (" + ${NamingUtils.alias(entities[curr.inheritance.superclass.name].ids[0].name)} + ") REFERENCES " + ${curr.inheritance.superclass.name}SQLiteAdapter.TABLE_NAME + "(" + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(entities[curr.inheritance.superclass.name].ids[0].name)} + ") ON DELETE CASCADE"
</#if>
<#list curr_fields as field>
	<#if (field.unique?? && field.unique)>
		+ ", UNIQUE(" + ${NamingUtils.alias(field.name)} + ")"
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
	<#if ((joinedInheritance || singleTabInheritance) && curr.inheritance.superclass??)>
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
		<#if ((joinedInheritance || singleTabInheritance) && curr.inheritance.superclass??)>
		this.motherAdapter = new ${curr.inheritance.superclass.name}SQLiteAdapter(ctx);
		</#if>
	}

<#if (!curr.internal)>
	// Converters
	<#if (hasInternalFields)>
	/** Convert ${curr.name} entity to Content Values for database.
	 *
	 * @param item ${curr.name} entity object<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>
	 * @param ${relation.relation.targetEntity?lower_case}Id ${relation.relation.targetEntity?lower_case} id</#if></#list>
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>,
				int ${relation.relation.targetEntity?lower_case}Id</#if></#list>) {
		final ContentValues result = this.itemToContentValues(item);
	<#list curr_fields as field>
		<#if (field.internal)>
		result.put(${NamingUtils.alias(field.name)},
				String.valueOf(${field.relation.targetEntity?lower_case}Id));
		</#if>
	</#list>
		return result;
	}
	</#if>

	/**
	 * Convert ${curr.name} entity to Content Values for database.
	 * @param item ${curr.name} entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item) {
		final ContentValues result = new ContentValues();
		<#if (InheritanceUtils.isExtended(curr))>
		result.putAll(this.motherAdapter.itemToContentValues(item));
		</#if>

<#list curr_fields as field>${AdapterUtils.itemToContentValuesFieldAdapter("item", field, 2)}</#list>
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		result.put(${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)},
					DISCRIMINATOR_IDENTIFIER);
		</#if>
		return result;
	}

	/**
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @return ${curr.name} entity
	 */
	public ${curr.name} cursorToItem(final Cursor cursor) {
		${curr.name} result = new ${curr.name}();
		this.cursorToItem(cursor, result);
		return result;
	}

	/**
	 * Convert Cursor of database to ${curr.name} entity.
	 * @param cursor Cursor object
	 * @param result ${curr.name} entity
	 */
	public void cursorToItem(final Cursor cursor, final ${curr.name} result) {
		if (cursor.getCount() != 0) {
			<#if (InheritanceUtils.isExtended(curr))>
			this.motherAdapter.cursorToItem(cursor, result);

			</#if>
			int index;

<#list curr_fields as field>${AdapterUtils.cursorToItemFieldAdapter("result", field, 3)}</#list>
		}
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
					.getBy${relation.relation.mappedBy?cap_first}(result.get${curr_ids[0].name?cap_first}(), null);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.cursorToItems(${relation.name?lower_case}Cursor));
				<#elseif (relation.relation.type=="ManyToMany")>
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter =
				new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		Cursor ${relation.name?lower_case}Cursor = ${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(
							result.get${curr_ids[0].name?cap_first}(), null);
		result.set${relation.name?cap_first}(new ${relation.relation.targetEntity}SQLiteAdapter(ctx).cursorToItems(${relation.name?lower_case}Cursor));
				<#else>
		if (result.get${relation.name?cap_first}() != null) {
			final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter =
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			
			result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter.getByID(
							result.get${relation.name?cap_first}().get${entities[relation.relation.targetEntity].ids[0].name?cap_first}()));
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
			<#if (relation.relation.type=="ManyToOne" | relation.relation.type=="OneToOne")>
	/**
	 * Find & read ${curr.name} by ${relation.name}.
	 * @param ${relation.name?lower_case}Id ${relation.name?lower_case}Id
	 * @param orderBy Order by string (can be null)
	 * @return List of ${curr.name} entities
	 */
	 public Cursor getBy${relation.name?cap_first}(final int ${relation.name?lower_case}Id, String orderBy) {
		final Cursor cursor = this.query(ALIASED_COLS,
				${relation.owner}SQLiteAdapter.${NamingUtils.alias(relation.name)} + "=?",
				new String[]{Integer.toString(${relation.name?lower_case}Id)},
				null,
				null,
				orderBy);

		return cursor;
	 }

			<#elseif (relation.relation.type=="ManyToMany")>	<#--
	/**
	 * Find & read ${curr.name} by ${relation.name}.
	 * @param ${relation.name?lower_case}Id ${relation.name?lower_case}Id
	 * @return List of ${curr.name} entities
	 */
	public Cursor getBy${relation.name?cap_first}(int ${relation.name?lower_case}Id) {
		final Cursor cursor = this.getCursor(${NamingUtils.alias(relation.name)} + "=?",
				new String[]{Integer.toString(${relation.name?lower_case}Id)});

		return cursor;
	 }

			--></#if>
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
							${curr.name?lower_case}.get${curr_ids[0].name?cap_first}())); // relation.relation.inversedBy?cap_first
		}

			</#if>
		</#list>
	</#if>

		return result;
	}

	<#if (singleTabInheritance && !isTopMostSuperClass)>
	@Override
	protected Cursor getAllCursor() {
		return this.query(ALIASED_COLS,
				${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?",
				new String[]{DISCRIMINATOR_IDENTIFIER},
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
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#if (singleTabInheritance && !isTopMostSuperClass)>
	<#list curr_ids as id>
		values.remove(${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)});
		long newid = this.motherAdapter.insert(null, values);
	</#list>		
	<#else>
	<#list curr_ids as id>
		values.remove(${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)});
	</#list>
	<#if !InheritanceUtils.isExtended(curr)>
		int newid;
	<#else>
		this.motherAdapter.open(this.mDatabase);
		final ContentValues currentValues =
				DatabaseUtil.extractContentValues(values, COLS);
		int newid = (int) this.motherAdapter.insert(null, values);
		currentValues.put(${NamingUtils.alias(entities[curr.inheritance.superclass.name].ids[0].name)}, newid);
	</#if>
		if (values.size() != 0) {
			<#if !InheritanceUtils.isExtended(curr)>newid = (int) </#if>this.insert(
					null,
					<#if InheritanceUtils.isExtended(curr)>currentValues<#else>values</#if>);

		item.set${curr_ids[0].name?cap_first}((int) newid);
	<#list (curr_relations) as relation>
		<#if (relation.relation.type=="ManyToMany")>

			${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter =
					new ${relation.relation.joinTable}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
				${relation.name?uncap_first}Adapter.insert(newid,
						i.get${relation.relation.field_ref[0].name?cap_first}());
			}
		<#elseif (relation.relation.type=="OneToMany")>
			${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter =
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			if (item.get${relation.name?cap_first}() != null) {
				for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case}
							: item.get${relation.name?cap_first}()) {
				<#if (relation.relation.mappedBy?? && !MetadataUtils.getMappedField(relation).internal)>
					${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
					${relation.name?uncap_first}Adapter.insertOrUpdate(${relation.relation.targetEntity?lower_case});
				<#else>
					${relation.name?uncap_first}Adapter.insertOrUpdateWith${curr.name?cap_first}${relation.name?cap_first}(
										${relation.relation.targetEntity?lower_case},
										newid);
				</#if>
				}
			}
		</#if>
	</#list>
		} else {
			newid = -1;
		}
	</#if>
		return newid;
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
		<#assign id = curr_ids[0] />
		if (this.getByID(item.get${id.name?cap_first}()) != null) {
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
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		final ContentValues values =
				this.itemToContentValues(item<#list (curr_relations) as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause =
				<#list curr_ids as id> ${id.owner}SQLiteAdapter.${NamingUtils.alias(id.name)}
				 + "=? <#if id_has_next>AND </#if>"</#list>
				 + " AND "
				 + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";
		final String[] whereArgs =
				new String[] {<#list curr_ids as id>String.valueOf(item.get${id.name?cap_first}()),
</#list>
								DISCRIMINATOR_IDENTIFIER};
		
		return this.motherAdapter.update(values, whereClause, whereArgs);
		<#else>
		final String whereClause =
				<#list curr_ids as id> ${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)}
				 + "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs =
				new String[] {<#list curr_ids as id>String.valueOf(item.get${id.name?cap_first}()) <#if id_has_next>,
							  </#if></#list>};

		<#if (InheritanceUtils.isExtended(curr))>
		final ContentValues currentValues =
				DatabaseUtil.extractContentValues(values, COLS);
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
		<#if (relation.relation.type=="ManyToOne" && relation.internal)>

	/**
	 * Update a ${curr.name} entity into database.
	 *
	 * @param item The ${curr.name} entity to persist
	 * @param ${relation.relation.targetEntity?lower_case}Id The ${relation.relation.targetEntity?lower_case} id
	 * @return count of updated entities
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
					${curr.name} item, int ${relation.relation.targetEntity?lower_case}Id) {
			<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}

		ContentValues values =
				this.itemToContentValues(item<#list (curr_relations) as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>,
							${relation.relation.targetEntity?lower_case}Id<#else>, 0</#if></#if></#list>);
		String whereClause =
				<#list curr_ids as id> ${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)}
				 + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs =
				new String[] {<#list curr_ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>,
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
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		ContentValues values = this.itemToContentValues(item<#list (curr_relations) as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>,
				${relation.relation.targetEntity?lower_case}Id<#else>,
				0</#if></#if></#list>);
	<#list curr_ids as id>
		values.remove(${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)});
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
		for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()) {
			<#if (relation.relation.mappedBy?? && !MetadataUtils.getMappedField(relation).internal)>
			${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(
					${relation.relation.targetEntity?lower_case});
			<#else>
			${relation.name?uncap_first}Adapter.updateWith${curr.name?cap_first}${relation.name?cap_first}(
					${relation.relation.targetEntity?lower_case}, newid);
			</#if>
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
	public int remove(<#list curr_ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
			,</#if></#list>) {
	<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME
					+ ") id : " + <#list curr_ids as id>${id.name}<#if (id_has_next)>
					+ " id : " + </#if></#list>);
		}

		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause = <#list curr_ids as id> ${id.owner}SQLiteAdapter.${NamingUtils.alias(id.name)}
					 + "=? <#if (id_has_next)>AND </#if>"</#list>
					 + " AND " + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";

		final String[] whereArgs = new String[] {<#list curr_ids as id>String.valueOf(${id.name}),
</#list>
					 DISCRIMINATOR_IDENTIFIER};

		return this.motherAdapter.delete(
				whereClause,
				whereArgs);
		<#else>
		
		final String whereClause = <#list curr_ids as id> ${id.owner?cap_first}SQLiteAdapter.${NamingUtils.alias(id.name)}
					 + "=? <#if (id_has_next)>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr_ids as id>String.valueOf(${id.name}) <#if (id_has_next)>,
					</#if></#list>};

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
		return this.delete(${curr.name?uncap_first}.get${curr_ids[0].name?cap_first}());
	}

	/**
	 *  Internal Cursor.
	 <#list curr_ids as id>* @param ${id.name} ${id.name}
	 </#list>
	 *  @return A Cursor pointing to the ${curr.name} corresponding
	 *		to the given id.
	 */
	protected Cursor getSingleCursor(<#list curr_ids as id>final ${m.javaType(id.type)} ${id.name}<#if id_has_next>
										,</#if></#list>) {
	<#if (curr_ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + <#list curr_ids as id>${id.name}<#if id_has_next>
					 + " id : " + </#if></#list>);
		}

		<#if (singleTabInheritance && !isTopMostSuperClass)>
		final String whereClause = <#list curr_ids as id> ${id.owner}SQLiteAdapter.${NamingUtils.alias(id.name)}
					 + "=? <#if (id_has_next)>AND </#if>"</#list>
					 + " AND " + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?";

		final String[] whereArgs = new String[] {<#list curr_ids as id>String.valueOf(${id.name}),
</#list>
					 DISCRIMINATOR_IDENTIFIER};

		return this.motherAdapter.query(ALIASED_COLS,
				whereClause,
				whereArgs,
				null,
				null,
				null);
		<#else>
		final String whereClause = <#list curr_ids as id> ${id.owner?cap_first}SQLiteAdapter.ALIASED_${NamingUtils.alias(id.name)}
					 + "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr_ids as id>String.valueOf(${id.name}) <#if id_has_next>,
					</#if></#list>};

		return this.query(ALIASED_COLS,
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
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result
	 */
	public Cursor query(final int id) {
		<#if curr_ids?size==0>
			throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
		<#else>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
		return this.motherAdapter.query(
				ALIASED_COLS,
				${curr_ids[0].owner}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr_ids[0].name)} + " = ?"
					+ " AND " + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?",
				new String[]{String.valueOf(id), DISCRIMINATOR_IDENTIFIER},
				null,
				null,
				null);
			<#else>
		return this.query(
				ALIASED_COLS,
				ALIASED_${NamingUtils.alias(curr_ids[0].name)} + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
			</#if>
		</#if>
	}

	/**
	 * Deletes the given entity.
	 * @param id The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		<#if curr_ids?size==0>
			throw new NotImplementedException(
				"An entity with no ID can't implement this method.");
		<#else>
			<#if (singleTabInheritance && !isTopMostSuperClass)>
		return this.delete(
				${curr_ids[0].owner}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr_ids[0].name)} + " = ?"
					+ " AND " + ${curr.inheritance.superclass.name}SQLiteAdapter.${NamingUtils.alias(curr.inheritance.superclass.inheritance.discriminatorColumn.name)} + " = ?",
				new String[]{String.valueOf(id), DISCRIMINATOR_IDENTIFIER});				
			<#else>
		return this.delete(
				ALIASED_${NamingUtils.alias(curr_ids[0].name)} + " = ?",
				new String[]{String.valueOf(id)});
			</#if>
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
		item.set${relation.name?cap_first}(new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx).cursorToItems(${relation.name}Adapter.getBy${curr.name}(item.getId(), null)));
				<#elseif relation.relation.type == "OneToMany">
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter = 
					new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
		${relation.name}Adapter.open(this.mDatabase);
		item.set${relation.name?cap_first}(${relation.name}Adapter.cursorToItems(${relation.name}Adapter.getBy${relation.relation.mappedBy?cap_first}(item.getId(), null)));
				<#else>
		if (item.get${relation.name?cap_first}() != null) {
			${relation.relation.targetEntity}SQLiteAdapter ${relation.name}Adapter = 
						new ${relation.relation.targetEntity}SQLiteAdapter(this.ctx);
			${relation.name}Adapter.open(this.mDatabase);
			item.set${relation.name?cap_first}(${relation.name}Adapter.getByID(item.get${relation.name?cap_first}().getId()));
		}
				</#if>
			</#if>
		</#list>
	}
</#if>

<#if (curr.internal)>
	<#--<#list (curr_relations) as relation>
	/**
	 * get${relation.type}s.
	 * @param id id
	 * @return ArrayList of ${relation.type} matching id
	 */
	public ArrayList<${relation.type}> get${relation.type}s(final int id) {
		final ${relation.type}SQLiteAdapter adapt =
				new ${relation.type}SQLiteAdapter(this.ctx);
		return adapt.getBy${curr.name}(id);
	}

	</#list>-->

	/**
	 * Insert a ${curr.name} entity into database.
	 *
	 * @param ${curr.relations[0].name?lower_case} ${curr.relations[0].name?lower_case}
	 * @param ${curr.relations[1].name?lower_case} ${curr.relations[1].name?lower_case}
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(final int ${curr.relations[0].name?lower_case},
					   final int ${curr.relations[1].name?lower_case}) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}

		ContentValues values = new ContentValues();
		values.put(${NamingUtils.alias(curr.relations[0].name)},
				${curr.relations[0].name?lower_case});
		values.put(${NamingUtils.alias(curr.relations[1].name)},
				${curr.relations[1].name?lower_case});

		return this.mDatabase.insert(
				TABLE_NAME,
				null,
				values);
	}


	<#assign leftRelation = curr.relations[0] />
	<#assign rightRelation = curr.relations[1] />
	<#list 1..2 as i>	
	/**
	 * Find & read ${curr.name} by ${leftRelation.name}.
     * @param ${leftRelation.name?lower_case} ${rightRelation.name?lower_case}
	 * @param orderBy Order by string (can be null)
	 * @return ArrayList of ${rightRelation.relation.targetEntity} matching ${leftRelation.name?lower_case}
	 */
	public Cursor getBy${leftRelation.relation.targetEntity}(
			final int ${leftRelation.name},
			final String orderBy) {

		Cursor ret = null;
		${curr.name}Criterias crit = new ${curr.name}Criterias(GroupType.AND);
		crit.add(ALIASED_${NamingUtils.alias(leftRelation.name)}, String.valueOf(${leftRelation.name}), Type.EQUALS);
		SelectValue value = new SelectValue();
		value.setRefKey(ALIASED_${NamingUtils.alias(rightRelation.name)});
		value.setRefTable(TABLE_NAME);
		value.setCriteria(crit);
		${rightRelation.relation.targetEntity}Criterias ${rightRelation.relation.targetEntity?lower_case}Crit = new ${rightRelation.relation.targetEntity}Criterias(GroupType.AND);
		Criteria ${rightRelation.relation.targetEntity?lower_case}SelectCrit = new Criteria();
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.setKey(${rightRelation.relation.targetEntity}SQLiteAdapter.ALIASED_COL_ID);
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.setType(Type.IN);
		${rightRelation.relation.targetEntity?lower_case}SelectCrit.addValue(value);
		${rightRelation.relation.targetEntity?lower_case}Crit.add(${rightRelation.relation.targetEntity?lower_case}SelectCrit);
		
		ret = this.mDatabase.query(${rightRelation.relation.targetEntity}SQLiteAdapter.TABLE_NAME,
				${rightRelation.relation.targetEntity}SQLiteAdapter.ALIASED_COLS,
				${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelection(),
				${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelectionArgs(),
				null,
				null,
				orderBy);
		return ret;
	}
	<#assign leftRelation = curr.relations[1] />
	<#assign rightRelation = curr.relations[0] />
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
