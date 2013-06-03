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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

/** Manipulate File tools. */
public abstract class TactFileUtils extends FileUtils {
	/** Default encoding for stream manipulation. */
	public static final String DEFAULT_ENCODING = "UTF-8";
		
	/** String for debug informations. */
	private static final String FOLDER 	= "Folder '";
	
	/** String for debug informations. */
	private static final String FILE 	= "File '";
	
	/** 
	 * Create a new file if doesn't exist (and path).
	 * 
	 * @param filename Full path of file
	 * @return File instance 
	 */
	public static File makeFile(final String filename) {
		final File file = new File(filename);
		
		final File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			final IllegalStateException exception = 
					new IllegalStateException(
							"Couldn't create dir: " + parent);
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
	 * Copy file content from srcFile to destFile.
	 *
	 * @param srcFile Source path
	 * @param destFile Destination path
	 */
	public static void copyfile(final File srcFile, final File destFile) {
		try {
			Files.copy(srcFile, destFile);
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);			
		}
	}
	
	/** handle folder creation with its parents.
	 * @param filename The folder name
	 * @return The newly created folder
	 */
	public static File makeFolder(final String filename) {
		final File folder = new File(filename);

		boolean success = false;

		success = folder.mkdirs();
	    if (success) {
	        // Folder did not exists and was created
	    	ConsoleUtils.display(FOLDER
	    			 + folder.getName()
	    			 + "' did not exists and was created...");
	    } else {
	        // Folder already exists
	    	if (folder.exists()) {
	    		ConsoleUtils.display(FOLDER
	    				 + folder.getName()
	    				 + "' already exists...");
	    	} else {
	    		ConsoleUtils.display(FOLDER
	    				 + folder.getName()
	    				 + "' creation error...");
	    	}
	    }
		
		return folder;
	}

	/** convert file content to a string.
	 * 
	 * @param file The file to open
	 * @return the String containing the file contents 
	 */
	public static String fileToString(final File file) {
		String result = null;
		DataInputStream inStream = null;

		try {
			final byte[] buffer = new byte[(int) file.length()];
			inStream = new DataInputStream(new FileInputStream(file));
			inStream.readFully(buffer);
			result = new String(buffer, TactFileUtils.DEFAULT_ENCODING);
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString",
					e);
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (final IOException e) { 
				ConsoleUtils.displayError(e);
			}
		}
		return result;
	}

	/** convert file content to a stringbuffer.
	 * 
	 * @param file The File to open
	 * @return the StringBuffer containing the file's content 
	 */
	public static StringBuffer fileToStringBuffer(final File file) {
		final StringBuffer result = new StringBuffer();
		String tmp;
		final String lineSeparator = System.getProperty("line.separator");
		
		FileInputStream fis = null;
		InputStreamReader inStream = null;
		BufferedReader bReader = null;
		try {
			fis = new FileInputStream(file);
			inStream = new InputStreamReader(fis, TactFileUtils.DEFAULT_ENCODING);
			bReader = new BufferedReader(inStream);
			while (true) {
				tmp = bReader.readLine();
				if (tmp == null) {
					break;
				}
				result.append(tmp);
				result.append(lineSeparator);
			}
			
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} finally {
			try {
				if (bReader != null) {
					bReader.close();
				}
				if (inStream != null) {
					inStream.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}
		
		
		return result;
	}
	
	/** Write StringBuffer contents to the given file.
	 * @param buff The buffer to write to the file
	 * @param file The file in which the buffer must be copied
	 */
	public static void stringBufferToFile(final StringBuffer buff,
				final File file)	 {
		FileOutputStream fos = null;
		OutputStreamWriter out = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos, TactFileUtils.DEFAULT_ENCODING);
			bw = new BufferedWriter(out);
			bw.write(buff.toString());

		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (out != null) {
					out.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/** convert file content to a string array with each line separated.
	 * @param file The File to read
	 * @return Array of Strings containing the file contents
	 */
	public static List<String> fileToStringArray(final File file) {
		
		ArrayList<String> result = null;
		String line = null;
		DataInputStream inStream = null;
		BufferedReader bReader = null;

		try {
			result = new ArrayList<String>();
			inStream = new DataInputStream(new FileInputStream(file));
			bReader = new BufferedReader(
					new InputStreamReader(inStream, TactFileUtils.DEFAULT_ENCODING));
			line = bReader.readLine(); 
			while (line != null) {
				result.add(line);
				line = bReader.readLine();
			}
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString",
					e);
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (bReader != null) {
					bReader.close();
				}
			} catch (final IOException e) { 
				ConsoleUtils.displayError(e);
			}
		}
		return result;
	}
	
	/** convert file content to a string array with each line separated.
	 * @param strings The lines to copy to the file
	 * @param file The File to read
	 */
	public static void stringArrayToFile(final List<String> strings,
			final File file) {
		
		DataOutputStream out = null;
		BufferedWriter br = null;

		try {
			out = new DataOutputStream(new FileOutputStream(file));
			br = new BufferedWriter(
					new OutputStreamWriter(out,
							TactFileUtils.DEFAULT_ENCODING));

			for (final String string : strings) {
				br.write(string);
				br.write('\n');
			}
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString",
					e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) { 
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/** Copy folder content recursively from srcPath to destPath,
	 * and copy files or not.
	 * @param srcPath Source folder
	 * @param destPath Destination folder
	 * @param makeFiles True if you want to copy files as well
	 * 
	 *  @return The newly created folder 
	 */
	public static File makeFolderRecursive(final String srcPath,
			final String destPath,
			final boolean makeFiles) {
		final File destFolder = new File(destPath);
		final File tplFolder = new File(srcPath);
		final File[] tplFiles = tplFolder.listFiles();
		File tmpFile = null;
		boolean success = false;

		if (!tplFolder.isDirectory()) {
			ConsoleUtils.display("Folder src '" 
						 + srcPath 
						 + "' is not a folder");
		} else if (tplFiles == null || tplFiles.length == 0) {
			ConsoleUtils.display("No items inside '" 
							 + srcPath 
							 + "'");
		} else {
			success = destFolder.mkdirs();
			if (destFolder.exists()) {
				if (success) {
					ConsoleUtils.display(FOLDER
							 + destFolder.getName()
							 + "' did not exists and was created.");
				} else {
					ConsoleUtils.display(FOLDER
							 + destFolder.getName()
							 + "' already exists...");
				}
				for (final File tplFile : tplFiles) {
					if (tplFile.isDirectory())	 {
						tmpFile = new File(destPath
                                + tplFile.getName()
								 + "/");
						success = tmpFile.mkdir(); 
					    if (success) {
					    	ConsoleUtils.displayDebug(FOLDER
					    			 + tmpFile.getName()
					    			 + "' did not exists and was created...");
					    } else {
					        // Folder already exists
					    	if (tmpFile.exists()) {
					    		ConsoleUtils.display(FOLDER
					    				 + tmpFile.getName()
					    				 + "' already exists...");
					    	} else {
					    		ConsoleUtils.display(FOLDER
					    				 + tmpFile.getName()
					    				 + "' creation error...");
					    	}
					    }
						TactFileUtils.makeFolderRecursive(
								String.format("%s%s/",
										srcPath,
										tplFile.getName()),
								String.format("%s%s/",
										destPath, 
										tmpFile.getName()),
								makeFiles);
					} else if (tplFile.isFile() && makeFiles) {
						tmpFile = TactFileUtils.makeFile(destPath
								 + tplFile.getName());
						if (tmpFile.exists()) {
			    			TactFileUtils.copyfile(tplFile, tmpFile);
			    			ConsoleUtils.displayDebug(FILE
			    					 + tplFile.getName() 
			    					 + "' created...");
						} else {
							ConsoleUtils.displayError(
									new Exception(
											FILE 
											 + tplFile.getName()
											 + "' creation error..."));
						}
					}
				}
			} else {
				ConsoleUtils.displayError(
						new Exception(FOLDER
								 + destFolder.getName()
								 + "' creation Error..."));
			}
		}
		return destFolder;
	}
	
	/** delete a directory with all its files recursively.
	 * @param dir The folder or file to delete
	 * @return number of files/folders deleted
	 */
	public static int deleteRecursive(final File dir) {
		return TactFileUtils.deleteRecursive(dir, 0);
	}
	
	/** delete a directory with all its files recursively.
	 * @param dir The folder or file to delete
	 * @param result number of files/folders deleted
	 * @return number of files deleted
	 */
	private static int deleteRecursive(final File dir, final int result) {
		int ret = result;

		try {
			if (dir.exists()) {
				if (dir.isDirectory()) {
					//it's a directory, list files and call deleteDir on a dir
					for (final File file : dir.listFiles()) {
						if (file.isDirectory()) {
							ret = TactFileUtils.deleteRecursive(file, ret);
						} else {
							if (!file.delete()) {
								ret++;
								
								ConsoleUtils.displayWarning(FILE
										 + file.getPath()
										 + "' delete ERROR!");
							}
						}
					}
					
					//folder content check, remove folder
					if (dir.listFiles().length == 0) {
						boolean deleteSuccess = false;
						deleteSuccess = dir.delete();
						
						if (deleteSuccess) {
							ConsoleUtils.displayDebug(FOLDER
									 + dir.getPath()
									 + "' deleted.");
						} else {
							ret++;
							
							ConsoleUtils.displayWarning(FOLDER
									 + dir.getPath()
									 + "' delete ERROR!");
						}
					} else {
						ret++;
						
						ConsoleUtils.displayWarning(FOLDER
								 + dir.getPath()
								 + "' NOT Empty!");
					}
					
				} else {
					// it's a file delete simply
					if (dir.delete()) {
						ConsoleUtils.displayDebug(FILE
								 + dir.getPath()
								 + "' deleted.");
					} else {
						ret++;
	
						ConsoleUtils.displayWarning(FILE
								 + dir.getPath()
								 + "' delete ERROR!");
					}
				}
			} else {
				ret++;
				
				ConsoleUtils.displayWarning(FOLDER
						 + dir.getPath()
						 + "' doesn't exists!");
			}

		} catch (SecurityException e) {
			ConsoleUtils.displayError(e);
		} catch (NullPointerException e) {
			ConsoleUtils.displayError(new Exception("Error while deleting " 
					+ dir.getAbsolutePath()
					+ ". Check if you have permissions for this file "
					+ "and if it is not opened by another application"));
		}
		return ret;
	}
	
	/**
	 * Tests if file exists.
	 * @param filename The file name
	 * @return true if the file exists
	 */
	public static boolean exists(final String filename) {
		final File file = new File(filename);
		if (file.exists()) {
			ConsoleUtils.displayDebug("File "
					 + filename
					 + " already exists !");
		} else { 
			ConsoleUtils.displayDebug("File "
						 + filename
						 + " doesn't exists !");
		}
		return file.exists();
	}
	
	/**
	 * Get the extension of a file.
	 * @param f The file
	 * @return The file's extension
	 */  
	public static String getExtension(final File file) {
	    String ext = null;
	    final String fileName = file.getName();
	    final int i = fileName.lastIndexOf('.');

	    if (i > 0 &&  i < fileName.length() - 1) {
	        ext = fileName.substring(i + 1).toLowerCase();
	    }
	    return ext;
	}
	
	/**
	 * Append String to at the end of a file 
	 * if it doesn't exists in the file yet.
	 * @param content The content to append
	 * @param file The file to write to
	 * @return true if the content has been correctly appended
	 */
	public static boolean appendToFile(final String content, final File file) {
		boolean success = false;
		final StringBuffer buffer = TactFileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if (buffer.indexOf(content) == -1) {
			final int offset = buffer.length(); 
			buffer.insert(offset, content);
			TactFileUtils.stringBufferToFile(buffer, file);
			success = true;
		}
		return success;
	}
	
	
	/**
	 * Add String after a given String in the given file.
	 * (Only if it doesn't already exists in the file)
	 * @param content The content to append
	 * @param after The String after which the content must be added
	 * @param file The file to write to
	 * @return true if the content has been correctly appended
	 */
	public static boolean addToFile(final String content, 
			final String after,
			final File file) {
		boolean success = false;
		final StringBuffer buffer = TactFileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if (buffer.indexOf(content) == -1) {
			final int offset = buffer.indexOf(after) + after.length();
			buffer.insert(offset, content);
			TactFileUtils.stringBufferToFile(buffer, file);
			success = true;
		}
		return success;
	}
	
	/**
	 * Converts an absolute path to a path relative to working dir.
	 * @param absolute The absolute path
	 * @return The relative path
	 */
	public static String absoluteToRelativePath(final String absolute) {
		return absoluteToRelativePath(absolute, ".");
	}
	
	/**
	 * Converts an absolute path to a path relative to working dir.
	 * @param absolute The absolute path
	 * @param relative The relative path to use
	 * @return The relative path
	 */
	public static String absoluteToRelativePath(
			final String absolute, final String relative) {
		String result = ".";
		
		final File abs = new File(absolute);
		final File workingDir = new File(relative);
		
		final URI resultString = workingDir.toURI().relativize(abs.toURI());
		
		if (!resultString.toString().equals("")) {
			result = resultString.toString();
		}
		
		if (result.startsWith("file:")) {
			result = result.substring("file:".length());
			
		}
		
		return result;
	}
}
