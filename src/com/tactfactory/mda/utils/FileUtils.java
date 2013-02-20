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
import java.util.List;

/** Manipulate File tools */
public abstract class FileUtils extends org.apache.commons.io.FileUtils {
	private static final String FOLDER 	= "Folder '";
	private static final String FILE 	= "File '";
	
	/** 
	 * Create a new file if doesn't exist (and path)
	 * 
	 * @param filename Full path of file
	 * @return File instance 
	 */
	public static File makeFile(final String filename) {
		final File file = new File(filename);
		
		final File parent = file.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
			final IllegalStateException exception = new IllegalStateException("Couldn't create dir: " + parent);
			ConsoleUtils.displayError(exception);
		    throw exception;
		}

		try {
			file.createNewFile();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
		return file;
	}
	
	/** 
	 * Copy file content from srcFile to destFile 
	 *
	 * @param srcFile Source path
	 * @param destFile Destination path
	 */
	public static void copyfile(final File srcFile, final File destFile) {
		final byte[] buf = new byte[1024];
		int len;
		
		try {
			final InputStream in = new FileInputStream(srcFile);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			final OutputStream out = new FileOutputStream(destFile);
			len = in.read(buf);
			while (len > 0) {
				out.write(buf, 0, len);
				len = in.read(buf);
			}
			
			in.close();
			out.close();
			
			// Debug Log
			ConsoleUtils.displayDebug("File "+srcFile.getName()+" copied to "+destFile.getPath());
			
		} catch (final FileNotFoundException ex) {
			ConsoleUtils.displayError(new Exception(ex.getMessage() + " in the specified directory.", ex));
			//System.exit(0);
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** handle folder creation with its parents */
	public static File makeFolder(final String filename) {
		final File folder = new File(filename);

		boolean success = false;

		success = folder.mkdirs();
	    if (success) {
	        // Folder did not exists and was created
	    	ConsoleUtils.display(FOLDER+folder.getName()+"' did not exists and was created...");
	    } else {
	        // Folder already exists
	    	if(folder.exists()) {
	    		ConsoleUtils.display(FOLDER+folder.getName()+"' already exists...");
	    	} else {
	    		ConsoleUtils.display(FOLDER+folder.getName()+"' creation error...");
	    	}
	    }
		
		return folder;
	}

	/** convert file content to a string */
	public static String fileToString(final File file)
	{
		String result = null;
		DataInputStream in = null;

		try {
			final byte[] buffer = new byte[(int) file.length()];
			in = new DataInputStream(new FileInputStream(file));
			in.readFully(buffer);
			result = new String(buffer);
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) { 
				ConsoleUtils.displayError(e);
			}
		}
		return result;
	}

	/** convert file content to a stringbuffer */
	public static StringBuffer fileToStringBuffer(final File file)
	{
		final StringBuffer result = new StringBuffer();
		String tmp;
		final String ln = System.getProperty("line.separator");
		
		FileInputStream fis = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		try{
			fis = new FileInputStream(file);
			in = new InputStreamReader(fis);
			br = new BufferedReader(in);
			while(true){
				tmp = br.readLine();
				if(tmp==null) {
					break;
				}
				result.append(tmp);
				result.append(ln);
			}
			
		}catch(final IOException e){
			ConsoleUtils.displayError(e);
		}finally{
			try{
				if (br != null) {
					br.close();
				}
				if (in != null) {
					in.close();
				}
				if (fis != null) {
					fis.close();
				}
			}catch(final IOException e){
				ConsoleUtils.displayError(e);
			}
		}
		
		
		return result;
	}
	
	/** write stringbuffer contents to the given file */
	public static void stringBufferToFile(final StringBuffer buff, final File file)
	{
		FileOutputStream fos = null;
		OutputStreamWriter out = null;
		BufferedWriter bw = null;
		try{
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos);
			bw = new BufferedWriter(out);
			bw.write(buff.toString());

		}catch(final IOException e){
			ConsoleUtils.displayError(e);
		}finally{
			try{
				if (bw != null) {
					bw.close();
				}
				if (out != null) {
					out.close();
				}
				if (fos != null) {
					fos.close();
				}
			}catch(final IOException e){
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/** convert file content to a string array with each line separated */
	public static List<String> fileToStringArray(final File file) {
		
		ArrayList<String> result = null;
		String line = null;
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			result = new ArrayList<String>();
			in = new DataInputStream(new FileInputStream(file));
			br = new BufferedReader(new InputStreamReader(in));
			line = br.readLine(); 
			while (line != null) {
				result.add(line);
				line = br.readLine();
			}
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (final IOException e) { 
				ConsoleUtils.displayError(e);
			}
		}
		return result;
	}
	
	/** copy folder content recursively from srcPath to destPath, and copy files or not */
	public static File makeFolderRecursive(final String srcPath, final String destPath, final boolean makeFiles) {
		final File destFolder = new File(destPath);
		final File tplFolder = new File(srcPath);
		final File tplFiles[] = tplFolder.listFiles();
		File tmpFile = null;
		boolean success = false;

		if(!tplFolder.isDirectory()) {
			ConsoleUtils.display("Folder src '"+srcPath+"' is not a folder");
		} else if(tplFiles == null || tplFiles.length == 0) {
			ConsoleUtils.display("No items inside '"+srcPath+"'");
		} else {
			success = destFolder.mkdirs();
			if(destFolder.exists())
			{
				if(success) {
					ConsoleUtils.display(FOLDER+destFolder.getName()+"' did not exists and was created...");
				} else {
					ConsoleUtils.display(FOLDER+destFolder.getName()+"' already exists...");
				}
				for (final File tplFile : tplFiles) {
					if(tplFile.isDirectory())	{
						tmpFile = new File(destPath+tplFile.getName()+"/");
						success = tmpFile.mkdir();
					    if (success) {
					    	ConsoleUtils.displayDebug(FOLDER+tmpFile.getName()+"' did not exists and was created...");
					    } else {
					        // Folder already exists
					    	if(tmpFile.exists()) {
					    		ConsoleUtils.display(FOLDER+tmpFile.getName()+"' already exists...");
					    	} else {
					    		ConsoleUtils.display(FOLDER+tmpFile.getName()+"' creation error...");
					    	}
					    }
						FileUtils.makeFolderRecursive(String.format("%s%s/", srcPath, tplFile.getName()),
														String.format("%s%s/",destPath, tmpFile.getName()),false);
					}
					else if(tplFile.isFile() && makeFiles)	{
						tmpFile = FileUtils.makeFile(destPath+tplFile.getName());
						if(tmpFile.exists()) {
			    			FileUtils.copyfile(tplFile, tmpFile);
			    			ConsoleUtils.displayDebug(FILE+tplFile.getName()+"' created...");
						} else {
							ConsoleUtils.displayError(new Exception(FILE+tplFile.getName()+"' creation error..."));
						}
					}
				}
			}
			else {
				ConsoleUtils.displayError(new Exception(FOLDER+destFolder.getName()+"' creation Error..."));
			}
		}
		return destFolder;
	}
	
	/** delete a directory with all its files recursively */
	public static int deleteRecursive(final File dir)
	{
		return FileUtils.deleteRecursive(dir,0);
	}
	
	public static int deleteRecursive(final File dir, int result){
		if(dir.exists()) {
			if(dir.isDirectory()) {
				//it's a directory, list files and call deleteDir on a dir
				for(final File f : dir.listFiles()) {
					if(f.isDirectory()) {
						result = FileUtils.deleteRecursive(f,result);
					} else {
						if(f.delete()){
							ConsoleUtils.displayDebug(FILE+f.getPath()+"' deleted.");
						} else{
							result++;
							
							ConsoleUtils.displayWarning(FILE+f.getPath()+"' delete ERROR!");
						}
					}
				}
				
				//folder content check, remove folder
				if(dir.listFiles().length==0){
					if(dir.delete()) {
						ConsoleUtils.displayDebug(FOLDER+dir.getPath()+"' deleted.");
					} else {
						result++;
						
						ConsoleUtils.displayWarning(FOLDER+dir.getPath()+"' delete ERROR!");
					}
				} else {
					result++;
					
					ConsoleUtils.displayWarning(FOLDER+dir.getPath()+"' NOT Empty!");
				}
				
			} else {
				// it's a file delete simply
				if(dir.delete()) {
					ConsoleUtils.displayDebug(FILE+dir.getPath()+"' deleted.");
				} else {
					result++;

					ConsoleUtils.displayWarning(FILE+dir.getPath()+"' delete ERROR!");
				}
			}
		} else {
			result++;
			
			ConsoleUtils.displayWarning(FOLDER+dir.getPath()+"' doesn't exists!");
		}
		return result;
	}
	
	public static boolean exists(final String filename){
		final File f = new File(filename);
		if( f.exists()) {
			ConsoleUtils.displayDebug("File "+filename+ " already exists !");
		} else { 
			ConsoleUtils.displayDebug("File "+filename+ " doesn't exists !");
		}
		return f.exists();
	}
	
	/*
	 * Get the extension of a file.
	 */  
	public static String getExtension(final File f) {
	    String ext = null;
	    final String s = f.getName();
	    final int i = s.lastIndexOf('.');

	    if (i > 0 &&  i < s.length() - 1) {
	        ext = s.substring(i+1).toLowerCase();
	    }
	    return ext;
	}
	
	public static boolean appendToFile(final String content, final File file){
		boolean success = false;
		final StringBuffer sb = FileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if(sb.indexOf(content)==-1) {
			final int offset = sb.length(); 
			sb.insert(offset, content);
			FileUtils.stringBufferToFile(sb, file);
			success = true;
		}
		return success;
	}
	
	public static boolean addToFile(final String content, final String after, final File file){
		boolean success = false;
		final StringBuffer sb = FileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if(sb.indexOf(content)==-1) {
			final int offset = sb.indexOf(after)+after.length();
			sb.insert(offset, content);
			FileUtils.stringBufferToFile(sb, file);
			success = true;
		}
		return success;
	}
}
