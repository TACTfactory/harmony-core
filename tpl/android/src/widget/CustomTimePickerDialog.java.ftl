<@header?interpret />
package ${project_namespace}.harmony.widget;

import ${project_namespace}.R;

import org.joda.time.DateTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/** CustomTimePickerDialog widget class.
 *  A simple AlertDialog containing an TimePicker.
 */
public class CustomTimePickerDialog extends AlertDialog {
	/** Context. */
	private Context ctx;
	/** time picker. */
	private TimePicker timePicker;
	/** title. */
	private String title;
	/** hour of the day. */
	private int hourOfDay;
	/** minute. */
	private int minute;
	/** is 24 hour. */
	private boolean is24HourView;

	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial time of the dialog.
	 * @param is24HourView Whether this is a 24 hour view, or AM/PM. 
	 * @param title The text of the title.
	 */
	public CustomTimePickerDialog(Context ctx, DateTime date, 
										   boolean is24HourView, String title) {
		super(ctx);
		
		this.initializeTimePickerDialog(ctx, date, is24HourView, title);
	}
	
	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial time of the dialog.
	 * @param is24HourView Whether this is a 24 hour view, or AM/PM. 
	 * @param titleId The resource id of the title.
	 */
	public CustomTimePickerDialog(Context ctx, DateTime date, 
											boolean is24HourView, int titleId) {
		super(ctx);
		
		this.initializeTimePickerDialog(ctx, date, is24HourView, 
													ctx.getString(titleId));
	}
	
	/** TimePicker dialog initialisation.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial time of the dialog.
	 * @param is24HourView Whether this is a 24 hour view, or AM/PM. 
	 * @param title The text of the title.
	 */
	private void initializeTimePickerDialog(Context ctx, DateTime date, 	
										   boolean is24HourView, String title) {
		this.ctx = ctx;
		this.title = title;
		this.hourOfDay = date.getHourOfDay();
		this.minute = date.getMinuteOfHour();
		this.is24HourView = is24HourView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(title);
		this.setCancelable(true);
		this.setIcon(0);
		
		LayoutInflater inflater = getLayoutInflater();
		View alertDialogView = inflater.inflate(R.layout.dialog_time_picker, 
												null);	
		this.setView(alertDialogView);
		
		this.timePicker = (TimePicker) alertDialogView.findViewById(
														 R.id.dialog_pick_time);
		this.timePicker.setIs24HourView(this.is24HourView);
		this.timePicker.setCurrentHour(this.hourOfDay);
		this.timePicker.setCurrentMinute(this.minute);
		
		this.setNegativeButton(this.ctx.getString(android.R.string.cancel),
										 new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((CustomTimePickerDialog) dialog).hide();
			}
		});

		super.onCreate(savedInstanceState);
	}

	/** Set a listener to be invoked when the positive button of the dialog is 
	 * pressed. 
	 * @param text The text to display in the positive button
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setPositiveButton(CharSequence text, 
									  DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_POSITIVE, text, listener);
	}
	
	/** Set a listener to be invoked when the negative button of the dialog is 
	 * pressed. 
	 * @param text The text to display in the negative button
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setNegativeButton(CharSequence text,
									  DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_NEGATIVE, text, listener);
	}
	
	/** Set a listener to be invoked when the positive button of the dialog is 
	 * pressed. 
	 * @param textId The resource id of the text to display in the positive 
	 * button
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setPositiveButton(int textId, 
									  DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_POSITIVE, this.ctx.getString(textId), 
																	  listener);
	}
	
	/** Set a listener to be invoked when the negative button of the dialog is 
	 * pressed. 
	 * @param textId The resource id of the text to display in the negative 
	 * button
	 * @param listener The DialogInterface.OnClickListener to use.
	 */
	public void setNegativeButton(int textId, 
									  DialogInterface.OnClickListener listener) {
		this.setButton(AlertDialog.BUTTON_NEGATIVE, this.ctx.getString(textId),
																	  listener);
	}
	
	/** Gets the TimePicker contained in this dialog.
	 * @return the TimePicker
	 */
	public TimePicker getTimePicker() {
		return timePicker;
	}
}
