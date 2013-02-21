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

public class SQLiteAdapterGenerator extends BaseGenerator {	
	protected String localNameSpace;
	protected boolean isWritable = true;

	public SQLiteAdapterGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate Adapter...");
		
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (!cm.fields.isEmpty()) {
				this.localNameSpace = this.adapter.getNameSpace(cm, this.adapter.getData());
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
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
	}
	
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Adapter for " +  this.datamodel.get(TagConstant.CURRENT_ENTITY));
		
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
		ConsoleUtils.display(">>> Generate Criterias for " +  this.datamodel.get(TagConstant.CURRENT_ENTITY));
		try {
			this.makeSourceCriteria(
					"TemplateCriterias.java", 
					"%sCriterias.java", false);
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. 
	 * 		For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceControler(final String template, final String filename, final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				String.format(filename, this.datamodel.get(TagConstant.CURRENT_ENTITY)));
		
		final String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + template;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. 
	 * 		For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceCriteria(final String template, final String filename, final boolean override) {
		final String fullFilePath = String.format("%s%s/%s/%s",
				this.adapter.getSourcePath(),
				this.appMetas.projectNameSpace,
				this.adapter.getCriterias(),
				String.format(filename, this.datamodel.get(TagConstant.CURRENT_ENTITY)));
		
		final String fullTemplatePath = this.adapter.getTemplateSourceCriteriasPath().substring(1) + template;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
