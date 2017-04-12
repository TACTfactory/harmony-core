<@header?interpret />
package ${project_namespace}.menu;


import android.support.v4.app.Fragment;

import ${project_namespace}.menu.base.${project_name?cap_first}MenuBase;

/**
 * ${project_name?cap_first}Menu.
 * 
 * This class is an engine used to manage the different menus of your application.
 * Its use is quite simple :
 * Create a class called [YourMenuName]MenuWrapper in this package and
 * make it implement the interface MenuWrapperBase.
 * (For examples, please see CrudCreateMenuWrapper and CrudEditDeleteMenuWrapper in
 * this package.)
 * When this is done, just call this harmony command :
 * script/console.sh orm:menu:update.
 * This will auto-generate a group id for your menu.
 */
public class ${project_name?cap_first}Menu
                extends ${project_name?cap_first}MenuBase {

    /** Singleton unique instance. */
    private static volatile ${project_name?cap_first}Menu singleton;

    /**
     * Constructor.
     * @param ctx The android.content.Context
     * @throws Exception If something bad happened
     */
    public ${project_name?cap_first}Menu(final android.content.Context ctx) throws Exception {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx The context
     * @param fragment The parent fragment
     * @throws Exception If something bad happened
     */
    public ${project_name?cap_first}Menu(final android.content.Context ctx,
                        final Fragment fragment) throws Exception {
        super(ctx, fragment);
    }

    /** Get unique instance.
     * @param ctx The context
     * @return ${project_name?cap_first}Menu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized ${project_name?cap_first}Menu getInstance(
                        final android.content.Context ctx) throws Exception {
        return getInstance(ctx, null);
    }

    /** Get unique instance.
     * @param ctx The context
     * @param fragment The parent fragment
     * @return ${project_name?cap_first}Menu instance
     * @throws Exception If something bad happened
     */
    public static final synchronized ${project_name?cap_first}Menu getInstance(
            final android.content.Context ctx, final Fragment fragment) throws Exception {
        if (singleton == null) {
            singleton = new ${project_name?cap_first}Menu(ctx, fragment);
        }  else {
            singleton.ctx = ctx;
            singleton.fragment = fragment;
        }

        return singleton;
    }
}
