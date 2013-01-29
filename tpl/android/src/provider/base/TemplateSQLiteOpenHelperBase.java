<#function callLoader entity>
	<#assign ret="//Load "+entity.name+" fixtures\r\t\t" /> 
	<#assign ret=ret+entity.name?cap_first+"DataLoader "+entity.name?uncap_first+"Loader = new "+entity.name?cap_first+"DataLoader(this.context);\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.getModelFixtures("+entity.name?cap_first+"DataLoader.MODE_BASE);\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.load(manager);\r\r" />
	<#return ret />
</#function>


<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if entity.relations?size==0>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>	
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>
			<#if !isInArray(ret, entity.name)>
				<#if isOnlyDependantOf(entity, ret)>
					<#assign ret = ret + [entity.name] />
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>
package ${data_namespace}.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ${data_namespace}.*;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
	
	// Android's default system path of the database.
	private static String DB_PATH = "/data/data/${project_namespace}/databases/";	
	private static String DB_NAME;
	private static boolean assetsExist = false;
	
	public ${project_name?cap_first}SQLiteOpenHelperBase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
		DB_NAME = name;
		
		try {
			this.context.getAssets().open(DB_NAME);
			assetsExist = true;
		} catch (IOException e) {
			assetsExist = false;
		}
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Create database..");
		
		if (!assetsExist) {
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
		
	}
	
	/**
	 * Clear the database given in parameters
	 * @param db The database to clear
	 */
	public static void clearDatabase(SQLiteDatabase db){
		<#list entities?values as entity>
			<#if (entity.fields?? && (entity.fields?size>0))>
		db.delete(${entity.name?cap_first}SQLiteAdapter.TABLE_NAME, null, null);	
			</#if>
		</#list>
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
	//@SuppressWarnings("rawtypes")
	private void loadData(SQLiteDatabase db){
		// Sample of data
		DataManager manager = new DataManager(this.context, db);
		
		<#assign entitiesOrder = orderEntitiesByRelation() />
		<#list entitiesOrder as entityName>
			<#assign entity = entities[entityName]>
			<#if (entity.fields?size>0)>
		${callLoader(entity)}
			</#if>
		</#list>
	}
	</#if>
	
	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		if (assetsExist && !checkDataBase()){
			// By calling this method and empty database will be created into
			// the default system path
			// so we're gonna be able to overwrite that database with ours
			this.getReadableDatabase();
	
			try {
				copyDataBase();
	
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
	
	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			// NOTE : the system throw error message : "Database is locked" when
			// the Database is not found (incorrect path)
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database doesn't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = this.context.getAssets().open(DB_NAME);
		
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
