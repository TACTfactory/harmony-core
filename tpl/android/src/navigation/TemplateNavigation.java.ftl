package ${project_namespace}.navigation;


import ${project_namespace}.R;
<#if (entities??)>
	<#list entities?values as entity>
		<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}CreateActivity;
import ${project_namespace}.view.${entity.name?lower_case}.${entity.name}ListActivity;
		</#if>
	</#list>
</#if>

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Navigation class. This class will manage the different buttons used to
 * navigate through your application.
 */
public class ${project_name?cap_first}Navigation implements OnClickListener {
	/** Context. */
	private Context context;
	/** Button's container. */
	private View container;
	
	/**
	 * Constructor.
	 *
	 * @param context The context.
	 * @param container The view containing the buttons used to navigate
	 */
	private ${project_name?cap_first}Navigation(Context context, View container) {
		this.context = context;
		this.container = container;
		this.initButtons();
	}
	
	/**
	 * Initialize the navigation.
	 *
	 * @param context The context.
	 * @param container The view containing the buttons used to navigate
	 */	
	public static void initialize(Context context, View container) {
		new ${project_name?cap_first}Navigation(context, container);
	}
	
	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
		// Base buttons
		<#if (entities??)>
			<#list entities?values as entity>
				<#if (entity.fields?? && (entity.fields?size>0) && entity.internal!="true")>
		// Create ${entity.name}
		this.container.findViewById(R.id.${entity.name?lower_case}_create_button).setOnClickListener(this);

		// List ${entity.name}
		this.container.findViewById(R.id.${entity.name?lower_case}_list_button).setOnClickListener(this);

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
				intent = new Intent(this.context, ${entity.name}CreateActivity.class);
				break;

			case R.id.${entity.name?lower_case}_list_button:
				intent = new Intent(this.context, ${entity.name}ListActivity.class);
				break;

				</#if>
			</#list>
		}

		if (intent != null) {
			this.context.startActivity(intent);
		}
	}
	
	
}

