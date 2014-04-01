<@header?interpret />
package ${project_namespace}.harmony.widget;

import org.joda.time.DateTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import com.google.common.base.Strings;

import ${project_namespace}.${project_name?cap_first}Application;
import ${project_namespace}.R;
import ${project_namespace}.harmony.util.DateUtils;
import ${project_namespace}.harmony.util.DateUtils.TimeFormatType;

/**
 * View for DateTime selection.
 * This view is composed of two disabled edit text next to each other and
 * of two alert dialogs (DatePickerDialog & TimePickerDialog).
 * It is really helpful to let your users chose a date.
 */
public class DateTimeWidget extends FrameLayout implements OnClickListener {
	/** Enum format24h value H24. */
	private static final int H24_ENUM_CONSTANT = 1;
	/** Enum format24h value AMPM. */
	private static final int AM_PM_ENUM_CONSTANT = 2;
	/** Enum format24h value ANDROID_CONF. */
	private static final int ANDROID_CONF_ENUM_CONSTANT = 3;

	/** Date Click Listener. */
	private OnDateClickListener dateListener;
	/** Time Click Listener. */
	private OnTimeClickListener timeListener;
	/** Date Edit Text. */
	private EditText dateEditText;
	/** Time Edit Text. */
	private EditText timeEditText;
	/** Time format (24h or am/pm). */
	private TimeFormatType timeFormat = TimeFormatType.ANDROID_CONF;
	/** Date dialog title. */
	private String dateDialogTitle;
	/** Time dialog title. */
	private String timeDialogTitle;
	/** Default date. */
	private DateTime defaultDate;
	/** Default time. */
	private DateTime defaultTime;

	/**
	 * Constructor.
	 *
	 * @param context View's context
	 */
	public DateTimeWidget(Context context) {
		this(context, null);
	}

	/**
	 * Constructor.
	 *
	 * @param context View's context
	 * @param attrs The attribute set
	 */
	public DateTimeWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_datetime, null);

		this.initializeComponent(view);
		if (attrs != null) {
			this.initializeAttributes(attrs);
		}
		this.addView(view);
	}

	/**
	 * Initialize this views components.
	 * @param view The view
	 */
	private void initializeComponent(View view) {
		this.dateEditText = (EditText) view.findViewById(R.id.date);
		this.dateEditText.setOnClickListener(this);
		this.timeEditText = (EditText) view.findViewById(R.id.time);
		this.timeEditText.setOnClickListener(this);
	}

	/**
	 * Initialize this views attribute set.
	 * @param attrs the attribute set
	 */
	private void initializeAttributes(AttributeSet attrs) {
		TypedArray a = this.getContext().getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.DateTimeWidget,
		        0, 0);

		   try {
			   // 24H format
			   int format24Hid = a.getInt(
		    		   R.styleable.DateTimeWidget_dateTimeWidget_format24H,
		    		   2);
			   switch (format24Hid) {
					case H24_ENUM_CONSTANT:
					   this.timeFormat = TimeFormatType.H24;
					   break;

					case AM_PM_ENUM_CONSTANT:
					   this.timeFormat = TimeFormatType.AMPM;
					   break;

					case ANDROID_CONF_ENUM_CONSTANT:
					   this.timeFormat = TimeFormatType.ANDROID_CONF;
					   break;

					default:
						this.timeFormat = TimeFormatType.ANDROID_CONF;
						break;
			   }

		       // Date Dialog Title
		       this.dateDialogTitle = a.getString(
		    		   R.styleable.
							DateTimeWidget_dateTimeWidget_dateDialogTitle);
		       if (this.dateDialogTitle == null) {
					int rId = a.getResourceId(
			    		   R.styleable.
								DateTimeWidget_dateTimeWidget_dateDialogTitle,
			    		   0);
		    	   	this.dateDialogTitle = this.getContext().getString(rId);
		       }

		       // Time Dialog Title
		       this.timeDialogTitle = a.getString(
		    		   R.styleable.
							DateTimeWidget_dateTimeWidget_timeDialogTitle);
		       if (this.timeDialogTitle == null) {
		    	   int rId = a.getResourceId(
			    		   R.styleable.
								DateTimeWidget_dateTimeWidget_timeDialogTitle,
			    		   0);
		    	   this.timeDialogTitle = this.getContext().getString(rId);
		       }
		   } finally {
		       a.recycle();
		   }
	}

	@Override
	public void onClick(View arg0) {
		DateTime dt;

		switch (arg0.getId()) {
			case R.id.date:
				if (this.defaultDate == null) {
					dt = DateTime.now();
				} else {
					dt = this.defaultDate;
				}
				if (this.dateListener != null) {
					this.dateListener.onClickDateEditText(this);
				}
		        final String date = this.dateEditText.getText().toString();

				if (!Strings.isNullOrEmpty(date)) {
					dt = DateUtils.formatStringToDate(date);
				}

			    final CustomDatePickerDialog datePicker =
			    		new CustomDatePickerDialog(
			    				getContext(),
			    				dt,
			    				this.dateDialogTitle);


			    DateDialogClickListener listener =
							new DateDialogClickListener(this);
			    datePicker.setPositiveButton(android.R.string.ok,
							listener);
			    datePicker.setNegativeButton(android.R.string.cancel,
							listener);
			    datePicker.show();
			    break;
			case R.id.time:
				if (this.defaultTime == null) {
					dt = DateTime.now();
				} else {
					dt = this.defaultTime;
				}
				if (this.timeListener != null) {
					this.timeListener.onClickTimeEditText(this);
				}
		        final String time =
		        		this.timeEditText.getText().toString();
				if (!Strings.isNullOrEmpty(time)) {
					dt = DateUtils.formatStringToTime(time);
				}

				boolean format24H = false;
				if (this.timeFormat.equals(TimeFormatType.AMPM)) {
					format24H = false;
				} else if (this.timeFormat.equals(TimeFormatType.H24)) {
					format24H = true;
				} else if (this.timeFormat.equals(
								TimeFormatType.ANDROID_CONF)) {
					format24H = ${project_name?cap_first}Application.is24Hour();
				}
			    final CustomTimePickerDialog timePicker =
			    		new CustomTimePickerDialog(
			    				getContext(),
			    				dt,
			    				format24H,
			    				this.timeDialogTitle);

			    TimeDialogClickListener timeListener =
							new TimeDialogClickListener(this);
			    timePicker.setPositiveButton(android.R.string.ok,
							timeListener);
			    timePicker.setNegativeButton(android.R.string.cancel,
							timeListener);
			    timePicker.show();
			    break;

			default:
				break;
		}

	}

	/**
	 * Clear the date field.
	 */
	public void clearDate() {
		this.dateEditText.setText("");
	}

	/**
	 * Set the default date when user click and field is empty.
	 */
	public void setDefaultDate(DateTime defaultDate) {
		this.defaultDate = defaultDate;
	}

	/**
	 * Set the component date.
	 *
	 * @param date The date to set
	 */
	public void setDate(DateTime date) {
		this.dateEditText.setText(DateUtils.formatDateToString(date));
	}

	/**
	 * Get the component date.
	 *
	 * @return The date
	 */
	public DateTime getDate() {
		DateTime result;
		if (Strings.isNullOrEmpty(this.dateEditText.getText().toString())) {
			result = null;
		} else {
			result = DateUtils.formatStringToDate(
					this.dateEditText.getText().toString());
		}
		return result;
	}

	/**
	 * Clear the time field.
	 */
	public void clearTime() {
		this.timeEditText.setText("");
	}

	/**
	 * Set the default time when user click and field is empty.
	 */
	public void setDefaultTime(DateTime defaultTime) {
		this.defaultTime = defaultTime;
	}

	/**
	 * Set the component time.
	 *
	 * @param time The time to set
	 */
	public void setTime(DateTime time) {
		this.timeEditText.setText(DateUtils.formatTimeToString(
						time,
						this.timeFormat));
	}

	/**
	 * Get the component time.
	 *
     * @return The time
	 */
	public DateTime getTime() {
		DateTime result;
		if (Strings.isNullOrEmpty(this.timeEditText.getText().toString())) {
			result = null;
		} else {
			result = DateUtils.formatStringToTime(
					this.timeEditText.getText().toString(),
					this.timeFormat);
		}
		return result;
	}

	/**
	 * Clear the date and time fields.
	 */
	public void clearDateTime() {
		this.clearTime();
		this.clearDate();
	}

	/**
	 * Set the default date and time when user click and field is empty.
	 */
	public void setDefaultDateTime(DateTime defaultDateTime) {
		this.setDefaultDate(defaultDateTime);
		this.setDefaultTime(defaultDateTime);
	}

	/**
	 * Set the component date and time.
	 *
	 * @param dateTime The datetime to set
	 */
	public void setDateTime(DateTime dateTime) {
		this.dateEditText.setText(DateUtils.formatDateToString(dateTime));
		this.timeEditText.setText(
				DateUtils.formatTimeToString(
						dateTime,
						this.timeFormat));
	}

	/**
	 * Get the component datetime.
	 *
	 * @return The datetime.
	 */
	public DateTime getDateTime() {
		DateTime result;
		if (Strings.isNullOrEmpty(this.timeEditText.getText().toString())
				|| Strings.isNullOrEmpty(this.dateEditText.getText().toString())) {
			result = null;
		} else {
			result = DateUtils.formatStringToDateTime(
					this.dateEditText.getEditableText().toString(),
					this.timeEditText.getEditableText().toString(),
					this.timeFormat);
		}
		return result;
	}

	/**
	 * Internal Click Listener for the dialog.
	 */
	private class DateDialogClickListener
				implements DialogInterface.OnClickListener {
		/** The datetime widget associated to this listener. */
		private DateTimeWidget dateWidget;

		/**
		 * Constructor.
		 *
		 * @param dateWidget The datewidget
		 */
		public DateDialogClickListener(DateTimeWidget dateWidget) {
			this.dateWidget = dateWidget;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
				case AlertDialog.BUTTON_POSITIVE :
					final DatePicker dp =
						((CustomDatePickerDialog) dialog).getDatePicker();
					final DateTime date =
						new DateTime(dp.getYear(),
								dp.getMonth() + 1,
								dp.getDayOfMonth(),
								0,
								0);
					this.dateWidget.setDate(date);
					if (DateTimeWidget.this.dateListener != null) {
						DateTimeWidget.this.dateListener.onValidateDate(DateTimeWidget.this);
					}
					break;

				case AlertDialog.BUTTON_NEGATIVE :
					if (DateTimeWidget.this.dateListener != null) {
						DateTimeWidget.this.dateListener.onCancelDate(DateTimeWidget.this);
					}
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Internal Click Listener for the dialog.
	 */
	private class TimeDialogClickListener
		implements DialogInterface.OnClickListener {
		/** The datetime widget associated to this listener. */
		private DateTimeWidget timeWidget;


		/**
		 * Constructor.
		 *
		 * @param timeWidget The timeWidget
		 */
		public TimeDialogClickListener(DateTimeWidget timeWidget) {
			this.timeWidget = timeWidget;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			final TimePicker tp =
					((CustomTimePickerDialog) dialog).getTimePicker();
			switch (which) {
				case AlertDialog.BUTTON_POSITIVE :
					DateTime time = new DateTime(0);
					time = new DateTime(time.getYear(),
						time.getMonthOfYear(),
						time.getDayOfMonth(),
						tp.getCurrentHour(),
						tp.getCurrentMinute());
					this.timeWidget.setTime(time);
					if (DateTimeWidget.this.timeListener != null) {
						DateTimeWidget.this.timeListener.onValidateTime(DateTimeWidget.this);
					}
				break;

				case AlertDialog.BUTTON_NEGATIVE :
					if (DateTimeWidget.this.timeListener != null) {
						DateTimeWidget.this.timeListener.onCancelTime(DateTimeWidget.this);
					}
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Sets an OnDateClickListener for this view.
	 *
	 * @param listener The listener to set
	 */
	public void setOnDateClickListener(OnDateClickListener listener) {
		this.dateListener = listener;
	}

	/**
	 * Sets an OnTimeClickListener for this view.
	 *
	 * @param listener The listener to set
	 */
	public void setOnTimeClickListener(OnTimeClickListener listener) {
		this.timeListener = listener;
	}

	/**
	 * Remove the current OnDateClickListener.
	 */
	public void removeOnDateClickListener() {
		this.dateListener = null;
	}

	/**
	 * Remove the current OnTimeClickListener.
	 */
	public void removeOnTimeClickListener() {
		this.timeListener = null;
	}

	/** Widget Interface for click events. */
	public interface OnDateClickListener {
		/**
		 * Called when User click on the Date EditText.
		 */
		void onClickDateEditText(DateTimeWidget dateTimeWidget);

		/**
		 * Called when User click on the date picker dialog's ok button.
		 */
		void onValidateDate(DateTimeWidget dateTimeWidget);

		/**
		 * Called when User click on the date picker dialog's cancel button.
		 */
		void onCancelDate(DateTimeWidget dateTimeWidget);
	}

	/** Widget Interface for click events. */
	public interface OnTimeClickListener {
		/**
		 * Called when User click on the Time EditText.
		 */
		void onClickTimeEditText(DateTimeWidget dateTimeWidget);

		/**
		 * Called when User click on the Time picker dialog's ok button.
		 */
		void onValidateTime(DateTimeWidget dateTimeWidget);

		/**
		 * Called when User click on the Time picker dialog's cancel button.
		 */
		void onCancelTime(DateTimeWidget dateTimeWidget);
	}
}

