package ${project_namespace}.harmony.view;

import ${project_namespace}.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

public abstract class HarmonyListActivity<T extends Parcelable> 
	extends HarmonyFragmentActivity
	implements HarmonyListFragment.OnClickCallback,
				HarmonyListFragment.OnLoadCallback {
	protected HarmonyListFragment<?> listFragment;
	protected Fragment detailFragment;
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		
		this.detailFragment = 
				this.getSupportFragmentManager().findFragmentById(
						R.id.fragment_show);
		this.listFragment = 
				(HarmonyListFragment<?>) this.getSupportFragmentManager()
						.findFragmentById(R.id.fragment_list);
		
		if (this.detailFragment != null) {
			this.dualMode = true;
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final Parcelable item = (Parcelable) l.getItemAtPosition(position);
		if (this.dualMode) {
			this.listFragment.getListView().setItemChecked(position, true);
			v.setActivated(true);
			this.loadDetailFragment(item);
			
		} else {
			final Intent intent = new Intent(this,
					this.getShowActivity());
			Bundle extras = new Bundle();
			extras.putParcelable(item.getClass().getSimpleName(), item);
			intent.putExtras(extras);
			this.startActivity(intent);
		}
	}
	
	@Override
	public void onListLoaded() {
		if (this.dualMode) {
			if (this.listFragment.getListAdapter().getCount() > 0) {
				this.listFragment.getListView().setItemChecked(0, true);
				Parcelable item = (Parcelable) 
						this.listFragment.getListAdapter().getItem(0);
				this.loadDetailFragment(item);
			}
		}
	}
	
	private void loadDetailFragment(Parcelable item) {
		this.getIntent().putExtra(item.getClass().getSimpleName(), item);
		
		Fragment frag = 
				Fragment.instantiate(this,
						this.detailFragment.getClass().getName(),
						null);
		
		FragmentTransaction trans = 
				this.getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.fragment_show, frag);
		trans.commitAllowingStateLoss();
	}
	
	public abstract Class<?> getShowActivity();
}

