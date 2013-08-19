package com.tactfactory.harmony.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.rauschig.jarchivelib.ArchiveFormat;

import com.tactfactory.harmony.threads.DownloadFileThread;
import com.tactfactory.harmony.threads.UnpackThread;
import com.tactfactory.harmony.threads.DownloadFileThread.OnDownloadFinishedListener;
import com.tactfactory.harmony.threads.UnpackThread.OnUnpackedFinishedListener;

/**
 * SDK Utils.
 */
public class AndroidSDKUtils {
	/** Constant for Windows. */
	public static final String WINDOWS = "windows";
	
	/** Constant for Linux. */
	public static final String LINUX = "linux";
	
	/** Constant for MacOS/X. */
	public static final String MAC_OSX = "macosx";

	/** Default Listener for unpacking files. */
	private static final OnUnpackedFinishedListener unpackListener =
			new OnUnpackedFinishedListener() {
		@Override
		public void onUnpackedFinished(File unpackedFile, File folder) {
			unpackedFile.delete();
			ConsoleUtils.display(
					"The Android SDK has been successfuly installed into "
							+ folder.getAbsolutePath());

		}
	};

	/** Default Listener for downloading files. */
	private static final OnDownloadFinishedListener downListener =
			new OnDownloadFinishedListener() {
		@Override
		public void onDownloadFinished(File f) {
			new UnpackThread(AndroidSDKUtils.unpackListener,
					f.getAbsolutePath(),
					f.getParent(),
					ArchiveFormat.ZIP).start();

		}
	};

	/**
	 * Download and install Android SDK to destPath.
	 * @param destPath The path where to install the android sdk.
	 */
	public static void downloadAndInstallAndroidSDK(final String url,
			final String destPath) {
		String destFileName = url.split("/")[url.split("/").length - 1];
		try {
			File destFolder = new File(destPath + "/" + destFileName);
			destFolder.createNewFile();

			new DownloadFileThread(AndroidSDKUtils.downListener,
				url,
				destFolder.getAbsolutePath()).start();
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}

	}
	
	/**
	 * Find the latest SDK Tools link.
	 * @return The latest SDK tools link
	 */
	public static String findLatestSDKToolsLink(final String platform) {
		final String baseUrl = "https://dl-ssl.google.com/android/repository/";
		String result = null;
		final String xmlUrl = baseUrl + "repository-8.xml";
		
		Document document = XMLUtils.getRemoteXML(xmlUrl);
		Element root = document.getRootElement();
		Namespace ns = root.getNamespace("sdk");
		
		Element sdkTool = root.getChild("tool", ns);
		List<Element> sdkArchives = sdkTool.getChild("archives", ns).getChildren();
		
		for (Element sdkArchive : sdkArchives) {
			if (sdkArchive.getAttribute("os").getValue().equals(platform)) {
				result = baseUrl + sdkArchive.getChildText("url",ns);
			}
		}
		
		return result;
	}
}
