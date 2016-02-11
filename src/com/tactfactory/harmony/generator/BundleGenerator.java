/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.HarmonyContext;
import com.tactfactory.harmony.platform.BundleAdapter;
import com.tactfactory.harmony.updater.impl.SourceFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Generator for bundles.
 */
public class BundleGenerator extends BaseGenerator<BundleAdapter> {

    /**
     * Constructor.
     * @param adapter Adapter
     * @throws Exception Exception
     */
    public BundleGenerator(final BundleAdapter adapter) {
        super(adapter);
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
        final String genPath =
                this.getAdapter().getAnnotationBundlePath(bundleOwnerName, bundleNameSpace, bundleName)
                        + "/"
                        + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName)
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
        final String genPath = this.getAdapter().getBundlePath(bundleOwnerName, bundleName)
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
        final String genPath = this.getAdapter().getBundlePath(bundleOwnerName, bundleName)
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
        final String genPath = this.getAdapter().getParserBundlePath(bundleOwnerName, bundleNameSpace, bundleName)
                + "/"
                + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName)
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
        final String genPath = this.getAdapter().getCommandBundlePath(bundleOwnerName, bundleNameSpace, bundleName)
                + "/"
                + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName)
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
        final String genPath = this.getAdapter().getGeneratorBundlePath(bundleOwnerName, bundleNameSpace, bundleName)
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
        final String genPath = this.getAdapter().getMetaBundlePath(bundleOwnerName, bundleNameSpace, bundleName)
                + "/"
                + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName)
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

        // Generate bundle windows adapter
        tplPath = String.format("%swindows/%s",
                tplAdapterPath,
                "TemplateAdapterWindows.java");

        genPath = String.format("%swindows/%s%s",
                genAdapterPath,
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, bundleName),
                "AdapterWindows.java");

        this.makeSource(tplPath, genPath, false);
    }


    /**
     * Make Java Source Code.
     *
     * @param templatePath Template path file.
     *      For list activity is "TemplateListActivity.java"
     * @param generatePath The destination file path
     * @param override True for recreating the file.
     *          False for not writing anything if the file already exists.
     */
    protected void makeSource(final String templatePath,
            final String generatePath,
            final boolean override) {

        if (!TactFileUtils.exists(generatePath) || override) {
            final File generateFile = TactFileUtils.makeFile(generatePath);

            try {
                String oldFile = TactFileUtils.fileToString(generateFile);
                // Debug Log
                ConsoleUtils.displayDebug("Generate Source : " +
                        generateFile.getCanonicalPath());

                // Create
                final Template tpl =
                        this.getCfg().getTemplate(templatePath + ".ftl");

                // Write and close
                final OutputStreamWriter output =
                        new OutputStreamWriter(
                                new FileOutputStream(generateFile),
                                TactFileUtils.DEFAULT_ENCODING);

                final String fileName = generatePath.split("/")
                        [generatePath.split("/").length - 1];

                this.getDatamodel().put("fileName", fileName);
                tpl.process(this.getDatamodel(), output);
                output.flush();
                output.close();

                if (oldFile != null && !oldFile.isEmpty()) {
                    this.backupOrRollbackIfNeeded(generateFile, oldFile);
                }
            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
            } catch (final TemplateException e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    /**
     * Make source file.
     * @param file
     */
    protected final void makeSource(SourceFile file) {
        this.makeSource(
                file.getTemplateSource(),
                file.getFileDestination(),
                file.isOverwrite());
    }

    /**
     * Make sources files
     * @param files
     */
    protected final void makeSource(List<SourceFile> files) {
        for (SourceFile file : files) {
            this.makeSource(file);
        }
    }

    /**
     * Backup the given file if its old content is not the same.
     * @param file The file to backup
     * @param oldContent Its old content
     */
    private void backupOrRollbackIfNeeded(
            final File file, final String oldContent) {
        String newContent = TactFileUtils.fileToString(file);

        if (!this.getAdapter().filesEqual(oldContent, newContent, file.getName(), true)) {
            String backupFileName = "." + file.getName() + ".back";
            TactFileUtils.stringBufferToFile(
                    new StringBuffer(oldContent),
                    new File(file.getParent() + "/" + backupFileName));
        } else {
            TactFileUtils.stringBufferToFile(
                    new StringBuffer(oldContent),
                    file);
        }
    }
}
