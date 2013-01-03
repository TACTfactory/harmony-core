/**
 * 
 */
package com.tactfactory.mda.test.demact.view.post;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.widget.DatePicker;

/**
 * @author yo
 * 
 */
public class RangeDatePickerDialog extends DatePickerDialog {
	private Calendar minDate;
	private Calendar maxDate;
	private String title;

	/**
	 * @param context
	 * @param title
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param title;
	 * @param minDate
	 * @param maxDate
	 */
	public RangeDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth, String title, Calendar minDate, Calendar maxDate) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		
		this.initDatePickerDialog(title, minDate, maxDate);
	}
	
	/**
	 * @param context
	 * @param title
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param title;
	 */
	public RangeDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth, String title) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		
		this.initDatePickerDialog(title, null, null);
	}
	
	private void initDatePickerDialog(String title, Calendar minDate, Calendar maxDate){
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.title = title;
		
		this.setTitle(this.title);
		this.setCancelable(true);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if (this.minDate != null) {
				this.getDatePicker().setMinDate(this.minDate.getTimeInMillis());
			} else {
				Calendar minDateCal = new GregorianCalendar();
				minDateCal.setTimeInMillis(this.getDatePicker().getMinDate());
				this.minDate = minDateCal;
			}
			
			
			if (this.maxDate != null) {
			this.getDatePicker().setMaxDate(this.maxDate.getTimeInMillis());
			} else {
				Calendar maxDateCal = new GregorianCalendar();
				maxDateCal.setTimeInMillis(this.getDatePicker().getMaxDate());
				this.minDate = maxDateCal;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.DatePickerDialog#onDateChanged(android.widget.DatePicker,
	 * int, int, int)
	 */
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
			Calendar newDate = Calendar.getInstance();
			newDate.set(year, month, day);
	
			if (minDate != null && minDate.after(newDate)) {
				view.init(
						minDate.get(Calendar.YEAR), 
						minDate.get(Calendar.MONTH), 
						minDate.get(Calendar.DAY_OF_MONTH), this);
			} else if (maxDate != null && maxDate.before(newDate)) {
				view.init(
						maxDate.get(Calendar.YEAR), 
						maxDate.get(Calendar.MONTH), 
						maxDate.get(Calendar.DAY_OF_MONTH), this);
			} else {
				view.init(year, month, day, this);
			}
		}
		
		this.setTitle(this.title);
	}

}
