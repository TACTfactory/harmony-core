/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * SQLite Generator.
 *
 */
public class SQLiteGenerator extends BaseGenerator {
	/** Local name space. */
	private String localNameSpace;

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public SQLiteGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		this.localNameSpace =
				this.getAppMetas().getProjectNameSpace()
				+ "/" + this.getAdapter().getData();
	}

	/**
	 * Generate Database Interface Source Code.
	 */
	public final void generateDatabase() {
		// Info
		ConsoleUtils.display(">> Generate Database");

		try {
			this.makeSourceData(
					"TemplateSQLiteOpenHelper.java",
					"%sSQLiteOpenHelper.java",
					false);

			this.makeSourceData(
					"base/TemplateSQLiteOpenHelperBase.java",
					"base/%sSQLiteOpenHelperBase.java",
					true);

			this.makeSourceData(
					"base/ApplicationSQLiteAdapterBase.java",
					"base/SQLiteAdapterBase.java",
					true);
			
			this.makeSourceData(
					"data-package-info.java",
					"package-info.java",
					true);
			
			this.makeSourceData(
					"base/data-package-info.java",
					"base/package-info.java",
					true);

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/** Make Java Source Code.
	 * @param template Template path file.
	 * @param filename The destination file.
	 * @param override True if must overwrite file.
	 */
	private void makeSourceData(final String template,
			final String filename,
			final boolean override) {

		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace)
							.toLowerCase(),
						String.format(filename,
								CaseFormat.LOWER_CAMEL.to(
										CaseFormat.UPPER_CAMEL,
										this.getAppMetas().getName())));

		final String fullTemplatePath =
				this.getAdapter().getTemplateSourceProviderPath()
				+ template;

		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
