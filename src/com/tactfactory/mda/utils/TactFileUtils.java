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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/** Manipulate File tools. */
public abstract class TactFileUtils extends FileUtils {
	/** Default encoding for stream manipulation. */
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/** Buffer size for file manipulation. */
	private static final int BUFFER_SIZE = 1024;
	
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
		final byte[] buf = new byte[BUFFER_SIZE];
		int len;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2, true);

			// For Overwrite the file.
			out = new FileOutputStream(destFile);
			len = in.read(buf);
			while (len > 0) {
				out.write(buf, 0, len);
				len = in.read(buf);
			}
			
			// Debug Log
			ConsoleUtils.displayDebug("File "
						 + srcFile.getName()
						 + " copied to "
						 + destFile.getPath());
			
		} catch (final FileNotFoundException ex) {
			ConsoleUtils.displayError(ex);
			
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
			
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
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
		DataInputStream in = null;

		try {
			final byte[] buffer = new byte[(int) file.length()];
			in = new DataInputStream(new FileInputStream(file));
			in.readFully(buffer);
			result = new String(buffer, TactFileUtils.DEFAULT_ENCODING);
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString",
					e);
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

	/** convert file content to a stringbuffer.
	 * 
	 * @param file The File to open
	 * @return the StringBuffer containing the file's content 
	 */
	public static StringBuffer fileToStringBuffer(final File file) {
		final StringBuffer result = new StringBuffer();
		String tmp;
		final String ln = System.getProperty("line.separator");
		
		FileInputStream fis = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			in = new InputStreamReader(fis, TactFileUtils.DEFAULT_ENCODING);
			br = new BufferedReader(in);
			while (true) {
				tmp = br.readLine();
				if (tmp == null) {
					break;
				}
				result.append(tmp);
				result.append(ln);
			}
			
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (in != null) {
					in.close();
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
		DataInputStream in = null;
		BufferedReader br = null;

		try {
			result = new ArrayList<String>();
			in = new DataInputStream(new FileInputStream(file));
			br = new BufferedReader(
					new InputStreamReader(in, TactFileUtils.DEFAULT_ENCODING));
			line = br.readLine(); 
			while (line != null) {
				result.add(line);
				line = br.readLine();
			}
		} catch (final IOException e) {
			throw new RuntimeException("IO problem in fileToString",
					e);
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

			for (String s : strings) {
				br.write(s);
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
		if (dir.exists()) {
			if (dir.isDirectory()) {
				//it's a directory, list files and call deleteDir on a dir
				for (final File f : dir.listFiles()) {
					if (f.isDirectory()) {
						ret = TactFileUtils.deleteRecursive(f, ret);
					} else {
						if (!f.delete()) {
							ret++;
							
							ConsoleUtils.displayWarning(FILE
									 + f.getPath()
									 + "' delete ERROR!");
						}
					}
				}
				
				//folder content check, remove folder
				if (dir.listFiles().length == 0) {
					if (dir.delete()) {
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
		return ret;
	}
	
	/**
	 * Tests if file exists.
	 * @param filename The file name
	 * @return true if the file exists
	 */
	public static boolean exists(final String filename) {
		final File f = new File(filename);
		if (f.exists()) {
			ConsoleUtils.displayDebug("File "
					 + filename
					 + " already exists !");
		} else { 
			ConsoleUtils.displayDebug("File "
						 + filename
						 + " doesn't exists !");
		}
		return f.exists();
	}
	
	/**
	 * Get the extension of a file.
	 * @param f The file
	 * @return The file's extension
	 */  
	public static String getExtension(final File f) {
	    String ext = null;
	    final String s = f.getName();
	    final int i = s.lastIndexOf('.');

	    if (i > 0 &&  i < s.length() - 1) {
	        ext = s.substring(i + 1).toLowerCase();
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
		final StringBuffer sb = TactFileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if (sb.indexOf(content) == -1) {
			final int offset = sb.length(); 
			sb.insert(offset, content);
			TactFileUtils.stringBufferToFile(sb, file);
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
		final StringBuffer sb = TactFileUtils.fileToStringBuffer(file);
		//If content doesn't exists in the file yet
		if (sb.indexOf(content) == -1) {
			final int offset = sb.indexOf(after) + after.length();
			sb.insert(offset, content);
			TactFileUtils.stringBufferToFile(sb, file);
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
		
		File abs = new File(absolute);
		File workingDir = new File(relative);
		
		URI resultString = workingDir.toURI().relativize(abs.toURI());
		
		if (!resultString.toString().equals("")) {
			result = resultString.toString();
		}
		
		if (result.startsWith("file:")) {
			result = result.substring("file:".length());
			
		}
		
		return result;
	}
}
