<@header?interpret />
package ${project_namespace}.test.base;

<#if dataLoader?? && dataLoader>import java.io.File;</#if>

import ${project_namespace}.provider.${project_name?cap_first}Provider;
<#if dataLoader?? && dataLoader>import ${project_namespace}.${project_name?cap_first}Application;</#if>
<#if dataLoader?? && dataLoader>import ${project_namespace}.fixture.DataLoader;</#if>
<#if dataLoader?? && dataLoader>import ${project_namespace}.harmony.util.DatabaseUtil;</#if>
import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;
<#if dataLoader?? && dataLoader>import ${data_namespace}.base.SQLiteAdapterBase;</#if>

import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
<#if dataLoader?? && dataLoader>import android.database.sqlite.SQLiteDatabase;</#if>
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
<#if dataLoader?? && dataLoader></#if>


/** android.content.Context mock for tests.<br/>
 * <b><i>This class will be overwrited whenever
 * you regenerate the project with Harmony.</i></b>
 */
public class TestContextMock {
    private final static String CONTEXT_PREFIX = "test.";
    private final static String PROVIDER_AUTHORITY =
                    "${project_namespace}.provider";
    private final static Class<? extends ContentProvider> PROVIDER_CLASS =
                    ${project_name?cap_first}Provider.class;

    private static android.content.Context context = null;
    private AndroidTestCase androidTestCase;
    private android.content.Context baseContext;
    
    public TestContextMock(AndroidTestCase androidTestCase) {
        this.androidTestCase = androidTestCase;
    }

    /**
     * Get the original context
     * @return unmocked android.content.Context
     */
    protected android.content.Context getBaseContext() {
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
     * Get the mock for android.content.Context
     * @return MockContext
     */
    protected android.content.Context getMockContext() {
            return this.androidTestCase.getContext();
    }

    /**
     * Initialize the mock android.content.Context
     * @throws Exception
     */
    protected void setMockContext() throws Exception {
        if (this.baseContext == null) {
            this.baseContext = this.androidTestCase.getContext();
        }

        if (context == null) {
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
    
            context = new IsolatedContext(
                    resolver,
                    targetContextWrapper) {
                        @Override
                        public Object getSystemService(String name) {
                            return TestContextMock.this
                                    .baseContext.getSystemService(name);
                        }
                        
                        @Override
                        public void sendOrderedBroadcast(
                                Intent intent, String receiverPermission) {
                            TestContextMock.this.baseContext
                                    .sendOrderedBroadcast(
                                            intent, receiverPermission);
                        }
                        
                        @Override
                        public Intent registerReceiver(
                                BroadcastReceiver receiver,
                                IntentFilter filter) {
                            return TestContextMock.this.baseContext
                                    .registerReceiver(receiver, filter);
                        }
                    };
    
            PackageManager packageManager = this.baseContext.getPackageManager();
            ProviderInfo providerInfo = packageManager.resolveContentProvider(
                    ${project_name?cap_first}Provider.class.getPackage().getName(), 0);

            provider.attachInfo(context, providerInfo);

            resolver.addProvider(PROVIDER_AUTHORITY, provider);
        }

        this.androidTestCase.setContext(context);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        ${project_name?cap_first}SQLiteOpenHelper.isJUnit = true;
        this.setMockContext();
        <#if dataLoader?? && dataLoader>

        String dbPath =
                this.androidTestCase.getContext()
                        .getDatabasePath(SQLiteAdapterBase.DB_NAME)
                        .getAbsolutePath() + ".test";

        File cacheDbFile = new File(dbPath);

        if (!cacheDbFile.exists() || !DataLoader.hasFixturesBeenLoaded) {
            if (${project_name?cap_first}Application.DEBUG) {
                android.util.Log.d("TEST", "Create new Database cache");
            }

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
            dataLoader.loadData(db,
                        DataLoader.MODE_APP |
                        DataLoader.MODE_DEBUG |
                        DataLoader.MODE_TEST);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

            DatabaseUtil.exportDB(this.getMockContext(),
                    cacheDbFile,
                    SQLiteAdapterBase.DB_NAME);
        } else {
            if (${project_name?cap_first}Application.DEBUG) {
                android.util.Log.d("TEST", "Re use old Database cache");
            }
            DatabaseUtil.importDB(this.getMockContext(),
                    cacheDbFile,
                    SQLiteAdapterBase.DB_NAME,
                    false);
        }</#if>
    }
}
