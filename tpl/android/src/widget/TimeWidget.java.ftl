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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import ${project_namespace}.DemactApplication;
import ${project_namespace}.R;
import ${project_namespace}.harmony.util.DateUtils;
import ${project_namespace}.harmony.util.DateUtils.TimeFormatType;

public class TimeWidget extends FrameLayout implements OnClickListener {
	private OnTimeClickListener timeListener;
	private EditText timeEditText;
	private TimeFormatType timeFormat = TimeFormatType.ANDROID_CONF;
	private String dialogTitle;
	
	public TimeWidget(Context context) {
		this(context, null);
	}

	public TimeWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_time, null);

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
		this.timeEditText = (EditText) view.findViewById(R.id.time);
		this.timeEditText.setOnClickListener(this);
	}

	private void initializeAttributes(AttributeSet attrs) {
		TypedArray a = this.getContext().getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.TimeWidget,
		        0, 0);

		   try {
			   // 24H format
			   int format24Hid = a.getInt(
		    		   R.styleable.TimeWidget_timeWidget_format24H, 
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
		       
		       // Dialog Title
		       this.dialogTitle = a.getString(
		    		   R.styleable.TimeWidget_timeWidget_dialogTitle); 
		       if (this.dialogTitle == null) {
		    	   int rId = a.getResourceId(
			    		   R.styleable.TimeWidget_timeWidget_dialogTitle,
			    		   0);
		    	   this.dialogTitle = this.getContext().getString(rId);
		       }
		             
		   } finally {
		       a.recycle();
		   }
	}


	@Override
	public void onClick(View arg0) {
		if (this.timeListener != null) {
			this.timeListener.onClickTimeEditText();
		}
		DateTime dt = new DateTime();

        final String time = 
        		this.timeEditText.getText().toString();
		if (!TextUtils.isEmpty(time)) {
			final String strInputTime = 
					time;
			dt = DateUtils.formatStringToTime(strInputTime);
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
	    				this.dialogTitle);
	    
	    DialogClickListener timeListener = new DialogClickListener(this);
			    timePicker.setPositiveButton(android.R.string.ok, timeListener);
			    timePicker.setNegativeButton(android.R.string.cancel, timeListener);
			    timePicker.show();

	    timePicker.show();

		
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
	
	private class DialogClickListener 
				implements DialogInterface.OnClickListener {
		private TimeWidget timeWidget;

		public DialogClickListener(TimeWidget timeWidget) {
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
					if (TimeWidget.this.timeListener != null) {
						TimeWidget.this.timeListener.onValidateTime();
					}
				break;
				
			case AlertDialog.BUTTON_NEGATIVE :
				if (TimeWidget.this.timeListener != null) {
					TimeWidget.this.timeListener.onCancelTime();
				}
				break;
			}
		}
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
	 * Remove the current OnTimeClickListener.
	 */
	public void removeOnTimeClickListener() {
		this.timeListener = null;
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
