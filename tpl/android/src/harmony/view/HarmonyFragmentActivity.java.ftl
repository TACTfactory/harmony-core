package ${project_namespace}.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * @author yo
 *
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {

	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPrepareOptionsMenu(com.actionbarsherlock.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean result = true;
		
		menu.clear();
		
		try {
			${project_name?cap_first}Menu.getInstance(this).updateMenu(menu, this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		if(result) {
			result = super.onPrepareOptionsMenu(menu);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onOptionsItemSelected(com.actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = ${project_name?cap_first}Menu.getInstance(this).dispatch(item, this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			${project_name?cap_first}Menu.getInstance(this).onActivityResult(requestCode, resultCode, data, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
