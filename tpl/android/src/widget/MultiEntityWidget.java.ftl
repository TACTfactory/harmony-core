package ${project_namespace}.harmony.widget;

import java.util.ArrayList;
import java.util.List;
import com.tactfactory.harmony.test.demact.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MultiEntityWidget extends FrameLayout implements OnClickListener, OnMultiChoiceClickListener {
	private EditText entityEditText;
	private String title;
	private AlertDialog dialog;
	private EntityAdapter<?> adapter;
	
	public MultiEntityWidget(Context context) {
		this(context, null);
	}
	
	public MultiEntityWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_multi_entity, null);

		this.initializeComponent(view);
		this.addView(view);
	}
	
	public void setAdapter(EntityAdapter<?> adapter) {
		this.adapter = adapter;
		this.adapter.setWidget(this);
	}

	public void setTitle(String title) {
		this.title = title;
	}

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
	
	private void recreateDialog(String[] displayedStrings, boolean[] checkedPositions) {
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
	
	public void refreshText() {
		String[] strings = this.adapter.extractStrings();
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < this.adapter.itemList.size(); i++) {
			if (this.adapter.checkedItems[i]) {
				if (text.length() > 0) {
					text.append(", ");
				}
				text.append(strings[i]);
			}
		}
		this.entityEditText.setText(text.toString());
	}
	
	public static abstract class EntityAdapter<T> {
		private MultiEntityWidget widget;
		protected List<T> itemList;
		protected boolean[] checkedItems;
		
		public abstract String entityToString(T entity);
		
		public void loadData(List<T> items) {
			this.loadData(items, null);
		}
	

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
		
		public String[] extractStrings() {
			String[] result = new String[this.itemList.size()];
			for (int i = 0; i < this.itemList.size(); i++) {
				result[i] = this.entityToString(this.itemList.get(i));
			}
			return result;
		}
		
		public void checkItem(T item, boolean check) {
			this.checkItem(this.itemList.indexOf(item), check); 
		}
		

		public void checkItem(int pos, boolean check) {
			this.checkedItems[pos] = check; 
			this.widget.recreateDialog(this.extractStrings(),
					this.checkedItems);
			this.widget.refreshText();
		}
		
		protected void setWidget(MultiEntityWidget widget) {
			this.widget = widget;
		}
		
		public ArrayList<T> getCheckedItems() {
			ArrayList<T> result = new ArrayList<T>();
			for (int i = 0; i < this.checkedItems.length; i++) {
				if (this.checkedItems[i]) {
					result.add(this.itemList.get(i));
				}
			}
			return result;
		}
		
		public void setCheckedItems(List<T> items) {
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

