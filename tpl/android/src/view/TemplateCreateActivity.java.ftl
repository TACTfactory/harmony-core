<#assign curr = entities[current_entity] />

package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

public class ${curr.name}CreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${curr.name?lower_case}_create);
  
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
}
