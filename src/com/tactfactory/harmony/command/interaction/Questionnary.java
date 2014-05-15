/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command.interaction;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
}
