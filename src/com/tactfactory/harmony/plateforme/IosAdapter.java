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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.tactfactory.harmony.meta.ClassMetadata;

/** Apple iOS Adapter of project structure. */
public final class IosAdapter extends BaseAdapter {

	/**
	 * Constructor.
	 */
	public IosAdapter()	 {
		super();
		this.setProject("project");
		this.setPlatform("ios");
		this.setResource("res");
		this.setSource("src");
	}


	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameSpaceEntity(final ClassMetadata cm,
			final String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public String getViewComponentShow(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public String getViewComponentEdit(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public String getNativeType(final String type) {
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
		return oldContent.equals(newContent);
	}

	@Override
	public void installGitLibrary(String url, String pathLib,
			String versionTag, String libName, List<File> filesToDelete,
			String libraryProjectPath, String target, String referencePath,
			boolean isSupportV4Dependant) {
		throw new NotImplementedException();

	}

}
