package ${project_namespace}.harmony.widget;

import org.joda.time.DateTime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import ${project_namespace}.DemactApplication;
import ${project_namespace}.R;
import ${project_namespace}.harmony.util.DateUtils;
import ${project_namespace}.harmony.util.DateUtils.TimeFormatType;

public class DateTimeWidget extends FrameLayout implements OnClickListener {
	private OnDateClickListener dateListener;
	private OnTimeClickListener timeListener;
	private EditText dateEditText;
	private EditText timeEditText;
	private TimeFormatType timeFormat = TimeFormatType.ANDROID_CONF;
	private String dateDialogTitle;
	private String timeDialogTitle;
	
	public DateTimeWidget(Context context) {
		this(context, null);
	}

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
				   case 1:
					   this.timeFormat = TimeFormatType.H24;
					   break;
					   
				   case 2:
					   this.timeFormat = TimeFormatType.AMPM;
					   break;
					   
				   case 3:
					   this.timeFormat = TimeFormatType.ANDROID_CONF;
					   break;
			   }
		       
		       // Date Dialog Title
		       this.dateDialogTitle = a.getString(
		    		   R.styleable.DateTimeWidget_dateTimeWidget_dateDialogTitle); 
		       if (this.dateDialogTitle == null) {
		    	   int rId = a.getResourceId(
			    		   R.styleable.DateTimeWidget_dateTimeWidget_dateDialogTitle,
			    		   0);
		    	   this.dateDialogTitle = this.getContext().getString(rId);
		       }
		       
		       // Time Dialog Title
		       this.timeDialogTitle = a.getString(
		    		   R.styleable.DateTimeWidget_dateTimeWidget_timeDialogTitle); 
		       if (this.timeDialogTitle == null) {
		    	   int rId = a.getResourceId(
			    		   R.styleable.DateTimeWidget_dateTimeWidget_timeDialogTitle,
			    		   0);
		    	   this.timeDialogTitle = this.getContext().getString(rId);
		       }		       
		   } finally {
		       a.recycle();
		   }
	}

	@Override
	public void onClick(View arg0) {
		DateTime dt = new DateTime();
		
		switch (arg0.getId()) {
			case R.id.date:	
				if (this.dateListener != null) {
					this.dateListener.onClickDateEditText();
				}
		        final String date = this.dateEditText.getText().toString();
		        
				if (!TextUtils.isEmpty(date)) {
					dt = DateUtils.formatStringToDate(date);
				}
				
			    final CustomDatePickerDialog datePicker = 
			    		new CustomDatePickerDialog(
			    				getContext(), 
			    				dt, 
			    				this.dateDialogTitle);
			    
			    
			    DateDialogClickListener listener = new DateDialogClickListener(this);
			    datePicker.setPositiveButton(android.R.string.ok, listener);
			    datePicker.setNegativeButton(android.R.string.cancel, listener);
			    datePicker.show();
			    break;
			case R.id.time:
				if (this.timeListener != null) {
					this.timeListener.onClickTimeEditText();
				}
		        final String time = 
		        		this.timeEditText.getText().toString();
				if (!TextUtils.isEmpty(time)) {
					dt = DateUtils.formatStringToTime(time);
				}
				
				boolean format24H = false;
				if (this.timeFormat.equals(TimeFormatType.AMPM)) {
					format24H = false;
				} else if (this.timeFormat.equals(TimeFormatType.H24)) {
					format24H = true;
				} else if (this.timeFormat.equals(TimeFormatType.ANDROID_CONF)) {
					format24H = DemactApplication.is24Hour();
				}
			    final CustomTimePickerDialog timePicker = 
			    		new CustomTimePickerDialog(
			    				getContext(), 
			    				dt,
			    				format24H,
			    				this.timeDialogTitle);
			    
			    TimeDialogClickListener timeListener = new TimeDialogClickListener(this);
			    timePicker.setPositiveButton(android.R.string.ok, timeListener);
			    timePicker.setNegativeButton(android.R.string.cancel, timeListener);
			    timePicker.show();
			    break;
		}
		
	}
	
	public void setDate(DateTime date) {
		this.dateEditText.setText(DateUtils.formatDateToString(date));
	}
	
	public DateTime getDate() {
		DateTime result;
		if (this.dateEditText.getText().toString().isEmpty()) {
			result = null;
		} else {
			result = DateUtils.formatStringToDate(
					this.dateEditText.getText().toString());
		}
		return result;
	}
	
	
	public void setTime(DateTime time) {
		this.timeEditText.setText(DateUtils.formatTimeToString(
						time,
						this.timeFormat));
	}
	
	public DateTime getTime() {
		DateTime result;
		if (this.timeEditText.getText().toString().isEmpty()) {
			result = null;
		} else {
			result = DateUtils.formatStringToTime(
					this.timeEditText.getText().toString(),
					this.timeFormat);
		}
		return result;
	}
	
	public void setDateTime(DateTime dateTime) {
		this.dateEditText.setText(DateUtils.formatDateToString(dateTime));
		this.timeEditText.setText(
				DateUtils.formatTimeToString(
						dateTime, 
						this.timeFormat));
	}
	
	public DateTime getDateTime() {
		DateTime result;
		if (this.timeEditText.getText().toString().isEmpty()
				|| this.dateEditText.getText().toString().isEmpty()) {
			result = null;
		} else {
			result = DateUtils.formatStringToDateTime(
					this.dateEditText.getEditableText().toString(),
					this.timeEditText.getEditableText().toString(),
					this.timeFormat);
		}
		return result;
	}
	
	private class DateDialogClickListener 
				implements DialogInterface.OnClickListener {
		private DateTimeWidget dateWidget;

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
					dateWidget.setDate(date);
					if (DateTimeWidget.this.dateListener != null) {
						DateTimeWidget.this.dateListener.onValidateDate();
					}
					break;
					
				case AlertDialog.BUTTON_NEGATIVE :
					if (DateTimeWidget.this.dateListener != null) {
						DateTimeWidget.this.dateListener.onCancelDate();
					}
					break;
			}
		}
	}
	
	private class TimeDialogClickListener 
		implements DialogInterface.OnClickListener {
		private DateTimeWidget timeWidget;
		
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
					timeWidget.setTime(time);
					if (DateTimeWidget.this.timeListener != null) {
						DateTimeWidget.this.timeListener.onValidateTime();
					}
				break;
				
			case AlertDialog.BUTTON_NEGATIVE :
				if (DateTimeWidget.this.timeListener != null) {
					DateTimeWidget.this.timeListener.onCancelTime();
				}
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
		public void onClickDateEditText();
		
		/** 
		 * Called when User click on the date picker dialog's ok button. 
		 */
		public void onValidateDate();
		
		/** 
		 * Called when User click on the date picker dialog's cancel button. 
		 */
		public void onCancelDate();
	}
	
	/** Widget Interface for click events. */
	public interface OnTimeClickListener {
		/** 
		 * Called when User click on the Time EditText. 
		 */
		public void onClickTimeEditText();
		
		/** 
		 * Called when User click on the Time picker dialog's ok button. 
		 */
		public void onValidateTime();
		
		/** 
		 * Called when User click on the Time picker dialog's cancel button. 
		 */
		public void onCancelTime();
	}
}

