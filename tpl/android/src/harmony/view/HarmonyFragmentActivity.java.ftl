package ${project_namespace}.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ${project_namespace}.menu.${project_name?cap_first}Menu;

/**
 * Custom FragmentActivity for harmony projects.
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {
	/** Hack number for support v4 onActivityResult. */
	protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean result = true;
		
		try {
			${project_name?cap_first}Menu.getInstance(this).clear(menu);
			${project_name?cap_first}Menu.getInstance(this).updateMenu(menu, 
																		  this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		if (result) {
			result = super.onPrepareOptionsMenu(menu);
		}
		
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result;
		try {
			result = ${project_name?cap_first}Menu.getInstance(this).dispatch(
																	item, this);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, 
																  Intent data) {
		try {
			${project_name?cap_first}Menu.getInstance(this).onActivityResult(
										   requestCode, resultCode, data, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
