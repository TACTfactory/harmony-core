/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Manipulate File tools */
public class FileUtils {
	/** Create a new file if doesn't exist (and path)
	 * 
	 * @param filename Full path of file
	 * @return File instance
	 */
	public static File makeFile(String filename) {
		File file = new File(filename);
		
		File parent = file.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}

		boolean success = false;
		try {
			success = file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (success) {
	        // File did not exist and was created
	    } else {
	        // File already exists
	    }
		
		return file;
	}
	
	public static void copyfile(File srcFile, File destFile) {
		try {
			InputStream in = new FileInputStream(srcFile);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(destFile);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File "+srcFile.getName()+" copied to "+destFile.getPath());
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			//System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static File makeFolder(String filename) {
		File folder = new File(filename);

		boolean success = false;
		try {
			success = folder.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (success) {
	        // Folder did not exists and was created
	    	System.out.println("Folder '"+folder.getName()+"' did not exists and was created...");
	    } else {
	        // Folder already exists
	    	if(folder.exists())
	    		System.out.println("Folder '"+folder.getName()+"' already exists...");
	    	else
	    		System.out.println("Folder '"+folder.getName()+"' creation error...");
	    }
		
		return folder;
	}

	public static File makeFolderRecursive(String srcPath, String destPath, boolean makeFiles)
	{
		File dest_folder = new File(destPath);
		File tpl_folder = new File(srcPath);
		File tpl_files[] = tpl_folder.listFiles();
		File tmp_file = null;
		boolean success = false;

		if(!tpl_folder.isDirectory())
			System.out.println("Folder src '"+srcPath+"' is not a folder");
		else if(tpl_files == null || tpl_files.length == 0)
			System.out.println("No items inside '"+srcPath+"'");
		else
		{
			success = dest_folder.mkdirs();
			if(dest_folder.exists())
			{
				if(success)
					System.out.println("Folder '"+dest_folder.getName()+"' did not exists and was created...");
				else
		    		System.out.println("Folder '"+dest_folder.getName()+"' already exists...");
				for(int i=0;i<tpl_files.length;i++)
				{
					if(tpl_files[i].isDirectory())
					{
						tmp_file = new File(destPath+tpl_files[i].getName()+"/");
						success = tmp_file.mkdir();
					    if (success) {
					        // Folder did not exists and was created
					    	System.out.println("Folder '"+tmp_file.getName()+"' did not exists and was created...");
					    } else {
					        // Folder already exists
					    	if(tmp_file.exists())
					    		System.out.println("Folder '"+tmp_file.getName()+"' already exists...");
					    	else
					    		System.out.println("Folder '"+tmp_file.getName()+"' creation error...");
					    }
						FileUtils.makeFolderRecursive(srcPath+tpl_files[i].getName()+"/",destPath+tmp_file.getName()+"/",false);
					}
					else if(tpl_files[i].isFile() && makeFiles)
					{
						tmp_file = FileUtils.makeFile(destPath+"/"+tpl_files[i].getName());
						if(tmp_file.exists()) {
			    			System.out.println("File '"+tpl_files[i].getName()+"' created...");
			    			FileUtils.copyfile(tpl_files[i], tmp_file);
						} else {
			    			System.out.println("File '"+tpl_files[i].getName()+"' creation error...");
						}
					}
				}
			}
			else {
		    	System.out.println("Folder '"+dest_folder.getName()+"' creation Error...");
			}
		}
		return dest_folder;
	}

}
