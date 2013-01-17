package ${data_namespace};

import ${project_namespace}.${project_name?cap_first}Application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

public abstract class ${project_name?cap_first}SQLiteAdapterBase{

	/** Table name of SQLite database */
	public static String TAG = "${project_name?cap_first}SQLiteAdapterBase";
	
	// Database tools
	protected Context context;
	protected SQLiteDatabase mDatabase;
	protected ${project_name?cap_first}SQLiteOpenHelper mBaseHelper;
	
	
	/** Constructor
	 * 
	 * @param ctx context
	 */
	protected ${project_name?cap_first}SQLiteAdapterBase(Context ctx) {	
		this.context = ctx;
		this.mBaseHelper = new DemactSQLiteOpenHelper(
				ctx, 
				"database", 
				null,
				1);
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
	
	protected abstract String getTableName();
	protected abstract String[] getCols();
}