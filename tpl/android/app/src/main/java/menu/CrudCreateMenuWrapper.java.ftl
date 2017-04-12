<@header?interpret />
package ${project_namespace}.menu;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import ${project_namespace}.R;
import ${project_namespace}.menu.base.MenuWrapperBase;

/**
 * Menu wrapper for CRUD Create action.
 */
public class CrudCreateMenuWrapper implements MenuWrapperBase {
    /** Menu item ADD. */
    private MenuItem addItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        
        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {    
            
            this.addItem     = menu.add(
                    ${project_name?cap_first}Menu.CRUDCREATE,
                    0,
                    Menu.NONE,
                    R.string.menu_item_create);
                    
            MenuItemCompat.setShowAsAction(this.addItem,
                    MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                    | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
                    
            this.addItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
            menu.setGroupVisible(
                    ${project_name?cap_first}Menu.CRUDCREATE, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
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
            Intent data, android.content.Context ctx, Fragment fragment) {
        // We don't need this.
    }

    @Override
    public void clear(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {

        if (fragment != null && fragment instanceof CrudCreateMenuInterface) {
            menu.removeGroup(${project_name?cap_first}Menu.CRUDCREATE);
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

