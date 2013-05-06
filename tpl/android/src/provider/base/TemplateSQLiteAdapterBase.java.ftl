<#assign curr = entities[current_entity] />
<#assign sync = curr.options.sync?? />
<#import "methods.ftl" as m />
<#function alias name>
	<#return "COL_" +name?upper_case />
</#function>
<#function hasRelationOrIds>
	<#if (curr.ids?size>1)><#return true /></#if>
	<#list curr.relations as relation>
		<#if (relation.relation.type!="OneToMany" && relation.relation.type!="ManyToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isInArray array var>
	<#list array as item>
		<#if (item==var)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function getMappedField field>
	<#assign ref_entity = entities[field.relation.targetEntity] />
	<#list ref_entity.fields as ref_field>
		<#if ref_field.name == field.relation.mappedBy>
			<#return ref_field />
		</#if>
	</#list>
</#function>
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#list curr.fields as field>
	<#if field.type=="date">
		<#assign hasDate=true />
	<#elseif field.type=="time">
		<#assign hasTime=true />
	<#elseif field.type="datetime">
		<#assign hasDateTime=true />
	</#if>
</#list>
package ${data_namespace}.base;

import ${data_namespace}.*;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import ${project_namespace}.${project_name?cap_first}Application;
<#if (curr.internal=="false")>
import ${curr.namespace}.entity.${curr.name};
</#if>
<#assign import_array = [curr.name] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if (!isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
import ${curr.namespace}.entity.${relation.relation.targetEntity};
		</#if>
	</#if>
</#list>
<#if hasDate || hasTime || hasDateTime>
import ${curr.namespace}.harmony.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
</#if>
<#if curr.internal?? && curr.internal=='true'>
	<#assign extendType = "Void" />
<#else>
	<#assign extendType = curr.name />
</#if>
<#if sync>
	<#assign extend="SyncSQLiteAdapterBase<" +extendType+ ">" />
<#else>
	<#assign extend="SQLiteAdapterBase<" +extendType+ ">" />
</#if>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony. You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}SQLiteAdapterBase 
									extends ${extend} {
	protected static final String TAG = "${curr.name}DBAdapter";
	
	/** Table name of SQLite database. */
	public static final String TABLE_NAME = "${curr.name}";
	
	/** Columns constants fields mapping.
	 * 
	 */
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
	public static final String ${alias(field.name)} = 
			"${field.columnName}";
	public static final String ALIASED_${alias(field.name)} = 
			TABLE_NAME + "." + ${alias(field.name)};
	</#if>
</#list>
	
	/** Global Fields. */
	public static final String[] COLS = new String[] {
<#assign firstFieldDone=false />
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
<#if (firstFieldDone)>,</#if>
		${alias(field.name)}<#assign firstFieldDone=true /></#if></#list>
	};
	
	/**
	 * Get the table name used in DB for your ${curr.name} entity.
	 * @return A String showing the table name
	 */
	public String getTableName() {
		return TABLE_NAME;
	}
	
	/**
	 * Get the column names from the ${curr.name} entity table.
	 * @return An array of String representing the columns
	 */
	public String[] getCols() {
		return COLS;
	}

	/** Generate Entity Table Schema.
	 * 
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
<#list curr.fields as field>
	<#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany"))>
		<#if (lastLine??)>${lastLine},"</#if>
		<#assign lastLine=" + " + alias(field.name) + "	+ \"" + field.schema />
	</#if>
</#list>
		${lastLine}<#if hasRelationOrIds()>,</#if>"
<#if (curr.relations??)>
	<#list curr.relations as relation>
		<#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
			<#if (lastRelation??)>${lastRelation},"</#if>
			<#assign lastRelation=" + \"FOREIGN KEY(\" + " + alias(relation.name) 
			+ " + \") REFERENCES \" + " + relation.relation.targetEntity 
			+ "SQLiteAdapter.TABLE_NAME + \" (\" + " + relation.relation.targetEntity 
			+ "SQLiteAdapter." + alias(relation.relation.field_ref[0]) + " + \")">
		</#if>
	</#list>
		<#if (lastRelation??)>${lastRelation}<#if (curr.ids?size>1)>,</#if>"</#if>
</#if>
<#if (curr.ids?size>1)>
		+ "PRIMARY KEY (" + <#list curr.ids as id>${alias(id.name)}<#if (id_has_next)> + "," + </#if></#list> + ")"
</#if>
		+ ");";
	}
	
		
	/** Constructor.
	 * 
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapterBase(final Context ctx) {	
		super(ctx);
	}
	
<#if (curr.internal!="true")>
	// Converters
	/** Convert ${curr.name} entity to Content Values for database.
	 * 
	 * @param item ${curr.name} entity object
	 * @return ContentValues object
	 */
	public ContentValues itemToContentValues(final ${curr.name} item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 
				int ${relation.relation.targetEntity?lower_case}_id</#if></#list>) {
		final ContentValues result = new ContentValues();		
	<#list curr.fields as field>
		<#if (!field.internal)>
			<#if (!field.relation??)>
		result.put(${alias(field.name)}, 			
				${m.typeToParser("item", field)});				
			<#else>
				<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
		if (item.get${field.name?cap_first}() != null) {
			result.put(${alias(field.name)},
					String.valueOf(item.get${field.name?cap_first}().getId()));
		}
		
				</#if>
			</#if>
		<#else>
		result.put(${alias(field.name)},
				String.valueOf(${field.relation.targetEntity?lower_case}_id));
		</#if>
	</#list>

		
		return result;
	}
	
	/** Convert Cursor of database to ${curr.name} entity.
	 * 
	 * @param cursor Cursor object
	 * @return ${curr.name} entity
	 */
	public ${curr.name} cursorToItem(final Cursor cursor) {
		${curr.name} result = null;

		if (cursor.getCount() != 0) {
			result = new ${curr.name}();
			
			int index;
	<#list curr.fields as field>
		<#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
			<#assign t="" />
			index = cursor.getColumnIndexOrThrow(${alias(field.name)});
			<#if (field.nullable?? && field.nullable)>
			if (!cursor.isNull(index)) {<#assign t="\t" />
			</#if>
			<#if (!field.relation??)>
				<#if ((field.type == "date") || (field.type == "datetime") || (field.type == "time"))> 
			
					<#if field.is_locale>
			${t}final DateTime dt${field.name?cap_first} = 
					DateUtils.formatLocalISOStringToDateTime(
							cursor.getString(index));	
					<#else>
			${t}final DateTime dt${field.name?cap_first} = 
					DateUtils.formatISOStringToDateTime(
							cursor.getString(index));	
					</#if>
				${t}if (dt${field.name?cap_first} != null) {
					${t}result.set${field.name?cap_first}(
							dt${field.name?cap_first});
				${t}} else {
				${t}result.set${field.name?cap_first}(new DateTime());
			${t}}
				<#elseif (field.type == "boolean")>
			${t}result.set${field.name?cap_first}(
					cursor.getString(index).equals("true"));
				<#elseif (field.type == "int" || field.type == "integer" || field.type == "ean" || field.type == "zipcode")>
			${t}result.set${field.name?cap_first}(
					cursor.getInt(index));
				<#elseif (field.type == "float")>
			${t}result.set${field.name?cap_first}(
					cursor.getFloat(index));
				<#elseif (field.type?lower_case=="string")>
			${t}result.set${field.name?cap_first}(
					cursor.getString(index)); 
				<#else>
					<#if field.columnDefinition?lower_case=="integer" || field.columnDefinition?lower_case=="int">
			${t}result.set${field.name?cap_first}(
				${curr.name}.${field.type}.fromValue(cursor.getInt(index))); 
					<#else>
			${t}result.set${field.name?cap_first}(
				${curr.name}.${field.type}.fromValue(cursor.getString(index))); 
					</#if>
				</#if>
			<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			${t}final ${field.type} ${field.name} = new ${field.type}();
			${t}${field.name}.setId(cursor.getInt(index));
			${t}result.set${field.name?cap_first}(${field.name});	
			</#if>
			<#if (field.nullable?? && field.nullable)>
			}
			</#if>
		</#if>
	</#list>
		}
		
		return result;
	}
	
	//// CRUD Entity ////
	/** Find & read ${curr.name} by id in database.
	 * 
	 * @param id Identify of ${curr.name}
	 * @return ${curr.name} entity
	 */
	public ${curr.name} getByID(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
							,</#if></#list>) {
	<#if (curr.ids?size>0)>
		final Cursor cursor = this.getSingleCursor(<#list curr.ids as id>${id.name}<#if (id_has_next)>,
										</#if></#list>);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
		}
		
		final ${curr.name} result = this.cursorToItem(cursor);
		cursor.close();
		
		<#list curr.relations as relation>
			<#if (!relation.internal)>
				<#if (relation.relation.type=="OneToMany")>
		final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(${relation.name?uncap_first}Adapter
					.getBy${relation.relation.mappedBy?cap_first}(result.getId())); // relation.relation.inversedBy?cap_first
				<#elseif (relation.relation.type=="ManyToMany")>
		${relation.relation.joinTable}SQLiteAdapter ${relation.relation.joinTable?lower_case}Adapter = 
				new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		${relation.relation.joinTable?lower_case}Adapter.open(this.mDatabase);
		result.set${relation.name?cap_first}(
					${relation.relation.joinTable?lower_case}Adapter.getBy${curr.name}(
							result.getId())); // relation.relation.inversedBy?cap_first
				<#else>
		if (result.get${relation.name?cap_first}() != null) {
			final ${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			result.set${relation.name?cap_first}(
					${relation.name?uncap_first}Adapter.getByID(
							result.get${relation.name?cap_first}().getId()));
		}
				</#if>
			</#if>
		</#list>
		return result;
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}	
	
	<#if (curr.relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="ManyToOne" | relation.relation.type=="OneToOne")>
	/** Find & read ${curr.name} by ${relation.name}.
	 * 
	 * @return List of ${curr.name} entities
	 */
	 public ArrayList<${curr.name}> getBy${relation.name?cap_first}(final int ${relation.name?lower_case}Id) {
		final Cursor cursor = this.query(COLS, ${alias(relation.name)} + "=?", 
				new String[]{Integer.toString(${relation.name?lower_case}Id)}, 
				null,
				null,
				null);
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
		
		return result;
	 }
	 
			<#elseif (relation.relation.type=="ManyToMany")>	<#--
	/** Find & read ${curr.name} by ${relation.name}.
	 * 
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getBy${relation.name?cap_first}(int ${relation.name?lower_case}Id) {
		final Cursor cursor = this.getCursor(${alias(relation.name)} + "=?", 
				new String[]{Integer.toString(${relation.name?lower_case}Id)});
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
		
		return result;
	 }		
			
			--></#if>
		</#list>
	</#if>
	
	/** Read All ${curr.name}s entities.
	 * 
	 * @return List of ${curr.name} entities
	 */
	public ArrayList<${curr.name}> getAll() {
		final Cursor cursor = this.getAllCursor();
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();		
	<#if (relations??)>
		<#list curr.relations as relation>
			<#if (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter adapt${relation.relation.targetEntity} = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		adapt${relation.relation.targetEntity}.open(this.mDatabase);
		for (${curr.name} ${curr.name?lower_case} : result) {
			${curr.name?lower_case}.set${relation.name?cap_first}(
					adapt${relation.relation.targetEntity}.getBy${curr.name}(
							${curr.name?lower_case}.getId())); // relation.relation.inversedBy?cap_first
		}
		
			</#if>
		</#list>
	</#if>
		
		return result;
	}

	
	/** Insert a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return Id of the ${curr.name} entity
	 */
	public long insert(final ${curr.name} item) {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		}
		
		final ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
	
		int newid;
	
		if (values.size() != 0) {
			newid = (int) this.insert(
					null, 
					values);
	
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
			${relation.relation.joinTable}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.joinTable}SQLiteAdapter(this.context);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
				${relation.name?uncap_first}Adapter.insert(newid, 
						i.get${relation.relation.field_ref[0]?cap_first}());
			}
		<#elseif (relation.relation.type=="OneToMany")>
			${relation.relation.targetEntity}SQLiteAdapterBase ${relation.name?uncap_first}Adapter = 
					new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
			${relation.name?uncap_first}Adapter.open(this.mDatabase);
			if (item.get${relation.name?cap_first}() != null) {
				for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()) {
				<#if (relation.relation.mappedBy?? && !getMappedField(relation).internal)>
					${relation.relation.targetEntity?lower_case}.set${relation.relation.mappedBy?cap_first}(item);
					${relation.name?uncap_first}Adapter.insertOrUpdate(${relation.relation.targetEntity?lower_case});
				<#else>
					${relation.name?uncap_first}Adapter.insertOrUpdateWith${curr.name?cap_first}${relation.name?cap_first}(${relation.relation.targetEntity?lower_case}, newid);
				</#if>
				}
			}
		</#if>
	</#list>
		} else {
			newid = -1;
		}
		
		return newid;
	}


	/** Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdate(final ${curr.name} item) {
		int result = 0;
		<#assign id = curr.ids[0] />
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
	}
	
	/** Update a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int update(final ${curr.name} item) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		}
		
		final ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);	
		final String whereClause = 
				<#list curr.ids as id> ${alias(id.name)} 
				+ "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs = 
				new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, 
							  </#if></#list>};
		
		return this.update(
				values, 
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}

	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToOne" && relation.internal)>
			
	/** Update a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist
	 * @return 
	 */
	public int updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
					${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
			<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");

		ContentValues values = 
				this.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, 
							${relation.relation.targetEntity?lower_case}_id<#else>, 0</#if></#if></#list>);	
		String whereClause = 
				<#list curr.ids as id> ${alias(id.name)} 
				+ "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = 
				new String[] {<#list curr.ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>,
				</#if></#list>};

		return this.update(
				values, 
				whereClause, 
				whereArgs);
			<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
			</#if>
	}


	/** Either insert or update a ${curr.name} entity into database whether.
	 * it already exists or not.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return 1 if everything went well, 0 otherwise
	 */
	public int insertOrUpdateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
		int result = 0;
		<#assign id = curr.ids[0] />
		if (this.getByID(item.get${id.name?cap_first}()) != null) {
			// Item already exists => update it
			result = this.updateWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item, 
					${relation.relation.targetEntity?lower_case}_id);
		} else {
			// Item doesn't exist => create it
			long id = this.insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(item, 
					${relation.relation.targetEntity?lower_case}_id);
			if (id != 0) {
				result = 1;
			}
		}

		return result;
	}

	
	/** Insert a ${curr.name} entity into database.
	 * 
	 * @param item The ${curr.name} entity to persist 
	 * @return Id of the ${curr.name} entity
	 */
	public long insertWith${relation.relation.targetEntity?cap_first}${relation.relation.inversedBy?cap_first}(
			${curr.name} item, int ${relation.relation.targetEntity?lower_case}_id) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = this.itemToContentValues(item<#list curr.relations as allRelation><#if allRelation.relation.type=="ManyToOne" && allRelation.internal><#if allRelation.relation.targetEntity==relation.relation.targetEntity && allRelation.relation.inversedBy==relation.relation.inversedBy>, 
				${relation.relation.targetEntity?lower_case}_id<#else>, 
				0</#if></#if></#list>);
	<#list curr.ids as id>
		values.remove(${alias(id.name)});
	</#list>
		int newid = (int)this.insert(
			null, 
			values);
	
	<#list curr.relations as relation>
		<#if (relation.relation.type=="ManyToMany")>
			
		${relation.relation.joinTable}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.joinTable}SQLiteAdapter(this.context);
		for (${relation.relation.targetEntity?cap_first} i : item.get${relation.name?cap_first}()) {
			${relation.name?uncap_first}Adapter.insert(newid, i.get${relation.relation.field_ref[0]?cap_first}());
		}
		<#elseif (relation.relation.type=="OneToMany")>
		${relation.relation.targetEntity}SQLiteAdapter ${relation.name?uncap_first}Adapter = 
				new ${relation.relation.targetEntity}SQLiteAdapter(this.context);
		${relation.name?uncap_first}Adapter.open(this.mDatabase);
		for (${relation.relation.targetEntity?cap_first} ${relation.relation.targetEntity?lower_case} : item.get${relation.name?cap_first}()) {
			<#if (relation.relation.mappedBy?? && !getMappedField(relation).internal)>
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
	
	/** Delete a ${curr.name} entity of database.
	 * 
	 * @param id Identify the ${curr.name} entity to delete
	 * @return
	 */
	public int remove(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if (id_has_next)>
			,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Delete DB(" + TABLE_NAME 
					+ ") id : " + <#list curr.ids as id>${id.name}<#if (id_has_next)>
					+ " id : " + </#if></#list>);
		}
		
		final String whereClause = <#list curr.ids as id> ${alias(id.name)} 
					+ "=? <#if (id_has_next)>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if (id_has_next)>,
					</#if></#list>};
		
		return this.delete(
				whereClause, 
				whereArgs);
	<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
	</#if>
	}
	
	/**
	 * Deletes the given entity.
	 * @param ${curr.name?uncap_first} The entity to delete
	 */
	public int delete(final ${curr.name?cap_first} ${curr.name?uncap_first}) {
		return this.delete(${curr.name?uncap_first}.getId());
	}
	
	/**
	 *  Internal Cursor.
	 */
	protected Cursor getSingleCursor(<#list curr.ids as id>final ${m.javaType(id.type)} ${id.name}<#if id_has_next>
										,</#if></#list>) {
	<#if (curr.ids?size>0)>
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Get entities id : " + <#list curr.ids as id>${id.name}<#if id_has_next> 
					 + " id : " + </#if></#list>);
		}
		
		final String whereClause = <#list curr.ids as id> ${alias(id.name)} 
					+ "=? <#if id_has_next>AND </#if>"</#list>;
		final String[] whereArgs = new String[] {<#list curr.ids as id>String.valueOf(${id.name}) <#if id_has_next>, 
					</#if></#list>};
		
		return this.query(COLS, 
				whereClause, 
				whereArgs, 
				null, 
				null, 
				null);
	<#else>
		throw new UnsupportedOperationException(
				"Method not implemented yet.");
	</#if>
	}
	
</#if>
	
	/**
	 * Query the DB to find a ${curr.name} entity.
	 * @param id The id of the entity to get from the DB
	 * @return The cursor pointing to the query's result 
	 */
	public Cursor query(final int id) {
		<#if curr.ids?size==0>
			throw new UnsupportedOperationException(
					"Method not implemented yet.");
		<#else>
		return this.query(
				COLS,
				COL_ID + " = ?",
				new String[]{String.valueOf(id)},
				null,
				null,
				null);
		</#if>
	}
	
	/**
	 * Deletes the given entity
	 * @param The ID of the entity to delete
	 * @return the number of token deleted
	 */
	public int delete(final int id) {
		<#if curr.ids?size==0>
			throw new UnsupportedOperationException(
					"Method not implemented yet.");
		<#else>
		return this.delete(
				COL_ID + " = ?",
				new String[]{String.valueOf(id)});
		</#if>
	}
	
<#if (curr.internal=="true")>
	<#--<#list curr.relations as relation>
	/**
	 * 
	 */
	public ArrayList<${relation.type}> get${relation.type}s(final int id) {
		final ${relation.type}SQLiteAdapter adapt = 
				new ${relation.type}SQLiteAdapter(this.context);
		return adapt.getBy${curr.name}(id);
	}

	</#list>-->
		
	/** Insert a ${curr.name} entity into database.
	 * 
	 * param item The ${curr.name} entity to persist 
	 * return Id of the ${curr.name} entity
	 */
	public long insert(int ${curr.relations[0].name?lower_case}, 
						int ${curr.relations[1].name?lower_case}) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = new ContentValues();
		values.put(${alias(curr.relations[0].name)}, 
				${curr.relations[0].name?lower_case});
		values.put(${alias(curr.relations[1].name)}, 
				${curr.relations[1].name?lower_case});
		
		return this.mDatabase.insert(
				TABLE_NAME, 
				null, 
				values);
	}
	
	<#--/** Find & read ${curr.name} by ${curr.relations[0].name}.*/
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity?cap_first}(
			int ${curr.relations[1].name?lower_case}) {
		Cursor cursor = this.getCursor(${alias(curr.relations[1].name)} 
					+ "=?", new String[]{${curr.relations[1].name?lower_case}+ ""});
		final ArrayList<${curr.name}> result = this.cursorToItems(cursor);
		cursor.close();
		
		return result;
	}-->
	/** Find & read ${curr.name} by ${curr.relations[0].name}.*/ 
	public ArrayList<${curr.relations[0].relation.targetEntity}> getBy${curr.relations[1].relation.targetEntity}(
			int ${curr.relations[1].name}) {
		String whereClause = ${alias(curr.relations[1].name)} + "=?";
		String whereArg = String.valueOf(${curr.relations[1].name});
		Cursor cursor = this.query(this.getCols(), 
				whereClause, 
				new String[]{whereArg}, 
				null, 
				null, 
				null);
		${curr.relations[0].relation.targetEntity}SQLiteAdapter adapt = 
				new ${curr.relations[0].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		final ArrayList<${curr.relations[0].relation.targetEntity}> ret = 
				new ArrayList<${curr.relations[0].relation.targetEntity}>();

		while (cursor.moveToNext()) {
			ret.add(adapt.getByID(cursor.getInt(
					cursor.getColumnIndexOrThrow(
							${alias(curr.relations[0].name)}))));
		}
		return ret;
	}

	/** Find & read ${curr.name} by ${curr.relations[0].name}v*/
	public ArrayList<${curr.relations[1].relation.targetEntity}> getBy${curr.relations[0].relation.targetEntity}(
			int ${curr.relations[0].name}) {
		String whereClause = ${alias(curr.relations[0].name)}+ "=?";
		String whereArg = String.valueOf(${curr.relations[0].name});
		Cursor cursor = this.query(this.getCols(), 
				whereClause, 
				new String[]{whereArg}, 
				null, 
				null, 
				null);
		${curr.relations[1].relation.targetEntity}SQLiteAdapter adapt = 
				new ${curr.relations[1].relation.targetEntity}SQLiteAdapter(this.context);
		adapt.open(this.mDatabase);
		final ArrayList<${curr.relations[1].relation.targetEntity}> ret = 
				new ArrayList<${curr.relations[1].relation.targetEntity}>();

		while (cursor.moveToNext()) {
			ret.add(adapt.getByID(cursor.getInt(
					cursor.getColumnIndexOrThrow(${alias(
							curr.relations[1].name)}))));
		}
		return ret;
	}
	
</#if>
}
