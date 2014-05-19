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
 * Crud Menu wrapper for edit and delete actions.
 */
public class CrudEditMenuWrapper implements MenuWrapperBase {
    /** Edit menu item. */
    private MenuItem editItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null
                && fragment instanceof CrudEditMenuInterface)
                || activity instanceof CrudEditMenuInterface) {            
            this.editItem     = menu.add(
                    ${project_name?cap_first}Menu.CRUDEDIT,
                    1,
                    Menu.NONE,
                    R.string.menu_item_edit);

            this.editItem.setShowAsAction(
                    ActionMenuItem.SHOW_AS_ACTION_IF_ROOM
                    | ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
            this.editItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null 
                && fragment instanceof CrudEditMenuInterface)
                || ctx instanceof CrudEditMenuInterface) {
            menu.setGroupVisible(
                    ${project_name?cap_first}Menu.CRUDEDIT, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
        boolean result = false;
        if ((fragment != null 
                && fragment instanceof CrudEditMenuInterface)
                || ctx instanceof CrudEditMenuInterface) {
            if (item.equals(this.editItem)) {
                ((CrudEditMenuInterface) fragment).onClickEdit();
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
                && fragment instanceof CrudEditMenuInterface) {
            menu.removeGroup(${project_name?cap_first}Menu.CRUDEDIT);
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
     * to have edit and delete menu buttons.
     */
    public interface CrudEditMenuInterface {
        /** On click edit. */
        void onClickEdit();
    }
}


