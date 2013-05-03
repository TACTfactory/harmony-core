package ${project_namespace}.test.base;

import ${project_namespace}.provider.${project_name?cap_first}Provider;

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
     		return this.baseContext;
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
	}
}
