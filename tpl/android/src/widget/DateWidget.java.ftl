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

public class DateWidget extends FrameLayout implements OnClickListener {
	private EditText dateEditText;
	private String dialogTitle;
	
	public DateWidget(Context context) {
		this(context, null);
	}

	public DateWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_date, null);

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
	}

	private void initializeAttributes(AttributeSet attrs) {
		TypedArray a = this.getContext().getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.DateWidget,
		        0, 0);

		   try {
		       // Dialog Title
		       this.dialogTitle = a.getString(
		    		   R.styleable.DateWidget_dateWidget_dialogTitle); 
		       if (this.dialogTitle == null) {
		    	   int rId = a.getResourceId(
			    		   R.styleable.DateWidget_dateWidget_dialogTitle,
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

        final String createdAtDate = 
        		this.dateEditText.getText().toString();
		if (!TextUtils.isEmpty(createdAtDate)) {
			final String strInputDate = 
					createdAtDate;
			dt = DateUtils.formatStringToDate(strInputDate);
		}
		
	    final CustomDatePickerDialog createdAtDpd = 
	    		new CustomDatePickerDialog(
	    				getContext(), 
	    				dt, 
	    				this.dialogTitle);
	    
	    createdAtDpd.setPositiveButton(
	    		getContext().getString(android.R.string.ok), 
	    		new DialogClickListener(this));

	    createdAtDpd.show();

		
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
	
	private class DialogClickListener 
				implements DialogInterface.OnClickListener {
		private DateWidget dateWidget;

		public DialogClickListener(DateWidget dateWidget) {
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
}
