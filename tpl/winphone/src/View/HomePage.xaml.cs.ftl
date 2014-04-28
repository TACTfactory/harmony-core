<@header?interpret />
package ${project_namespace};

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
<#if (entities??)>
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && !entity.hidden)>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ListActivity;
		</#if>
	</#list>
</#if>

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
		implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		this.initButtons();
	}

	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		<#if (entities??)>
			<#list entities?values as entity>
				<#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && !entity.hidden)>
		this.findViewById(R.id.${entity.name?lower_case}_list_button)
						.setOnClickListener(this);
				</#if>
			</#list>
		</#if>
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			<#list entities?values as entity>
				<#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && !entity.hidden)>
			case R.id.${entity.name?lower_case}_list_button:
				intent = new Intent(this,
						${entity.name}ListActivity.class);
				break;

				</#if>
			</#list>
			default:
				intent = null;
				break;
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
