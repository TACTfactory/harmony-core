/**************************************************************************
* ValidationButtons.java, Harmony Android
*
* Copyright 2013 Mickael Gaillard / TACTfactory
* Description : 
* Author(s)   : Mickael Gaillard <mickael.gaillard@tactfactory.com>
* Licence     : all right reserved
* Create      : 2013
*
**************************************************************************/
package ${project_namespace}.harmony.widget;

import ${project_namespace}.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class ValidationButtons extends FrameLayout {

	public interface OnValidationListener {
		public void onValidationSelected();

		public void onCancelSelected();
	}

	private OnValidationListener listener;

	public ValidationButtons(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_validation_buttons, null);

		this.initializeComponent(view);
		this.addView(view);
	}

	private void initializeComponent(View view) {
		Button bCancel = (Button) view
				.findViewById(R.id.validation_cancel);
		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ValidationButtons.this.listener != null)
					listener.onCancelSelected();
			}
		});

		Button bValidate = (Button) view
				.findViewById(R.id.validation_validate);
		bValidate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ValidationButtons.this.listener != null)
					listener.onValidationSelected();
			}
		});
	}

	public void setListener(OnValidationListener listener) {
		this.listener = listener;
	}

}
