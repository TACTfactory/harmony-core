<#assign curr = entities[current_entity]>
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ${curr.name}ListActivity extends FragmentActivity {

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

		${curr.name}ListFragment fragment = (${curr.name}ListFragment)  getSupportFragmentManager().findFragmentById(R.id.fragment);
		fragment.getLoaderManager().restartLoader(0, null, fragment);
	}

}
