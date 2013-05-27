package com.tactfactory.mda.template;

import java.io.File;

import com.tactfactory.mda.meta.EntityMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Generator for provider tests.
 */
public class TestProviderGenerator extends BaseGenerator {
	/** Local name space. */
	private String localNameSpace;
	
	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception 
	 */
	public TestProviderGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	/**
	 * Generate all tests.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Provider test...");
	
		this.getDatamodel().put("dataLoader", 
				this.isDataLoaderAlreadyGenerated());
		
		for (final EntityMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (!cm.isInternal() && !cm.getFields().isEmpty()) {
				this.localNameSpace =
						this.getAdapter().getNameSpace(
								cm, this.getAdapter().getTest());
				this.getDatamodel().put(TagConstant.CURRENT_ENTITY,
						cm.getName());
				this.generate();
			}
		}
	}
	
	/**
	 * Check if the fixture dataloader class has already been generated.
	 * @return True if it already exists.
	 */
	private boolean isDataLoaderAlreadyGenerated() {
		String dataLoaderPath = this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace()
				+ "/" + this.getAdapter().getFixture() + "/" 
				+ "DataLoader.java";
		
		
		return new File(dataLoaderPath).exists();
	}
	
	/**  
	 * Generate DataBase Test.
	 */ 
	private void generate() {
		// Info
				ConsoleUtils.display(">>> Generate Providers test for " 
							+ this.getDatamodel().get(
									TagConstant.CURRENT_ENTITY));
		
		try {			
			this.makeSourceTest(
					"base/TemplateTestProviderBase.java", 
					"base/%sTestProviderBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestProvider.java", 
					"%sTestProvider.java",
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
	 * @param filename Destination file name
	 * @param override True if must overwrite file.
	 */
	private void makeSourceTest(final String template, 
			final String filename,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getTestPath(),
						PackageUtils.extractPath(
								String.format("%s/%s",
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
