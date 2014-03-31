/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command.questionnary;

import com.google.common.base.Strings;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * A question class is a simple class containing parameters used to define
 * what the user should gives you as piece of information.
 * It uses a question if the parameter hasn't been passed in arguments.
 */
public class Question {
	private static final String INVALID_ARGUMENT_ERROR = 
			"Invalid value \"%1$s\" for parameter %2$s";
	private String paramName;
	private String shortParamName;
	private String question;
	private String[] authorizedValues;
	private boolean acceptBlankAnswer;
	
	private String answer;
	private String defaultValue;
	
	/** 
	 * @return the answer 
	 */
	public String getAnswer() {
		return this.answer;
	}

	/**
	 * @return the paramName
	 */
	public final String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public final void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the shortParamName
	 */
	public final String getShortParamName() {
		return shortParamName;
	}

	/**
	 * @param shortParamName the shortParamName to set
	 */
	public final void setShortParamName(String shortParamName) {
		this.shortParamName = shortParamName;
	}

	/**
	 * @return the question
	 */
	public final String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the authorizedValues
	 */
	public final String[] getAuthorizedValues() {
		return authorizedValues;
	}

	/**
	 * @param authorizedValues the authorizedValues to set
	 */
	public final void setAuthorizedValues(String[] authorizedValues) {
		this.authorizedValues = authorizedValues;
	}

	/**
	 * @return the acceptBlankAnswer
	 */
	public final boolean isAcceptBlankAnswer() {
		return acceptBlankAnswer;
	}

	/**
	 * @param acceptBlankAnswer the acceptBlankAnswer to set
	 */
	public final void setAcceptBlankAnswer(boolean acceptBlankAnswer) {
		this.acceptBlankAnswer = acceptBlankAnswer;
	}

	/**
	 * @return the defaultValue
	 */
	public final String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public final void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/** 
	 * Ask question to user if needed.
	 */
	public final void process(String arg) {
		if (arg == null || (!this.acceptBlankAnswer && arg.isEmpty())) {
			this.answer = this.askQuestion();
		} else {
			boolean validAnswer = true;
			if (this.authorizedValues != null 
					&& this.authorizedValues.length > 0) {
				
				validAnswer = false;
				for (String authorizedValue : this.authorizedValues) {
					if (authorizedValue.equals(arg)) {
						validAnswer = true;
						break;
					}
				}
			}
			if (validAnswer) {
				this.answer = arg;
			} else {
				ConsoleUtils.display(String.format(
						INVALID_ARGUMENT_ERROR,
							arg,
							this.paramName));
				
				this.answer = this.askQuestion();
			}
		}
	}
	
	/**
	 * Force ask question to  user.
	 * @return The string answered
	 */
	private final String askQuestion() {
		String result;
		if (this.authorizedValues == null) {
			result = ConsoleUtils.getUserInput(
					this.question,
					(this.acceptBlankAnswer || (this.defaultValue != null)));
			if (this.defaultValue != null && Strings.isNullOrEmpty(result)) {
				result = this.defaultValue;
			}
		} else {
			result = ConsoleUtils.getValidUserInput(
					this.question,
					this.authorizedValues);
		}
		return result;
	}
}