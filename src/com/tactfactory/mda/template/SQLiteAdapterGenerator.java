/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * SQLite Adapters generator.
 */
public class SQLiteAdapterGenerator extends BaseGenerator {	
	/** The local name space. */
	private String localNameSpace;

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception 
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
		
		for (final ClassMetadata classMeta 
				: this.getAppMetas().getEntities().values()) {
			if (!classMeta.getFields().isEmpty()) {
				this.localNameSpace = this.getAdapter().getNameSpace(
						classMeta, this.getAdapter().getData());
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, classMeta.getName());
				this.generate();
			}
		}
		
		ConsoleUtils.display(">> Generate CriteriaBase...");
		this.makeSourceCriteria(
				"base/Criteria.java", 
				"base/Criteria.java", false);
		this.makeSourceCriteria(
				"base/CriteriasBase.java", 
				"base/CriteriasBase.java", true);
		this.makeSourceCriteria(
				"base/ICriteria.java", 
				"base/ICriteria.java", false);
		this.makeSourceCriteria(
				"base/value/CriteriaValue.java", 
				"base/value/CriteriaValue.java", false);
		this.makeSourceCriteria(
				"base/value/StringValue.java", 
				"base/value/StringValue.java", false);
		this.makeSourceCriteria(
				"base/value/ArrayValue.java", 
				"base/value/ArrayValue.java", false);
		this.makeSourceCriteria(
				"base/value/SelectValue.java", 
				"base/value/SelectValue.java", false);
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
		
		// Info
		ConsoleUtils.display(">>> Generate Criterias for "
				+ this.getDatamodel().get(TagConstant.CURRENT_ENTITY));
		try {
			this.makeSourceCriteria(
					"TemplateCriterias.java", 
					"%sCriterias.java", false);
			
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
