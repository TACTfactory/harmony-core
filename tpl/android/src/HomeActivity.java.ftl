<@header?interpret />
package ${project_namespace};

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.R;
<#if (entities??)>
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}CreateActivity;
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
				<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
		this.findViewById(R.id.${entity.name?lower_case}_create_button).setOnClickListener(this);
		this.findViewById(R.id.${entity.name?lower_case}_list_button).setOnClickListener(this);
				</#if>
			</#list>
		</#if>
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			<#list entities?values as entity>
				<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
			case R.id.${entity.name?lower_case}_create_button:
				intent = new Intent(this, ${entity.name}CreateActivity.class);
				break;

			case R.id.${entity.name?lower_case}_list_button:
				intent = new Intent(this, ${entity.name}ListActivity.class);
				break;

				</#if>
			</#list>
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
