/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import print.color.Ansi.Attribute;
import print.color.Ansi.BColor;
import print.color.Ansi.FColor;
import print.color.ColoredPrinter;

public class ConsoleUtils {
	public static boolean quiet = false;
	public static boolean ansi = true;
	
	private static ColoredPrinter cp = new ColoredPrinter.Builder(0, false).build();
	
	public static void display(String value) {
		if (!quiet)
			if (ansi) {
				cp.println(value);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}
	
	public static void displayWarning(String value) {
		if (!quiet)
			if (ansi) {
				cp.println("[WARNING]\t" + value + "\n", Attribute.NONE, FColor.YELLOW, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}
	
	public static void displayDebug(String value) {
		if (!quiet && Harmony.debug)
			if (ansi) {
				cp.println("[DEBUG]\t" + value + "\n", Attribute.NONE, FColor.BLUE, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[DEBUG]\t" + value);
			}
	}
	
	public static void displayError(String value) {
		if (!quiet) 
			if (ansi) {
				cp.println("[ERROR]\t" + value + "\n", Attribute.NONE, FColor.RED, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println("[ERROR]\t" + value);
			}
	}

	public static void displayLicence(String value) {
		if (!quiet)
			if (ansi) {
				cp.println(value + "\n", Attribute.BOLD, FColor.GREEN, BColor.BLACK);
				cp.clear();
			} else {
				System.out.println(value);
			}
	}
	
	
	public static class ProcessToConsoleBridge{
		InputBridge in;
		OutputBridge out;
		
		public ProcessToConsoleBridge(Process proc){
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
		
		private class InputBridge extends Thread{
			private BufferedReader processInput;
			private BufferedReader processError;
			private boolean isRunning = false;
			
			public InputBridge(Process proc){
				this.processInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				this.processError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			}
			
			public void run() {
				while(this.isRunning){
					try{
						if(this.processInput.ready()){
							String input  = this.processInput.readLine();
							if(input!=null && !input.isEmpty())
								ConsoleUtils.display(input);
						}else if(this.processError.ready()){
							String error = this.processError.readLine();
							if(error!=null && !error.isEmpty())
								ConsoleUtils.displayError(error);
						}else{
							Thread.sleep(200);
						}
					}catch(InterruptedException e){
						ConsoleUtils.displayError(e.getMessage());
					}catch(IOException e){
						ConsoleUtils.displayError(e.getMessage());
					}
				}
				try {
					this.processInput.close();
					this.processError.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		private class OutputBridge extends Thread{
			private BufferedReader consoleInput;
			private BufferedWriter processOutput;
			private boolean isRunning = false;
			
			public OutputBridge(Process proc){
				this.processOutput = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
				this.consoleInput = new BufferedReader(new InputStreamReader(System.in));
			}
			
			public void run() {
				while(this.isRunning){
					try {
						/*while(!this.consoleInput.ready()){
							if(!this.isRunning)
								break;
						}*/
						String output = null;
						if(this.consoleInput.ready()){
								output = this.consoleInput.readLine();
							if(output!=null && !output.isEmpty())
								this.processOutput.write(output);
						}else{
							Thread.sleep(200);
						}
					} catch (IOException e) {
						ConsoleUtils.displayError(e.getMessage());
					} catch (InterruptedException e) {
						ConsoleUtils.displayError(e.getMessage());
					}
					
				}
				try {
					this.consoleInput.close();
					this.processOutput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
