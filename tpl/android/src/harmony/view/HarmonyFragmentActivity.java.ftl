<@header?interpret />
package ${project_namespace}.harmony.view;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ${project_namespace}.menu.${project_name?cap_first}Menu;
import ${project_namespace}.${project_name?cap_first}Application;
import ${project_namespace}.${project_name?cap_first}ApplicationBase.DeviceType;

/**
 * Custom FragmentActivity for harmony projects.
 * This fragment activity helps you use the menu wrappers, detect alone if
 * you're in tablet/dual mode.
 */
public abstract class HarmonyFragmentActivity extends SherlockFragmentActivity {
    /** Hack number for support v4 onActivityResult. */
    protected static final int SUPPORT_V4_RESULT_HACK = 0xFFFF;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            result = super.onCreateOptionsMenu(menu);
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

    /**
     * Is this device in tablet mode ?
     *
     * @return true if tablet mode
     */
    public boolean isDualMode() {
        return ${project_name?cap_first}Application.getDeviceType(this).equals(DeviceType.TABLET);
    }
}
