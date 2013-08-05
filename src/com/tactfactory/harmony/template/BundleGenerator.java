package com.tactfactory.harmony.template;

import java.util.HashMap;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.plateforme.BaseAdapter;

public class BundleGenerator extends BaseGenerator {	
	
	public BundleGenerator(BaseAdapter adapt) throws Exception {
		super(adapt);

		
	}
	
	public void generateBundleFiles(
			String bundleOwnerName,
			String bundleName,
			String bundleNameSpace) {
		
		this.generateDataModel(bundleOwnerName,
				bundleName,
				bundleNameSpace);
		
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
		
		this.makeSource(tplPath, genPath, true);
	}
	
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
		
		this.makeSource(tplPath, genPath, true);
	}
	
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
		
		this.makeSource(tplPath, genPath, true);
	}
	
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
		
		this.makeSource(tplPath, genPath, true);
	}
	
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
		
		this.makeSource(tplPath, genPath, true);
	}
}
