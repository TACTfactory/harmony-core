package com.tactfactory.harmony.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Thread used to download a file from the internet.
 */
public class DownloadFileThread extends Thread {
	/**
	 * Download finish listener.
	 */
	public interface OnDownloadFinishedListener {
		/**
		 * Called when the download is finished.
		 * @param f The file which has been downloaded.
		 */
		void onDownloadFinished(File f);
	}

	/** URL of the file to download. */
	private String url;
	/** Destination path. */
	private String destPath;
	/** Listener. */
	private OnDownloadFinishedListener listener;

	/**
	 * Constructor.
	 * @param listener The listener
	 * @param url The URL of the file to download.
	 * @param destPath The destination path.
	 */
	public DownloadFileThread(OnDownloadFinishedListener listener,
			String url,
			String destPath) {
		this.url = url;
		this.destPath = destPath;
		this.listener = listener;
	}

	/** Called on thread start. */
	private void onStart() {
		System.out.println("Starting download");
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		this.onStart();
		
		final int bufferSize = 1024;
		final int percentsMax = 100;

		FileOutputStream output = null;
		URL inUrl;
		try {
			inUrl = new URL(this.url);
			URLConnection connection = inUrl.openConnection();

			int fileLength = connection.getContentLength();
			InputStream input = connection.getInputStream();

			File f = new File(this.destPath);
			output = new FileOutputStream(f);
			byte[] buffer = new byte[bufferSize];
			int read;
			int totalRead = 0;
			while ((read = input.read(buffer)) > 0) {
				output.write(buffer, 0, read);
				totalRead += (int) read;
				this.onProgress((totalRead) / (fileLength / percentsMax));
			}

			this.onFinished();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (this.listener != null) {
				this.listener.onDownloadFinished(new File(this.destPath));
			}
		}
	}

	/**
	 * Called on thread progress.
	 * @param progress The progress in %.
	 */
	public void onProgress(int progress) {
		System.out.print("\rProgress : " + progress + "%");
	}

	/** Called on thread finished. */
	private void onFinished() {
		System.out.println("\nDownload successful");
	}
}
