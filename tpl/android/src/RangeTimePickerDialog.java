/**
 * 
 */
package com.tactfactory.mda.test.demact;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

/**
 * @author yo
 * 
 */
public class RangeTimePickerDialog extends TimePickerDialog {
	private String title;
	
	/**
	 * @param context
	 * @param theme
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public RangeTimePickerDialog(Context context, int theme, OnTimeSetListener callBack, int hourOfDay, int minute,
			boolean is24HourView, String title) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
		
		this.initTimePickerDialog(title);
	}
	
	private void initTimePickerDialog(String title) {
		this.title = title;
		this.setTitle(this.title);
		this.setCancelable(true);
	}

	/**
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public RangeTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute,
			boolean is24HourView, String title) {
		super(context, callBack, hourOfDay, minute, is24HourView);
		
		this.initTimePickerDialog(title);
	}

	/* (non-Javadoc)
	 * @see android.app.TimePickerDialog#onTimeChanged(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		super.onTimeChanged(view, hourOfDay, minute);
		
		this.setTitle(this.title);
	}
}
