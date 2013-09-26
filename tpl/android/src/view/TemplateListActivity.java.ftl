<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
import ${project_namespace}.harmony.view.HarmonyListFragment;
import ${project_namespace}.entity.${curr.name};

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * This class will display ${curr.name} entities in a list.
 */
public class ${curr.name}ListActivity 
		extends HarmonyFragmentActivity
		implements HarmonyListFragment.OnClickCallback,
				HarmonyListFragment.OnLoadCallback {

	protected ${curr.name}ListFragment listFragment;
	protected ${curr.name}ShowFragment detailFragment;
	protected boolean dualMode;
	private int lastSelectedItemPosition = 0;
	private ${curr.name} lastSelectedItem;
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);

		
		this.detailFragment = (${curr.name}ShowFragment) 
						this.getSupportFragmentManager().findFragmentById(R.id.fragment_show);

		this.listFragment = (${curr.name}ListFragment)
						this.getSupportFragmentManager().findFragmentById(R.id.fragment_list);
		
		if (this.detailFragment != null) {
			this.dualMode = true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_${curr.name?lower_case}_list);

		// Google Analytics
		//GoogleAnalyticsSessionManager.getInstance(getApplication())
		//.incrementActivityCount();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Google Analytics
		/*GoogleAnalyticsTracker.getInstance().dispatch();
        GoogleAnalyticsSessionManager.getInstance().decrementActivityCount();*/
	}

	@Override
	public void onActivityResult(
				int requestCode,
				int resultCode,
				Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode <= SUPPORT_V4_RESULT_HACK) {
			switch(requestCode) {
				default:
					break;
			}
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
			this.lastSelectedItemPosition = position;
		
		if (this.dualMode) {
			this.selectListItem(this.lastSelectedItemPosition);
		} else {
			final Intent intent = new Intent(this, ${curr.name}ShowActivity.class);
			final ${curr.name} item = (${curr.name}) l.getItemAtPosition(position);
			intent.putExtra("${curr.name}", (Parcelable) item);
			this.startActivity(intent);
		}
	}


	private void loadDetailFragment(${curr.name} item) {
		this.detailFragment.update(item);
	}

	private void selectListItem(int listPosition) {
		int listSize = this.listFragment.getListAdapter().getCount();
		if (listSize > 0) {
			if (listPosition >= listSize) {
				listPosition = listSize - 1;
			} else if (listPosition < 0) {
				listPosition = 0;
			}
			this.listFragment.getListView().setItemChecked(listPosition, true);
			${curr.name} item = (${curr.name}) 
					this.listFragment.getListAdapter().getItem(listPosition);
			this.lastSelectedItem = item;
			this.loadDetailFragment(item);
		} else {
			this.loadDetailFragment(null);
		}
	}

	@Override
	public void onListLoaded() {
		if (this.dualMode) {
			int newPosition = ((${curr.name}ListAdapter)this.listFragment.getListAdapter()).getPosition(this.lastSelectedItem);
			if (newPosition < 0) {
				this.selectListItem(this.lastSelectedItemPosition);
			} else {				
				this.selectListItem(newPosition);
			}
		}
	}

	/**
	 * Is the activity in dual fragment mode (tablet) ?
	 *
	 * @return true if dual mode
	 */
	public boolean isDualMode() {
		return this.dualMode;
	}
}
