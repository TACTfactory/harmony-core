/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.harmony.command.interaction.Question;
import com.tactfactory.harmony.command.interaction.Questionnary;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.TargetPlatform;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.platform.ios.IosAdapter;
import com.tactfactory.harmony.platform.winphone.WinphoneAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * The project context is the specific configuration of your project.
 * 
 * You can find :
 * <ul><li>Project name;</li>
 * <li>Global name space;</li>
 * <li>SDK version</li>
 * </ul>
 */
public final class ProjectContext {
    // DEMO/TEST MODE
    /** Default project name. */
    private static final String DEFAULT_PRJ_NAME = "demact";
    
    private static final String ERROR_INVALID_PRJ_NAME = 
    		"Your project name should begin with a letter"
    		+ "and contain only alphanumeric characters.";

    /** Default project NameSpace. */
    private static final String DEFAULT_PRJ_NS =
            "com.tactfactory.harmony.test.demact";

    private HashMap<TargetPlatform, BaseAdapter> adapters = 
            new HashMap<TargetPlatform, BaseAdapter>();

    private Harmony harmony;

    /**
     * Constructor.
     */
    public ProjectContext(Harmony harmony) {
        this.harmony = harmony;
    }

    public void detectProject() {
     // Check name space
        if (Strings.isNullOrEmpty(
                ApplicationMetadata.INSTANCE.getProjectNameSpace())) {

            // get project namespace from AndroidManifest.xml
            final File manifest = new File(String.format("%s/%s",
                    this.harmony.getHarmonyContext().getProjectAndroidPath(),
                    "AndroidManifest.xml"));

            if (manifest.exists()) {
                ProjectContext.loadNameSpaceFromManifest(manifest);
            }

            // get project name from configs.xml
            /*final File config = new File(String.format("%s/%s",
                    this.context.getProjectAndroidPath(),
                    "/res/values/configs.xml"));        //FIXME path by adapter

            if (config.exists()) {
                ApplicationMetadata.INSTANCE.setName(
                        ProjectContext.getProjectNameFromConfig(config));
            }*/

            // TODO MATCH : Voir avec Mickael pertinence d'utiliser le build.xml
            // pour récupérer le project name
            final File config = new File(String.format("%s/%s",
                    this.harmony.getHarmonyContext().getProjectAndroidPath(),
                    "build.xml"));

            if (config.exists()) {
                ProjectContext.loadProjectNameFromConfig(config);
            }

            // get SDK from local.properties
            final String projectProp = String.format("%s/%s",
                    this.harmony.getHarmonyContext().getProjectAndroidPath(),
                    "local.properties");
            final File projectPropFile = new File(projectProp);

            if (projectPropFile.exists()) {
                ApplicationMetadata.setAndroidSdkPath(
                        HarmonyContext.getSdkDirFromPropertiesFile(
                                projectProp));
            }

        } else {
            final String[] projectNameSpaceData =
                    ApplicationMetadata.INSTANCE.getProjectNameSpace()
                            .split(HarmonyContext.DELIMITER);

            ApplicationMetadata.INSTANCE.setName(
                    projectNameSpaceData[projectNameSpaceData.length - 1]);
        }
    }

    public void detectPlatforms() {
        final File dir = new File(Harmony.getProjectPath());
        final String[] files = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        final ArrayList<String> directories =
                new ArrayList<String>(Arrays.asList(files));
        Collections.sort(directories);

        ConsoleUtils.display("Current platforms found :");
        for (final String directory : directories) {
            switch (TargetPlatform.parse(directory)) {
            case ANDROID:
                this.adapters.put(
                        TargetPlatform.ANDROID,
                        new AndroidAdapter());
                ConsoleUtils.display("\t- Detected Android project !");
                break;
            case IPHONE :
                this.adapters.put(
                        TargetPlatform.IPHONE,
                        new IosAdapter());
                ConsoleUtils.display("\t- Detected iOS project !");
                break;
            case RIM:
                //this.adapters.put(TargetPlatform.RIM, new RimAdapter());
                ConsoleUtils.display("\t- Detected RIM project !");
                break;
            case WEB:
                //this.adapters.put(TargetPlatform.WEB, new WebAdapter());
                ConsoleUtils.display("\t- Detected Web project !");
                break;
            case WINPHONE:
                this.adapters.put(
                        TargetPlatform.WINPHONE,
                        new WinphoneAdapter());
                ConsoleUtils.display("\t- Detected Windows Phone project !");
                break;
            default:
                break;
            }
        }
    }

    public void addAdapter(TargetPlatform platform, BaseAdapter adapter) {
        this.adapters.put(platform, adapter);

    }

    public ArrayList<IAdapter> getAdapters() {
        return new ArrayList<IAdapter>(this.adapters.values());
    }

    public BaseAdapter getAdapter(TargetPlatform platform) {
        return this.adapters.get(platform);
    }

    /**
     * Extract Project NameSpace from AndroidManifest file.
     *
     * @param manifest Manifest File
     * @return Project Name Space
     */
    public static void loadNameSpaceFromManifest(final File manifest) {
        String result = null;
        SAXBuilder builder;
        Document doc;

        if (manifest.exists()) {
            // Make engine
            builder = new SAXBuilder();
            try {
                // Load XML File
                doc = builder.build(manifest);

                // Load Root element
                final Element rootNode = doc.getRootElement();

                // Get Name Space from package declaration
                result = rootNode.getAttributeValue("package");
                result = result.replaceAll("\\.", HarmonyContext.DELIMITER);
            } catch (final JDOMException e) {
                ConsoleUtils.displayError(e);
            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
            }
        }

        if (result != null) {
            ApplicationMetadata.INSTANCE.setProjectNameSpace(result.trim());
        }
    }

    /**
     * Extract Project Name from configuration file.
     *
     * @param config Configuration file
     * @return Project Name Space
     */
    public static void loadProjectNameFromConfig(final File config) {
        String result = null;
        SAXBuilder builder;
        Document doc;

        if (config.exists()) {
            // Make engine
            builder = new SAXBuilder();
            try {
                // Load XML File
                doc = builder.build(config);
                // Load Root element
                final Element rootNode = doc.getRootElement();
                result = rootNode.getAttribute("name").getValue();
            } catch (final JDOMException e) {
                ConsoleUtils.displayError(e);
            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
            }
        }

        if (result != null) {
            ApplicationMetadata.INSTANCE.setName(result.trim());
        }
    }

    /**
     * Prompt Project Name to the user.
     * 
     * @param arguments The console arguments passed by the user
     */
    public static void promptProjectName(HashMap<String, String> arguments) {
        if (Strings.isNullOrEmpty(ApplicationMetadata.INSTANCE.getName())) {
            final String KEY =  "name";

            Question question = new Question();
            question.setParamName(KEY, "n");
            question.setQuestion("Please enter your Project Name [%s]:", 
                    DEFAULT_PRJ_NAME);
            question.setDefaultValue(DEFAULT_PRJ_NAME);

            Questionnary questionnary = new Questionnary(arguments);
            questionnary.addQuestion(KEY, question);
            
            String projectName;

        	questionnary.launchQuestionnary();
        	projectName = questionnary.getAnswer(KEY);
        	
        	while (!projectName.matches("[a-zA-Z][a-zA-Z0-9]*")) {
        		ConsoleUtils.display(ERROR_INVALID_PRJ_NAME);
            	questionnary.launchQuestionnary();
            	projectName = questionnary.getAnswer(KEY);
            }
            
            
            ApplicationMetadata.INSTANCE.setName(projectName);
        }
    }

    /**
     * Prompt Project Name Space to the user.
     * 
     * @param arguments The console arguments passed by the user
     */
    public static void promptProjectNameSpace(
            HashMap<String, String> arguments) {
        if (Strings.isNullOrEmpty(
                ApplicationMetadata.INSTANCE.getProjectNameSpace())) {
            final String KEY =  "namespace";
            String result = null;

            Question question = new Question();
            question.setParamName(KEY, "ns");
            question.setQuestion("Please enter your Project NameSpace [%s]:",
                    DEFAULT_PRJ_NS);
            question.setDefaultValue(DEFAULT_PRJ_NS);

            Questionnary questionnary = new Questionnary(arguments);
            questionnary.addQuestion(KEY, question);

            boolean good = false;

            while (!good) {
                questionnary.launchQuestionnary();
                String nameSpace = questionnary.getAnswer(KEY);

                if (Strings.isNullOrEmpty(nameSpace)) {
                    good = true;
                } else {

                    final String namespaceForm =
                            "^(((([a-z0-9_]+)\\.)*)([a-z0-9_]+))$";

                    if (Pattern.matches(namespaceForm, nameSpace)) {
                        result = nameSpace.trim();
                        good = true;
                    } else {
                        ConsoleUtils.display(
                                "You can't use special characters "
                                        + "except '.' in the NameSpace.");
                        question.setParamName(null);
                        question.setShortParamName(null);
                    }
                }
            }

            if (result != null) {
                ApplicationMetadata.INSTANCE.setProjectNameSpace(result
                        .replaceAll("\\.", HarmonyContext.DELIMITER)
                        .trim());
            }
        }
    }
}
