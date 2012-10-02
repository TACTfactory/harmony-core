package ${local_namespace};

import ${project_namespace}.BuildConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class makes it easy for ContentProvider implementations to defer opening and upgrading the database until first use, to avoid blocking application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name}SqliteOpenHelper extends SQLiteOpenHelper {
	protected String TAG = "DatabaseHelper";
	
	public ${project_name}SqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Create database..");
		
		/// Create Schema
		<#list entities as entity>
		db.execSQL( ${entity.name}Adapter.getSchema() );
	    </#list>
		
		// Sample of data
		/*InitialDataBase data = new InitialData(db);
		
		try {
			data.initialData();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage() );
		}*/
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update database..");
		
		//if (SqliteAdapter.BASE_VERSION < 0) {
			Log.i(TAG, "Upgrading database from version " + oldVersion + 
					   " to " + newVersion + ", which will destroy all old data");
		
			<#list entities as entity>
			db.execSQL("DROP TABLE IF EXISTS "+ ${entity.name}Adapter.TABLE_NAME);
		    </#list>
		//}
		    
		this.onCreate(db);
	}
}
