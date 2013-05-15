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
import java.util.List;

import ${project_namespace}.criterias.base.CriteriasBase;

/**
 * This is the base SQLiteAdapter.
 * @param <T> Type
 */
public abstract class SQLiteAdapterBase<T> {

	/** Table name of SQLite database. */
	public static String DB_NAME = "database.sqlite";
	/** TAG for debug purpose. */
	public static String TAG = "${project_name?cap_first}SQLiteAdapterBase";

	
	/** Database tools. */
	protected Context ctx;
	protected SQLiteDatabase mDatabase;
	protected ${project_name?cap_first}SQLiteOpenHelper mBaseHelper;
	
	
	/** Constructor.
	 * 
	 * @param ctx context
	 */
	protected SQLiteAdapterBase(Context ctx) {	
		this.ctx = ctx;
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
	
	
	/** Initialize and open database.
	 * 
	 * @return Open database
	 */
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}
	
	/** Initialize and open database.
	 * @param db database
	 * @return Open database
	 */
	public SQLiteDatabase open(SQLiteDatabase db) {
		this.mDatabase = db;
		return this.mDatabase;
	}

	/** Close database. */
	public void close() {
		mDatabase.close();
	}
	
	/**
	 * Get all entities from the DB.
	 * @return A cursor pointing to all entities
	 */
	protected Cursor getAllCursor() {
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Get all entities");
		}
		
		return this.query(this.getCols(), 
				null, 
				null, 
				null, 
				null, 
				null);
	}
	
	/**
	 * Send a query to the DB.
	 * @param projection Columns to work with
	 * @param whereClause WHERE clause for SQL
	 * @param whereArgs WHERE arguments for SQL
	 * @param having HAVING clause
	 * @param orderBy ORDER BY clause
	 * @return A cursor pointing to the result of the query
	 */
	public Cursor query(String[] projection, 
								String whereClause, 
								String[] whereArgs, 
								String groupBy, 
								String having, 
								String orderBy) {
		return this.mDatabase.query(
				this.getTableName(),
				projection,
				whereClause,
				whereArgs,
				groupBy,
				having,
				orderBy);
	}
	
	/**
	 * Insert a new entity into the DB.
	 * @param nullColumnHack nullColumnHack
	 * @param item The ContentValues to insert
	 * @return the id of the inserted entity
	 */
	public long insert(String nullColumnHack, ContentValues item) {
		return this.mDatabase.insert(
				this.getTableName(),
				nullColumnHack,
				item);
	}
	
	/**
	 * Delete the entities matching with query from the DB.
	 * @param whereClause WHERE clause for SQL
	 * @param whereArgs WHERE arguments for SQL
	 * @return how many token deleted
	 */
	public int delete(String whereClause, String[] whereArgs) {
		return this.mDatabase.delete(
				this.getTableName(),
				whereClause,
				whereArgs);
	}
	
	/**
	 * Updates the entities from the DB matching with the query.
	 * @param item The ContentValues to be updated
	 * @param whereClause WHERE clause for SQL
	 * @param whereArgs WHERE arguments for SQL
	 * @return How many tokens updated
	 */
	public int update(ContentValues item, String whereClause, 
											String[] whereArgs) {
		return this.mDatabase.update(
				this.getTableName(),
				item,
				whereClause,
				whereArgs);
	}
	
	/**
	 * Get the table Name.
	 */
	public abstract String getTableName();
	/**
	 * Get the table's columns.
	 */
	public abstract String[] getCols();
	
		
	/** Read All Comments entities.
	 * 
	 * @return List of Comment entities
	 */
	public ArrayList<T> getAll() {
		Cursor cursor = this.getAllCursor();
		ArrayList<T> result = this.cursorToItems(cursor);
		cursor.close();
		
		
		return result;
	}
	
	/** Read All T entities given some criterias.
	 * @param crits The criterias to use for the request
	 * @return List of T entities
	 */
	public ArrayList<T> getAll(CriteriasBase crits) {
		ArrayList<T> result; 
		
		if (crits == null || crits.isEmpty()) {
			result = this.getAll();
		} else {
			final Cursor cursor = this.mDatabase.rawQuery("SELECT * FROM " 
						+ this.getTableName() + " WHERE " 
						+ crits.toSQLiteString(), 
						null);
			result = this.cursorToItems(cursor);
			cursor.close();
		}	
		
		return result;
	}
	
	/** Convert Cursor of database to Array of Comment entity.
	 * 
	 * @param cursor Cursor object
	 * @return Array of Comment entity
	 */
	public ArrayList<T> cursorToItems(final Cursor cursor) {
		final ArrayList<T> result = new ArrayList<T>(cursor.getCount());

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			
			T item;
			do {
				item = this.cursorToItem(cursor);
				result.add(item);
			} while (cursor.moveToNext());
			
			//if (DemactApplication.DEBUG)
			//Log.d(TAG, "Read DB(" + TABLE_NAME + ") count : " 
		    //			+ cursor.getCount() );
		}

		return result;
	}
	
	/** Convert Cursor of database to Comment entity.
	 * 
	 * @param cursor Cursor object
	 * @return Comment entity
	 */
	public abstract T cursorToItem(Cursor c);

	/** Insert a Comment entity into database.
	 * 
	 * @param item The Comment entity to persist 
	 * @return Id of the Comment entity
	 */
	public abstract long insert(T item);
	
	/** Update a Comment entity into database.
	 * 
	 * @param item The Comment entity to persist
	 * @return The count of updated entities  
	 */
	public abstract int update(T item);
	
	/** Delete a Comment entity into database.
	 * 
	 * @param item The Comment entity to persist
	 * @return The count of deleted entities
	 */
	public abstract int delete(T item);
}