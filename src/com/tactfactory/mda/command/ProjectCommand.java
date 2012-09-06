/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.IosAdapter;
import com.tactfactory.mda.plateforme.RimAdapter;
import com.tactfactory.mda.plateforme.WinphoneAdapter;

public class ProjectCommand extends BaseCommand {
	public static String INIT_ANDROID = "project:init:android";
	public static String INIT_IOS = "project:init:ios";
	public static String INIT_RIM = "project:init:rim";
	public static String INIT_WINPHONE = "project:init:winphone";
	public static String INIT_ALL = "project:init:all";

	protected BaseAdapter adapterAndroid = new AndroidAdapter();
	protected BaseAdapter adapterIOS = new IosAdapter();
	protected BaseAdapter adapterRIM = new RimAdapter();
	protected BaseAdapter adapterWinPhone = new WinphoneAdapter();
	
	private static boolean isProjectInit = false;

	public void initProjectParam()
	{
		if(!isProjectInit) {
			isProjectInit = true;
			
			//Prompt project parameters to user
			String projectName = Harmony.getUserInput("Please enter your Project Name [toto]:");
			if(projectName!=null)
				Harmony.projectName = projectName;
			String projectNameSpace = Harmony.getUserInput("Please enter your Project NameSpace [my.name.space]:");
			if(projectName!=null)
				Harmony.projectNameSpace = projectNameSpace;
			String sdkDir = Harmony.getUserInput("Please enter AndroidSDK full path [/root/androidsdk/]:");
			if(projectName!=null){
				
			}
		}
	}
	
	public boolean initAndroid()
	{
		System.out.println("\nInit Project Google Android");
		System.out.println("---------------------------\n");
		
		this.initProjectParam();
		boolean result = false;
		if(this.adapterAndroid.makeProject()){
			System.out.println("Init Android Project Success!");
			result = true;
		} else {
			System.out.println("Init Android Project Fail!");
		}
		return result;
	}

	public boolean initIOS()
	{
		System.out.println("\nInit Project Apple iOS");
		System.out.println("----------------------\n");

		this.initProjectParam();
		boolean result = false;
		if(this.adapterIOS.makeProject()){
			System.out.println("Init IOS Project Success!");
			result = true;
		} else {
			System.out.println("Init IOS Project Fail!");
		}
		return result;
	}

	public boolean initRIM()
	{
		System.out.println("\nInit Project BlackBerry RIM");
		System.out.println("---------------------------\n");

		this.initProjectParam();
		boolean result = false;
		if(this.adapterRIM.makeProject()){
			System.out.println("Init RIM Project Success!");
			result = true;
		} else {
			System.out.println("Init RIM Project Fail!");
		}
		return result;
	}

	public boolean initWinPhone()
	{
		System.out.println("\nInit Project Windows Phone");
		System.out.println("--------------------------\n");

		this.initProjectParam();
		boolean result = false;
		if(this.adapterAndroid.makeProject()){
			System.out.println("Init WinPhone Project Success!");
			result = true;
		} else {
			System.out.println("Init WinPhone Project Fail!");
		}
		return result;
	}

	public void initAll()
	{
		System.out.println("\nInit All Projects");
		System.out.println("-----------------\n");
		this.initProjectParam();
		this.initAndroid();
		this.initIOS();
		//this.initRIM();
		//this.initWinPhone();		
	}
	
	@Override
	public void summary() {
		System.out.print("\n> Project\n");
		System.out.print("project:init:android\t => Init Android project directory\n");
		System.out.print("project:init:ios\t\t => Init Apple IOS project directory\n");
		System.out.print("project:init:rim\t\t => Init BlackBerry project directory\n");
		System.out.print("project:init:winphone\t => Init Windows Phone project directory\n");
		System.out.print("project:init:all\t\t => Init All project directories\n");
	}

	@Override
	public void execute(String action, ArrayList<CompilationUnit> entities) {
		
		if (action.equals(INIT_ANDROID)) {
			this.initAndroid();
		} else

		if (action.equals(INIT_IOS)) {
			this.initIOS();
		} else
			
		if (action.equals(INIT_RIM)) {
			this.initRIM();
		} else
			
		if (action.equals(INIT_WINPHONE)) {
			this.initWinPhone();
		} else
			
		if (action.equals(INIT_ALL)) {
			this.initAll();
		} else
			
		{
			
		}
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(INIT_ANDROID) ||
				command.equals(INIT_IOS) ||
				//command.equals(INIT_RIM) ||
				//command.equals(INIT_WINPHONE) ||
				command.equals(INIT_ALL)
				);
	}

}