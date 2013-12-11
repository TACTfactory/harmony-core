<@header?interpret />
package ${project_namespace}.harmony.util;

import java.io.File;

import android.content.Context;

import com.google.common.base.Strings;
import com.google.common.io.Files;

/**
 * Utility class for backup/restore procedures for the DB.
 */
public class DatabaseUtil {

	/** Private Constructor. */
	private DatabaseUtil() { }

	/**
	 * Import the Database.
	 *
	 * @param context The context.
	 * @param source The file containing the database.
	 * @param databaseName The name of the database.
	 * @param removeSource true if the source should be deleted after import.
	 * 			False otherwise.
     * @throws Exception if any parameter is null or empty.
	 */
	public static void importDB(Context context,
			File source,
			String databaseName,
			boolean removeSource)
						throws Exception {
		if (Strings.isNullOrEmpty(databaseName)
				|| source == null
				|| context ==  null) {
			throw new Exception("Invalid parameter");
		}

		if (source.exists()) {
			String path = context.getDatabasePath(databaseName).getPath();
			File destination = new File(path);

			Files.copy(source, destination);

			if (removeSource) {
				source.delete();
			}
		}
	}

	/**
	 * Export the Database.
	 *
	 * @param context The context.
	 * @param destination The destination file of the export.
	 * @param databaseName The name of the database.
     * @throws Exception if any parameter is null or empty.
	 */
	public static void exportDB(Context context,
			File destination,
			String databaseName)
						throws Exception {
		if (Strings.isNullOrEmpty(databaseName)
				|| destination == null
				|| context == null) {
			throw new Exception("Invalid parameter");
		}

		String path = context.getDatabasePath(databaseName).getPath();
		File source = new File(path);

		Files.copy(source, destination);

	}

	/**
	 * Extract the content values for this entity.
	 * (in case of joined inheritance)
	 *
	 * @param from The content values containing all the values 
	 *	(superclasses + children)
	 * @param columnsToExtract The columns to extract from the values
	 * @return the content values of this entity
	 */
	public static ContentValues extractContentValues(
			ContentValues from, String[] columnsToExtract) {
		ContentValues to = new ContentValues();
		for (String colName : columnsToExtract) {
			if (from.containsKey(colName)) {
				DatabaseUtil.transfer(from, to, colName, false);
			}
		}
		return to;
	}

	/**
	 * Transfer a column from a contentvalue to another one.
	 *
	 * @param from The source content value
	 * @param to The destination contentvalue
	 * @param colName The name of the column to transfer
	 * @param keep if false, delete it from the old contentvalue
	 */
	protected static void transfer(ContentValues from,
			ContentValues to,
			String colName,
			boolean keep) {
		Object fromObject = from.get(colName);
		if (fromObject instanceof Boolean) {
			to.put(colName, from.getAsBoolean(colName));
		} else {
			to.put(colName, from.getAsString(colName));
		}
		if (!keep) {
			from.remove(colName);
		}
	}
}
