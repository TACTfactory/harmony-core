package ${local_namespace};

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ${project_namespace}.entity.${name};
import ${project_namespace}.BuildConfig;

/** ${name} adapter database abstract class <br/>
 * <b><i>Can't be edit this class is generate, and you loose all modification</i></b>
 */
public abstract class ${name}AdapterBase {
	private static final String TAG = "${name}DatabaseAdapter";
	
	/** Table name of SQLite database */
	public static final String TABLE_NAME = "${name}";
	
	// Columns constants fields mapping
	<#list fields as field>
	public static final String ${field.alias} = "${field.name}";
	</#list>
	
	/** Global Fields */
	public static final String[] COLS = new String[] {
		<#list fields as field>
		${field.alias}<#if field_has_next>,</#if>
		</#list>
	};

	/** Generate Schema for Database
	 * 
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
		<#list fields as field>
		+ ${field.alias}	+ " ${field.schema} <#if field_has_next>,</#if>"
		</#list>
		+ ");";
		
		//+ COL_ID 		+ " integer primary key autoincrement, "
	}
	
	// Database tools
	protected SQLiteDatabase mDatabase;
	protected ${project_name}SqliteOpenHelper mBaseHelper;
		
	/** Constructor
	 * 
	 * @param ctx context
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
	 * @return Open database
	 */
	public Database open() {
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
	 * @param ${name?lower_case} ${name} entity object
	 * @return ContentValues object
	 */
	public static ContentValues ${name?lower_case}ToContentValues(${name} ${name?lower_case}) {		
		ContentValues result = new ContentValues();
		
		<#list fields as field>
			<#if (field.type == "Date")>
		result.put(${field.alias}, 			String.valueOf(${name?lower_case}.get${field.name?cap_first}().toLocaleString()) );
			<#elseif (field.type == "Boolean")>
		result.put(${field.alias}, 			String.valueOf(${name?lower_case}.${field.name?uncap_first}()) );
			<#else>
		result.put(${field.alias}, 			String.valueOf(${name?lower_case}.get${field.name?cap_first}()) );
			</#if>
		</#list>
		
		return result;
	}
	
	/** Convert Cursor of database to ${name} entity
	 * 
	 * @param c Cursor object
	 * @return ${name} entity
	 */
	public static ${name} cursorTo${name}(Cursor c) {
		${name} result = null;

		if (c.getCount() != 0) {
			result = new ${name}();			
			<#list fields as field>
				<#if (field.type == "Date")>
			result.set${field.name?cap_first}(new Date(c.getString( c.getColumnIndexOrThrow(${field.alias})) ));
				<#elseif (field.type == "Boolean" || field.type == "boolean")>
			result.${field.name?uncap_first}(c.getString( c.getColumnIndexOrThrow(${field.alias}) ) == "true");
				<#elseif (field.type == "int" || field.type == "Integer")>
			result.set${field.name?cap_first}(c.getInt( c.getColumnIndexOrThrow(${field.alias}) ));
				<#else>
			result.set${field.name?cap_first}(c.getString( c.getColumnIndexOrThrow(${field.alias}) ));
				</#if>
			</#list>
		}
		
		return result;
	}
	
	/** Convert Cursor of database to Array of ${name} entity
	 * 
	 * @param c Cursor object
	 * @return Array of ${name} entity
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
	 * @param id Identify of ${name}
	 * @return ${name} entity
	 */
	public ${name} getByID(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		Cursor c = this.getSingleCursor(id);
		${name} result = ${name}AdapterBase.cursorTo${name}(c);
		c.close();
		
		return result;
	}
	
	/** Read All ${name}s entities
	 * 
	 * @return List of ${name} entities
	 */
	public ArrayList<${name}> getAll() {
		Cursor c = this.getAllCursor();
		ArrayList<${name}> result = ${name}AdapterBase.cursorTo${name}s(c);
		c.close();
		
		return result;
	}
	
	/** Insert a ${name} entity into database
	 * 
	 * @param item The ${name} entity to persist 
	 * @return Id of the ${name} entity
	 */
	public long insert(${name} item) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Insert DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${name}AdapterBase.${name?lower_case}ToContentValues(item);
		
		return this.mDatabase.insert(
				TABLE_NAME, 
				null, 
				values);
	}
	
	/** Update a ${name} entity into database 
	 * 
	 * @param item The ${name} entity to persist
	 * @return 
	 */
	public int update(${name} item) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${name}AdapterBase.${name?lower_case}ToContentValues(item);	
		String whereClause = <#list ids as id> ${id.alias} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(item.get${id.name?capitalize}()) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.update(
				TABLE_NAME, 
				values, 
				whereClause, 
				whereArgs);
	}
	
	/** Delete a ${name} entity of database
	 * 
	 * @param id Identify the ${name} entity to delete
	 * @return
	 */
	public int remove(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Delete DB(" + TABLE_NAME + ") id : "+ <#list ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list ids as id> ${id.alias} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.delete(
				TABLE_NAME, 
				whereClause, 
				whereArgs);
	}
	
	// Internal Cursor
	protected Cursor getSingleCursor(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get entities id : " + <#list ids as id>${id.name}<#if id_has_next> + " id : " + </#if></#list>);
		
		String whereClause = <#list ids as id> ${id.alias} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.query(
				TABLE_NAME, 
				COLS, 
				whereClause, 
				whereArgs, 
				null, 
				null, 
				null);
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
