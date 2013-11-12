<@header?interpret />
package ${project_namespace}.menu;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * ${project_name?cap_first}Menu.
 */
public class ${project_name?cap_first}Menu
				extends ${project_name?cap_first}MenuBase {

	/** Singleton unique instance. */
	private static volatile ${project_name?cap_first}Menu singleton;

	/**
	 * Constructor.
	 * @param ctx The Context
	 * @throws Exception If something bad happened
	 */
	public ${project_name?cap_first}Menu(final Context ctx) throws Exception {
		super(ctx);
	}

	/**
	 * Constructor.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @throws Exception If something bad happened
	 */
	public ${project_name?cap_first}Menu(final Context ctx,
						final Fragment fragment) throws Exception {
		super(ctx, fragment);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @return ${project_name?cap_first}Menu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized ${project_name?cap_first}Menu getInstance(
						final Context ctx) throws Exception {
		return getInstance(ctx, null);
	}

	/** Get unique instance.
	 * @param ctx The context
	 * @param fragment The parent fragment
	 * @return ${project_name?cap_first}Menu instance
	 * @throws Exception If something bad happened
	 */
	public static final synchronized ${project_name?cap_first}Menu getInstance(
			final Context ctx, final Fragment fragment) throws Exception {
		if (singleton == null) {
			singleton = new ${project_name?cap_first}Menu(ctx, fragment);
		}  else {
			singleton.ctx = ctx;
			singleton.fragment = fragment;
		}

		return singleton;
	}
}
