package com.tactfactory.harmony.utils;

import java.io.File;
import java.io.IOException;

import com.tactfactory.harmony.threads.DownloadFileThread;
import com.tactfactory.harmony.threads.UnpackTGZThread;
import com.tactfactory.harmony.threads.DownloadFileThread.OnDownloadFinishedListener;
import com.tactfactory.harmony.threads.UnpackTGZThread.OnUnpackedFinishedListener;

/**
 * SDK Utils.
 */
public class SDKUtils {

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
			new UnpackTGZThread(SDKUtils.unpackListener,
					f.getAbsolutePath(),
					f.getParent()).start();

		}
	};

	/**
	 * Download and install Android SDK to destPath.
	 * @param destPath The path where to install the android sdk.
	 */
	public static void downloadAndInstallAndroidSDK(final String destPath) {
		try {
			File destFolder = new File(destPath + "/android-sdk_r22.0.5-linux.tgz");
			destFolder.createNewFile();

			new DownloadFileThread(SDKUtils.downListener,
				"http://dl.google.com/android/android-sdk_r22.0.5-linux.tgz",
				destFolder.getAbsolutePath()).start();
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}

	}
}
