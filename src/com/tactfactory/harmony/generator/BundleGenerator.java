/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import java.util.HashMap;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.HarmonyContext;
import com.tactfactory.harmony.platform.IAdapter;

/**
 * Generator for bundles.
 */
public class BundleGenerator extends BaseGenerator<IAdapter> {

	/**
	 * Constructor.
	 * @param adapt Adapter
	 * @throws Exception Exception
	 */
	public BundleGenerator(final IAdapter adapt) {
		super(adapt);
	}

	/**
	 * Generate the empty bundle basic files.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	public final void generateBundleFiles(
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		this.generateDataModel(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateBuildFile(bundleOwnerName, bundleName);
		
		this.generateGitIgnore(bundleOwnerName, bundleName);

		this.generateAnnotation(bundleOwnerName,
				bundleName,
				bundleNameSpace);
		
		this.generateParser(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateCommand(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateGenerator(bundleOwnerName,
				bundleName,
				bundleNameSpace);

		this.generateMeta(bundleOwnerName,
				bundleName,
				bundleNameSpace);
		
		this.generateAdapters(bundleOwnerName,
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
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final HashMap<String, Object> datamodel = new HashMap<String, Object>();
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
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final String tplPath = 
				this.getAdapter().getAnnotationBundleTemplatePath()
				+ "/TemplateAnnotation.java";
		final String genPath = this.getAdapter().getAnnotationBundlePath(
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
	 * Generate the build.gradle.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 */
	private void generateBuildFile(
			final String bundleOwnerName,
			final String bundleName) {

		final String tplPath = this.getAdapter().getBundleTemplatePath()
				+ "/build.gradle";
		final String genPath = this.getAdapter().getBundlePath(
							bundleOwnerName,
							bundleName)
						+ "/build.gradle";

		this.makeSource(tplPath, genPath, false);
	}
	
	/**
	 * Generate the .gitignore.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 */
	private void generateGitIgnore(
			final String bundleOwnerName,
			final String bundleName) {

		final String tplPath = this.getAdapter().getBundleTemplatePath()
				+ "/.gitignore";
		final String genPath = this.getAdapter().getBundlePath(
							bundleOwnerName,
							bundleName)
						+ "/.gitignore";

		this.makeSource(tplPath, genPath, false);
	}

	/**
	 * Generate bundle's parser.
	 * @param bundleOwnerName Owner name
	 * @param bundleName Bundle name
	 * @param bundleNameSpace Bundle namespace
	 */
	private void generateParser(
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final String tplPath = this.getAdapter().getParserBundleTemplatePath()
				+ "/TemplateParser.java";
		final String genPath = this.getAdapter().getParserBundlePath(
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
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final String tplPath = this.getAdapter().getCommandBundleTemplatePath()
				+ "/TemplateCommand.java";
		final String genPath = this.getAdapter().getCommandBundlePath(
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
	private void generateGenerator(
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final String tplPath = this.getAdapter().getGeneratorBundleTemplatePath()
				+ "/TemplateGenerator.java";
		final String genPath = this.getAdapter().getGeneratorBundlePath(
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
			final String bundleOwnerName,
			final String bundleName,
			final String bundleNameSpace) {

		final String tplPath = this.getAdapter().getMetaBundleTemplatePath()
				+ "/TemplateMetadata.java";
		final String genPath = this.getAdapter().getMetaBundlePath(
							bundleOwnerName,
							bundleNameSpace,
							bundleName)
						+ "/"
						+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
								bundleName)
						+ "Metadata.java";

		this.makeSource(tplPath, genPath, false);
	}
	
	/**
     * Generate Bundle adapters.
     * @param bundleOwnerName Owner name
     * @param bundleName Bundle name
     * @param bundleNameSpace Bundle namespace
     */
    private void generateAdapters(
            final String bundleOwnerName,
            final String bundleName,
            final String bundleNameSpace) {

        String tplAdapterPath = String.format("%s%s/%s",
                Harmony.getTemplatesPath(),
                this.getAdapter().getBundleTemplates(),
                "platform/");
        
        String genAdapterPath = String.format("%s%s/src/%s/%s",
                Harmony.getBundlePath(),
                bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
                bundleNameSpace.replaceAll("\\.", HarmonyContext.DELIMITER),
                "platform/");
        
        // Generate bundle adapter
        String tplPath = String.format("%s%s",
                tplAdapterPath,
                "TemplateAdapter.java");
        
        String genPath = String.format("%s%s%s",
                genAdapterPath,
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName),
                "Adapter.java");

        this.makeSource(tplPath, genPath, false);
        
        // Generate bundle android adapter
        tplPath = String.format("%sandroid/%s",
                tplAdapterPath,
                "TemplateAdapterAndroid.java");
        
        genPath = String.format("%sandroid/%s%s",
                genAdapterPath,
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName),
                "AdapterAndroid.java");

        this.makeSource(tplPath, genPath, false);
        
        // Generate bundle ios adapter
        tplPath = String.format("%sios/%s",
                tplAdapterPath,
                "TemplateAdapterIos.java");
        
        genPath = String.format("%sios/%s%s",
                genAdapterPath,
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName),
                "AdapterIos.java");

        this.makeSource(tplPath, genPath, false);
        
        // Generate bundle winphone adapter
        tplPath = String.format("%swinphone/%s",
                tplAdapterPath,
                "TemplateAdapterWinphone.java");
        
        genPath = String.format("%swinphone/%s%s",
                genAdapterPath,
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName),
                "AdapterWinphone.java");

        this.makeSource(tplPath, genPath, false);
    }
}
