package ${project_namespace};

<#list entities as entity>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ShowActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}EditActivity;
</#list>

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

        //Debug buttons
		<#list entities as entity>
		Button ${entity.name}ShowButton = new Button(this);
		${entity.name}ShowButton.setText("${entity.name}ShowActivity");
		${entity.name}ShowButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,${entity.name}ShowActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
        homeLayout.addView(${entity.name}ShowButton);
        
        Button ${entity.name}EditButton = new Button(this);
		${entity.name}EditButton.setText("${entity.name}EditActivity");
		${entity.name}EditButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,${entity.name}EditActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
        homeLayout.addView(${entity.name}EditButton);
	    </#list>
    }
}
