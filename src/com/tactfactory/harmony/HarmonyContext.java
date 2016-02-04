/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.tactfactory.harmony.command.interaction.Question;
import com.tactfactory.harmony.command.interaction.Questionnary;
import com.tactfactory.harmony.dependencies.libraries.LibraryPool;
import com.tactfactory.harmony.exception.HarmonyException;
import com.tactfactory.harmony.generator.TagConstant;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * The harmony context is the environment in which harmony runs. (where !)
 * <ul><li>Root path;</li>
 * <li>Application source path;</li>
 * <li>Bundle path;</li>
 * <li>Template path.</li></ul><br/><br/>
 *
 * All path is base on auto-detection of your environment. eg.
 * <ul><li>from eclipse (debug)</li>
 * <li>from gradle (release)</li>
 * <li>from .... (and other case)</li></ul>
 */
public final class HarmonyContext {
    /** Delimiter. */
    public static final String DELIMITER = "/";

    /** Library pool. */
    private final LibraryPool libPool = new LibraryPool();

    /** Project folder. */
    private static final String projectFolder        = "app" + DELIMITER;

    /** Library folder. */
    private static final String libraryFolder        = "lib" + DELIMITER;

    /** Bundle folder. */
    private static final String bundleFolder         = "vendor" + DELIMITER;

    /** Template folder. */
    private static final String templateFolder       = "tpl" + DELIMITER;

    /** Project base folder. */
    private static final String projectBaseFolder    = "android" + DELIMITER;

    /** Path of harmony.jar. or Binary */
    private String harmonyPath;

    /** Current bundle folder. */
    private String currentBundleFolder;

    /** Path of Harmony base. */
    private String basePath = new File("./").getAbsolutePath();

    /** Path of project (app folder in Harmony root).<br/>
     * eg. /app/
     */
    private String projectPath = "";

    /** Project space. <br/>
     * eg. /app/android/
     */
    private String projectBasePath;

    /** Path of bundles.<br/>
     * eg. /vendor/
     */
    private String bundlePath;

    /** Path of libraries.<br/>
     * eg. /lib/
     */
    private String libraryPath;

    //TODO remove by bundle path

    /** Path of templates.<br/>
     * eg. /vendor/../tpl/
     */
    private String templatePath;

    /** Android SDK install path. */
    private static String defaultSDKPath;

    /** Android SDK version. */
    private static String androidSdkVersion;

    /** Android home environment variables key. */
    private static final String ANDROID_HOME = "ANDROID_HOME";

    /**
     * Contructor.
     */
    public HarmonyContext() {
        this.harmonyPath = URI.create(Harmony.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath()).getPath();

        File harmonyFile = new File(this.harmonyPath);
        File baseDir = this.getBaseDir(harmonyFile);

        if (baseDir != null) {
            // Transform PATH_BASE !!!
            this.basePath = baseDir.getPath().toString() + "/";
        } else {
            // For any other case
            ConsoleUtils.displayError(new Exception(
                    "INVALID FOLDERS TREE. APP FOLDER MISSING."));
            System.exit(-1);
        }

        ConsoleUtils.displayDebug("Detect app folder on " + basePath);

        // Set Path
        this.projectPath         = this.basePath + projectFolder;
        this.projectBasePath    = this.projectPath + projectBaseFolder;
        this.libraryPath         = this.basePath + libraryFolder;

        if (Strings.isNullOrEmpty(bundlePath)) { // TODO check why..
            this.bundlePath     = this.basePath + bundleFolder;
        }

        //TODO remove by bundle path
        this.templatePath     = templateFolder;

        this.libPool.parseLibraries(new File(this.bundlePath));
    }

    /**
     * Check if the given folder contains the App folder.
     * @param checkPath The path to check.
     * @return The path if App was found, null if not.
     */
    private File detectAppTree(final File checkPath) {
        File result = null;
        final File[] list = checkPath.listFiles();

        if (list != null) {
            for (File dir : list) {
                if (dir.getPath().endsWith(projectFolder.replace("/", ""))) {
                    result = checkPath;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Get the base directory of harmony.
     * @return File representing base directory.
     */
    private File getBaseDir(File harmonyFile) {
        // For root case
        File baseDir = this.detectAppTree(harmonyFile);

        // Clean binary case (for /bin and /vendor/**/bin)
        if (baseDir == null && this.harmonyPath.endsWith("bin/")) {
            final File predictiveBaseDir =
                    harmonyFile.getParentFile();

            baseDir = this.detectAppTree(predictiveBaseDir);

            if (baseDir != null) {
                ConsoleUtils.displayDebug("Eclipse Mode : " + this.harmonyPath);
            }
        }

        if (baseDir == null && this.harmonyPath.endsWith("harmony.jar")) {
            final File predictiveBaseDir = harmonyFile
                    .getParentFile()
                    .getParentFile()
                    .getParentFile();

            baseDir = this.detectAppTree(predictiveBaseDir);

            if (baseDir != null) {
                ConsoleUtils.displayDebug("Console Mode : " + this.harmonyPath);
            }
        }

        //For Gradle
        if (baseDir == null) {
            final File predictiveBaseDir =
                    harmonyFile
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile();

            baseDir = this.detectAppTree(predictiveBaseDir);

            if (baseDir != null) {
                ConsoleUtils.displayDebug("Gradle Mode : " + this.harmonyPath);
            }
        }

        //For Gradle Emma
        if (baseDir == null) {
            final File predictiveBaseDir =
                    harmonyFile
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile()
                        .getParentFile();

            baseDir = this.detectAppTree(predictiveBaseDir);

            if (baseDir != null) {
                ConsoleUtils.displayDebug(
                        "Gradle Emma Mode : " + this.harmonyPath);
            }
        }

        return baseDir;
    }

    static {
        HarmonyContext.defaultSDKPath = // Into context
                HarmonyContext.findSDKEnvironmentVariable();

        if (HarmonyContext.defaultSDKPath == null) {
            if (OsUtil.isWindows()) {
                if (OsUtil.isX64()) {
                    HarmonyContext.defaultSDKPath =
                            "C:/Program Files (x86)/android-sdk";
                } else {
                    HarmonyContext.defaultSDKPath =
                            "C:/Program Files/android-sdk";
                }
            } else if (OsUtil.isLinux()) {
                HarmonyContext.defaultSDKPath = "/opt/android-sdk/";
            } else {
                HarmonyContext.defaultSDKPath = "/opt/android-sdk/";
            }
        }
    }

    /**
     * Search the system's environment variables for the android sdk path and
     * returns it.
     *
     * @return The AndroidSDKPath if found, null otherwise
     */
    private static String findSDKEnvironmentVariable() {
        String result = null;

        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase(ANDROID_HOME)) {
                result = env.get(envName);
            }
        }

        return result;
    }

    /**
     * @return Default detected Android SDK path
     */
    public static String getDefautlAndroidSdkPath() {
        return defaultSDKPath;
    }

    /**
     * @param androidSdkVersion the androidSdkVersion to set
     */
    public static void setAndroidSdkVersion(final String androidSdkVersion) {
        HarmonyContext.androidSdkVersion = androidSdkVersion;
    }

    /**
     * @return the androidSdkVersion
     */
    public static String getAndroidSdkVersion() {
        return androidSdkVersion;
    }

    /**
     * Extract Android SDK Path from .properties file.
     *
     * @param fileProp .properties File
     * @return Android SDK Path
     */
    public static String getSdkDirFromPropertiesFile(final File fileProp) {
        String result = null;

        if (fileProp.exists()) {
            final List<String> lines = TactFileUtils.fileToStringArray(fileProp);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("sdk.dir=")) {
                    if (lines.get(i).contains(TagConstant.ANDROID_SDK_DIR)) {
                        ConsoleUtils.displayWarning(
                                "Android SDK Dir not defined,"
                                + " please init project...");
                    } else {
                        result = lines.get(i).replace("sdk.dir=", "");
                    }
                    break;
                }
            }
        }

        if (result != null) {
            HarmonyContext.defaultSDKPath = result;
        }

        return result;
    }

    /**
     * Extract Android SDK Path from .properties file.
     *
     * @param filePath .properties File path
     * @return Android SDK Path
     */
    public static String getSdkDirFromPropertiesFile(final String filePath) {
        String result = null;

        final File fileProp = new File(filePath);
        result = getSdkDirFromPropertiesFile(fileProp);

        if (result != null) {
            androidSdkVersion = setAndroidSdkPath(result);
        } else {
            result = ApplicationMetadata.getAndroidSdkPath();
        }

        return result;
    }

    /**
     * Extract Android SDK Path from local.properties file.
     *
     * @param sdkPath The sdk path
     * @return Android SDK Path
     */
    public static String setAndroidSdkPath(final String sdkPath) {
        String result = null;

        final File sdkProperties =
                new File(sdkPath + "/tools/source.properties");

        if (sdkProperties.exists()) {
            try {
                final FileInputStream fis = new FileInputStream(sdkProperties);
                final InputStreamReader isr =
                        new InputStreamReader(fis,
                                TactFileUtils.DEFAULT_ENCODING);
                final BufferedReader br = new BufferedReader(isr);
                String line = br.readLine();
                while (line != null) {
                    if (line.startsWith("Pkg.Revision")) {
                        result = line.substring(line.lastIndexOf('=') + 1);

                        break;
                    }
                    line = br.readLine();
                }
                br.close();
            } catch (final IOException e) {
                ConsoleUtils.displayError(e);
            }
        }

        return result;
    }

    /**
     * Prompt Project Android SDK Path to the user.
     *
     * @param arguments The console arguments passed by the user
     */
    public static void initProjectAndroidSdkPath(HashMap<String, String> arguments) {

        if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
            Questionnary questionnary = new Questionnary(arguments);
            Question question = new Question();
            question.setParamName("androidsdk");
            question.setDefaultValue(HarmonyContext.defaultSDKPath);
            question.setQuestion(String.format(
                    "Please enter AndroidSDK full path [%s]:",
                    HarmonyContext.defaultSDKPath));
            question.setShortParamName("sdk");


            questionnary.addQuestion("sdk", question);
            questionnary.launchQuestionnary();
            ApplicationMetadata.setAndroidSdkPath(questionnary.getAnswer("sdk"));
        }
    }

    /** eg. /
     * @return the executePath
     */
    public String getBasePath() {
        return this.basePath;
    }

    /** Get project path <br/>
     * eg. /app/
     *
     * @return the projectPath
     */
    public String getProjectPath() {
        return this.projectPath;
    }

    /** eg. /app/android/
     * @return the projectAndroidPath
     */
    public String getProjectAndroidPath() {
        return this.projectBasePath;
    }

    /** eg. /vendor/
     * @return the bundlePath
     */
    public String getBundlesPath() {
        return this.bundlePath;
    }

    /** eg. /vendor/tact-core/ or /bin
     * @return the harmonyPath
     */
    public String getHarmonyPath() {
        return this.harmonyPath;
    }

    /** eg. /lib/
     * @return the libraryPath
     */
    public String getLibsPath() {
        return this.libraryPath;
    }

    /**
     * @return the templatePath
     */
    public String getTemplatesPath() {
        return this.templatePath;
    }

    /**
     * @param libraryName The library to get
     * @return The library File
     * @throws HarmonyException
     */
    public File getLibrary(final String libraryName) {
        return this.libPool.getLibrary(libraryName);
    }

    /**
     * @param libraryName The library to get
     * @return The library license File
     */
    public File getLibraryLicense(final String libraryName) {
        return this.libPool.findLibraryLicense(libraryName);
    }

    /**
     * Sets the current bundle folder.
     * @param folder The folder path
     */
    public void setCurrentBundleFolder(String folder) {
        this.currentBundleFolder = folder;
    }

    /**
     * Sets the current bundle folder.
     * @return the current bundle folder path
     */
    public String getCurrentBundleFolder() {
        return this.currentBundleFolder;
    }
}
