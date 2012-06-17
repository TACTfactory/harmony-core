package com.tactfactory.mda.android.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

public class FosCommand extends Command {

	public void fosUserCreate() {
	}

	public void fosUserActivate(String login) {
	}

	public void fosUserDeactivate() {
	}

	public void fosUserChangepassword() {
	}

	public void fosUserDemote(Object role) {
	}

	public void fosUserPromote(Object role) {
	}

	@Override
	public void summary() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String action, ArrayList<CompilationUnit> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

}