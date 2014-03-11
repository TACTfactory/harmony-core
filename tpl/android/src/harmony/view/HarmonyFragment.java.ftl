<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * Harmony custom Fragment.
 * This fragment will help you use a lot of harmony's functionnality
 * (menu wrappers, etc.)
 */
public abstract class HarmonyFragment extends SherlockFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
											.clear(menu);
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
										  .updateMenu(menu, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;

		try {
			result = ${project_name?cap_first}Menu.getInstance(
				   this.getActivity(), this).dispatch(item, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
			.onActivityResult(requestCode, resultCode, data, this.getActivity(),
			this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
