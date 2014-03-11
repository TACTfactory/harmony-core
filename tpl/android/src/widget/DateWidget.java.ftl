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

import com.google.common.base.Strings;

import ${project_namespace}.R;
import ${project_namespace}.harmony.util.DateUtils;

/**
 * View for Date selection.
 * This view is composed of a disabled edit text and
 * of an alert dialogs (DatePickerDialog).
 * It is really helpful to let your users chose a date.
 */
public class DateWidget extends FrameLayout implements OnClickListener {
	/** Date Click Listener. */
	private OnDateClickListener dateListener;
	/** Date Edit Text. */
	private EditText dateEditText;
	/** Date dialog title. */
	private String dialogTitle;

	/**
	 * Constructor.
	 *
	 * @param context View's context
	 */
	public DateWidget(Context context) {
		this(context, null);
	}

	/**
	 * Constructor.
	 *
	 * @param context View's context
	 * @param attrs The attribute set
	 */
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

	/**
	 * Initialize this views attribute set.
	 * @param attrs the attribute set
	 */
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
		if (this.dateListener != null) {
			this.dateListener.onClickDateEditText();
		}
		DateTime dt = new DateTime();

        final String createdAtDate =
        		this.dateEditText.getText().toString();
		if (!Strings.isNullOrEmpty(createdAtDate)) {
			final String strInputDate =
					createdAtDate;
			dt = DateUtils.formatStringToDate(strInputDate);
		}

	    final CustomDatePickerDialog datePicker =
	    		new CustomDatePickerDialog(
	    				getContext(),
	    				dt,
	    				this.dialogTitle);

	    DialogClickListener listener = new DialogClickListener(this);
	    datePicker.setPositiveButton(android.R.string.ok, listener);
	    datePicker.setNegativeButton(android.R.string.cancel, listener);
	    datePicker.show();
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
	 * Internal Click Listener for the dialog.
	 */
	private class DialogClickListener
				implements DialogInterface.OnClickListener {
		/** The date widget associated to this listener. */
		private DateWidget dateWidget;

		/**
		 * Constructor.
		 *
		 * @param dateWidget The datewidget
		 */
		public DialogClickListener(DateWidget dateWidget) {
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
					if (DateWidget.this.dateListener != null) {
						DateWidget.this.dateListener.onValidateDate();
					}
					break;

				case AlertDialog.BUTTON_NEGATIVE :
					if (DateWidget.this.dateListener != null) {
						DateWidget.this.dateListener.onCancelDate();
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
	 * Remove the current OnDateClickListener.
	 */
	public void removeOnDateClickListener() {
		this.dateListener = null;
	}

	/** Widget Interface for click events. */
	public interface OnDateClickListener {
		/**
		 * Called when User click on the Date EditText.
		 */
		void onClickDateEditText();

		/**
		 * Called when User click on the date picker dialog's ok button.
		 */
		void onValidateDate();

		/**
		 * Called when User click on the date picker dialog's cancel button.
		 */
		void onCancelDate();
	}

}
