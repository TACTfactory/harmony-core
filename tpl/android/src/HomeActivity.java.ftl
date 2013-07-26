<@header?interpret />
package ${project_namespace};

<#if (entities??)>
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}CreateActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ListActivity;
		</#if>
	</#list>
</#if>

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/** Home Activity.
 * This is from where you can access to your entities activities by default.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity {
	/** Called when the activity is first created.
	 * @see android.app.Activity#onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		final LinearLayout homeLayout = 
				(LinearLayout) findViewById(R.id.homeLayout);

		// Base buttons
		<#if (entities??)>
			<#list entities?values as entity>
				<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
		// Create ${entity.name}
		Button ${entity.name?uncap_first}CreateButton = new Button(this);
		${entity.name?uncap_first}CreateButton.setText("${entity.name}CreateActivity");
		${entity.name?uncap_first}CreateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = 
						new Intent(HomeActivity.this, 
								${entity.name}CreateActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
		homeLayout.addView(${entity.name?uncap_first}CreateButton);
		
		// List ${entity.name}
		Button ${entity.name?uncap_first}ListButton = new Button(this);
		${entity.name?uncap_first}ListButton.setText("${entity.name}ListActivity");
		${entity.name?uncap_first}ListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =
						new Intent(HomeActivity.this, 
								${entity.name}ListActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
		homeLayout.addView(${entity.name?uncap_first}ListButton);
				</#if>
			</#list>
		</#if>
	}
}
