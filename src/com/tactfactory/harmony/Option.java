package com.tactfactory.harmony;

import com.google.common.base.Strings;

/** Possible options. */
public enum Option {
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

	/** Option full name. */
	private String fullName;

	/** Option short name. */
	private String shortName;

	/** Option description. */
	private String description;

	/**
	 * Constructor.
	 * @param fName The option full name
	 * @param sName The option short name
	 * @param desc The option description
	 */
	private Option(final String fName,
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
	public static Option fromFullName(final String value) {
		Option ret = null;
		if (value != null) {
			for (final Option option : Option.values()) {
				if (value.equalsIgnoreCase(option.fullName)) {
					ret = option;
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
	public static Option fromShortName(final String value) {
		Option ret = null;
		if (value != null) {
			for (final Option option : Option.values()) {
				if (value.equalsIgnoreCase(option.shortName)) {
					ret = option;
				}
			}
		}

		return ret;
	}
}