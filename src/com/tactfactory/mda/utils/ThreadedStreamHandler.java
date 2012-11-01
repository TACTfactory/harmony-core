package com.tactfactory.mda.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


class ThreadedStreamHandler extends Thread {
	InputStream inputStream;
	String adminPassword;
	OutputStream outputStream;
	PrintWriter printWriter;
	StringBuilder outputBuffer = new StringBuilder();
	//private boolean sudoIsRequested = false;

	ThreadedStreamHandler(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * @param inputStream
	 * @param streamType
	 * @param outputStream
	 * @param adminPassword
	 */
	ThreadedStreamHandler(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.printWriter = new PrintWriter(outputStream);
	}
	
	public void run() {
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				outputBuffer.append(line + "\n");
			}
		} catch (IOException ioe) {
			// TODO handle this better; users won't want the code doing this
			ioe.printStackTrace();
		} catch (Throwable t) {
			// TODO handle this better; users won't want the code doing this
			t.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// ignore this one
			}
		}
	}
	
	private void doSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public StringBuilder getOutputBuffer() {
		return outputBuffer;
	}
}
	