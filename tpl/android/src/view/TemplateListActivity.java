<#assign curr = entities[current_entity] />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.HarmonyFragmentActivity;

import android.content.Intent;
import android.os.Bundle;

public class ${curr.name}ListActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_${curr.name?lower_case}_list);

		// Google Analytics
		//GoogleAnalyticsSessionManager.getInstance(getApplication()).incrementActivityCount();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Google Analytics
		/*GoogleAnalyticsTracker.getInstance().dispatch();
        GoogleAnalyticsSessionManager.getInstance().decrementActivityCount();*/
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode<=0xFFFF){
			switch(requestCode){
				default:
					${curr.name}ListFragment fragment = (${curr.name}ListFragment)  getSupportFragmentManager().findFragmentById(R.id.fragment);
					fragment.getLoaderManager().restartLoader(0, null, fragment);
					break;
			}
		}
	}

}
