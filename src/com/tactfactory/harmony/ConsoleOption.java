/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import com.google.common.base.Strings;

/**
 * Harmony options.<br/>
 * Available options for harmony instance.
 */
public enum ConsoleOption {
	//name		Internal	Short	Help message
	/** Help command. */
	HELP		("help",	"h",	"Display this help message."),
	/** Quiet command. */
	QUIET		("quiet",	"q",	"Do not output any message."),
	/** Verbose command. */
	VERBOSE		("verbose",	"v",	"Increase verbosity of messages."),
	/** Show version Command. */
	VERSION		("version",	"V",	"Display this application version."),
	/** ANSI command. */
	ANSI		("ansi",	null,	"Force ANSI output."),
	/** No-ANSI command. */
	NO_ANSI		("no-ansi",	null,	"Disable ANSI output.");

	// "\t--no-interaction -n Do not ask any interactive question.\n" +
	// "\t--shell\t\t -s Launch the shell.\n" +
	// "\t--env\t\t -e The Environment name.\n" +
	// "\t--no-debug \t    Switches off debug mode.\n\n"

	/** ConsoleOption full name. */
	private String fullName;

	/** ConsoleOption short name. */
	private String shortName;

	/** ConsoleOption description. */
	private String description;

	/**
	 * Constructor.
	 * @param fName The option full name
	 * @param sName The option short name
	 * @param desc The option description
	 */
	private ConsoleOption(final String fName,
			final String sName,
			final String desc) {
		this.fullName = fName;
		this.shortName = sName;
		this.description = desc;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder(
				String.format(
						"\t%s%-12s",
						Console.ARGUMENT_PREFIX,
						this.fullName));
		
		result.append("\t");
		if (!Strings.isNullOrEmpty(this.shortName)) {
			result.append(
					String.format(
							"%s%-4s",
							Console.ARGUMENT_PREFIX_SHORT,
							this.shortName));
		}
		
		result.append(String.format("\t%s\n", this.description));
		return result.toString();
	}

	/**
	 * Check if given command exists.
	 * @param value The name of the command
	 * @return True if it exists
	 */
	public boolean equal(final String value) {
		return value.equals(this.shortName) || value.equals(this.fullName);
	}

	/**
	 * Get option from its full name.
	 * @param value The full name
	 * @return The option
	 */
	public static ConsoleOption fromFullName(final String value) {
		ConsoleOption ret = null;
		if (value != null) {
			for (final ConsoleOption consoleOption : ConsoleOption.values()) {
				if (value.equalsIgnoreCase(consoleOption.fullName)) {
					ret = consoleOption;
				}
			}
		}

		return ret;
	}

	/**
	 * Get option from its short name.
	 * @param value The short name
	 * @return The option
	 */
	public static ConsoleOption fromShortName(final String value) {
		ConsoleOption ret = null;
		if (value != null) {
			for (final ConsoleOption consoleOption : ConsoleOption.values()) {
				if (value.equalsIgnoreCase(consoleOption.shortName)) {
					ret = consoleOption;
				}
			}
		}

		return ret;
	}
}