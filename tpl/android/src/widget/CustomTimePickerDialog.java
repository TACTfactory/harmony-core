package ${localnamespace}.harmony.widget;

import ${namespace}.R;

import org.joda.time.DateTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * @author yo
 * 
 */
public class CustomTimePickerDialog extends AlertDialog {
	private Context ctx;
	private TimePicker timePicker;
	private String title;
	private int hourOfDay;
	private int minute;
	private boolean is24HourView;

	/**
	 * @param context
	 * @param date
	 * @param title
	 */
	public CustomTimePickerDialog(Context context, DateTime date, boolean is24HourView, String title) {
		super(context);
		
		this.initializeTimePickerDialog(context, date, is24HourView, title);
	}
	
	private void initializeTimePickerDialog(Context context, DateTime date, boolean is24HourView, String title) {
		this.ctx = context;
		this.title = title;
		this.hourOfDay = date.getHourOfDay();
		this.minute = date.getMinuteOfHour();
		this.is24HourView = is24HourView;
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
		View alertDialogView = inflater.inflate(R.layout.dialog_time_picker, null);	
		this.setView(alertDialogView);
		
		this.timePicker = (TimePicker) alertDialogView.findViewById(R.id.dialog_pick_time);
		this.timePicker.setIs24HourView(this.is24HourView);
		this.timePicker.setCurrentHour(this.hourOfDay);
		this.timePicker.setCurrentMinute(this.minute);
		
		this.setNegativeButton(this.ctx.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((CustomTimePickerDialog) dialog).hide();
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
	 * @return the TimePicker
	 */
	public TimePicker getTimePicker() {
		return timePicker;
	}
}
