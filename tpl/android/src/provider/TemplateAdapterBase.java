package ${local_namespace};

import java.util.ArrayList;

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
	
	protected SQLiteDatabase mDatabase;
	protected ${project_name}SqliteOpenHelper mBaseHelper;
	
	/** Table */
	public static final String TABLE_NAME = "${name}";
	
	// Fields
	<#list fields as field>
	public static final String ${field.alias} = "${field.name}";
	</#list>
	
	/** Global Fields */
	public static final String[] COLS = new String[] {
		<#list fields as field>
		${field.alias}<#if field_has_next>,</#if>
		</#list>
	};

	/**
	 * Generate Schema for Database
	 * 
	 * @return "SQL query : CREATE TABLE..."
	 */
	public static final String getSchema() {
		return "CREATE TABLE "
		+ TABLE_NAME	+ " ("
		<#list fields as field>
		+ ${field.alias}	+ " ${field.type} <#if field_has_next>,</#if>"
		</#list>
		+ ");";
		
		//+ COL_ID 		+ " integer primary key autoincrement, "
	}
	
	// General
	/**
	 * Constructor
	 * @param ctx context
	 */
	public ${name}AdapterBase(Context ctx) {		
		this.mBaseHelper = new ${project_name}SqliteOpenHelper(
				ctx, 
				"database", 
				null,
				1);
	}
	
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}

	public void close() {
		mDatabase.close();
	}

	// Internals Converters
	/**
	 * Convert a Cursor to one entity
	 * 
	 * @param c Cursor object
	 * @return Entity
	 */
	protected ${name} cursorToEntity(Cursor c) {
		${name} result = ${name}AdapterBase.cursorTo${name}(c);
		
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Read DB(" + TABLE_NAME + ") : (" + result.hashCode() + ")");
		
		return result;
	}
	
	/**
	 * Convert a Cursor of database to Array of entity
	 * 
	 * @param c Cursor object
	 * @return Array of entity
	 */
	protected ArrayList<${name}> cursorToEntities(Cursor c) {
		ArrayList<${name}> result = new ArrayList<${name}>(c.getCount());

		if (c.getCount() != 0) {
			c.moveToFirst();
			
			${name} item;
			do {
				item = this.cursorToEntity(c);
				result.add(item);
			} while (c.moveToNext());
			
			if (BuildConfig.DEBUG)
				Log.d(TAG, "Read DB(" + TABLE_NAME + ") count : " + c.getCount() );
		}

		return result;
	}

	// Converters
	/**
	 * Convert ${name} entity to Content Values for database
	 * @param ${name?lower_case} ${name} entity object
	 * @return ContentValues object
	 */
	public static ContentValues ${name?lower_case}ToContentValues(${name} ${name?lower_case}) {		
		ContentValues result = new ContentValues();
		//result.put(COL_NAME, 	company.getName() );
		
		// TODO Insert or Update Collection of Speaker
		
		return result;
	}
	
	/**
	 * Convert Cursor of database to ${name} entity
	 * @param c Cursor object
	 * @return ${name} entity
	 */
	public static ${name} cursorTo${name}(Cursor c) {
		${name} result = null;

		if (c.getCount() != 0) {
			result = new ${name}();
			//result.setId(	c.getInt(	c.getColumnIndexOrThrow(COL_ID)));
			//result.setName(	c.getString( c.getColumnIndexOrThrow(COL_NAME)));
			
			// TODO List<Speaker> speakers = ...
			//result.setSpeakers(speakers);
		}
		
		return result;
	}
	
	// CRUD
	/**
	 * Find & read ${name} by id in database
	 * @param id Identify of ${name}
	 * @return ${name} entity
	 */
	public ${name} getByID(<#list ids as id>${id.type} ${id.name}<#if id_has_next>,</#if></#list>) {
		Cursor c = this.getSingleCursor(id);
		${name} result = this.cursorToEntity(c);
		c.close();
		
		return result;
	}
	
	/**
	 * Read All ${name}s entities
	 * @return List of ${name} entities
	 */
	public ArrayList<${name}> getAll() {
		Cursor c = this.getAllCursor();
		ArrayList<${name}> result = this.cursorToEntities(c);
		c.close();
		
		return result;
	}
	
	/**
	 * Insert a ${name} entity into database
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
	
	/**
	 * Update a ${name} entity into database 
	 * @param id Identify the ${name} entity to update
	 * @param item The ${name} entity to persist
	 * @return 
	 */
	public int update(int id, ${name} item) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update DB(" + TABLE_NAME + ")");
		
		ContentValues values = ${name}AdapterBase.${name?lower_case}ToContentValues(item);	
		String whereClause = <#list ids as id> ${id.alias} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.update(
				TABLE_NAME, 
				values, 
				whereClause, 
				whereArgs);
	}
	
	/**
	 * Delete a ${name} entity of database
	 * @param id Identify the ${name} entity to delete
	 * @return
	 */
	public int remove(int id) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Delete DB(" + TABLE_NAME + ")");
		
		String whereClause = <#list ids as id> ${id.alias} + "=? <#if id_has_next>AND </#if>"</#list>;
		String[] whereArgs = new String[] {<#list ids as id>String.valueOf(${id.name}) <#if id_has_next>, </#if></#list>};
		
		return this.mDatabase.delete(
				TABLE_NAME, 
				whereClause, 
				whereArgs);
	}
	
	protected Cursor getSingleCursor(int id) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Get entities id: " + id);
		
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
