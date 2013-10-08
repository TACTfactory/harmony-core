<@header?interpret />
package ${project_namespace}.menu;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * MenuWrapperBase.
 */
public abstract class MenuWrapperBase {
	/**
	 * Menu initialization.
	 * @param menu The menu object
	 */
	protected abstract void initializeMenu(Menu menu,
			Activity activity,
			Fragment fragment,
			Context ctx);

	/**
	 * Menu update.
	 * @param menu The menu object
	 * @param currentActivityHashCode The current activity hashcode.
	 * @param currentFragmentHashCode The current fragment hashcode.
	 * @param ctx The context
	 */
	protected abstract void updateMenu(Menu menu,
										Activity activity,
										Fragment fragment,
										Context ctx);

	/**
	 * Menu dispatch.
	 * Called when user clicked on menu.
	 * @param item The menu item clicked
	 * @param ctx The context
	 * @param fragment The fragment in which it has been clicked
	 * @return true if event has been treated
	 */
	protected abstract boolean dispatch(MenuItem item,
										 Context ctx,
									     Fragment fragment);

	/**
	 * Menu onActivityResult.
	 * Used when one of your menu call another
	 * activity with startActivityOnResult().
	 * @param requestCode the requestCode
	 * @param resultCode the resultCode
	 * @param data The intent
	 * @param ctx The context
	 * @param fragment The fragment
	 */
	protected abstract void onActivityResult(int requestCode,
										      int resultCode,
											  Intent data,
											  Context ctx,
											  Fragment fragment);


	/**
	 * Menu clear.
	 * @param menu The menu object
	 */
	protected abstract void clear(Menu menu,
			Activity activity,
			Fragment fragment,
			Context ctx);
}
