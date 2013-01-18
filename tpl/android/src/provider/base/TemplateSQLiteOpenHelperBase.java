package ${data_namespace}.base;

import ${data_namespace}.*;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

<#if options.fixture?? && options.fixture.enabled>
import ${fixture_namespace}.*;
</#if>

/**
 * This class makes it easy for ContentProvider implementations to defer opening and upgrading the database until first use, to avoid blocking application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name?cap_first}SQLiteOpenHelperBase extends SQLiteOpenHelper {
	protected String TAG = "DatabaseHelper";
	protected Context context;
	
	public ${project_name?cap_first}SQLiteOpenHelperBase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Create database..");
		
		/// Create Schema
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0))>
		db.execSQL( ${entity.name}SQLiteAdapter.getSchema() );
			<#list entity["relations"] as relation>
				<#if (relation.type=="ManyToMany")>
		db.execSQL( ${entity.name}SQLiteAdapter.get${relation.name?cap_first}RelationSchema() );
				</#if>
			</#list>
		</#if>
	</#list>
	
	<#if options.fixture?? && options.fixture.enabled>
		this.loadData(db);
	</#if>
		
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Update database..");
		
		//if (SqliteAdapter.BASE_VERSION < 0) {
			Log.i(TAG, "Upgrading database from version " + oldVersion + 
					   " to " + newVersion + ", which will destroy all old data");
		
		<#list entities?values as entity>
			<#if (entity.fields?? && (entity.fields?size>0))>
			db.execSQL("DROP TABLE IF EXISTS "+ ${entity.name}SQLiteAdapter.TABLE_NAME);
				<#list entity['relations'] as relation>
					<#if (relation.type=="ManyToMany")>
			db.execSQL("DROP TABLE IF EXISTS "+${entity.name}SQLiteAdapter.RELATION_${relation.name?upper_case}_TABLE_NAME );
					</#if>
				</#list>
			</#if>
	    </#list>
		//}
		    
		this.onCreate(db);
	}
	
	<#if options.fixture?? && options.fixture.enabled>
	@SuppressWarnings("rawtypes")
	private void loadData(SQLiteDatabase db){
		// Sample of data
		DataManager manager = new DataManager(this.context, db);
		
		<#list entities?values as entity>
			<#if (entity.fields?size>0)>
		//Load ${entity.name} fixtures 
		${entity.name?cap_first}DataLoader ${entity.name?uncap_first}Loader = new ${entity.name?cap_first}DataLoader(this.context);
		${entity.name?uncap_first}Loader.getModelFixtures();
		${entity.name?uncap_first}Loader.load(manager);
		
			</#if>
		</#list>
	}
	</#if>
}
