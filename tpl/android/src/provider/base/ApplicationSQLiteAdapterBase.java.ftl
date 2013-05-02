package ${data_namespace}.base;

import ${data_namespace}.*;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;

public abstract class SQLiteAdapterBase<T>{

	/** Table name of SQLite database */
	public static String DB_NAME = "database.sqlite";
	public static String TAG = "${project_name?cap_first}SQLiteAdapterBase";

	
	// Database tools
	protected Context context;
	protected SQLiteDatabase mDatabase;
	protected ${project_name?cap_first}SQLiteOpenHelper mBaseHelper;
	
	
	/** Constructor
	 * 
	 * @param ctx context
	 */
	protected SQLiteAdapterBase(Context ctx) {	
		this.context = ctx;
		this.mBaseHelper = new ${project_name?cap_first}SQLiteOpenHelper(
				ctx, 
				DB_NAME, 
				null,
				1);
		
		try {
			this.mBaseHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
	}
	
	
	/** Initialize and open database
	 * 
	 * @return Open database
	 */
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}
	
	/** Initialize and open database
	 * 
	 * @return Open database
	 */
	public SQLiteDatabase open(SQLiteDatabase db) {
		this.mDatabase = db;
		return this.mDatabase;
	}

	/** Close database */
	public void close() {
		mDatabase.close();
	}
	
	
	
	protected Cursor getAllCursor() {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Get all entities");
		
		return this.query(this.getCols(), null, null, null, null, null);
	}
	
	

	public Cursor query(String[] projection, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy){
		return this.mDatabase.query(
				this.getTableName(),
				projection,
				whereClause,
				whereArgs,
				groupBy,
				having,
				orderBy);
	}
	
	public long insert(String nullColumnHack, ContentValues item){
		return this.mDatabase.insert(
				this.getTableName(),
				nullColumnHack,
				item);
	}
	
	public int delete(String whereClause, String[] whereArgs){
		return this.mDatabase.delete(
				this.getTableName(),
				whereClause,
				whereArgs);
	}
	
	public int update(ContentValues item, String whereClause, String[] whereArgs){
		return this.mDatabase.update(
				this.getTableName(),
				item,
				whereClause,
				whereArgs);
	}
	
	public abstract String getTableName();
	public abstract String[] getCols();
	
		
	/** Read All Comments entities
	 * 
	 * @return List of Comment entities
	 */
	public ArrayList<T> getAll() {
		Cursor c = this.getAllCursor();
		ArrayList<T> result = this.cursorToItems(c);
		c.close();
		
		
		return result;
	}
	
	/** Read All T entities given some criterias
	 * @param crits The criterias to use for the request
	 * @return List of T entities
	 */
	public ArrayList<T> getAll(CriteriasBase crits) {
		if (crits == null || crits.isEmpty()){
			return this.getAll();
		} else {
			Cursor c = this.mDatabase.rawQuery("SELECT * FROM "+this.getTableName()+" WHERE "+crits.toSQLiteString(), null);
			ArrayList<T> result = this.cursorToItems(c);
			c.close();
			return result;
		}	
	}
	
	/** Convert Cursor of database to Array of Comment entity
	 * 
	 * @param c Cursor object
	 * @return Array of Comment entity
	 */
	public ArrayList<T> cursorToItems(Cursor c) {
		ArrayList<T> result = new ArrayList<T>(c.getCount());

		if (c.getCount() != 0) {
			c.moveToFirst();
			
			T item;
			do {
				item = this.cursorToItem(c);
				result.add(item);
			} while (c.moveToNext());
			
			//if (DemactApplication.DEBUG)
			//	Log.d(TAG, "Read DB(" + TABLE_NAME + ") count : " + c.getCount() );
		}

		return result;
	}
	
	/** Convert Cursor of database to Comment entity
	 * 
	 * @param c Cursor object
	 * @return Comment entity
	 */
	public abstract T cursorToItem(Cursor c);

	/** Insert a Comment entity into database
	 * 
	 * @param item The Comment entity to persist 
	 * @return Id of the Comment entity
	 */
	public abstract long insert(T item);
	
	/** Update a Comment entity into database 
	 * 
	 * @param item The Comment entity to persist
	 * @return 
	 */
	public abstract int update(T item);
	
	public abstract int delete(T item);
}
