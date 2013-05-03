<#assign curr = entities[current_entity] />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** ${curr.name} edit Activity.
 * 
 * @see android.app.Activity
 */
public class ${curr.name}EditActivity extends HarmonyFragmentActivity {

	/**
	* Called when the Activity is created.
	* @see android.app.Activity#onCreate
	*/
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${curr.name?lower_case}_edit);
        
        // Google Analytics
        //GoogleAnalyticsSessionManager.getInstance(getApplication()).incrementActivityCount();
	}
	
	/**
	 * Called when the Activity is destroyed.
	 * @see android.app.Activity#onDestroy
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// Google Analytics
		/*GoogleAnalyticsTracker.getInstance().dispatch();
        GoogleAnalyticsSessionManager.getInstance().decrementActivityCount();*/
	}

}
