package com.tactfactory.mda.android.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

public abstract class Command {

	public abstract void execute(String action, ArrayList<CompilationUnit> entities);

	public abstract void summary();
	
	public abstract boolean isAvailableCommand(String command);

}