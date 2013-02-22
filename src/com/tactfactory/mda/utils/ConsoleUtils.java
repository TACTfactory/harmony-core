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
import java.io.UnsupportedEncodingException;
import java.util.List;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

public abstract class ConsoleUtils {
	/** Debug state*/
	private static boolean isDebug;
	private static boolean isQuiet;
	private static boolean isAnsi = true;
	private static boolean isConsole;
	
	/** Constants */
	private static final char NEWLINE = '\n';
	private static final char TAB = '\t';
	private static final int SLEEP_TIME = 200;
	
	private static ColoredPrinter cp =
			new ColoredPrinter.Builder(0, false).build();
	
	public static boolean isDebug() {
		return isDebug;
	}

	public static void setDebug(final boolean debug) {
		ConsoleUtils.isDebug = debug;
	}

	public static boolean isQuiet() {
		return isQuiet;
	}

	public static void setQuiet(final boolean quiet) {
		ConsoleUtils.isQuiet = quiet;
	}

	public static boolean isAnsi() {
		return isAnsi;
	}

	public static void setAnsi(final boolean ansi) {
		ConsoleUtils.isAnsi = ansi;
	}
	
	public static boolean isConsole() {
		return isConsole;
	}

	public static void setConsole(final boolean console) {
		ConsoleUtils.isConsole = console;
	}
	
	public static void display(final String value) {
		if (!isQuiet) {
			if (isAnsi) {
				cp.println(value);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	public static void displayWarning(final String value) {
		if (!isQuiet) {
			if (isAnsi) {
				cp.println("[WARNING]" + TAB + value + NEWLINE,
						Attribute.NONE,
						FColor.YELLOW,
						BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	public static void displayDebug(final String value) {
		if (!isQuiet && ConsoleUtils.isDebug()) {
			if (isAnsi) {
				cp.println("[DEBUG]" + TAB + value + NEWLINE,
						Attribute.NONE,
						FColor.BLUE,
						BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[DEBUG]" + TAB + value);
			}
		}
	}
	
	private static String getStackTrace(
			final StackTraceElement[] stackTraceElements) {
		final StringBuilder result = new StringBuilder();

		for (final StackTraceElement stackTraceElement : stackTraceElements) {
			result.append(stackTraceElement.toString()).append(NEWLINE);
		}
		
		return result.toString();
	}
	
	public static void displayError(final Exception value) {
		if (!isQuiet) {
			String message = 	"[ERROR]" 
								 + TAB 
								 + value 
								 + NEWLINE 
								 + getStackTrace(value.getStackTrace()) 
								 + NEWLINE; 
			if (isAnsi) {
				cp.println(message,
						Attribute.NONE,
						FColor.RED,
						BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(message);
			}
		}
	}

	public static void displayLicence(final String value) {
		if (!isQuiet) {
			if (isAnsi) {
				cp.println(value + NEWLINE,
						Attribute.BOLD,
						FColor.GREEN,
						BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
		}
	}
	
	
	public static void launchCommand(final List<String> command) {
		try {
			final ProcessBuilder pb = new ProcessBuilder(command);
			final Process exec = pb.start();
			

			final ProcessToConsoleBridge bridge = 
					new ProcessToConsoleBridge(exec);
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
		
		public ProcessToConsoleBridge(final Process proc) {
			this.in = new InputBridge(proc);
			this.out = new OutputBridge(proc);
		}

		public final void start() {
			this.in.start();
			this.out.start();
		}
		
		public final void stop() {
			this.in.terminate();
			this.out.terminate();
		}
		
		private static class InputBridge extends Thread {
			private BufferedReader processInput;
			private BufferedReader processError;
			private boolean isRunning;
			
			public InputBridge(final Process proc) {
				super();
				try {
					this.processInput = 
							new BufferedReader(
									new InputStreamReader(proc.getInputStream(),
											FileUtils.DEFAULT_ENCODING));
					this.processError = 
							new BufferedReader(
									new InputStreamReader(proc.getErrorStream(),
											FileUtils.DEFAULT_ENCODING));
				} catch (UnsupportedEncodingException e) {
					ConsoleUtils.displayError(e);
				}
			}
			
			@Override
			public void run() {
				while (this.isRunning) {
					try {
						if (!(this.processInput.ready() 
								|| this.processError.ready())) {
							Thread.sleep(SLEEP_TIME);
						}
						if (this.processInput.ready()) {
							final String input  = this.processInput.readLine();
							if (input != null && !input.isEmpty()) {
								ConsoleUtils.display(input);
							}
						}
						if (this.processError.ready()) {
							final String error = this.processError.readLine();
							if (error != null && !error.isEmpty()) {
								ConsoleUtils.displayError(new Exception(error));
							}
						}
					} catch (final InterruptedException e) {
						ConsoleUtils.displayError(e);
					} catch (final IOException e) {
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
		
		private static class OutputBridge extends Thread {
			private BufferedReader consoleInput;
			private BufferedWriter processOutput;
			private boolean isRunning;
			
			public OutputBridge(final Process proc) {
				super();
				try {
					this.processOutput = 
							new BufferedWriter(
									new OutputStreamWriter(
											proc.getOutputStream(),
											FileUtils.DEFAULT_ENCODING));
					this.consoleInput = 
							new BufferedReader(
									new InputStreamReader(System.in,
											FileUtils.DEFAULT_ENCODING));
				} catch (UnsupportedEncodingException e) {
					ConsoleUtils.displayError(e);
				}
			}
			
			@Override
			public void run() {
				while (this.isRunning) {
					try {
						String output = null;
						if (this.consoleInput.ready()) {
							output = this.consoleInput.readLine();
							if (output != null && !output.isEmpty()) {
								this.processOutput.write(output);
							}
						} else {
							Thread.sleep(SLEEP_TIME);
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
