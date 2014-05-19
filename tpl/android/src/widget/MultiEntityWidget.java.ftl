<@header?interpret />
package ${project_namespace}.harmony.widget;

import java.util.ArrayList;
import java.util.List;
import ${project_namespace}.R;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Graphical component used to pick multiple instances of an entity.
 */
public class MultiEntityWidget 
		extends FrameLayout 
		implements OnClickListener,
				OnMultiChoiceClickListener {
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
	public MultiEntityWidget(android.content.Context context) {
		this(context, null);
	}
	
	/** Constructor.
	 * @param context The context
	 * @param attrs Attribute set
	 */
	public MultiEntityWidget(android.content.Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_multi_entity, null);

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
	}
	
	/**
	 * Recreates the dialog.
	 *
	 * @param displayedStrings The displayed strings
	 * @param checkedPositions The checked positions
	 */
	private void recreateDialog(String[] displayedStrings,
				boolean[] checkedPositions) {
		final AlertDialog.Builder builder =
				new AlertDialog.Builder(this.getContext());
		
		builder.setTitle(this.title);
		builder.setMultiChoiceItems(displayedStrings, checkedPositions, this);
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

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		this.adapter.checkItem(which, isChecked);
		this.refreshText();
	}
	
	/**
	 * Refresh the text of the edit text.
	 */
	public void refreshText() {
		String[] strings = this.adapter.extractStrings();
		StringBuilder text = new StringBuilder();
		if (this.adapter.itemList != null) {
			for (int i = 0; i < this.adapter.itemList.size(); i++) {
				if (this.adapter.checkedItems[i]) {
					if (text.length() > 0) {
						text.append(", ");
					}
					text.append(strings[i]);
				}
			}
		}
		this.entityEditText.setText(text.toString());
	}

	/**
	 * Adapter class for this widger.
	 */
	public abstract static class EntityAdapter<T> {
		/** The associated widget. */
		private MultiEntityWidget widget;
		/** The item list. */
		protected List<T> itemList;
		/** The checked items. */
		protected boolean[] checkedItems;
		
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
		 * @param checkedItems The checked items
		 */
		public void loadData(List<T> items, boolean[] checkedItems) {
			this.itemList = items;
			if (checkedItems != null) {
				this.checkedItems = checkedItems;
			} else {
				this.checkedItems = new boolean[items.size()];
			}
			
			this.widget.recreateDialog(
					this.extractStrings(),
					this.checkedItems);
			this.widget.refreshText();
		}
		
		/**
		 * Extract the entities strings.
		 *
		 * @return The string array
		 */
		public String[] extractStrings() {
			String[] result = new String[this.itemList.size()];
			if (this.itemList != null) {
				for (int i = 0; i < this.itemList.size(); i++) {
					result[i] = this.entityToString(this.itemList.get(i));
				}
			}
			return result;
		}
		
		/**
		 * Check/Uncheck item.
		 *
		 * @param item The item to check
		 * @param check True to check, false to uncheck
		 */
		public void checkItem(T item, boolean check) {
			this.checkItem(this.itemList.indexOf(item), check); 
		}

		/**
		 * Check/Uncheck item.
		 *
		 * @param pos The position of the item to check
		 * @param check True to check, false to uncheck
		 */
		public void checkItem(int pos, boolean check) {
			this.checkedItems[pos] = check; 
			this.widget.recreateDialog(this.extractStrings(),
					this.checkedItems);
			this.widget.refreshText();
		}

		/**
		 * Associate a widget to this adapter.
		 *
		 * @param widget The widget
		 */
		protected void setWidget(MultiEntityWidget widget) {
			this.widget = widget;
		}

		/**
		 * Get the checked items.
		 *
		 * @return The list of checked items
		 */
		public ArrayList<T> getCheckedItems() {
			ArrayList<T> result = new ArrayList<T>();
			if (this.checkedItems != null) {
				for (int i = 0; i < this.checkedItems.length; i++) {
					if (this.checkedItems[i]) {
						result.add(this.itemList.get(i));
					}
				}
			}
			return result;
		}

		/**
		 * Set the checked items.
		 *
		 * @param items The list of checked items
		 */		
		public void setCheckedItems(List<T> items) {
			if (this.itemList != null) {
				for (int i = 0; i < this.itemList.size(); i++) {
					if (items.contains(this.itemList.get(i))) {
						this.checkedItems[i] = true;
					}
				}
			
				this.widget.recreateDialog(this.extractStrings(),
						this.checkedItems);
				this.widget.refreshText();
			}
		}
	}
}

