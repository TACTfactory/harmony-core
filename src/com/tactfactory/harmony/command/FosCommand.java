/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

/**
 * FOS Bundle Commands.
 */
public class FosCommand extends BaseCommand {

	/**
	 * Create.
	 */
	public void fosUserCreate() {
	}

	/**
	 * Activate bundle.
	 * @param login The login
	 */
	public void fosUserActivate(final String login) {
	}

	/**
	 * De-activate bundle.
	 */
	public void fosUserDeactivate() {
	}

	/**
	 * Change password.
	 */
	public void fosUserChangepassword() {
	}

	/**
	 * Remote a user.
	 * @param role User's role
	 */
	public void fosUserDemote(final Object role) {
	}

	/**
	 * Promote a user.
	 * @param role User's role
	 */
	public void fosUserPromote(final Object role) {
	}


	@Override
	public void summary() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(
			final String action,
			final String[] args,
			final String option) {
		// TODO Auto-generated method stub
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		// TODO Auto-generated method stub
		return false;
	}

}
