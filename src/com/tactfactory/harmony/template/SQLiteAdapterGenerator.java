/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * SQLite Adapters generator.
 */
public class SQLiteAdapterGenerator extends BaseGenerator {
	/** The local name space. */
	private String localNameSpace;

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public SQLiteAdapterGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate the adapters and the criterias.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Adapter...");

		for (final EntityMetadata classMeta
				: this.getAppMetas().getEntities().values()) {
			if (classMeta.hasFields()) {
				this.localNameSpace = 
						this.getAppMetas().getProjectNameSpace().replace(
								'/', '.')
						+ "."
						+ this.getAdapter().getData();
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, classMeta.getName());
				this.generate();
			}
		}

		ConsoleUtils.display(">> Generate CriteriaBase...");
		this.makeSourceCriteria(
				"package-info.java",
				"package-info.java", false);
		this.makeSourceCriteria(
				"base/package-info.java",
				"base/package-info.java", false);
		this.makeSourceCriteria(
				"base/value/package-info.java",
				"base/value/package-info.java", false);
		this.makeSourceCriteria(
				"base/CriteriaExpression.java",
				"base/CriteriaExpression.java", true);
		this.makeSourceCriteria(
				"base/Criterion.java",
				"base/Criterion.java", true);
		this.makeSourceCriteria(
				"base/ICriteria.java",
				"base/ICriteria.java", true);
		this.makeSourceCriteria(
				"base/value/CriteriaValue.java",
				"base/value/CriteriaValue.java", true);
		this.makeSourceCriteria(
				"base/value/StringValue.java",
				"base/value/StringValue.java", true);
		this.makeSourceCriteria(
				"base/value/ArrayValue.java",
				"base/value/ArrayValue.java", true);
		this.makeSourceCriteria(
				"base/value/SelectValue.java",
				"base/value/SelectValue.java", true);
		this.makeSourceCriteria(
				"base/value/MethodValue.java",
				"base/value/MethodValue.java", true);
		this.makeSourceCriteria(
				"base/value/DateTimeValue.java",
				"base/value/DateTimeValue.java", true);
		
	}

	/**
	 * Generate the current entity's adapters and criteria.
	 */
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Adapter for "
				+ this.getDatamodel().get(TagConstant.CURRENT_ENTITY));

		try {
			this.makeSourceControler(
					"base/TemplateSQLiteAdapterBase.java",
					"base/%sSQLiteAdapterBase.java", true);

			this.makeSourceControler(
					"TemplateSQLiteAdapter.java",
					"%sSQLiteAdapter.java", false);

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Make Java Source Code.
	 *
	 * @param template Template path file.
	 * 		For list activity is "TemplateListActivity.java"
	 * @param filename The destination file
	 * @param override True if must overwrite file.
	 */
	private void makeSourceControler(final String template,
			final String filename,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
				this.getAdapter().getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				String.format(filename,
						this.getDatamodel().get(TagConstant.CURRENT_ENTITY)));

		final String fullTemplatePath =
				this.getAdapter().getTemplateSourceProviderPath()
				+ template;

		super.makeSource(fullTemplatePath, fullFilePath, override);
	}

	/**
	 * Make Java Source Code.
	 *
	 * @param template Template path file.
	 * 		For list activity is "TemplateListActivity.java"
	 * @param filename Destination file name
	 * @param override True if must overwrite file.
	 */
	private void makeSourceCriteria(final String template,
			final String filename,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s/%s",
				this.getAdapter().getSourcePath(),
				this.getAppMetas().getProjectNameSpace(),
				this.getAdapter().getCriterias(),
				String.format(filename,
						this.getDatamodel().get(TagConstant.CURRENT_ENTITY)));

		final String fullTemplatePath =
				this.getAdapter().getTemplateSourceCriteriasPath()
				+ template;

		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
