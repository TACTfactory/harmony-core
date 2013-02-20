/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

public class ProviderGenerator extends BaseGenerator {
	protected String localNameSpace;
	protected String nameProvider;
	
	public ProviderGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.nameProvider = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name + "Provider");
		this.localNameSpace = this.appMetas.projectNameSpace.replace('/', '.') +"."+ this.adapter.getProvider();
		

		this.datamodel = this.appMetas.toMap(this.adapter);

		this.datamodel.put(TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
	}
	
	public void generateProvider() {
		try {
			this.makeSourceProvider("TemplateProvider.java", this.nameProvider + ".java");
			
			this.updateManifest(this.nameProvider);
			
			TranslationMetadata.addDefaultTranslation(
					"uri_not_supported", 
					"URI not supported", 
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					"app_provider_name", 
					"Provider of " + this.appMetas.name, 
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					"app_provider_description", 
					"Provider of " + this.appMetas.name + " for acces to data", 
					Group.PROVIDER);
			
			new TranslationGenerator(this.adapter).generateStringsXml();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceProvider(final String template, final String filename) {
		
		final String fullFilePath = String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						filename);
		
		final String fullTemplatePath = String.format("%s%s",
				this.adapter.getTemplateSourceProviderPath(),
				template);
		
		super.makeSource(fullTemplatePath, fullFilePath, false);
	}

	/**  Update Android Manifest
	 * @param nameProvider 
	 * 
	 * @param classFile
	 */
	private void updateManifest(final String nameProvider) {
		final String pathRelatif = String.format("%s.%s",
				this.localNameSpace, 
				nameProvider );

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			final SAXBuilder builder = new SAXBuilder();		// Make engine
			final File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			final Document doc = builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			// Find Application Node
			Element findProvider = null;
			final Element applicationNode = rootNode.getChild("application"); 	// Find a element
			if (applicationNode != null) {

				// Find Activity Node
				final List<Element> providers = applicationNode.getChildren("provider"); 	// Find many elements
				for (final Element provider : providers) {
					if (provider.hasAttributes() && provider.getAttributeValue("name",ns).equals(pathRelatif) ) {	// Load attribute value
						findProvider = provider;
						break;
					}
				}

				// If not found Node, create it
				if (findProvider == null) {
					findProvider = new Element("provider");				// Create new element
					findProvider.setAttribute("name", pathRelatif, ns);	// Add Attributes to element

					applicationNode.addContent(findProvider);
				}

				// Set values
				findProvider.setAttribute("authorities", 	this.appMetas.projectNameSpace.replace('/', '.'), ns);
				findProvider.setAttribute("label", 			"@string/app_provider_name", ns);
				findProvider.setAttribute("description", 	"@string/app_provider_description", ns);
				
				// Clean code
				applicationNode.sortChildren(new Comparator<Element>() {

					@Override
					public int compare(final Element o1, final Element o2) {
						return o1.getName().compareToIgnoreCase(o2.getName());
					}
				});
			}

			// Write to File
			final XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());				// Make beautiful file with indent !!!
			xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(xmlFile.getAbsoluteFile()), "UTF-8"));
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
