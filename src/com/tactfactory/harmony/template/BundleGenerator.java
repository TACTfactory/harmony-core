package com.tactfactory.harmony.template;

import java.util.HashMap;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Generator for bundles.
 */
public class BundleGenerator extends BaseGenerator {	
	
	/**
	 * Constructor.
	 * @param adapt Adapter
	 * @throws Exception Exception
	 */
	public BundleGenerator(BaseAdapter adapt) throws Exception {
		super(adapt);

		
	}
	
	/**
	 * Generate the empty bundle basic files.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	public void generateBundleFiles(
			String bundleOwnerName,
			String bundleName,
			String bundleNameSpace) {
		
		
		this.generateDataModel(bundleOwnerName,
				bundleName,
				bundleNameSpace);
		
		this.generateBuildXml(bundleOwnerName, bundleName);
		
		this.generateAnnotation(bundleOwnerName,
				bundleName,
				bundleNameSpace);
		this.generateParser(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateCommand(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateTemplate(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateMeta(bundleOwnerName,
				bundleName,
				bundleNameSpace);
	}
	
	/**
	 * Generate the datamodel associated with the bundle generation.
	 * (different from the mobile project generation)
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateDataModel(
			String bundleOwnerName,
			String bundleName,
			String bundleNameSpace) {
		
		HashMap<String, Object> datamodel = new HashMap<String, Object>();
		datamodel.put("bundle_namespace", bundleNameSpace);
		datamodel.put("bundle_name", bundleName);
		datamodel.put("bundle_owner", bundleOwnerName);
		
		this.setDatamodel(datamodel);
	}
	
	/**
	 * Generate the empty annotation.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateAnnotation(
			String bundleOwnerName,
			String bundleName, 
			String bundleNameSpace) {
		
		String tplPath = this.getAdapter().getAnnotationBundleTemplatePath() 
				+ "/TemplateAnnotation.java";
		String genPath = this.getAdapter().getAnnotationBundlePath(
							bundleOwnerName,
							bundleNameSpace, 
							bundleName) 
						+ "/" 
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
								bundleName)
						+ ".java";
		
		this.makeSource(tplPath, genPath, false);
	}
	
	/**
	 * Generate the build.xml.	 
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 */
	private void generateBuildXml(
			String bundleOwnerName,
			String bundleName) {
		
		String tplPath = this.getAdapter().getBundleTemplatePath() 
				+ "/build.xml";
		String genPath = this.getAdapter().getBundlePath(
							bundleOwnerName,
							bundleName) 
						+ "/build.xml";
		
		this.makeSource(tplPath, genPath, false);
	}
	
	/**
	 * Generate bundle's parser.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateParser(
			String bundleOwnerName,
			String bundleName, 
			String bundleNameSpace) {
		
		String tplPath = this.getAdapter().getParserBundleTemplatePath() 
				+ "/TemplateParser.java";
		String genPath = this.getAdapter().getParserBundlePath(
							bundleOwnerName,
							bundleNameSpace, 
							bundleName) 
						+ "/" 
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
								bundleName)
						+ "Parser.java";
		
		this.makeSource(tplPath, genPath, false);
	}
	
	/**
	 * Generate command file for empty bundle.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateCommand(
			String bundleOwnerName,
			String bundleName, 
			String bundleNameSpace) {
		
		String tplPath = this.getAdapter().getCommandBundleTemplatePath() 
				+ "/TemplateCommand.java";
		String genPath = this.getAdapter().getCommandBundlePath(
							bundleOwnerName,
							bundleNameSpace, 
							bundleName) 
						+ "/" 
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
								bundleName)
						+ "Command.java";
		
		this.makeSource(tplPath, genPath, false);
	}
	
	/** 
	 * Generate bundle's generator.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateTemplate(
			String bundleOwnerName,
			String bundleName, 
			String bundleNameSpace) {
		
		String tplPath = this.getAdapter().getTemplateBundleTemplatePath() 
				+ "/TemplateGenerator.java";
		String genPath = this.getAdapter().getTemplateBundlePath(
							bundleOwnerName,
							bundleNameSpace, 
							bundleName) 
						+ "/" 
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, 
								bundleName)
						+ "Generator.java";
		
		this.makeSource(tplPath, genPath, false);
	}
	
	/** 
	 * Generate Bundle metadata.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateMeta(
			String bundleOwnerName,
			String bundleName, 
			String bundleNameSpace) {
		
		String tplPath = this.getAdapter().getMetaBundleTemplatePath() 
				+ "/TemplateMetadata.java";
		String genPath = this.getAdapter().getMetaBundlePath(
							bundleOwnerName,
							bundleNameSpace, 
							bundleName) 
						+ "/" 
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, 
								bundleName)
						+ "Metadata.java";
		
		this.makeSource(tplPath, genPath, false);
	}
}
