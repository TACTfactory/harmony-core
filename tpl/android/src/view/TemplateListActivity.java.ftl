<#assign curr = entities[current_entity] />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * This class will display ${curr.name} entities in a list.
 */
public class ${curr.name}ListActivity extends HarmonyFragmentActivity {

	/**
	* Called when the Activity is created.
	* @see android.app.Activity#onCreate
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_${curr.name?lower_case}_list);

		// Google Analytics
		//GoogleAnalyticsSessionManager.getInstance(getApplication()).incrementActivityCount();
	}

	/**
	* Called when the Activity is destroyed.
	* @see android.app.Activity.onDestroy
	*/
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Google Analytics
		/*GoogleAnalyticsTracker.getInstance().dispatch();
        GoogleAnalyticsSessionManager.getInstance().decrementActivityCount();*/
	}

	/**
	* Called when the Activity previously launched exits.
	* @see android.app.Activity#onActivityResult
	*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode <= 0xFFFF) {
			switch(requestCode) {
				default:
					final ${curr.name}ListFragment fragment = (${curr.name}ListFragment)  getSupportFragmentManager().findFragmentById(R.id.fragment);
					fragment.getLoaderManager().restartLoader(0, null, fragment);
					break;
			}
		}
	}

}
