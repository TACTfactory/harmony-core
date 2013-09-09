<@header?interpret />
package ${project_namespace};

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.navigation.${project_name?cap_first}Navigation;

import android.os.Bundle;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		${project_name?cap_first}Navigation.initialize(this, 
			this.findViewById(R.id.homeLayout));
	}

}
