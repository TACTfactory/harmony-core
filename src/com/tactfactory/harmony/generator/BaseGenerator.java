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
import java.util.List;
import java.util.Map;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Base Generator.
 */
public abstract class BaseGenerator<T extends IAdapter> {

    // Meta-models
    /** The application metadata. */
    private ApplicationMetadata appMetas;

    // Platform adapter
    /** The used adapter. */
    private T adapter;
    /** The datamodel. */
    private Map<String, Object> datamodel;

    // Config
    /** The freemarker configuration. */
    private Configuration cfg = new Configuration();

    /**
     * @return the appMetas
     */
    public final ApplicationMetadata getAppMetas() {
        return appMetas;
    }

    /**
     * @param appMetas the appMetas to set
     */
    public final void setAppMetas(final ApplicationMetadata appMetas) {
        this.appMetas = appMetas;
    }

    /**
     * @return the adapter
     */
    public final T getAdapter() {
        return adapter;
    }

    /**
     * @return the datamodel
     */
    public final Map<String, Object> getDatamodel() {
        return datamodel;
    }

    /**
     * @param datamodel the datamodel to set
     */
    public final void setDatamodel(final Map<String, Object> datamodel) {
        this.datamodel = datamodel;
    }

    /**
     * @return the cfg
     */
    public final Configuration getCfg() {
        return cfg;
    }

    /**
     * @param cfg the cfg to set
     */
    public final void setCfg(final Configuration cfg) {
        this.cfg = cfg;
    }

    /**
     * Constructor.
     * @param adapter The adapter to use
     * @throws Exception if adapter is null
     */
    public BaseGenerator(final T adapter) {
        if (adapter == null) {
            throw new RuntimeException("No adapter define.");
        }

        try {
            // FIXME Clone object tree
            this.appMetas    = ApplicationMetadata.INSTANCE;
            this.adapter    = adapter;

            final  Object[] files =
                    Harmony.getTemplateFolders().values().toArray();
            final  TemplateLoader[] loaders =
                    new TemplateLoader[files.length + 1];

            for (int i = 0; i < files.length; i++) {
                final FileTemplateLoader ftl =
                        new FileTemplateLoader((File) files[i]);
                loaders[i] = ftl;
            }

            loaders[files.length] = new FileTemplateLoader(
                    new File(Harmony.getRootPath() + "/vendor/tact-core"));

            final MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);

            this.cfg.setTemplateLoader(mtl);

        } catch (IOException e) {
            throw new RuntimeException("Error with template loading : "
                        + e.getMessage());
        }
    }

    /**
     * Append Source Code to existing file.
     *
     * @param templatePath Template path file.
     *             For list activity is "TemplateListActivity.java"
     * @param generatePath Destination file.
     */
    protected final void appendSource(final String templatePath,
            final String generatePath) {
        if (TactFileUtils.exists(generatePath)) {
            final File generateFile = new File(generatePath);

            try {
                // Debug Log
                ConsoleUtils.displayDebug("Append Source : ",
                        generateFile.getPath());

                // Create
                final Template tpl =
                        this.cfg.getTemplate(templatePath + ".ftl");

                // Write and close
                final OutputStreamWriter output =
                        new OutputStreamWriter(
                                new FileOutputStream(generateFile, true),
                                TactFileUtils.DEFAULT_ENCODING);

                tpl.process(this.datamodel, output);
                output.flush();
                output.close();

            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
                ConsoleUtils.displayError(e);
            } catch (final TemplateException e) {
                ConsoleUtils.displayError(e);
                ConsoleUtils.displayError(e);
            }
        }
    }

    private void processExecutor(IUpdater updater) {
        updater.execute(this);
    }

    protected void processUpdater(List<IUpdater> updaters) {
        if (updaters != null) {
            for (IUpdater updater : updaters) {
                this.processExecutor(updater);
            }
        }
    }
}
