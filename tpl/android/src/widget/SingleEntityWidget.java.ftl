package ${project_namespace}.harmony.widget;

import java.util.List;
import com.tactfactory.harmony.test.demact.R;

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

public class SingleEntityWidget extends FrameLayout implements OnClickListener, DialogInterface.OnClickListener {
	private EditText entityEditText;
	private String title;
	private AlertDialog dialog;
	private EntityAdapter<?> adapter;
	
	public SingleEntityWidget(Context context) {
		this(context, null);
	}
	
	public SingleEntityWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.widget_single_entity, null);

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
	
	public void refreshText() {
		this.entityEditText.setText(this.adapter.getSelectedItemString());
	}
	
	public static abstract class EntityAdapter<T> {
		private SingleEntityWidget widget;
		protected List<T> itemList;
		protected T selectedItem;
		
		public abstract String entityToString(T entity);
		
		public void loadData(List<T> items) {
			this.loadData(items, null);
		}
	

		public void loadData(List<T> items, T selectedItem) {
			this.itemList = items;
			this.selectedItem = selectedItem;
			
			this.widget.recreateDialog(
					this.extractStrings(),
					this.itemList.indexOf(this.selectedItem));
		}
		
		public String[] extractStrings() {
			String[] result = new String[this.itemList.size()];
			for (int i = 0; i < this.itemList.size(); i++) {
				result[i] = this.entityToString(this.itemList.get(i));
			}
			return result;
		}
		
		public void selectItem(T item) {
			this.selectedItem = item;
			this.widget.recreateDialog(
					this.extractStrings(), 
					this.itemList.indexOf(this.selectedItem)); 
			this.widget.refreshText();
		}
		
		protected void selectItem(int pos) {
			this.selectedItem = this.itemList.get(pos);
		}
		
		protected String getSelectedItemString() {
			return this.entityToString(this.selectedItem);
		}
		
		protected void setWidget(SingleEntityWidget widget) {
			this.widget = widget;
		}
		
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


