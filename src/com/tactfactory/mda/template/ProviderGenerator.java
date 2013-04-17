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
import com.tactfactory.mda.utils.TactFileUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * The provider generator.
 *
 */
public class ProviderGenerator extends BaseGenerator {
	/** The local name space. */
	private String localNameSpace;
	/** The provider name. */
	private String nameProvider;
	
	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception 
	 */
	public ProviderGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.nameProvider = 
				CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, 
						this.getAppMetas().getName() + "Provider");
		this.localNameSpace = 
				this.getAppMetas().getProjectNameSpace().replace('/', '.') 
				+ "." 
				+ this.getAdapter().getProvider();
		

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));

		this.getDatamodel().put(
				TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
	}
	
	/**
	 * Generate the provider.
	 */
	public final void generateProvider() {
		try {
			this.makeSourceProvider("TemplateProvider.java",
					this.nameProvider + ".java");
			
			this.updateManifest();
			
			TranslationMetadata.addDefaultTranslation(
					"uri_not_supported", 
					"URI not supported", 
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					"app_provider_name", 
					"Provider of " + this.getAppMetas().getName(), 
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					"app_provider_description", 
					"Provider of "
						+ this.getAppMetas().getName() 
						+ " for acces to data", 
					Group.PROVIDER);
			
			new TranslationGenerator(this.getAdapter()).generateStringsXml();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** 
	 * Make Java Source Code.
	 * 
	 * @param template Template path file. 
	 * <br/>For list activity is "TemplateListActivity.java"
	 * @param filename The destination file name
	 */
	private void makeSourceProvider(final String template, 
			final String filename) {
		
		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace)
							.toLowerCase(),
						filename);
		
		final String fullTemplatePath = String.format("%s%s",
				this.getAdapter().getTemplateSourceProviderPath(),
				template);
		
		super.makeSource(fullTemplatePath, fullFilePath, false);
	}

	/**  
	 * Update Android Manifest. 
	 */
	private void updateManifest() {
		final String pathRelatif = String.format("%s.%s",
				this.localNameSpace, 
				this.nameProvider);

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			// Make engine
			final SAXBuilder builder = new SAXBuilder();		
			final File xmlFile 
				= TactFileUtils.makeFile(
						this.getAdapter().getManifestPathFile());
			
			// Load XML File
			final Document doc = builder.build(xmlFile);
			
			// Load Root element
			final Element rootNode = doc.getRootElement(); 			
			
			// Load Name space (required for manipulate attributes)
			final Namespace ns = rootNode.getNamespace("android");	

			// Find Application Node
			Element findProvider = null;
			
			// Find a element
			final Element applicationNode = rootNode.getChild("application"); 	
			if (applicationNode != null) {

				// Find Activity Node
				final List<Element> providers 
						= applicationNode.getChildren("provider");
				
				// Find many elements
				for (final Element provider : providers) {
					if (provider.hasAttributes() 
							&& provider.getAttributeValue("name", ns)
									.equals(pathRelatif)) {	
						// Load attribute value
						findProvider = provider;
						break;
					}
				}

				// If not found Node, create it
				if (findProvider == null) {
					// Create new element
					findProvider = new Element("provider");
					
					// Add Attributes to element
					findProvider.setAttribute("name", pathRelatif, ns);	

					applicationNode.addContent(findProvider);
				}

				// Set values
				findProvider.setAttribute("authorities", 	
						this.getAppMetas().getProjectNameSpace()
							.replace('/', '.'),
						ns);
				findProvider.setAttribute("label", 			
						"@string/app_provider_name",
						ns);
				findProvider.setAttribute("description", 	
						"@string/app_provider_description",
						ns);
				
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

			// Make beautiful file with indent !!!
			xmlOutput.setFormat(Format.getPrettyFormat());				
			xmlOutput.output(doc,
					new OutputStreamWriter(
							new FileOutputStream(xmlFile.getAbsoluteFile()),
									TactFileUtils.DEFAULT_ENCODING));
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
