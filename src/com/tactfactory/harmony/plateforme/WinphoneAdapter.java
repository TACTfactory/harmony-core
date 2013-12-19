/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme;

import java.io.File;
import java.util.List;

import com.tactfactory.harmony.exception.NotImplementedException;
import com.tactfactory.harmony.meta.ClassMetadata;

/**
 * Microsoft Adapter of project structure.
 *
 */
public final class WinphoneAdapter extends BaseAdapter {

	/**
	 * Constructor.
	 */
	public WinphoneAdapter() {
		super();
		this.setPlatform("winphone");
		this.setResource("res");
		this.setSource("src");
	}

	@Override
	public String getNameSpace(final ClassMetadata classMeta,
			final String type) {
		throw new NotImplementedException(
				"WinPhone adapter has not been implemented yet.");
	}

	@Override
	public String getNameSpaceEntity(final ClassMetadata classMeta,
			final String type) {
		throw new NotImplementedException(
				"WinPhone adapter has not been implemented yet.");
	}


	@Override
	public String getNativeType(final String type) {
		throw new NotImplementedException(
				"WinPhone adapter has not been implemented yet.");
	}

	@Override
	public void resizeImage() {
		throw new NotImplementedException(
				"WinPhone adapter has not been implemented yet.");
	}
	
	@Override
	public void installGitLibrary(String url, String pathLib,
			String versionTag, String libName, List<File> filesToDelete,
			String libraryProjectPath, String target, String referencePath,
			boolean isSupportV4Dependant) {
		throw new NotImplementedException(
				"WinPhone adapter has not been implemented yet.");
		
	}

	@Override
	public boolean filesEqual(String oldContent, String newContent,
			String fileName, boolean ignoreHeader) {
		return oldContent.equals(newContent);
	}
}
