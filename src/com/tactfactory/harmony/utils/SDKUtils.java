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
public final class SDKUtils {
	
	/**
	 * Private constructor.
	 */
	private SDKUtils() { }

	/** Default Listener for unpacking files. */
	private static final OnUnpackedFinishedListener UNPACK_DEFAULT_LISTENER =
			new OnUnpackedFinishedListener() {
		@Override
		public void onUnpackedFinished(File unpackedFile, File folder) {
			ConsoleUtils.display(
					"The Android SDK has been successfuly installed into "
							+ folder.getAbsolutePath());
			if (!unpackedFile.delete()) {
				ConsoleUtils.display("Temporary file " 
						+ unpackedFile.getAbsolutePath() 
						+ " couldn't be removed. You can remove it manually.");
			}

		}
	};

	/** Default Listener for downloading files. */
	private static final OnDownloadFinishedListener DOWNLOAD_DEFAULT_LISTENER =
			new OnDownloadFinishedListener() {
		@Override
		public void onDownloadFinished(File f) {
			new UnpackTGZThread(SDKUtils.UNPACK_DEFAULT_LISTENER,
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
			File destFolder = new File(destPath 
									+ "/android-sdk_r22.0.5-linux.tgz");
			if (destFolder.createNewFile()) {

				new DownloadFileThread(SDKUtils.DOWNLOAD_DEFAULT_LISTENER,
					"http://dl.google.com/android/android-sdk_r22.0.5-linux.tgz",
					destFolder.getAbsolutePath()).start();
			} else {
				ConsoleUtils.displayError(new Exception(
								"ABORTING : Couldn't create temporary file."));
			}
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}

	}
}
