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
import com.tactfactory.mda.utils.PackageUtils;

public class ApplicationGenerator extends BaseGenerator {
	private String localNameSpace;
	private String applicationName;

	public ApplicationGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.localNameSpace = this.appMetas.projectNameSpace.replace('/', '.');
		this.applicationName = 
				CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
						this.appMetas.name);
		
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
		
	}
	
	public final void generateApplication() {
		this.makeSource(
				"TemplateApplication.java", 
				this.applicationName + "Application.java",
				false);
		
		this.makeSource(
				"TemplateApplicationBase.java", 
				this.applicationName + "ApplicationBase.java",
				true);
	}
	
	@Override
	protected final void makeSource(final String templateName,
			final String fileName, 
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				fileName);

		final String fullTemplatePath = String.format("%s%s",
				this.adapter.getTemplateSourcePath() ,
				templateName);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
