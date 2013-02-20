/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

public abstract class ConsoleUtils {
	/** Debug state*/
	public static boolean debug;
	public static boolean quiet;
	public static boolean ansi = true;
	private static char newLine = '\n';
	private static char tab = '\t';
	
	private static ColoredPrinter cp = new ColoredPrinter.Builder(0, false).build();
	
	public static void display(final String value) {
		if (!quiet) {
			if (ansi) {
				cp.println(value);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	public static void displayWarning(final String value) {
		if (!quiet) {
			if (ansi) {
				cp.println("[WARNING]" + tab + value + newLine, Attribute.NONE, FColor.YELLOW, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	public static void displayDebug(final String value) {
		if (!quiet && ConsoleUtils.debug) {
			if (ansi) {
				cp.println("[DEBUG]" + tab + value + newLine, Attribute.NONE, FColor.BLUE, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[DEBUG]" + tab + value);
			}
		}
	}
	
	private static String getStackTrace(final StackTraceElement[] stackTraceElements) {
		final StringBuilder result = new StringBuilder();

		for (final StackTraceElement stackTraceElement : stackTraceElements) {
			result.append(stackTraceElement.toString()).append(newLine);
		}
		
		return result.toString();
	}
	
	public static void displayError(final Exception value) {
		if (!quiet) {
			if (ansi) {
				cp.println("[ERROR]" + tab + value + newLine + getStackTrace(value.getStackTrace()) + newLine, Attribute.NONE, FColor.RED, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[ERROR]" + tab + value + newLine + getStackTrace(value.getStackTrace()) + newLine);
			}
		}
	}

	public static void displayLicence(final String value) {
		if (!quiet) {
			if (ansi) {
				cp.println(value + newLine, Attribute.BOLD, FColor.GREEN, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	
	public static void launchCommand(final List<String> command){
		try {
			final ProcessBuilder pb = new ProcessBuilder(command);
			final Process exec = pb.start();
			

			final ProcessToConsoleBridge bridge = new ProcessToConsoleBridge(exec);
			bridge.start();
			try {
				exec.waitFor();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
			
			bridge.stop();
			
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	protected static class ProcessToConsoleBridge {
		private final InputBridge in;
		private final OutputBridge out;
		
		public ProcessToConsoleBridge(final Process proc){
			this.in = new InputBridge(proc);
			this.out = new OutputBridge(proc);
		}
		
		public void start(){
			this.in.start();
			this.out.start();
		}
		
		public void stop(){
			this.in.terminate();
			this.out.terminate();
		}
		
		private static class InputBridge extends Thread{
			private final BufferedReader processInput;
			private final BufferedReader processError;
			private boolean isRunning;
			
			public InputBridge(final Process proc){
				super();
				this.processInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				this.processError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			}
			
			@Override
			public void run() {
				while(this.isRunning){
					try{
						if(!(this.processInput.ready() || this.processError.ready())){
							Thread.sleep(200);
						}
						if(this.processInput.ready()){
							final String input  = this.processInput.readLine();
							if(input!=null && !input.isEmpty()){
								ConsoleUtils.display(input);
							}
						}
						if(this.processError.ready()){
							final String error = this.processError.readLine();
							if(error!=null && !error.isEmpty()){
								ConsoleUtils.displayError(new Exception(error));
							}
						}
					}catch(final InterruptedException e){
						ConsoleUtils.displayError(e);
					}catch(final IOException e){
						ConsoleUtils.displayError(e);
					}
				}
				try {
					this.processInput.close();
					this.processError.close();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					ConsoleUtils.displayError(e);
				}
			}
			

			public void terminate() {
				this.isRunning = false;
			}

			@Override
			public synchronized void start() {
				this.isRunning = true;
				super.start();
			}
		}
		
		private static class OutputBridge extends Thread{
			private final BufferedReader consoleInput;
			private final BufferedWriter processOutput;
			private boolean isRunning;
			
			public OutputBridge(final Process proc){
				super();
				this.processOutput = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
				this.consoleInput = new BufferedReader(new InputStreamReader(System.in));
			}
			
			@Override
			public void run() {
				while(this.isRunning){
					try {
						String output = null;
						if(this.consoleInput.ready()){
							output = this.consoleInput.readLine();
							if(output!=null && !output.isEmpty()){
								this.processOutput.write(output);
							}
						}else{
							Thread.sleep(200);
						}
					} catch (final IOException e) {
						ConsoleUtils.displayError(e);
					} catch (final InterruptedException e) {
						ConsoleUtils.displayError(e);
					}
					
				}
				try {
					this.consoleInput.close();
					this.processOutput.close();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					ConsoleUtils.displayError(e);
				}
			}

			public void terminate() {
				this.isRunning = false;
			}

			@Override
			public synchronized void start() {
				this.isRunning = true;
				super.start();
			}
		}
	}
}
