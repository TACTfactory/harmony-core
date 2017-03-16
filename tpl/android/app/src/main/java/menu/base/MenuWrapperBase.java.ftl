<@header?interpret />
package ${project_namespace}.menu.base;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;


/**
 * MenuWrapperBase.
 * This interface is used to declare your menu groups easily.
 * Please see examples to understand how this works.
 */
public interface MenuWrapperBase {
    /**
     * Menu initialization.
     * @param menu The menu object
     * @param activity The current activity.
     * @param fragment The current fragment.
     * @param ctx The context
     */
    void initializeMenu(Menu menu,
            FragmentActivity activity,
            Fragment fragment,
            android.content.Context ctx);

    /**
     * Menu update.
     * @param menu The menu object
     * @param activity The current activity.
     * @param fragment The current fragment.
     * @param ctx The context
     */
    void updateMenu(Menu menu,
                                        FragmentActivity activity,
                                        Fragment fragment,
                                        android.content.Context ctx);

    /**
     * Menu dispatch.
     * Called when user clicked on menu.
     * @param item The menu item clicked
     * @param ctx The context
     * @param fragment The fragment in which it has been clicked
     * @return true if event has been treated
     */
    boolean dispatch(MenuItem item,
                                         android.content.Context ctx,
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
    void onActivityResult(int requestCode,
                                              int resultCode,
                                              Intent data,
                                              android.content.Context ctx,
                                              Fragment fragment);


    /**
     * Menu clear.
     * @param menu The menu object
     * @param activity The current activity.
     * @param fragment The current fragment.
     * @param ctx The context
     */
    void clear(Menu menu,
            FragmentActivity activity,
            Fragment fragment,
            android.content.Context ctx);


    /**
     * Menu hide.
     * @param menu The menu object
     * @param activity The current activity.
     * @param fragment The current fragment.
     * @param ctx The context
     */
    void hide(Menu menu,
            FragmentActivity activity,
            Fragment fragment,
            android.content.Context ctx);
    
    /**
     * Menu show.
     * @param menu The menu object
     * @param activity The current activity.
     * @param fragment The current fragment.
     * @param ctx The context
     */
    void show(Menu menu,
            FragmentActivity activity,
            Fragment fragment,
            android.content.Context ctx);
}
