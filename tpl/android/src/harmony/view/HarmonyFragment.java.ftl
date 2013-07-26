package ${project_namespace}.harmony.view;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * @author yo
 *
 */
public abstract class HarmonyFragment extends SherlockFragment {
	
	/**
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	/**
	 * @see com.actionbarsherlock.app.SherlockFragment#onPrepareOptionsMenu
	 * (com.actionbarsherlock.view.Menu)
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		
		try {
			${project_name?cap_first}Menu.getInstance(this.getActivity(), this)
										  .updateMenu(menu, this.getActivity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.actionbarsherlock.app.SherlockFragment#onOptionsItemSelected
	 * (com.actionbarsherlock.view.MenuItem)
	 */
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

	/**
	 * @see android.support.v4.app.Fragment#onActivityResult
	 * (int, int, android.content.Intent)
	 */
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
