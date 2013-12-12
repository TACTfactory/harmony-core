<@header?interpret />
package ${project_namespace}.harmony.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Spinner dedicated to enums.
 */
public class EnumSpinner extends Spinner {
	/** ArrayAdapter for enums. */
	private ArrayAdapter<Enum<?>> adapter;
	
	/**
	 * Constructor. 
	 * @param context component context
	 */
	public EnumSpinner(Context context) {
		super(context);
		this.initAdapter();
	}

	/**
	 * Constructor. 
	 * @param context component context
	 * @param attrs attribute set
	 */
	public EnumSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initAdapter();
	}
	
	/**
	 * Constructor. 
	 * @param context component context
	 * @param mode mode
	 */
	public EnumSpinner(Context context, int mode) {
		super(context, mode);
		this.initAdapter();
	}
	
	/**
	 * Constructor. 
	 * @param context component context
	 * @param attrs attribute set
	 * @param defStyle Def Style
	 */
	public EnumSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.initAdapter();
	}
	
	/**
	 * Constructor. 
	 * @param context component context
	 * @param attrs attribute set
	 * @param defStyle Def Style
	 * @param mode mode
	 */
	public EnumSpinner(Context context,
			AttributeSet attrs,
			int defStyle,
			int mode) {
		super(context, attrs, defStyle, mode);
		this.initAdapter();
	}
	
	/**
	 * Init inner adapter.
	 */
	private void initAdapter() {
		this.adapter = new ArrayAdapter<Enum<?>>(
				this.getContext(),
				android.R.layout.simple_spinner_item);
		this.setAdapter(this.adapter);
	}
	
	/**
	 * Associate an enum with this spinner.
	 * @param e The enum class (for example: if your enum is called "Title",
	 * 			send "Title.class")
	 * @param T the class
	 */
	public <T extends Enum<T>> void setEnum(Class<T> e) {
		
		for (T t : e.getEnumConstants()) {
			this.adapter.add(t);
		}
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * Set the given enum as the selected item in the spinner.
	 * @param e the enum to select
	 */
	public void setSelectedItem(Enum<?> e) {
		this.setSelection(this.adapter.getPosition(e));
	}
}

