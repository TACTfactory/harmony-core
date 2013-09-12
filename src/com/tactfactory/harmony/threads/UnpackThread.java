package com.tactfactory.harmony.threads;

import java.io.File;
import java.io.IOException;

import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Thread used for Unpacking a TGZ file.
 * Use OnUnpackedFinishedListener to know when the unpack is finished.
 */
public class UnpackThread extends Thread {
	/** Listener for unpacking finish. */
	public interface OnUnpackedFinishedListener {
		/**
		 * Called when file is unpacked.
		 * @param unpackedFile The unpacked file.
		 * @param folder The folder where it has been unpacked.
		 */
		void onUnpackedFinished(File unpackedFile, File folder);
	}
	/** File to unpack. */
	private File file;
	/** Destination folder. */
	private File destFile;
	/** Listener. */
	private OnUnpackedFinishedListener listener;
	/** Archive Format. */
	private ArchiveFormat archiveFormat;
	/** Compression type. */
	private CompressionType compressionType;

	/**
	 * Constructor.
	 * @param listener The listener
	 * @param filePath The path to the file to unpack.
	 * @param destPath The destination folder path
	 * @param archiveFormat The archive format
	 */
	public UnpackThread(
			final OnUnpackedFinishedListener listener,
			final String filePath,
			final String destPath,
			final ArchiveFormat archiveFormat) {
		
		this(listener, filePath, destPath, archiveFormat, null);
	}
	
	/**
	 * Constructor.
	 * @param listener The listener
	 * @param filePath The path to the file to unpack.
	 * @param destPath The destination folder path
	 * @param archiveFormat The archive format
	 * @param compressionType The compression type (can be null)
	 */
	public UnpackThread(
			final OnUnpackedFinishedListener listener,
			final String filePath,
			final String destPath,
			final ArchiveFormat archiveFormat,
			final CompressionType compressionType) {
		super();
		this.file = new File(filePath);
		this.destFile = new File(destPath);
		this.listener = listener;
		this.archiveFormat = archiveFormat;
		this.compressionType = compressionType;

		if (!this.destFile.exists()) {
			if (!this.destFile.mkdir()) {
				ConsoleUtils.displayError(new Exception(
						"ERROR : Output folder couldn't be created."));
			}
		}
	}

	/**
	 * Called at thread start.
	 */
	private void onStart() {
		System.out.println("Unpacking " + this.file.getName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		super.run();
		this.onStart();
		final Archiver archiver; 
		if (this.compressionType != null) {
			archiver = ArchiverFactory.createArchiver(
				this.archiveFormat, 
				this.compressionType);
		} else {
			archiver = ArchiverFactory.createArchiver(
					this.archiveFormat);
		}
		
		try {
			archiver.extract(this.file, this.destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
		
		this.onFinished();
	}

	/**
	 * Called when thread is finished.
	 */
	private void onFinished() {
		System.out.println(this.file.getName()
				+ " has been unpacked successfully");

		if (this.listener != null) {
			this.listener.onUnpackedFinished(this.file, this.destFile);
		}
	}
}
