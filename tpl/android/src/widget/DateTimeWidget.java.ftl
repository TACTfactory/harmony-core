package ${project_namespace}.harmony.widget;

import org.joda.time.DateTime;

import ${project_namespace}.R;
import ${project_namespace}.harmony.util.DateUtils;
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

public class DateTimeWidget extends FrameLayout implements OnClickListener {
	private EditText dateEditText;
	private EditText timeEditText;
	private boolean isFormat24H = false;
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
		       this.isFormat24H = a.getBoolean(
		    		   R.styleable.DateTimeWidget_dateTimeWidget_format24H, 
		    		   false);
		       
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
		        final String date = this.dateEditText.getText().toString();
		        
				if (!TextUtils.isEmpty(date)) {
					dt = DateUtils.formatStringToDate(date);
				}
				
			    final CustomDatePickerDialog datePicker = 
			    		new CustomDatePickerDialog(
			    				getContext(), 
			    				dt, 
			    				this.dateDialogTitle);
			    
			    datePicker.setPositiveButton(
			    		getContext().getString(android.R.string.ok), 
			    		new DateDialogClickListener(this));
		
			    datePicker.show();
			    break;
			case R.id.time:
		        final String time = 
		        		this.timeEditText.getText().toString();
				if (!TextUtils.isEmpty(time)) {
					dt = DateUtils.formatStringToTime(time);
				}
				
			    final CustomTimePickerDialog timePicker = 
			    		new CustomTimePickerDialog(
			    				getContext(), 
			    				dt,
			    				this.isFormat24H,
			    				this.timeDialogTitle);
			    
			    timePicker.setPositiveButton(
			    		getContext().getString(android.R.string.ok), 
			    		new TimeDialogClickListener(this));
		
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
		this.timeEditText.setText(DateUtils.formatTimeToString(time));
	}
	
	public DateTime getTime() {
		DateTime result;
		if (this.timeEditText.getText().toString().isEmpty()) {
			result = null;
		} else {
			result = DateUtils.formatStringToTime(
					this.timeEditText.getText().toString());
		}
		return result;
	}
	
	public void setDateTime(DateTime dateTime) {
		this.dateEditText.setText(DateUtils.formatDateToString(dateTime));
		this.timeEditText.setText(DateUtils.formatTimeToString(dateTime));
	}
	
	public DateTime getDateTime() {
		DateTime result;
		if (this.timeEditText.getText().toString().isEmpty()
				|| this.dateEditText.getText().toString().isEmpty()) {
			result = null;
		} else {
			result = DateUtils.formatStringToDateTime(
					this.dateEditText.getEditableText().toString(),
					this.timeEditText.getEditableText().toString());
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
			final DatePicker dp = 
					((CustomDatePickerDialog) dialog).getDatePicker();
				final DateTime date = 
					new DateTime(dp.getYear(), 
							dp.getMonth() + 1, 
							dp.getDayOfMonth(), 
							0, 
							0);
				dateWidget.setDate(date);
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
			DateTime time = new DateTime(0);
			time = new DateTime(time.getYear(), 
					time.getMonthOfYear(), 
					time.getDayOfMonth(), 
					tp.getCurrentHour(), 
					tp.getCurrentMinute());
			timeWidget.setTime(time);
		}
	}
}

