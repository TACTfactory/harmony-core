package com.tactfactory.harmony.command;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * This class is used to help you ask questions to your user or use parameters.
 * It is composed of various questions that it will ask the user in the same
 * order you added them.
 */
public class Questionnary {
	/** Questions map. */
	private HashMap<String, Question> questions;
	/** Command args. */
	private HashMap<String, String> commandArgs;
	
	/**
	 * Constructor.
	 * @param commandArgs The command arguments entered by the user while
	 *  calling the command.
	 */
	public Questionnary(HashMap<String, String> commandArgs) {
		this.commandArgs = commandArgs;
		this.questions = new LinkedHashMap<String, Question>();
	}
	
	/**
	 * Add a question with a key.
	 * @param key The key of the question
	 * @param question The question
	 */
	public void addQuestion(String key, Question question) {
		this.questions.put(key, question);
	}
	
	/**
	 * Gets the answer of the question you added with the key
	 * @param key The key
	 * @return The answer
	 */
	public String getAnswer(String key) {
		String result;
		if (this.questions.containsKey(key)) {
			result = this.questions.get(key).getAnswer();
		} else {
			result = null;
		}
		return result;
	}
	
	/**
	 * Launch the questionnary.
	 */
	public void launchQuestionnary() {
		for (Question question : this.questions.values()) {
			String argument;
			if (this.commandArgs.containsKey(question.getParamName())) {
				argument = this.commandArgs.get(question.getParamName());
			} else if (this.commandArgs.containsKey(question.getShortParamName())) {
				argument = this.commandArgs.get(question.getShortParamName());
			} else {
				argument = null;
			}
			question.process(argument);
		}
	}
	
	/**
	 * A question class is a simple class containing parameters used to define
	 * what the user should gives you as piece of information.
	 * It uses a question if the parameter hasn't been passed in arguments.
	 */
	public static class Question {
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
		public final void setQuestion(String question) {
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
						this.acceptBlankAnswer);
			} else {
				result = ConsoleUtils.getValidUserInput(
						this.question,
						this.authorizedValues);
			}
			return result;
		}
	}
}
