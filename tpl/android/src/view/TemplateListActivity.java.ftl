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

	protected HarmonyListFragment<?> listFragment;
	protected FrameLayout detailFragmentContainer;
	protected boolean dualMode;
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);

		
		this.detailFragmentContainer = (FrameLayout) this.findViewById(R.id.fragment_show);
		this.listFragment = 
				(HarmonyListFragment<?>) this.getSupportFragmentManager()
						.findFragmentById(R.id.fragment_list);
		
		if (this.detailFragmentContainer != null) {
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
		final ${curr.name} item = (${curr.name}) l.getItemAtPosition(position);
		if (this.dualMode) {
			this.listFragment.getListView().setItemChecked(position, true);
			this.loadDetailFragment(item, false);
			
		} else {
			final Intent intent = new Intent(this, ${curr.name}ShowActivity.class);
			intent.putExtra("${curr.name}", (Parcelable) item);
			this.startActivity(intent);
		}
	}

	private void loadDetailFragment(${curr.name} item, boolean firstLoad) {
		this.getIntent().putExtra("${curr.name}", (Parcelable)item);
		
		Fragment frag = Fragment.instantiate(this,
				${curr.name}ShowFragment.class.getName(),
				null);
		
		FragmentTransaction trans = 
				this.getSupportFragmentManager().beginTransaction();
		if (firstLoad) {
			trans.add(R.id.fragment_show, frag);
		} else {
			trans.replace(R.id.fragment_show, frag);
		}
		trans.commitAllowingStateLoss();
	}

	@Override
	public void onListLoaded() {
		if (this.dualMode) {
			if (this.listFragment.getListAdapter().getCount() > 0) {
				this.listFragment.getListView().setItemChecked(0, true);
				${curr.name} item = (${curr.name}) 
						this.listFragment.getListAdapter().getItem(0);
				this.loadDetailFragment(item, true);
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
