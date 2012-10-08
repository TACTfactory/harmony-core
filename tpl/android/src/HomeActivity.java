package ${project_namespace};

<#if entities??>
<#list entities as entity>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}CreateActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ListActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ShowActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}EditActivity;
</#list>
</#if>

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout homeLayout = (LinearLayout) findViewById(R.id.homeLayout);

		<#if entities??>
		//Debug buttons
		<#list entities as entity>
		//Create
		Button ${entity.name}CreateButton = new Button(this);
		${entity.name}CreateButton.setText("${entity.name}CreateActivity");
		${entity.name}CreateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,${entity.name}CreateActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
		homeLayout.addView(${entity.name}CreateButton);
		//List
		Button ${entity.name}ListButton = new Button(this);
		${entity.name}ListButton.setText("${entity.name}ListActivity");
		${entity.name}ListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,${entity.name}ListActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
		homeLayout.addView(${entity.name}ListButton);
		</#list>
		<#else>
		</#if>
	}
}
