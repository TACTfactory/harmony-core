<@header?interpret />
package ${project_namespace}.harmony.widget;

import ${project_namespace}.R;

import org.joda.time.DateTime;

import android.annotation.TargetApi;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

/** CustomDatePickerDialog widget class.
 *  A simple AlertDialog containing an DatePicker.
 */
public class CustomDatePickerDialog extends AlertDialog
		implements OnDateChangedListener {
	/** android.content.Context. */
	private android.content.Context ctx;
	/** datePicker. */
	private DatePicker datePicker;
	/** Title. */
	private String title;
	/** minimum date. */
	private DateTime minDate;
	/** maximum date. */
	private DateTime maxDate;
	/** year. */
	private int year;
	/** month of year. */
	private int monthOfYear;
	/** day of month. */
	private int dayOfMonth;

	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial date of the dialog.
	 * @param title The text of the title.
	 */
	public CustomDatePickerDialog(android.content.Context ctx, DateTime date, String title) {
		super(ctx);

		this.initializeDatePickerDialog(ctx, date, title, null, null);
	}

	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial date of the dialog.
	 * @param titleId The resource id of the title.
	 */
	public CustomDatePickerDialog(android.content.Context ctx, DateTime date, int titleId) {
		super(ctx);

		this.initializeDatePickerDialog(
						 ctx, date, ctx.getString(titleId), null, null);
	}

	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial date of the dialog.
	 * @param title The text of the title.
	 * @param minDate The minimum date of the dialog.
	 * @param maxDate The maximum date of the dialog.
	 */
	public CustomDatePickerDialog(android.content.Context ctx, DateTime date, String title,
							  final DateTime minDate, final DateTime maxDate) {
		super(ctx);

		this.initializeDatePickerDialog(ctx, date, title, minDate, maxDate);
	}

	/** Constructor.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial date of the dialog.
	 * @param titleId The resource id of the title.
	 * @param minDate The minimum date of the dialog.
	 * @param maxDate The maximum date of the dialog.
	 */
	public CustomDatePickerDialog(android.content.Context ctx, DateTime date, int titleId,
							  final DateTime minDate, final DateTime maxDate) {
		super(ctx);

		this.initializeDatePickerDialog(ctx, date,
								  ctx.getString(titleId), minDate, maxDate);
	}

	/** DatePicker dialog initialisation.
	 * @param ctx The context the dialog is to run in.
	 * @param date The initial date of the dialog.
	 * @param title The text of the title.
	 * @param minDate The minimum date of the dialog.
	 * @param maxDate The maximum date of the dialog.
	 */
	private void initializeDatePickerDialog(android.content.Context ctx, DateTime date,
	                         String title, DateTime minDate, DateTime maxDate) {
		this.ctx = ctx;
		this.title = title;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.year = date.getYear();
		this.monthOfYear = date.getMonthOfYear() - 1;
		this.dayOfMonth = date.getDayOfMonth();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(this.title);
		this.setCancelable(true);
		this.setIcon(0);

		LayoutInflater inflater = this.getLayoutInflater();
		View alertDialogView = inflater.inflate(R.layout.dialog_date_picker,
												null);
		this.setView(alertDialogView);

		this.datePicker = (DatePicker) alertDialogView.findViewById(
														R.id.dialog_pick_date);
		this.datePicker.init(this.year,
							this.monthOfYear,
							this.dayOfMonth,
							this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (this.minDate != null) {
				this.datePicker.setMinDate(this.minDate.getMillis());
			}

			if (this.maxDate != null) {
				this.datePicker.setMinDate(this.maxDate.getMillis());
			}
		}

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

	/** Gets the DatePicker contained in this dialog.
	 * @return the datePicker
	 */
	public DatePicker getDatePicker() {
		return this.datePicker;
	}

	@Override
	public void onDateChanged(DatePicker view, int year,
									 int monthOfYear, int dayOfMonth) {
		if (!(Build.VERSION.SDK_INT
				>= Build.VERSION_CODES.HONEYCOMB)) {
			DateTime newDate =
				    new DateTime(year,
				    		monthOfYear + 1,
				    		dayOfMonth,
				    		0,
				    		0);

			if (this.minDate != null && this.minDate.isAfter(newDate)) {
				view.init(
						this.minDate.getYear(),
						this.minDate.getMonthOfYear() - 1,
						this.minDate.getDayOfMonth(), this);
			} else if (this.maxDate != null && this.maxDate.isBefore(newDate)) {
				view.init(
						this.maxDate.getYear(),
						this.maxDate.getMonthOfYear() - 1,
						this.maxDate.getDayOfMonth(), this);
			} else {
				view.init(year, monthOfYear, dayOfMonth, this);
			}
		}
	}
}
