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
public class UnpackTGZThread extends Thread {
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

	/**
	 * Constructor.
	 * @param listener The listener
	 * @param filePath The path to the file to unpack.
	 * @param destPath The destination folder path
	 */
	public UnpackTGZThread(
			final OnUnpackedFinishedListener listener,
			final String filePath,
			final String destPath) {
		super();
		this.file = new File(filePath);
		this.destFile = new File(destPath);
		this.listener = listener;

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
	public void run() {
		super.run();
		this.onStart();

		final Archiver archiver = ArchiverFactory.createArchiver(
				ArchiveFormat.TAR,
				CompressionType.GZIP);
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
