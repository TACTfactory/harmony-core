package ${local_namespace};

import ${project_namespace}.BuildConfig;
<#list entities as entity>
//import ${local_namespace}.${entity.name}Adapter;
</#list>

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ${project_name}SqliteOpenHelper extends SQLiteOpenHelper {
	protected String TAG = "DatabaseHelper";
	
	public ${project_name}SqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

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

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "Update database..");
		
		//if (XxxxxAdapter.BASE_VERSION < 0) {
			Log.i(TAG, "Upgrading database from version " + oldVersion + 
					   " to " + newVersion + ", which will destroy all old data");
		
			<#list entities as entity>
			db.execSQL("DROP TABLE IF EXISTS "+ ${entity.name}Adapter.TABLE_NAME);
		    </#list>
		//}
		    
		this.onCreate(db);
	}
}
