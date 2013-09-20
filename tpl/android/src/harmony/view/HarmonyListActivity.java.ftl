package ${project_namespace}.harmony.view;

import ${project_namespace}.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

public abstract class HarmonyListActivity<T extends Parcelable> extends HarmonyFragmentActivity
	implements HarmonyListFragment.OnClickCallback {
	protected HarmonyListFragment<T> listFragment;
	protected Fragment detailFragment;
	protected boolean dualMode;
	
	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);

		
		this.detailFragment = this.getSupportFragmentManager().findFragmentById(R.id.fragment_show);
		this.listFragment = 
				(HarmonyListFragment<T>) this.getSupportFragmentManager()
						.findFragmentById(R.id.fragment_list);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final T item = (T) l.getItemAtPosition(position);
		if (this.detailFragment == null) {
	
			final Intent intent = new Intent(this,
								this.getShowActivity());
			intent.putExtra(item.getClass().getSimpleName(), (Parcelable) item);
	
			this.startActivity(intent);
		} else {
			this.listFragment.getListView().setItemChecked(position, true);
			v.setActivated(true);
			this.getIntent().putExtra(item.getClass().getSimpleName(), item);
			
			Fragment frag = Fragment.instantiate(this, this.detailFragment.getClass().getName(), null);
			FragmentTransaction trans = this.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.fragment_details, frag);
			trans.commit();
		}
	}
	
	public abstract Class<?> getShowActivity();
}

