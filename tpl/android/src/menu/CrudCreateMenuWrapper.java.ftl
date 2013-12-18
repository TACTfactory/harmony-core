<@header?interpret />
package ${project_namespace}.menu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import ${project_namespace}.R;

/**
 * Menu wrapper for CRUD Create action.
 */
public class CrudCreateMenuWrapper implements MenuWrapperBase {
	/** Menu item ADD. */
	private MenuItem addItem;
	
	@Override
	public void initializeMenu(Menu menu, SherlockFragmentActivity activity,
			Fragment fragment, Context ctx) {
		
		if (fragment != null && fragment instanceof CrudCreateMenuInterface) {	
			
			this.addItem 	= menu.add(
					${project_name?cap_first}Menu.CRUDCREATE,
					0,
					Menu.NONE,
					R.string.menu_item_create);
			this.addItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.addItem.setVisible(false);
		}
	}

	@Override
	public void updateMenu(Menu menu, SherlockFragmentActivity activity,
			Fragment fragment, Context ctx) {
		if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
			menu.setGroupVisible(
					${project_name?cap_first}Menu.CRUDCREATE, true);
		}
	}

	@Override
	public boolean dispatch(MenuItem item, Context ctx, Fragment fragment) {
		boolean result;
		if (fragment instanceof CrudCreateMenuInterface) {
			switch (item.getItemId()) {
				case 0:
					((CrudCreateMenuInterface) fragment).onClickAdd();
					result = true;
					break;
				default:
					result = false;
					break;
			}
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent data, Context ctx, Fragment fragment) {
		// We don't need this.
	}

	@Override
	public void clear(Menu menu, SherlockFragmentActivity activity,
			Fragment fragment, Context ctx) {

		if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
			menu.removeGroup(${project_name?cap_first}Menu.CRUDCREATE);
		}
	}

	/**
	 * Implement this interface in your fragment or activity
	 * to activate this menu.
	 */
	public interface CrudCreateMenuInterface {
		/**
		 * Called when user clicks on Add menu button.
		 */
		void onClickAdd();
	}
}

