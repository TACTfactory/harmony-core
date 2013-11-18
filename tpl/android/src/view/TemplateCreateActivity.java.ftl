<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** ${curr.name} create Activity.
 *
 * @see android.app.Activity
 */
public class ${curr.name}CreateActivity extends HarmonyFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_${curr.name?lower_case}_create);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
