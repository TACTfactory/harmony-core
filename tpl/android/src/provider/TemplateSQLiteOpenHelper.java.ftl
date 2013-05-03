package ${data_namespace};

import ${data_namespace}.base.${project_name?cap_first}SQLiteOpenHelperBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer opening and upgrading the database until first use, to avoid blocking application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class ${project_name?cap_first}SQLiteOpenHelper extends ${project_name?cap_first}SQLiteOpenHelperBase {

	/**
	 * Constructor.
	 */
	public ${project_name?cap_first}SQLiteOpenHelper(final Context context, final String name,
			final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

}
