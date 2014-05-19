<@header?interpret />
package ${project_namespace}.menu;


import android.content.Intent;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import ${project_namespace}.R;

import ${project_namespace}.menu.base.MenuWrapperBase;

/**
 * Crud Menu wrapper for delete and delete actions.
 */
public class CrudDeleteMenuWrapper implements MenuWrapperBase {
	/** Delete menu item. */
	private MenuItem deleteItem;
	/** Menu Visibility. */
	private boolean visible = true;
	
	@Override
	public void initializeMenu(Menu menu, FragmentActivity activity,
			Fragment fragment, android.content.Context ctx) {
		if ((fragment != null
				&& fragment instanceof CrudDeleteMenuInterface)
				|| activity instanceof CrudDeleteMenuInterface) {			
			this.deleteItem 	= menu.add(
					${project_name?cap_first}Menu.CRUDDELETE,
					1,
					Menu.NONE,
					R.string.menu_item_delete);

			this.deleteItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.deleteItem.setVisible(false);
		}
	}

	@Override
	public void updateMenu(Menu menu, FragmentActivity activity,
			Fragment fragment, android.content.Context ctx) {
		if ((fragment != null 
				&& fragment instanceof CrudDeleteMenuInterface)
				|| ctx instanceof CrudDeleteMenuInterface) {
			menu.setGroupVisible(
					${project_name?cap_first}Menu.CRUDDELETE, this.visible);
		}
	}

	@Override
	public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
		boolean result = false;
		if ((fragment != null 
				&& fragment instanceof CrudDeleteMenuInterface)
				|| ctx instanceof CrudDeleteMenuInterface) {
			if (item.equals(this.deleteItem)) {
				((CrudDeleteMenuInterface) fragment).onClickDelete();
				result = true;
			}
		}
		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent data, android.content.Context ctx, Fragment fragment) {
		// We don't need this.
	}

	@Override
	public void clear(Menu menu, FragmentActivity activity,
			Fragment fragment, android.content.Context ctx) {
		
		if (fragment != null 
				&& fragment instanceof CrudDeleteMenuInterface) {
			menu.removeGroup(${project_name?cap_first}Menu.CRUDDELETE);
		}
	}

	@Override
	public void hide(Menu menu, FragmentActivity activity, Fragment fragment,
			android.content.Context ctx) {
		this.visible = false;
	}

	@Override
	public void show(Menu menu, FragmentActivity activity, Fragment fragment,
			android.content.Context ctx) {
		this.visible = true;
	}

	/**
	 * Implement this interface in your activity or fragment
	 * to have delete and delete menu buttons.
	 */
	public interface CrudDeleteMenuInterface {
		/** On click delete. */
		void onClickDelete();
	}
}


