/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.tactfactory.mda.Harmony;

/** Manipulate File tools */
public class FileUtils extends org.apache.commons.io.FileUtils {
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
	
	/** copy file content from srcFile to destFile */
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
			// Debug Log
			if (Harmony.DEBUG)
				System.out.println("File "+srcFile.getName()+" copied to "+destFile.getPath());
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			//System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/** handle folder creation with its parents */
	public static File makeFolder(String filename) {
		File folder = new File(filename);

		boolean success = false;

		success = folder.mkdirs();
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

	/** convert file content to a string */
	public static String FileToString(File file)
	{
		String result = null;
		DataInputStream in = null;

		try {
			byte[] buffer = new byte[(int) file.length()];
			in = new DataInputStream(new FileInputStream(file));
			in.readFully(buffer);
			result = new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException("IO problem in fileToString", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) { /* ignore it */
			}
		}
		return result;
	}

	/** convert file content to a stringbuffer */
	public static StringBuffer FileToStringBuffer(File file)
	{
		StringBuffer result = new StringBuffer();
		String tmp;
		String ln = System.getProperty("line.separator");
		
		FileInputStream fis = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		try{
			fis = new FileInputStream(file);
			in = new InputStreamReader(fis);
			br = new BufferedReader(in);
			while(true){
				tmp = br.readLine();
				if(tmp==null) break;
				result.append(tmp);
				result.append(ln);
			}
			
		}catch(IOException e){
			System.out.println("Error : "+e.getMessage());
		}finally{
			try{
				br.close();
				in.close();
				fis.close();
			}catch(IOException e){
				
			}
		}
		
		
		return result;
	}
	
	/** write stringbuffer contents to the given file */
	public static void StringBufferToFile(StringBuffer buff, File file)
	{
		FileOutputStream fos = null;
		OutputStreamWriter out = null;
		BufferedWriter bw = null;
		try{
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos);
			bw = new BufferedWriter(out);
			bw.write(buff.toString());

		}catch(IOException e){
			System.out.println("Error : "+e.getMessage());
		}finally{
			try{
				bw.close();
				out.close();
				fos.close();
			}catch(IOException e){
				
			}
		}
	}
	
	/** convert file content to a string array with each line separated */
	public static ArrayList<String> FileToStringArray(File file) {
		
		ArrayList<String> result = null;
		String line = null;
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			result = new ArrayList<String>();
			in = new DataInputStream(new FileInputStream(file));
			br = new BufferedReader(new InputStreamReader(in));
			
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException("IO problem in fileToString", e);
		} finally {
			try {
				in.close();
				br.close();
			} catch (IOException e) { /* ignore it */ }
		}
		return result;
	}
	
	/** copy folder content recursively from srcPath to destPath, and copy files or not */
	public static File makeFolderRecursive(String srcPath, String destPath, boolean makeFiles) {
		
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
							if (Harmony.DEBUG)
								System.out.println("Folder '"+tmp_file.getName()+"' did not exists and was created...");
					    } else {
					        // Folder already exists
					    	if(tmp_file.exists())
									System.out.println("Folder '"+tmp_file.getName()+"' already exists...");
					    	else
									System.out.println("Folder '"+tmp_file.getName()+"' creation error...");
					    }
						FileUtils.makeFolderRecursive(String.format("%s%s/", srcPath, tpl_files[i].getName()),
														String.format("%s%s/",destPath, tmp_file.getName()),false);
					}
					else if(tpl_files[i].isFile() && makeFiles)
					{
						tmp_file = FileUtils.makeFile(destPath+tpl_files[i].getName());
						if(tmp_file.exists()) {
			    			FileUtils.copyfile(tpl_files[i], tmp_file);
							if (Harmony.DEBUG)
								System.out.println("File '"+tpl_files[i].getName()+"' created...");
						} else {
							if (Harmony.DEBUG)
								System.out.println("File '"+tpl_files[i].getName()+"' creation error...");
						}
					}
				}
			}
			else {
				if (Harmony.DEBUG)
					System.out.println("Folder '"+dest_folder.getName()+"' creation Error...");
			}
		}
		return dest_folder;
	}
	
	/** delete a directory with all its files recursively */
	public static int deleteRecursive(File dir)
	{
		return FileUtils.deleteRecursive(dir,0);
	}
	
	public static int deleteRecursive(File dir, int result){
		if(dir.exists()) {
			if(dir.isDirectory()) {
				//it's a directory, list files and call deleteDir on a dir
				for(File f : dir.listFiles()) {
					if(f.isDirectory()) {
						result = FileUtils.deleteRecursive(f,result);
					} else {
						if(f.delete()){
							if (Harmony.DEBUG)
								System.out.println("File '"+f.getPath()+"' deleted.");
						} else{
							result++;
							if (Harmony.DEBUG)
								System.out.println("File '"+f.getPath()+"' delete ERROR!");
						}
					}
				}
				
				//folder content check, remove folder
				if(dir.listFiles().length==0){
					if(dir.delete()) {
						if (Harmony.DEBUG)
							System.out.println("Folder '"+dir.getPath()+"' deleted.");
					} else {
						result++;
						if (Harmony.DEBUG)
							System.out.println("Folder '"+dir.getPath()+"' delete ERROR!");
					}
				} else {
					result++;
					if (Harmony.DEBUG)
						System.out.println("Folder '"+dir.getPath()+"' NOT Empty!");
				}
				
			} else {
				// it's a file delete simply
				if(dir.delete()) {
					if (Harmony.DEBUG)
						System.out.println("File '"+dir.getPath()+"' deleted.");
				} else {
					result++;
					if (Harmony.DEBUG)
						System.out.println("File '"+dir.getPath()+"' delete ERROR!");
				}
			}
		} else {
			result++;
			if (Harmony.DEBUG)
				System.out.println("Folder '"+dir.getPath()+"' doesn't exists!");
		}
		return result;
	}
	
	public static boolean exists(String filename){
		File f = new File(filename);
		if(Harmony.DEBUG && f.exists())
			System.out.println("File "+filename+ "already exists !");
		else if(Harmony.DEBUG)
			System.out.println("File "+filename+ "doesn't exists !");
		return f.exists();
	}
}
