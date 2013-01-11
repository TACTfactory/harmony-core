package ${project_namespace}.harmony.widget;

import ${project_namespace}.R;

import org.joda.time.DateTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

/**
 * @author yo
 * 
 */
public class CustomDatePickerDialog extends AlertDialog {
	private Context ctx;
	private DatePicker datePicker;
	private String title;
	private DateTime minDate;
	private DateTime maxDate;
	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	/**
	 * @param context
	 * @param date
	 * @param title
	 */
	public CustomDatePickerDialog(Context context, DateTime date, String title) {
		super(context);
		
		this.initializeDatePickerDialog(context, date, title, null, null);
	}
	
	/**
	 * @param context
	 * @param date
	 * @param title
	 */
	public CustomDatePickerDialog(Context context, DateTime date, int idTitle) {
		super(context);
		
		this.initializeDatePickerDialog(context, date, context.getString(idTitle), null, null);
	}

	/**
	 * @param context
	 * @param date
	 * @param title
	 * @param minDate
	 * @param maxDate
	 */
	public CustomDatePickerDialog(Context context, DateTime date, String title, final DateTime minDate, final DateTime maxDate) {
		super(context);
		
		this.initializeDatePickerDialog(context, date, title, minDate, maxDate);
	}
	
	private void initializeDatePickerDialog(Context context, DateTime date, String title, DateTime minDate, DateTime maxDate) {
		this.ctx = context;
		this.title = title;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.year = date.getYear();
		this.monthOfYear = date.getMonthOfYear() - 1;
		this.dayOfMonth = date.getDayOfMonth();
	}

	/* (non-Javadoc)
	 * @see android.app.AlertDialog#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(title);
		this.setCancelable(true);
		this.setIcon(0);
		
		LayoutInflater inflater = getLayoutInflater();
		View alertDialogView = inflater.inflate(R.layout.dialog_date_picker, null);	
		this.setView(alertDialogView);
		
		this.datePicker = (DatePicker) alertDialogView.findViewById(R.id.dialog_pick_date);
		this.datePicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
					DateTime newDate = new DateTime(year, monthOfYear + 1, dayOfMonth,0,0);
			
					if (minDate != null && minDate.isAfter(newDate)) {
						view.init(
								minDate.getYear(), 
								minDate.getMonthOfYear() - 1, 
								minDate.getDayOfMonth(), this);
					} else if (maxDate != null && maxDate.isBefore(newDate)) {
						view.init(
								maxDate.getYear(), 
								maxDate.getMonthOfYear() - 1, 
								maxDate.getDayOfMonth(), this);
					} else {
						view.init(year, monthOfYear, dayOfMonth, this);
					}
				}
			}
		});
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if (minDate != null) {
				this.datePicker.setMinDate(minDate.getMillis());
			}

			if (maxDate != null) {
				this.datePicker.setMinDate(maxDate.getMillis());
			}
		}
		
		this.setNegativeButton(this.ctx.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((CustomDatePickerDialog) dialog).hide();
			}
		});

		super.onCreate(savedInstanceState);
	}

	public void setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener){
		this.setButton(AlertDialog.BUTTON_POSITIVE, text, listener);
	}
	
	public void setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener){
		this.setButton(AlertDialog.BUTTON_NEGATIVE, text, listener);
	}
	
	/**
	 * @return the datePicker
	 */
	public DatePicker getDatePicker() {
		return datePicker;
	}
}
