/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme.rim;

import java.io.File;
import java.util.List;

import com.tactfactory.harmony.exception.NotImplementedException;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.IAdapterProject;
import com.tactfactory.harmony.updater.impl.LibraryGit;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

/** RIM Blackberry Adapter of project structure. */
public final class RimAdapter extends BaseAdapter {
	/** Error message for not implemented feature. */
	private static final String NOT_IMPLEMENTED_MESSAGE =
			"Rim adapter has not been implemented yet.";

	/**
	 * Constructor.
	 */
	public RimAdapter() {
		super();
		this.setPlatform("rim");
		this.setResource("res");
		this.setSource("src");
	}

	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	@Override
	public String getNameSpaceEntity(final ClassMetadata cm,
			final String type) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	@Override
	public String getNativeType(final FieldMetadata field) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	@Override
	public void resizeImage() {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

	@Override
	public boolean filesEqual(String oldContent, String newContent,
			String fileName, boolean ignoreHeader) {
		return oldContent.equals(newContent);
	}

	@Override
	public SourceFileManipulator getFileManipulator(
			final File file,
			final Configuration config) {
		throw new NotImplementedException(NOT_IMPLEMENTED_MESSAGE);
	}

    @Override
    public void installGitLibrary(LibraryGit library) {
        // TODO Auto-generated method stub
        
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

    @Override
    public String getSourceEntityPath() {
        // TODO Auto-generated method stub
        return null;
    }
}
