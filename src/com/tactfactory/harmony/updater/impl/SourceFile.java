/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.updater.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Command of generator for copy file.
 *
 * @author Erwan LeHuitouze <erwan.lehuitouze@tactfactory.com>
 * @author Mickael Gaillard <mickael.gaillard@tactfactory.com>
 */
public final class SourceFile implements IUpdater {
    private final String templateSource;
    private final String fileDestination;
    private final boolean overwrite;

    public String getTemplateSource() {
        return templateSource;
    }
    public String getFileDestination() {
        return fileDestination;
    }
    public boolean isOverwrite() {
        return overwrite;
    }

    public SourceFile(String source, String destination) {
        this(source, destination, false);
    }

    public SourceFile(String source, String destination, boolean overwrite) {
        this.templateSource = source;
        this.fileDestination = destination;
        this.overwrite = overwrite;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        makeSource(
                generator,
                this.getTemplateSource(),
                this.getFileDestination(),
                this.isOverwrite());
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
    private static void makeSource(final BaseGenerator<? extends IAdapter> generator,
            final String templatePath,
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
                        generator.getCfg().getTemplate(templatePath + ".ftl");

                // Write and close
                final OutputStreamWriter output =
                        new OutputStreamWriter(
                                new FileOutputStream(generateFile),
                                TactFileUtils.DEFAULT_ENCODING);

                final String fileName = generatePath.split("/")
                        [generatePath.split("/").length - 1];

                generator.getDatamodel().put("fileName", fileName);
                tpl.process(generator.getDatamodel(), output);
                output.flush();
                output.close();

                if (oldFile != null && !oldFile.isEmpty()) {
                    backupOrRollbackIfNeeded(generator, generateFile, oldFile);
                }
            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
            } catch (final TemplateException e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    /**
     * Backup the given file if its old content is not the same.
     * @param file The file to backup
     * @param oldContent Its old content
     */
    private static void backupOrRollbackIfNeeded(
            final BaseGenerator<? extends IAdapter> generator,
            final File file, final String oldContent) {
        String newContent = TactFileUtils.fileToString(file);

        if (!generator.getAdapter().filesEqual(
                oldContent, newContent, file.getName(), true)) {
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
