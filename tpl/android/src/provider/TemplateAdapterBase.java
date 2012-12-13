<#function alias name>
	<#return "COL_"+name?upper_case>
</#function>

package ${local_namespace};

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ${project_namespace}.BuildConfig;
import ${project_namespace}.entity.${name};
<#list relations as relation>
	<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
import ${project_namespace}.entity.${relation.type};
	</#if>
</#list>

/** ${name} adapter database abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${name}Adapter class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${name}AdapterBase {
	private static final String TAG = "${name}DatabaseAdapter";
	
	/** Table name of SQLite database */
	public static final String TABLE_NAME = "${name}";
	<#if relations??>
		<#list relations as relation>
			<#if relation.relation.type=="ManyToMany">
	public static final String RELATION_${relation.name?upper_case}_TABLE_NAME ="${name}_to_${relation.name?cap_first}";
			</#if>
		</#list>
	</#if>
	
	// Columns constants fields mapping
	<#list fields as field>
		<#if !field.relation??>
	public static final String ${alias(field.name)} = "${field.name}";
		</#if>
	</#list>
	<#if relations??>
		<#list relations as relation>
			<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
	public static final String REF_${relation.relation.targetEntity?upper_case}_${relation.relation.field_ref[0]?upper_case} = "${relation.name}";
			</#if>
		</#list>
	</#if>
	
	/** Global Fields */
	public static final String[] COLS = new String[] {
		<#list fields as field>
		<#if !field.relation??>
		${alias(field.name)}<#if field_has_next>,<#else><#if relations?size!=0>,</#if></#if>
		</#if>
		</#list>
		<#list relations as relation>
			<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
		REF_${relation.relation.targetEntity?upper_case}_${relation.relation.field_ref[0]?upper_case}<#if (relation_has_next && (relations[relation_index+1].relation.type=="OneToOne" | relations[relation_index+1].relation.type=="ManyToOne"))>,</#if>
			</#if>
		</#list>
	};

	/** Generate Entity Table Schema
	 * 
	 * return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
		<#list fields as field>
			<#if !field.relation??>
		+ ${alias(field.name)}	+ " ${field.schema} <#list ids as id><#if id.name==field.name & ids?size==1>PRIMARY KEY <#if field.columnDefinition=="integer">AUTOINCREMENT</#if></#if></#list><#if field_has_next | relations?size!=0 | (ids?size>1)>,</#if>"
			<#else>
		+ REF_${field.relation.targetEntity?upper_case}_${field.relation.field_ref[0]?upper_case} + " int NOT NULL,"
			</#if>
		</#list>
		<#if relations??>
			<#list relations as relation>
				<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
		+ "FOREIGN KEY("+REF_${relation.relation.targetEntity?upper_case}_${relation.relation.field_ref[0]?upper_case}+") REFERENCES ${relation.relation.targetEntity}("+${relation.relation.targetEntity}AdapterBase.COL_${relation.relation.field_ref[0]?upper_case}+")<#if (relation_has_next && (relations[relation_index+1].relation.type=="OneToOne" | relations[relation_index+1].relation.type=="ManyToOne")) | (ids?size>1)>,</#if>"
				</#if>
			</#list>
		</#if>
		<#if (ids?size>1)>
		+ "PRIMARY KEY ("+<#list ids as id>${alias(id.name)}<#if id_has_next>+","+</#if></#list>+")"
		</#if>
		+ ");";
	}
	<#if relations??>
		<#list relations as relation>
			<#if relation.relation.type=="ManyToMany">
	/** Generate Entity Relations Table Schema
	 * 
	 * return "SQL query : CREATE TABLE..."
	 */
	public static final String get${relation.name?cap_first}RelationSchema() {
		return "CREATE TABLE "
		+ RELATION_${relation.name?upper_case}_TABLE_NAME + "("
		+ "COL_ID integer primary key autoincrement,"
		+ "COL_${name?upper_case}_ID integer,"
		+ "COL_${relation.name?upper_case}_ID integer"
		+ ");";
	}
			</#if>
		</#list>
	</#if>
	
	// Database tools
	protected SQLiteDatabase mDatabase;
	protected ${project_name}SqliteOpenHelper mBaseHelper;
		
	/** Constructor
	 * 
	 * param ctx context
	 */
	public ${name}AdapterBase(Context ctx) {		
		this.mBaseHelper = new ${project_name}SqliteOpenHelper(
				ctx, 
				"database", 
				null,
				1);
	}
	
	/** Initialize and open database
	 * 
	 * return Open database
	 */
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}

	/** Close database */
	public void close() {
		mDatabase.close();
	}

	// Converters
	/** Convert ${name} entity to Content Values for database
	 * 
	 * param ${name?lower_case} ${name} entity object
	 * return ContentValues object
	 */
	public static ContentValues ${name?lower_case}ToContentValues(${name} ${name?lower_case}) {		
		ContentValues result = new ContentValues();
		
		<#list fields as field>
			<#if !field.relation??>
				<#if (field.type == "Date")>
		result.put(COL_${field.name?upper_case}, 			String.valueOf(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(${name?lower_case}.get${field.name?cap_first}())) );
				<#elseif (field.type == "Boolean")>
		result.put(COL_${field.name?upper_case}, 			String.valueOf(${name?lower_case}.${field.name?uncap_first}()) );
				<#else>
		result.put(COL_${field.name?upper_case}, 			String.valueOf(${name?lower_case}.get${field.name?cap_first}()) );
				</#if>
			</#if>
		</#list>
		<#list relations as relation>
			<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
		result.put(REF_${relation.relation.targetEntity?upper_case}_${relation.relation.field_ref[0]?upper_case}, 			String.valueOf(${name?lower_case}.get${relation.name?cap_first}().getId()) );
			</#if>
		</#list>
		
		return result;
	}
	
	/** Convert Cursor of database to ${name} entity
	 * 
	 * param c Cursor object
	 * return ${name} entity
	 */
	public static ${name} cursorTo${name}(Cursor c) {
		${name} result = null;

		if (c.getCount() != 0) {
			result = new ${name}();			
			<#list fields as field>
				<#if !field.relation??>
					<#if (field.type == "Date")>
			result.set${field.name?cap_first}(new Date());
			try {
				result.set${field.name?cap_first}(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case})) ));
			} catch (Exception e) {
				e.printStackTrace();
			}
					<#elseif (field.type == "Boolean" || field.type == "boolean")>
			result.${field.name?uncap_first}(c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ) == "true");
					<#elseif (field.type == "int" || field.type == "Integer")>
			result.set${field.name?cap_first}(c.getInt( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ));
					<#else>
			result.set${field.name?cap_first}(c.getString( c.getColumnIndexOrThrow(COL_${field.name?upper_case}) ));
					</#if>
				</#if>
			</#list>
			<#list relations as relation>
				<#if (relation.relation.type=="OneToOne" | relation.relation.type=="ManyToOne")>
			${relation.type} ${relation.name} = new ${relation.type}();
			${relation.name}.setId( Integer.valueOf(c.getString( c.getColumnIndexOrThrow(REF_${relation.relation.targetEntity?upper_case}_${relation.relation.field_ref[0]?upper_case}) )) );
			result.set${relation.name?cap_first}(${relation.name});
				</#if>
			</#list>
		}
		
		return result;
	}
	
	/** Convert Cursor of database to Array of ${name} entity
	 * 
	 * param c Cursor object
	 * return Array of ${name} entity
	 */
	public static ArrayList<${name}> cursorTo${name}s(Cursor c) {
		ArrayList<${name}> result = new ArrayList<${name}>(c.getCount());

		if (c.getCount() != 0) {
			c.moveToFirst();
			
			${name} item;
			do {
				item = ${name}AdapterBase.cursorTo${name}(c);
				result.add(item);
			} while (c.moveToNext());
			
			if (BuildConfig.DEBUG)
				Log.d(TAG, "Read DB(" + TABLE_NAME + ") count : " + c.getCount() );
		}

		return result;
	}
	// CRUD Entity
	/** Find & read ${name} by id in database
	 * 
	 * param id Identify of ${name}
	 * return ${name} entity
	 */
	public ${name} getByID(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		<#if (ids?size>0)>
		Cursor c = this.getSingleCursor(id);
		if(c.getCount()!=0)
			c.moveToFirst();
		${name} result = ${name}AdapterBase.cursorTo${name}(c);
		c.close();
		
		return result;
		<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
		</#if>
	}
	/** Read All ${name}s entities
	 * 
	 * return List of ${name} entities
	 */
	public ArrayList<${name}> getAll() {
		Cursor c = this.getAllCursor();
		ArrayList<${name}> result = ${name}AdapterBase.cursorTo${name}s(c);
		c.close();
		
		return result;
	}
	
	/** Insert a ${name} entity into database
	 * 
	 * param item The ${name} entity to persist 
	 * return Id of the ${name} entity
	 */
	public long insert(${name} item) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${name}AdapterBase.${name?lower_case}ToContentValues(item);
		<#list ids as id>
				values.remove(${alias(id.name)});
		</#list>
		
		return this.mDatabase.insert(
				TABLE_NAME, 
				null, 
				values);
	}
	
	/** Update a ${name} entity into database 
	 * 
	 * param item The ${name} entity to persist
	 * return 
	 */
	public int update(${name} item) {
		<#if (ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${name}AdapterBase.${name?lower_case}ToContentValues(item);	
		String whereClause = <#list ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.update(
				TABLE_NAME, 
				values, 
				whereClause, 
				whereArgs);
		<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
		</#if>
	}
	
	/** Delete a ${name} entity of database
	 * 
	 * param id Identify the ${name} entity to delete
	 * return
	 */
	public int remove(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		<#if (ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Delete DB(" + TABLE_NAME + ") id : "+ <#list ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.delete(
				TABLE_NAME, 
				whereClause, 
				whereArgs);
		<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
		</#if>
	}
	
	// Internal Cursor
	protected Cursor getSingleCursor(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		<#if (ids?size>0)>
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get entities id : " + <#list ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list ids as id> ${alias(id.name)} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.query(
				TABLE_NAME, 
				COLS, 
				whereClause, 
				whereArgs, 
				null, 
				null, 
				null);
		<#else>
		throw new UnsupportedOperationException("Method not implemented yet.");
		</#if>
	}
	
	protected Cursor getAllCursor() {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get all entities");
		
		return this.mDatabase.query(
				TABLE_NAME, 
				COLS, 
				null, 
				null, 
				null, 
				null, 
				null);
	}
}
