<@header?interpret />
package ${project_namespace}.harmony.widget;

import java.util.List;
import ${project_namespace}.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * The single entity widget.
 */
public class SingleEntityWidget 
		extends FrameLayout 
		implements OnClickListener,
				DialogInterface.OnClickListener {
	/** Clickable edit text. */
	private EditText entityEditText;
	/** Title of the dialog. */
	private String title;
	/** Alert dialog. */
	private AlertDialog dialog;
	/** Entity Adapter. */
	private EntityAdapter<?> adapter;
	
	/** Constructor.
	 * @param context The context
	 */
	public SingleEntityWidget(Context context) {
		this(context, null);
	}
	
	/** Constructor.
	 * @param context The context
	 * @param attrs Attribute set
	 */
	public SingleEntityWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_single_entity, null);

		this.initializeComponent(view);
		this.addView(view);
	}
	
	/** Set the adapter of the widget.
	 * @param adapter The entity adapter
	 */
	public void setAdapter(EntityAdapter<?> adapter) {
		this.adapter = adapter;
		this.adapter.setWidget(this);
	}

	/**
	 * Set the title of the dialog.
	 * @param title The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set the title of the dialog.
	 * @param titleId The title resource id
	 */
	public void setTitle(int titleId) {
		this.title = this.getContext().getString(titleId);
	}

	/**
	 * Initialize this views components.
	 * @param view The view
	 */
	private void initializeComponent(View view) {
		this.entityEditText = (EditText) view.findViewById(R.id.editText);
		this.entityEditText.setOnClickListener(this);
		
		//this.recreateDialog(this.title, this.getStrings(), this.checkedItems);
	}
	
	/**
	 * Recreates the dialog.
	 *
	 * @param displayedStrings The displayed strings
	 * @param selectedPosition The selected positions
	 */
	private void recreateDialog(String[] displayedStrings, int selectedPosition) {
		final AlertDialog.Builder builder =
				new AlertDialog.Builder(this.getContext());
		
		builder.setTitle(this.title);
		builder.setSingleChoiceItems(displayedStrings, selectedPosition, this);
		builder.setPositiveButton(android.R.string.ok, null);
		builder.setNegativeButton(android.R.string.cancel, null);

		this.dialog = builder.create();
		
	}
	
	@Override
	public void onClick(View v) {
		if (this.dialog != null) {
			this.dialog.show();
		} else {
			Toast.makeText(this.getContext(),
					"Data not loaded yet", 
					Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Refresh the text of the edit text.
	 */
	public void refreshText() {
		this.entityEditText.setText(this.adapter.getSelectedItemString());
	}
	
	/**
	 * Adapter class for this widger.
	 */
	public abstract static class EntityAdapter<T> {
		/** The associated widget. */
		private SingleEntityWidget widget;
		/** The item list. */
		protected List<T> itemList;
		/** The checked items. */
		protected T selectedItem;
		
		/** 
		 * Gives the string representation of the given entity for the list.
	 	 * @param entity The entity
		 * @return The string of the entity
		 */
		public abstract String entityToString(T entity);
		
		/**
		 * Loads this list of entities.
		 * @param items The list of items
		 */
		public void loadData(List<T> items) {
			this.loadData(items, null);
		}
	
		/**
		 * Loads this list of entities.
		 *
		 * @param items The list of items
		 * @param selectedItem The selected item
		 */
		public void loadData(List<T> items, T selectedItem) {
			this.itemList = items;
			this.selectedItem = selectedItem;
			
			this.widget.recreateDialog(
					this.extractStrings(),
					this.itemList.indexOf(this.selectedItem));
		}
		
		/**
		 * Extract the entities strings.
		 *
		 * @return the string array
		 */
		public String[] extractStrings() {
			String[] result = new String[this.itemList.size()];
			for (int i = 0; i < this.itemList.size(); i++) {
				result[i] = this.entityToString(this.itemList.get(i));
			}
			return result;
		}

		/**
		 * Select item.
		 *
		 * @param item The item to select
		 */
		public void selectItem(T item) {
			this.selectedItem = item;
			this.widget.recreateDialog(
					this.extractStrings(), 
					this.itemList.indexOf(this.selectedItem)); 
			this.widget.refreshText();
		}
		
		/**
		 * Select item.
		 *
		 * @param post The position of the item to select
		 */
		protected void selectItem(int pos) {
			this.selectedItem = this.itemList.get(pos);
		}
		
		/**
		 * Gets the selected item string.
		 *
		 * @return the selected item string
		 */
		protected String getSelectedItemString() {
			return this.entityToString(this.selectedItem);
		}
		
		/**
		 * Associate a widget to this adapter.
		 *
		 * @param widget The widget
		 */
		protected void setWidget(SingleEntityWidget widget) {
			this.widget = widget;
		}
		
		/**
		 * Returns the selected item.
		 * @return the selected item
		 */
		public T getSelectedItem() {
			return this.selectedItem;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		this.adapter.selectItem(which);
		this.refreshText();
	}
}


