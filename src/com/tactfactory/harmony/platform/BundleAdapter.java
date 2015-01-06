/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform;

import java.io.File;
import java.util.List;

import com.tactfactory.harmony.HarmonyContext;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

/** Bundle Adapter of project structure. */
public class BundleAdapter extends BaseAdapter {
    
	/**	Bundle template files path. */
	private String bundleTemplates = "bundle";

	// Bundles
	/**	Bundle template files path. */
	private String annotationsBundleTemplates = "annotation";
	/**	Bundle template files path. */
	private String generatorBundleTemplates = "generator";
	/**	Bundle template files path. */
	private String parserBundleTemplates = "parser";
	/**	Bundle template files path. */
	private String metaBundleTemplates = "meta";
	/**	Bundle template files path. */
	private String commandBundleTemplates = "command";

    public BundleAdapter(final BundleAdapter adapter) {
        this();
        adapter.cloneTo(this);
    }
    
    public BundleAdapter() { }

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getAnnotationBundleTemplatePath() {
		return String.format("%s%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getAnnotationsBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getAnnotationBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", HarmonyContext.DELIMITER),
				this.getAnnotationsBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getCommandBundleTemplatePath() {
		return String.format("%s%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getCommandBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getCommandBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", HarmonyContext.DELIMITER),
				this.getCommandBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getMetaBundleTemplatePath() {
		return String.format("%s%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getMetaBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getMetaBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", HarmonyContext.DELIMITER),
				this.getMetaBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getGeneratorBundleTemplatePath() {
		return String.format("%s%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getGeneratorBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getGeneratorBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", HarmonyContext.DELIMITER),
				this.getGeneratorBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getParserBundleTemplatePath() {
		return String.format("%s%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getParserBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getParserBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", HarmonyContext.DELIMITER),
				this.getParserBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getBundleTemplatePath() {
		return String.format("%s%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates());
	}

	/**
	 * @return the bundleTemplates
	 */
	public final String getBundleTemplates() {
		return bundleTemplates;
	}

	/**
	 * @param bundleTemplates the bundleTemplates to set
	 */
	public final void setBundleTemplates(
			final String bundleTemplates) {
		this.bundleTemplates = bundleTemplates;
	}

	/**
	 * @return the annotationsBundleTemplates
	 */
	public final String getAnnotationsBundleTemplates() {
		return annotationsBundleTemplates;
	}

	/**
	 * @param annotationsBundleTemplates the annotationsBundleTemplates to set
	 */
	public final void setAnnotationsBundleTemplates(
			final String annotationsBundleTemplates) {
		this.annotationsBundleTemplates = annotationsBundleTemplates;
	}

	/**
	 * @return the generatorBundleTemplates
	 */
	public final String getGeneratorBundleTemplates() {
		return generatorBundleTemplates;
	}

	/**
	 * @param generatorBundleTemplates the generatorBundleTemplates to set
	 */
	public final void setGeneratorBundleTemplates(
			final String generatorBundleTemplates) {
		this.generatorBundleTemplates = generatorBundleTemplates;
	}

	/**
	 * @return the parserBundleTemplates
	 */
	public final String getParserBundleTemplates() {
		return parserBundleTemplates;
	}

	/**
	 * @param parserBundleTemplates the parserBundleTemplates to set
	 */
	public final void setParserBundleTemplates(
			final String parserBundleTemplates) {
		this.parserBundleTemplates = parserBundleTemplates;
	}

	/**
	 * @return the metaBundleTemplates
	 */
	public final String getMetaBundleTemplates() {
		return metaBundleTemplates;
	}

	/**
	 * @param metaBundleTemplates the metaBundleTemplates to set
	 */
	public final void setMetaBundleTemplates(
			final String metaBundleTemplates) {
		this.metaBundleTemplates = metaBundleTemplates;
	}

	/**
	 * @return the commandBundleTemplates
	 */
	public final String getCommandBundleTemplates() {
		return commandBundleTemplates;
	}

	/**
	 * @param commandBundleTemplates the commandBundleTemplates to set
	 */
	public final void setCommandBundleTemplates(
			final String commandBundleTemplates) {
		this.commandBundleTemplates = commandBundleTemplates;
	}
	
	public void cloneTo(BundleAdapter adapter) {
        //TODO use reflection for that !
	    super.cloneTo(adapter);
	    
	    adapter.setAnnotationsBundleTemplates(this.getAnnotationsBundleTemplates());
	    adapter.setBundleTemplates(this.getBundleTemplates());
	    adapter.setCommandBundleTemplates(this.getCommandBundleTemplates());
	    adapter.setMetaBundleTemplates(this.getMetaBundleTemplates());
	    adapter.setParserBundleTemplates(this.getParserBundleTemplates());
	    adapter.setGeneratorBundleTemplates(this.getGeneratorBundleTemplates());
	}

    @Override
    public String getNameSpace(ClassMetadata cm, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNameSpaceEntity(ClassMetadata cm, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNativeType(FieldMetadata field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void resizeImage() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean filesEqual(String oldContent, String newContent,
            String fileName, boolean ignoreHeader) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getSourceEntityPath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SourceFileManipulator getFileManipulator(File file,
            Configuration config) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getDirectoryForResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IAdapterProject getAdapterProject() {
        // TODO Auto-generated method stub
        return null;
    }
}
