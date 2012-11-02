/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

import java.io.*;
import java.util.List;

public class SystemCommand
{
	private List<String> commandArgs;
	private ThreadedStreamHandler inputStreamHandler;
	private ThreadedStreamHandler errorStreamHandler;
	private int execResult;
	
	/**
	 * Pass in the system command you want to run as a List of Strings, as shown here:
	 * 
	 * List<String> commands = new ArrayList<String>();
	 * commands.add("/sbin/ping");
	 * commands.add("-c");
	 * commands.add("5");
	 * commands.add("www.google.com");
	 * SystemCommand command = new SystemCommand(commands);
	 * command.executeCommand();
	 * @param commandArgs The command you want to run.
	 */
	public SystemCommand(final List<String> commandArgs)
	{
		this.commandArgs = commandArgs;
	}
	
	public SystemCommand(){
		
	}

	public void executeCommand()
	{
		int exitValue = -99;

		try
		{
			ProcessBuilder pb = new ProcessBuilder(commandArgs);
			Process process = pb.start();

			OutputStream stdOutput = process.getOutputStream();

			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();

			inputStreamHandler = new ThreadedStreamHandler(inputStream, stdOutput);
			errorStreamHandler = new ThreadedStreamHandler(errorStream);

			inputStreamHandler.start();
			errorStreamHandler.start();

			exitValue = process.waitFor();
 
			inputStreamHandler.interrupt();
			errorStreamHandler.interrupt();
			inputStreamHandler.join();
			errorStreamHandler.join();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			setExecResult(exitValue);
		}
	}


	public void setExecResult(int exitValue)
	{
		this.execResult = exitValue;
	}

	public int getExecResult()
	{
		return execResult;
	}
	
	/**
	 * @return the commandArgs
	 */
	public final List<String> getCommandArgs() {
		return commandArgs;
	}

	/**
	 * @param commandArgs the commandArgs to set
	 */
	public final void setCommandArgs(List<String> commandArgs) {
		this.commandArgs = commandArgs;
	}

	
	/**
	 * Get the standard output (stdout) from the command you just exec'd.
	 */
	public StringBuilder getStandardOutputFromCommand()
	{
		return inputStreamHandler.getOutputBuffer();
	}

	/**
	 * Get the standard error (stderr) from the command you just exec'd.
	 */
	public StringBuilder getStandardErrorFromCommand()
	{
		return errorStreamHandler.getOutputBuffer();
	}
}