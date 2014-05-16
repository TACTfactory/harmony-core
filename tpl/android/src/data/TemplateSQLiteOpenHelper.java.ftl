<@header?interpret />
package ${data_namespace};

import ${data_namespace}.base.${project_name?cap_first}SQLiteOpenHelperBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name?cap_first}SQLiteOpenHelper
					extends ${project_name?cap_first}SQLiteOpenHelperBase {

	/**
	 * Constructor.
	 * @param ctx context
	 * @param name name
	 * @param factory factory
	 * @param version version
	 */
	public ${project_name?cap_first}SQLiteOpenHelper(final Context ctx,
		   final String name, final CursorFactory factory, final int version) {
		super(ctx, name, factory, version);
	}

}
