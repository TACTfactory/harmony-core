<#assign name=name?lower_case>
package ${localnamespace};

import ${namespace}.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ${name?cap_first}ShowActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_${name}_show);

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