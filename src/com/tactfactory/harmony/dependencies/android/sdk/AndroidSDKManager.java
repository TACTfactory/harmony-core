package com.tactfactory.harmony.dependencies.android.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.rauschig.jarchivelib.ArchiveFormat;

import com.google.common.base.Joiner;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.threads.DownloadFileThread;
import com.tactfactory.harmony.threads.UnpackThread;
import com.tactfactory.harmony.threads.DownloadFileThread.OnDownloadFinishedListener;
import com.tactfactory.harmony.threads.UnpackThread.OnUnpackedFinishedListener;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;

/**
 * Manager class for Android SDK.
 */
public class AndroidSDKManager 
		implements OnUnpackedFinishedListener, OnDownloadFinishedListener {
	
	/**  Google SDK download URL. */
	public static final String SDK_URL = 
			"https://dl-ssl.google.com/android/repository/";
	
	/**  Google SDK download XML repository file. */
	public static final String XML_REPO_FILE = 
			"repository-8.xml";
	
	/** Constant for Windows. */
	public static final String WINDOWS = "windows";
	
	/** Constant for Linux. */
	public static final String LINUX = "linux";
	
	/** Constant for MacOS/X. */
	public static final String MAC_OSX = "macosx";

	/**
	 * Install the Android SDK for the given OS to the given path.
	 * @param OS The OS 
	 * @param path The path (Can be either "windows", "linux" or "macosx")
	 * @return True if installed occurred correctly.
	 */
	/*public boolean installSDKTo(String OS, String path) {
		boolean result = false;
		String distantSDKUrl = this.findLatestSDKToolsLink(OS);
		this.downloadAndInstallAndroidSDK(distantSDKUrl, path);
		result = AndroidSDKManager.checkIfAndroidSDKExists(path);
		return result;
	}*/
	
	/**
	 * Download and install Android SDK to destPath.
	 * 
	 * @param url The url of the android sdk
	 * @param destPath The path where to install the android sdk.
	 */
	public final void downloadAndInstallAndroidSDK(final String url,
			final String destPath) {
		String destFileName = url.split("/")[url.split("/").length - 1];
		try {
			File destination = new File(destPath);
			final String yes = "y";
			final String no = "n";
			
			if (!destination.exists()) {	
				String userInput = ConsoleUtils.getValidUserInput(
						"Folder " 
								+ destination.getAbsolutePath()
								+ " doesn't exists. "
								+ "Do you want to create it ? [y/n]",
						yes,
						no);
				
				if (userInput.equals(yes)) {
					destination.mkdirs();
				} else {
					ConsoleUtils.display("Aborting.");
					return;
				}
			} else {
				String userInput = ConsoleUtils.getValidUserInput(
						"Folder " 
								+ destination.getAbsolutePath()
								+ " already exists. "
								+ "Are you sure you want to install "
								+ "in this folder ?"
								+ " (All existing files will be overwritten)"
								+ " [y/n]",
						yes,
						no);
				
				if (userInput.equals(no)) {
					ConsoleUtils.display("Aborting.");
					return;
				}
			}
			File destFolder = new File(destPath + "/" + destFileName);
			destFolder.createNewFile();

			DownloadFileThread thread = new DownloadFileThread(
				this,
				url,
				destFolder.getAbsolutePath());
			thread.start();
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}

	}
	
	/**
	 * Find the latest SDK Tools link.
	 * 
	 * @param platform The user platform
	 * @return The latest SDK tools link
	 */
	public final String findLatestSDKToolsLink(final String platform) {
		String result = null;
		
		Document document = XMLUtils.getRemoteXML(SDK_URL + XML_REPO_FILE);
		Element root = document.getRootElement();
		Namespace ns = root.getNamespace("sdk");
		
		Element sdkTool = root.getChild("tool", ns);
		List<Element> sdkArchives = 
				sdkTool.getChild("archives", ns).getChildren();
		
		for (Element sdkArchive : sdkArchives) {
			if (sdkArchive.getAttribute("os").getValue().equals(platform)) {
				result = SDK_URL + sdkArchive.getChildText("url", ns);
			}
		}
		
		return result;
	}	
	
	/**
	 * Init SDK List and install dependencies.
	 * @param sdkPath The sdk path
	 */
	public final void initSDKList(final String sdkPath) {
		try {
			final File f = new File(sdkPath + "tools/android");
			// TODO : Set executable permissions for all executable files
			f.setExecutable(true); 
			
			
			final Runtime runtime = Runtime.getRuntime();
			final Process process = 
					runtime.exec(sdkPath + "tools/android list sdk --extended");
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			process.getErrorStream().close();
			process.getOutputStream().close();
			
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			reader.close();
			
			final String inputString = builder.toString();
			final AndroidSDKList list = new AndroidSDKList();
			list.parseString(inputString);
			
			String id;
			final ArrayList<String> ids = list.getIdsLikeName("tools");
			/*id = list.getIdByName("platform-tools");
			if (id != null) {
				ids.add(id);
			}*/
			id = list.getIdByName("android-17");
			if (id != null) {
				ids.add(id);
			}
			id = list.getIdByName("android-10");
			if (id != null) {
				ids.add(id);
			}
			ids.add("extra");
			
			
			
			this.installSDKDependencies(sdkPath, ids);
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Install the given list of SDK dependencies. 
	 * @param sdkPath The path to the android sdk
	 * @param dependencyList The dependency list (ids)
	 */
	public final void installSDKDependencies(
			final String sdkPath, 
			final ArrayList<String> dependencyList) {
		
		//try {
		final String commandArgs = Joiner.on(',').join(dependencyList);
		final ArrayList<String> command = new ArrayList<String>();
		command.add("./android");
		command.add("update");
		command.add("sdk");
		command.add("-t");
		command.add(commandArgs);
		command.add("--no-ui");
		
		ConsoleUtils.launchCommand(command, sdkPath + "tools/");
	}

	@Override
	public final void onDownloadFinished(final File f) {
		new UnpackThread(
				this,
				f.getAbsolutePath(),
				f.getParent(),
				ArchiveFormat.ZIP).start();

	}

	@Override
	public final void onUnpackedFinished(final File unpackedFile,
			final File folder) {
		unpackedFile.delete();
		ConsoleUtils.display(
				"The Android SDK has been successfuly installed into "
						+ folder.getAbsolutePath());
		
		this.initSDKList(folder.getAbsolutePath() + "/");
		
	}
	
	/**
	 * Check if sdk exists at given path.
	 * @param sdkPath The supposed sdk path
	 * @return True if exists. false otherwise
	 */
	public static final boolean checkIfAndroidSDKExists(final String sdkPath) {
		boolean result = false;
		File file = new File(sdkPath + "/tools/android");
		result = file.exists();
		if (!result) {
			file = new File(sdkPath + "/tools/android.bat");
			result = file.exists();
		}
		return result;
	}
	
	/**
	 * Copy support v4 jar into the given folder.
	 * @param destFolder The folder where to copy the support v4 library
	 */
	public static final void copySupportV4Into(final String destFolder) {
		// Replace android support v4 with the one from android sdk.
		final File sdkFolder = 
				new File(ApplicationMetadata.getAndroidSdkPath());
		if (sdkFolder.exists()) {
			final File supportV4SDKOld = 
					new File(sdkFolder.getAbsolutePath() 
							+ "/extras/android/compatibility/v4/"
							+ "android-support-v4.jar");
			
			final File supportV4SDKNew = 
					new File(sdkFolder.getAbsolutePath() 
							+ "/extras/android/support/v4/"
							+ "android-support-v4.jar");
			
			final File supportV4Menu = 
					new File(destFolder + "android-support-v4.jar");
			
			if (supportV4SDKNew.exists()) {
				TactFileUtils.copyfile(supportV4SDKNew, supportV4Menu);
				ConsoleUtils.display("Support V4 copied successfuly.");
			} else if (supportV4SDKOld.exists()) {
				TactFileUtils.copyfile(supportV4SDKOld, supportV4Menu);
				ConsoleUtils.display("Support V4 copied successfuly.");
			} else {
				ConsoleUtils.displayWarning(
						"SDK extras : android-support-v4 not found.");
			}
		} else {
			ConsoleUtils.displayWarning("SDK not found.");
		}
	}
}
