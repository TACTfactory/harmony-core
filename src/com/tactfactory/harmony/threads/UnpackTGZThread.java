package com.tactfactory.harmony.threads;

import java.io.File;
import java.io.IOException;

import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

public class UnpackTGZThread extends Thread {
	public interface OnUnpackedFinishedListener {
		public void onUnpackedFinished(File unpackedFile, File folder);
	}
	private File file;
	private File destFile;
	private OnUnpackedFinishedListener listener;
	
	public UnpackTGZThread(
			OnUnpackedFinishedListener listener,
			String filePath, 
			String destPath) {
		this.file = new File(filePath);
		this.destFile = new File(destPath);
		this.listener = listener;
		
		if (!this.destFile.exists()) {
			this.destFile.mkdir();
		}
	}
	
	private void onStart() {
		System.out.println("Unpacking " + this.file.getName());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		this.onStart();
		
		Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
		try {
			archiver.extract(this.file, this.destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.onFinished();
	}
	
	public void onProgress(int progress) {
		System.out.print("\rProgress : " + progress + "%");
	}
	
	private void onFinished() {
		System.out.println(this.file.getName() 
				+ " has been unpacked successfully");
		
		if (this.listener != null) {
			this.listener.onUnpackedFinished(this.file, this.destFile);
		}
	}
}
