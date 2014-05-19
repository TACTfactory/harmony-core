<@header?interpret />
package ${data_namespace}.base;

import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriaExpression;

/**
 * This is the base SQLiteAdapter. DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This is the base class for all basic operations for your sqlite adapters.
 *
 * @param <T> Entity type of this adapter.
 */
public abstract class SQLiteAdapterBase<T> {

	/** Table name of SQLite database. */
	public static final String DB_NAME = "database.sqlite";
	/** TAG for debug purpose. */
	public static final String TAG = "${project_name?cap_first}SQLiteAdapterBase";


	/** android.content.Context. */
	protected android.content.Context ctx;
	/**
	 * Database.
	 */
	protected SQLiteDatabase mDatabase;
	/**
	 * Open Helper.
	 */
	protected ${project_name?cap_first}SQLiteOpenHelper mBaseHelper;


	/**
	 * Constructor.
	 * @param ctx context
	 */
	protected SQLiteAdapterBase(final android.content.Context ctx) {
		this.ctx = ctx;
		this.mBaseHelper = new ${project_name?cap_first}SQLiteOpenHelper(
				ctx,
				DB_NAME,
				null,
				${project_name?cap_first}Application.getVersionCode(ctx));

		try {
			this.mBaseHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
	}


	/**
	 * Initialize and open database.
	 * @return Open database
	 */
	public SQLiteDatabase open() {
		this.mDatabase = this.mBaseHelper.getWritableDatabase();
		return this.mDatabase;
	}

	/**
	 * Initialize and open database.
	 * @param db database
	 * @return Open database
	 */
	public SQLiteDatabase open(final SQLiteDatabase db) {
		this.mDatabase = db;
		return this.mDatabase;
	}

	/** Close database. */
	public void close() {
		this.mDatabase.close();
	}

	/**
	 * Get all entities from the DB.
	 * @return A cursor pointing to all entities
	 */
	protected android.database.Cursor getAllCursor() {
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
	 * @param groupBy GROUP BY argument for SQL
	 * @param having HAVING clause
	 * @param orderBy ORDER BY clause
	 * @return A cursor pointing to the result of the query
	 */
	public android.database.Cursor query(final String[] projection,
						final String whereClause,
						final String[] whereArgs,
						final String groupBy,
						final String having,
						final String orderBy) {
		return this.mDatabase.query(
				this.getJoinedTableName(),
				projection,
				whereClause,
				whereArgs,
				groupBy,
				having,
				orderBy);
	}

	/**
	 * Send a query to the DB.
	 * @param projection Columns to work with
	 * @param whereClause WHERE clause for SQL
	 * @param whereArgs WHERE arguments for SQL
	 * @param groupBy GROUP BY argument for SQL
	 * @param having HAVING clause
	 * @param orderBy ORDER BY clause
	 * @param limit LIMIT clause
	 * @return A cursor pointing to the result of the query
	 */
	public android.database.Cursor query(final String[] projection,
						final String whereClause,
						final String[] whereArgs,
						final String groupBy,
						final String having,
						final String orderBy,
						final String limit) {
		return this.mDatabase.query(
				this.getJoinedTableName(),
				projection,
				whereClause,
				whereArgs,
				groupBy,
				having,
				orderBy,
				limit);
	}

	/**
	 * Insert a new entity into the DB.
	 * @param nullColumnHack nullColumnHack
	 * @param item The ContentValues to insert
	 * @return the id of the inserted entity
	 */
	public long insert(final String nullColumnHack, final ContentValues item) {
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
	public int delete(final String whereClause, final String[] whereArgs) {
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
	public int update(final ContentValues item, final String whereClause,
												final String[] whereArgs) {
		return this.mDatabase.update(
				this.getTableName(),
				item,
				whereClause,
				whereArgs);
	}

	/**
	 * Get the table Name.
	 * @return the table name
	 */
	public abstract String getTableName();

	/**
	 * Get the join table Name for inheritance.
	 * @return the table name joined with its mothers names
	 */
	public abstract String getJoinedTableName();

	/**
	 * Get the table's columns.
	 * @return array of cols
	 */
	public abstract String[] getCols();


	/**
	 * Read All T entities.
	 * @return List of T entities
	 */
	public ArrayList<T> getAll() {
		android.database.Cursor cursor = this.getAllCursor();
		ArrayList<T> result = this.cursorToItems(cursor);
		cursor.close();


		return result;
	}

	/**
	 * Read All T entities given some criterias.
	 * @param crits The criterias to use for the request
	 * @return List of T entities
	 */
	public ArrayList<T> getAll(final CriteriaExpression crits) {
		ArrayList<T> result;

		if (crits == null || crits.isEmpty()) {
			result = this.getAll();
		} else {
			final android.database.Cursor cursor = this.query(this.getCols(),
							crits.toSQLiteSelection(),
							crits.toSQLiteSelectionArgs(),
							null,
							null,
							null);
			result = this.cursorToItems(cursor);
			cursor.close();
		}

		return result;
	}

	/**
	 * Convert android.database.Cursor of database to Array of T entity.
	 * @param cursor android.database.Cursor object
	 * @return Array of T entity
	 */
	public ArrayList<T> cursorToItems(final android.database.Cursor cursor) {
		final ArrayList<T> result = new ArrayList<T>(cursor.getCount());

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();

			T item;
			do {
				item = this.cursorToItem(cursor);
				result.add(item);
			} while (cursor.moveToNext());
		}

		return result;
	}

	/**
	 * Convert android.database.Cursor of database to a T entity.
	 * @param c android.database.Cursor object
	 * @return T entity
	 */
	public abstract T cursorToItem(final android.database.Cursor c);

	/**
	 * Convert a T item to a ContentValues for the database.
	 * @param item The item to convert
	 * @return The ContentValues
	 */
	public abstract ContentValues itemToContentValues(final T item);

	/**
	 * Insert a T entity into database.
	 * @param item The T entity to persist
	 * @return Id of the T entity
	 */
	public abstract long insert(final T item);

	/**
	 * Update a T entity into database.
	 * @param item The T entity to persist
	 * @return The count of updated entities
	 */
	public abstract int update(final T item);

	/**
	 * Delete a T entity into database.
	 * @param item The T entity to persist
	 * @return The count of deleted entities
	 */
	public abstract int delete(final T item);

	<#list entities?values as entity>
		<#if entity.options.sync??>
	/**
	 * Read All T entities for the synchronization agorithm.
	 * @return List of T entities
	 */
	public ArrayList<T> getAllForSync() {
		return this.getAll(null);
	}
			<#break />
		</#if>
	</#list>
}
