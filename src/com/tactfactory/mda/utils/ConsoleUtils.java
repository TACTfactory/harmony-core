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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.common.base.Strings;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

/**
 * Utility class for console.
 */
public abstract class ConsoleUtils {
	
	/** Debug state. */
	private static boolean isDebug;
	
	/** Quiet state. */
	private static boolean isQuiet;
	
	/** ANSI state (used for colors in console or not). */
	private static boolean isAnsi = true;
	
	/** Console state. */
	private static boolean isConsole;
	
	/** Newline character. */
	private static final char NEWLINE = '\n';
	
	/** Tab character. */
	private static final char TAB = '\t';
	
	/** Sleep Time for bridge threads. */
	private static final int SLEEP_TIME = 200;
	
	/** Printer for colors. */
	private static ColoredPrinter cp =
			new ColoredPrinter.Builder(0, false).build();
	
	
	/** Is application in debug mode ?
	 * @return true if application is in debug mode
	 */
	public static boolean isDebug() {
		return isDebug;
	}

	/**
	 * Set application in debug mode.
	 * @param debug Debug mode
	 */
	public static void setDebug(final boolean debug) {
		ConsoleUtils.isDebug = debug;
	}
	
	/** Is application in quiet mode ?
	 * @return true if application is in quiet mode
	 */
	public static boolean isQuiet() {
		return isQuiet;
	}

	/**
	 * Set application in quiet mode.
	 * @param quiet Quiet mode
	 */
	public static void setQuiet(final boolean quiet) {
		ConsoleUtils.isQuiet = quiet;
	}

	/** Is application in ANSI mode ?
	 * @return true if application is in ANSI mode
	 */
	public static boolean isAnsi() {
		return isAnsi;
	}

	/**
	 * Set application in ANSI mode.
	 * @param ansi ANSI mode
	 */
	public static void setAnsi(final boolean ansi) {
		ConsoleUtils.isAnsi = ansi;
	}
	
	/** Is application in console mode ?
	 * @return true if application is in console mode
	 */
	public static boolean isConsole() {
		return isConsole;
	}

	/**
	 * Set application in console mode.
	 * @param console Console mode
	 */
	public static void setConsole(final boolean console) {
		ConsoleUtils.isConsole = console;
	}
	
	
	/**
	 * Display given String to the console.
	 * (White color)
	 * @param value The String to display
	 */
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
	
	/**
	 * Display given String to the console prefixed by [WARNING].
	 * (Yellow color)
	 * @param value The String to display
	 */
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
	
	/**
	 * Display given String to the console prefixed by [DEBUG].
	 * (Blue color)
	 * @param value The String to display
	 */
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
	
	/**
	 * Converts a StackTrace to a String.
	 * @param stackTraceElements 
	 * @return The StrackTrace
	 */
	private static String getStackTrace(
			final StackTraceElement[] stackTraceElements) {
		final StringBuilder result = new StringBuilder();

		for (final StackTraceElement stackTraceElement : stackTraceElements) {
			result.append(stackTraceElement.toString()).append(NEWLINE);
		}
		
		return result.toString();
	}
	
	/**
	 * Display given Exception to the console prefixed by [ERROR].
	 * (Red color)
	 * @param value The Exception to display
	 */
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

	/**
	 * Display given String to the console.
	 * (Green color)
	 * @param value The String to display
	 */
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
	
	/**
	 * Launch the given command.
	 * 
	 * @param command The list containing the command and its arguments
	 * 	to execute
	 * @return The exception throwed by the launched command.
	 * null if everything has gone well
	 */
	public static Exception launchCommand(final List<String> command) {
		return launchCommand(command, null);
	}
	
	/**
	 * Bridge used for transmitting input and output between an external process
	 * and the console.
	 * @param command The list containing the command and its arguments 
	 * to execute
	 * @param commandPath The path where to launch the command
	 * @return The exception throwed by the launched command.
	 * null if everything has gone well
	 */
	public static Exception launchCommand(
			final List<String> command, final String commandPath) {
		Exception result = null;
		String commandPathDisplay = "";
		if (!Strings.isNullOrEmpty(commandPath)) {
			commandPathDisplay = commandPath; 
		}
		
		ConsoleUtils.displayDebug(commandPathDisplay + command.toString());
		
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			
			if (commandPath != null) {
				pb = pb.directory(new File(commandPath));
			}
			
			final Process exec = pb.start();
			final ProcessToConsoleBridge bridge = 
					new ProcessToConsoleBridge(exec);
			
			bridge.start();
			try {
				exec.waitFor();
			} catch (final InterruptedException e) {
				result = e;
				ConsoleUtils.displayError(e);
			}
			
			bridge.stop();
			
		} catch (final IOException e) {
			result = e;
			ConsoleUtils.displayError(e);
		}
		
		return result;
	}

	/**
	 * Bridge between a process output/input and the console.
	 */
	protected static class ProcessToConsoleBridge {
		
		/** Input thread. */
		private final InputBridge in;
		
		/** Output thread. */
		private final OutputBridge out;
		
		/**
		 * Constructor.
		 * @param proc The process to bridge with the console
		 */
		public ProcessToConsoleBridge(final Process proc) {
			this.in = new InputBridge(proc);
			this.out = new OutputBridge(proc);
		}

		/** Start the threads. */
		public final void start() {
			this.in.start();
			this.out.start();
		}
		
		/** Stop the threads. */
		public final void stop() {
			this.in.terminate();
			this.out.terminate();
		}
		
		/** Input bridge thread. */
		private static class InputBridge extends Thread {
			/** Reader for process input stream. */
			private BufferedReader processInput;
			
			/** Reader for process error stream. */
			private BufferedReader processError;
			
			/** Reader for process input stream. */
			private boolean isRunning;
			
			/**
			 * Constructor.
			 * @param proc The process to bridge with the console
			 */
			public InputBridge(final Process proc) {
				super();
				try {
					this.processInput = 
							new BufferedReader(
									new InputStreamReader(proc.getInputStream(),
											TactFileUtils.DEFAULT_ENCODING));
					this.processError = 
							new BufferedReader(
									new InputStreamReader(proc.getErrorStream(),
											TactFileUtils.DEFAULT_ENCODING));
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
							if (error != null && !error.isEmpty() && !"Note: checking out '4.2.0'.".equals(error)) {
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
			
			/**
			 * Stop thread.
			 */
			public void terminate() {
				this.isRunning = false;
			}

			@Override
			public synchronized void start() {
				this.isRunning = true;
				super.start();
			}
		}
		
		/**
		 * Constructor.
		 * @param proc The process to bridge with the console
		 */		
		private static class OutputBridge extends Thread {
			/** Reader for console input stream. */
			private BufferedReader consoleInput;
			
			/** Writer for console output stream. */
			private BufferedWriter processOutput;
			
			/** Is thread running ? */
			private boolean isRunning;
			
			/**
			 * Constructor.
			 * @param proc The process to bridge with the console
			 */
			public OutputBridge(final Process proc) {
				super();
				try {
					this.processOutput = 
							new BufferedWriter(
									new OutputStreamWriter(
											proc.getOutputStream(),
											TactFileUtils.DEFAULT_ENCODING));
					this.consoleInput = 
							new BufferedReader(
									new InputStreamReader(System.in,
											TactFileUtils.DEFAULT_ENCODING));
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

			/**
			 * Stop thread.
			 */
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
