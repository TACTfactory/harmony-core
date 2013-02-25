/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.template;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * WebService tests generator.
 *
 */
public class TestWSGenerator extends BaseGenerator {
	/** Local name space. */
	private String localNameSpace;
	
	/**
	 * Constructor. 
	 * @param adapter The adapter to use.
	 * @throws Exception 
	 */
	public TestWSGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	/**
	 * Generate all tests.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Rest test...");
		
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getOptions().containsKey("rest") 
					&& !cm.isInternal() 
					&& !cm.getFields().isEmpty()) {
				this.localNameSpace = 
						this.getAdapter().getNameSpace(cm,
								this.getAdapter().getTest());
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.generate();
			}
		}
	}
	
	/**  
	 * Generate Rest Test. 
	 */ 
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Rest test for " 
			+  this.getDatamodel().get(TagConstant.CURRENT_ENTITY));
		
		try {			
			this.makeSourceTest(
					"base/TemplateTestWSBase.java", 
					"base/%sTestWSBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestWS.java", 
					"%sTestWS.java",
					false);

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** 
	 * Make Java Source Code.
	 * 
	 * @param template Template path file. 
	 * <br/>For list activity is "TemplateListActivity.java"
	 * @param filename Destination file name.
	 * @param override True if must overwrite file.
	 */
	private void makeSourceTest(final String template, 
			final String filename, 
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getTestPath(),
						PackageUtils.extractPath(String.format("%s/%s",
								this.getAdapter().getSource(), 
								this.localNameSpace)).toLowerCase(),
						String.format(filename,
								this.getDatamodel().get(
										TagConstant.CURRENT_ENTITY)));
		
		final String fullTemplatePath = String.format("%s%s",
					this.getAdapter().getTemplateTestsPath(),
					template);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}

}
