<#include utilityPath + "all_imports.ftl" />
<#function callLoader entity>
	<#assign ret="//Load "+entity.name+" fixtures\r\t\t" />
	<#assign ret=ret+entity.name?cap_first+"DataLoader "+entity.name?uncap_first+"Loader = new "+entity.name?cap_first+"DataLoader(this.ctx);\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.getModelFixtures("+entity.name?cap_first+"DataLoader.MODE_BASE);\r\t\t" />
	<#assign ret=ret+entity.name?uncap_first+"Loader.load(manager);\r\r" />
	<#return ret />
</#function>
<@header?interpret />
package ${data_namespace}.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

<#if options.fixture?? && options.fixture.enabled>import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;</#if>
<#list entities?values as entity>
	<#if (entity.fields?size>0 || entity.inheritance?? || entity.inheritance??)>
import ${data_namespace}.${entity.name?cap_first}SQLiteAdapter;
import ${project_namespace}.provider.contract.${entity.name?cap_first}Contract;
	</#if>
</#list>
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

<#if options.fixture?? && options.fixture.enabled>
import ${fixture_namespace}.DataLoader;
</#if>


/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name?cap_first}SQLiteOpenHelperBase
						extends SQLiteOpenHelper {
	/** TAG for debug purpose. */
	protected static final String TAG = "DatabaseHelper";
	/** Context. */
	protected Context ctx;

	/** Android's default system path of the database.
	 *
	 */
	private static String DB_PATH;
	/** database name. */
	private static String DB_NAME;
	/** is assets exist.*/
	private static boolean assetsExist;
	/** Are we in a JUnit context ?*/
	public static boolean isJUnit = false;

	/**
	 * Constructor.
	 * @param ctx Context
	 * @param name name
	 * @param factory factory
	 * @param version version
	 */
	public ${project_name?cap_first}SQLiteOpenHelperBase(final Context ctx,
		   final String name, final CursorFactory factory, final int version) {
		super(ctx, name, factory, version);
		this.ctx = ctx;
		DB_NAME = name;
		DB_PATH = ctx.getDatabasePath(DB_NAME).getAbsolutePath();

		try {
			this.ctx.getAssets().open(DB_NAME);
			assetsExist = true;
		} catch (IOException e) {
			assetsExist = false;
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		// Activation of SQLiteConstraints
		//db.execSQL("PRAGMA foreign_keys = ON;");
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		Log.i(TAG, "Create database..");

		if (!assetsExist) {
			/// Create Schema
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !(entity.inheritance?? && entity.inheritance.inheritanceType?? && entity.inheritance.inheritanceType == "SingleTable" && entity.inheritance.superclass??))>
			if (${project_name?cap_first}Application.DEBUG) {
				Log.d(TAG, "Creating schema : ${entity.name}");
			}

			db.execSQL(${entity.name}SQLiteAdapter.getSchema());
			<#list entity["relations"] as relation>
				<#if (relation.type=="ManyToMany")>
			db.execSQL(${entity.name}SQLiteAdapter
					.get${relation.name?cap_first}RelationSchema());
				</#if>
			</#list>
		</#if>
	</#list>
			db.execSQL("PRAGMA foreign_keys = ON;");
	<#if options.fixture?? && options.fixture.enabled>
			if (!${project_name?cap_first}SQLiteOpenHelper.isJUnit) {
				this.loadData(db);
			}
	</#if>
		}

	}

	/**
	 * Clear the database given in parameters.
	 * @param db The database to clear
	 */
	public static void clearDatabase(final SQLiteDatabase db) {
		Log.i(TAG, "Clearing database...");

		<#list entities?values as entity>
			<#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??))>
		db.delete(${entity.name?cap_first}Contract.${entity.name?cap_first}.TABLE_NAME,
				null,
				null);
			</#if>
		</#list>
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		Log.i(TAG, "Update database..");

		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Upgrading database from version " + oldVersion
					   + " to " + newVersion);
		}

		// TODO : Upgrade your tables !
	}

	<#if options.fixture?? && options.fixture.enabled>
	/**
	 * Loads data from the fixture files.
	 * @param db The database to populate with fixtures
	 */
	private void loadData(final SQLiteDatabase db) {
		final DataLoader dataLoader = new DataLoader(this.ctx);
		dataLoader.clean();
		int mode = DataLoader.MODE_APP;
		if (${project_name?cap_first}Application.DEBUG) {
			mode = DataLoader.MODE_APP | DataLoader.MODE_DEBUG;
		}
		dataLoader.loadData(db, mode);
	}
	</#if>

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * @throws IOException if error has occured while copying files
	 */
	public void createDataBase() throws IOException {
		if (assetsExist && !checkDataBase()) {
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
		boolean result;

		SQLiteDatabase checkDB = null;
		try {
			final String myPath = DB_PATH + DB_NAME;
			// NOTE : the system throw error message : "Database is locked"
			// when the Database is not found (incorrect path)
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
			result = true;
		} catch (SQLiteException e) {
			// database doesn't exist yet.
			result = false;
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return result;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * @throws IOException if error has occured while copying files
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		final InputStream myInput = this.ctx.getAssets().open(DB_NAME);

		// Path to the just created empty db
		final String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		final OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		final byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
