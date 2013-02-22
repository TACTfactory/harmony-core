/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

public class SQLiteGenerator extends BaseGenerator {
	private String localNameSpace;

	public SQLiteGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.localNameSpace = 
				this.appMetas.projectNameSpace + "/" + this.adapter.getData();
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
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** Make Java Source Code.
	 * @param template Template path file.
	 * @param filename
	 */
	private void makeSourceData(final String template, 
			final String filename, 
			final boolean override) {
		
		final String fullFilePath = String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace)
							.toLowerCase(),
						String.format(filename,
								CaseFormat.LOWER_CAMEL.to(
										CaseFormat.UPPER_CAMEL,
										this.appMetas.name)));
		
		final String fullTemplatePath =
				this.adapter.getTemplateSourceProviderPath().substring(1) 
				+ template;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
