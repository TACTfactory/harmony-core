/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;
import com.tactfactory.mda.utils.ImageUtils;

/** Google Android Adapter of project structure. */
public final class AndroidAdapter extends BaseAdapter {
	/** Ratio for HD images resizing. */
	private static final float HD_RATIO = 0.75f;
	
	/** Ratio for MD images resizing. */
	private static final float MD_RATIO = 0.50f;
	
	/** Ratio for LD images resizing. */
	private static final float LD_RATIO = 0.375f;
	
	/** Float type. */
	private static final String FLOAT = "float";
	
	/** String type. */
	private static final String STR = "String";
	
	/** Int type. */
	private static final String INT = "int";
	
	/** DateTime type. */
	private static final String DATETIME = "DateTime";
	
	/**
	 * FilenameFilter for images.
	 */
	private final FilenameFilter filter = new FilenameFilter() {
	    @Override
		public boolean accept(final File dir, final String name) {
	        return	name.endsWith(".png") 
	        		|| name.endsWith(".jpg");
	    }
	};

	/**
	 * Constructor.
	 */
	public AndroidAdapter() {
		super();
		// Structure
		this.setProject("project");
		this.setPlatform("android");
		this.setResource("res");
		this.setAssets("assets");
		this.setSource("src");
		this.setLibs("libs");
		this.setTest("test");
		this.setTestLibs("libs");
		this.setHarmony("harmony");
		this.setWidget("widget");
		this.setUtil("util");
		this.setMenu("menu");
		
		// MVC
		//this.model 		= "entity";
		//this.view 		= "layout";
		//this.controller	= "view";
		//this.data			= "data";
		//this.provider		= "provider";
		
		// File
		this.setManifest("AndroidManifest.xml");
		this.setStrings("strings.xml");
		this.setHome("HomeActivity.java");
		this.setConfigs("configs.xml");
	}
 
	@Override
	public String getNameSpaceEntity(final ClassMetadata cm, 
			final String type) {
		return String.format("%s.%s", 
				this.getNameSpace(cm, type),
				cm.getName().toLowerCase());
	}

	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		return String.format("%s.%s", 
				cm.getSpace(),
				type);
	}

	@Override
	public String getNativeType(final String type) {
		String ret = type;
		
		if (type.equals(Column.Type.STRING.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.TEXT.getValue())) {
			ret = STR;	
		} else
			
		if (type.equals(Column.Type.INTEGER.getValue())) {
			ret = INT;
		} else
			
		if (type.equals(Column.Type.INT.getValue())) {
			ret = INT;
		} else
			
		if (type.equals(Column.Type.FLOAT.getValue())) {
			ret = FLOAT;
		} else
			
		if (type.equals(Column.Type.DATETIME.getValue())) {
			ret = DATETIME;
		} else
			
		if (type.equals(Column.Type.DATE.getValue())) {
			ret = DATETIME;
		} else
			
		if (type.equals(Column.Type.TIME.getValue())) {
			ret = DATETIME;
		} else
			
		if (type.equals(Column.Type.LOGIN.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.PASSWORD.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.EMAIL.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.PHONE.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.CITY.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.ZIPCODE.getValue())) {
			ret = INT;
		} else
			
		if (type.equals(Column.Type.COUNTRY.getValue())) {
			ret = STR;
		} else
			
		if (type.equals(Column.Type.BC_EAN.getValue())) {
			ret = INT;
		}
		return ret;
	}
	

	
	@Override
	public void resizeImage() {
		final File imageDirectoryXHD	
				= new File(this.getRessourcePath() + "/drawable-xhdpi");
		final File imageDirectoryHD 
				= new File(this.getRessourcePath() + "/drawable-hdpi");
		final File imageDirectoryMD 	
				= new File(this.getRessourcePath() + "/drawable-mdpi");
		final File imageDirectoryLD 	
				= new File(this.getRessourcePath() + "/drawable-ldpi");
		
		File imageHD;
		File imageMD;
		File imageLD;
		
		if (imageDirectoryXHD.exists() 
				&& imageDirectoryXHD.listFiles().length > 0) {
			final File[] imagesFiles = imageDirectoryXHD.listFiles(this.filter);
			TactFileUtils.makeFolder(imageDirectoryHD.getAbsolutePath());
			TactFileUtils.makeFolder(imageDirectoryMD.getAbsolutePath());
			TactFileUtils.makeFolder(imageDirectoryLD.getAbsolutePath());
			
			for (final File imageXHD : imagesFiles) {
				try {
					imageHD = new File(imageDirectoryHD.getCanonicalPath() 
							+ "/" 
							+ imageXHD.getName());
					
					imageMD = new File(imageDirectoryMD.getCanonicalPath() 
							+ "/" 
							+ imageXHD.getName());
					
					imageLD = new File(imageDirectoryLD.getCanonicalPath() 
							+ "/" 
							+ imageXHD.getName());
						
					ImageUtils.resize(imageXHD, imageHD, HD_RATIO);
					ImageUtils.resize(imageXHD, imageMD, MD_RATIO);
					ImageUtils.resize(imageXHD, imageLD, LD_RATIO);
					
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					ConsoleUtils.displayError(e);
				}
				
			}
		}
	}
}
