package ${project_namespace}.test.base;

import java.io.File;
import java.io.FilenameFilter;

import ${project_namespace}.provider.${project_name?cap_first}Provider;
import ${project_namespace}.EpackhygieneApplication;
import ${project_namespace}.fixture.DataLoader;
import ${project_namespace}.util.DatabaseUtil;
import ${data_namespace}.EpackhygieneSQLiteOpenHelper;
import ${data_namespace}.base.SQLiteAdapterBase;

import android.content.ContentProvider;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;


/** Base test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.</i></b>
 */
public abstract class TestDBBase extends AndroidTestCase {
	private static final String SEPARATOR = "#";
	private static final String EXTENTION = ".test-cache";
	private static final long   CACHE_DURACY = 600L;

	private final static String CONTEXT_PREFIX = "test.";
	private final static String PROVIDER_AUTHORITY = "${project_namespace}.provider";
	private final static Class<? extends ContentProvider> PROVIDER_CLASS = ${project_name?cap_first}Provider.class;
	
	private Context baseContext;
	
	/**
	 * Get the original context
	 * @return unmocked Context
	 */
	protected Context getBaseContext() {
		return this.baseContext;
	}
	
	/**
	 * Get the mock for ContentResolver
	 * @return MockContentResolver
	 */
	protected MockContentResolver getMockContentResolver() {
		return new MockContentResolver();
	}
	
	/**
	 * Get the mock for Context
	 * @return MockContext
	 */
	protected Context getMockContext() {        
     		return this.getContext();
	}
	
	/**
	 * Initialize the mock Context
	 * @throws Exception
	 */
	protected void setMockContext() throws Exception {
		if (this.baseContext == null) {
			this.baseContext = this.getContext();
		}
	    
	    ContentProvider provider = PROVIDER_CLASS.newInstance();
	    MockContentResolver resolver = this.getMockContentResolver();
        		
	    RenamingDelegatingContext targetContextWrapper 
	    	= new RenamingDelegatingContext(
    			// The context that most methods are delegated to:
	            this.getMockContext(), 
	            // The context that file methods are delegated to:
	            this.baseContext, 
	            // Prefix database
	            CONTEXT_PREFIX);
	    
	    Context context = new IsolatedContext(
	    		resolver, 
	    		targetContextWrapper);
	    
	    provider.attachInfo(context, null);
		resolver.addProvider(PROVIDER_AUTHORITY, provider);
	    
	    this.setContext(context);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.setMockContext();
		super.setUp();

		String dbPath =  
				this.getContext().getDatabasePath(SQLiteAdapterBase.DB_NAME)
					.getAbsolutePath() + ".test";
				
		File cacheDbFile = new File(dbPath);
		
		if (!cacheDbFile.exists() || !DataLoader.hasFixturesBeenLoaded) {
			Log.d("TEST", "Create new Database cache");
			
			// Create initial database
			${project_name?cap_first}SQLiteOpenHelper helper = 
					new ${project_name?cap_first}SQLiteOpenHelper(
						this.getMockContext(), 
						SQLiteAdapterBase.DB_NAME, 
						null,
						${project_name?cap_first}Application.getVersionCode(
								this.getMockContext()));
			
			SQLiteDatabase db = helper.getWritableDatabase();
			${project_name?cap_first}SQLiteOpenHelper.clearDatabase(db);
			
			db.beginTransaction();
			DataLoader dataLoader = new DataLoader(this.getMockContext());
			dataLoader.clean();
			dataLoader.loadData(db, DataLoader.MODE_APP | DataLoader.MODE_DEBUG | DataLoader.MODE_TEST);
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
			
			DatabaseUtil.exportDB(this.getMockContext(), cacheDbFile, SQLiteAdapterBase.DB_NAME);
		} else {
			Log.d("TEST", "Re use old Database cache");
			DatabaseUtil.importDB(this.getMockContext(), cacheDbFile, SQLiteAdapterBase.DB_NAME, false);
		}
	}
}
