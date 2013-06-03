package ${project_namespace}.menu;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * MenuWrapperBase.
 */
public abstract class MenuWrapperBase {
	protected abstract void initializeMenu(Menu menu);

	protected abstract void updateMenu(Menu menu, 
										int currentActivityHashCode, 
										int currentFragmentHashCode, 
										Context ctx);

	protected abstract boolean dispatch(MenuItem item, 
										 Context ctx, 
									     Fragment fragment);

	protected abstract void onActivityResult(int requestCode, 
										      int resultCode, 
											  Intent data, 
											  Context ctx, 
											  Fragment fragment);


	protected abstract void clear(Menu menu);
}
