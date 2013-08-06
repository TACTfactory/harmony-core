package com.tactfactory.harmony.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileThread extends Thread {
	public interface OnDownloadFinishedListener {
		public void onDownloadFinished(File f);
	}
	
	private String url;
	private String destPath;
	private OnDownloadFinishedListener listener;
	
	public DownloadFileThread(OnDownloadFinishedListener listener,
			String url,
			String destPath) {
		this.url = url;
		this.destPath = destPath;
		this.listener = listener;
	}
	
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
		
		FileOutputStream output = null;
		URL inUrl;
		try {
			inUrl = new URL(this.url);
			URLConnection connection = inUrl.openConnection();
			
			int fileLength = connection.getContentLength();
			InputStream input = connection.getInputStream();
			
			File f = new File(this.destPath);
			output = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int read;
			int totalRead = 0;
			while ((read = input.read(buffer)) > 0) {
				output.write(buffer, 0, read);
				totalRead += (int) read;
				this.onProgress((totalRead) / (fileLength / 100));
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
	
	public void onProgress(int progress) {
		System.out.print("\rProgress : " + progress + "%");
	}
	
	private void onFinished() {
		System.out.println("\nDownload successful");
	}
}
