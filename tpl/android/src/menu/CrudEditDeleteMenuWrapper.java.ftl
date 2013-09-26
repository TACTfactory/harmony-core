package ${project_namespace}.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class CrudEditDeleteMenuWrapper extends MenuWrapperBase {
	
	private MenuItem deleteItem;
	private MenuItem editItem;
	
	@Override
	protected void initializeMenu(Menu menu, Activity activity,
			Fragment fragment, Context ctx) {
		if (fragment != null && fragment instanceof CrudEditDeleteMenuInterface) {
			this.deleteItem 	= menu.add(${project_name?cap_first}Menu.CRUDEDITDELETE, 0, Menu.NONE, "Delete");
			this.deleteItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.deleteItem.setVisible(false);
			
			this.editItem 	= menu.add(${project_name?cap_first}Menu.CRUDEDITDELETE, 1, Menu.NONE, "Edit");
			this.editItem.setShowAsAction(
					ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
					| ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
			this.editItem.setVisible(false);
		}
	}

	@Override
	protected void updateMenu(Menu menu, Activity activity,
			Fragment fragment, Context ctx) {
		if (fragment != null && fragment instanceof CrudEditDeleteMenuInterface) {
			menu.setGroupVisible(${project_name?cap_first}Menu.CRUDEDITDELETE, true);
		}
	}

	@Override
	protected boolean dispatch(MenuItem item, Context ctx, Fragment fragment) {
		if (fragment != null && fragment instanceof CrudEditDeleteMenuInterface) {
			if (item.equals(this.deleteItem)) {
				((CrudEditDeleteMenuInterface) fragment).onClickDelete();
				return true;
			} else if (item.equals(this.editItem)) {
				((CrudEditDeleteMenuInterface) fragment).onClickEdit();
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data, Context ctx, Fragment fragment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clear(Menu menu, Activity activity,
			Fragment fragment, Context ctx) {
		
		if (fragment != null && fragment instanceof CrudEditDeleteMenuInterface) {
			menu.removeGroup(${project_name?cap_first}Menu.CRUDEDITDELETE);
		}
	}

	public interface CrudEditDeleteMenuInterface {
		public void onClickEdit();
		public void onClickDelete();
	}
}


