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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;

public class TimeWidget extends FrameLayout implements OnClickListener {
	private EditText timeEditText;
	private boolean isFormat24H = false;
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
		       this.isFormat24H = a.getBoolean(
		    		   R.styleable.TimeWidget_timeWidget_format24H, 
		    		   false);
		       
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
		 DateTime dt = new DateTime();

        final String time = 
        		this.timeEditText.getText().toString();
		if (!TextUtils.isEmpty(time)) {
			final String strInputTime = 
					time;
			dt = DateUtils.formatStringToTime(strInputTime);
		}
		
	    final CustomTimePickerDialog timePicker = 
	    		new CustomTimePickerDialog(
	    				getContext(), 
	    				dt, 
	    				this.isFormat24H,
	    				this.dialogTitle);
	    
	    timePicker.setPositiveButton(
	    		getContext().getString(android.R.string.ok), 
	    		new DialogClickListener(this));

	    timePicker.show();

		
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
