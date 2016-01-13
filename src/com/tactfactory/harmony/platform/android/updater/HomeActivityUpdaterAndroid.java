/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.android.AndroidAdapter;
import com.tactfactory.harmony.updater.IUpdaterFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;


public final class HomeActivityUpdaterAndroid implements IUpdaterFile {
    private final String activity;
    private final String buttonId;

    public HomeActivityUpdaterAndroid(String activity,
            String buttonId) {
        this.activity = activity;
        this.buttonId = buttonId;
    }

    /**
     * Update Class : HomeActivity.
     * HomeActivity : add new Map button to Entity activity
     * @param activity Activity without Entity name (GMapsActivity, OSMActivity)
     * @param entity Entity to add
     */
    protected final void addLaunchActivityButton(BaseGenerator<? extends IAdapter> generator) {

        // Update HomeActivity
        File file = new File(((AndroidAdapter)generator.getAdapter()).getHomeActivityPathFile());

        if (file != null && file.isFile()) {
            try {
                String activityName = activity.substring(
                        activity.lastIndexOf('.') + 1);

                String listenerTpl = String.format("\t\tthis.findViewById(R.id.%s).setOnClickListener(this);",
                        buttonId);

                String caseTpl = String.format("\t\t\tcase R.id.%s:\n\t\t\t\tintent = new Intent(this, %s.class);\n\t\t\t\tbreak;\n",
                        buttonId,
                        activityName);

                // Import Activity
                this.updateFileAdd(file,
                        "import", "import", "import " + activity + ";", true);

                this.addButtonToMainXML(generator, activityName, buttonId);

                // If Listener not set
                this.updateFileAdd(file,
                        "private void initButtons() {", "private void initButtons() {", listenerTpl, false);

                // If case not set
                this.updateFileAdd(file,
                        "public void onClick(View v) {", "switch (v.getId()) {", caseTpl, false);
            } catch (IOException e) {
                ConsoleUtils.displayError(e);
            }
        }
    }

    /**
     * Insert the given string into another string at given position.
     * @param initialString The initial string
     * @param insert The string to insert
     * @param position The position where to insert
     * @return The new forged string
     */
    private String insertAtPosition(
            String initialString,
            String insert,
            int position) {

        String result = null;

        String begin = initialString.substring(0, position);
        String end = initialString.substring(position);
        result = begin + insert + end;

        return result;
    }

    /**
     * Add a button to main.xml.
     * @param text The displayed text
     * @param buttonId The button id
     */
    private void addButtonToMainXML(
            final BaseGenerator<? extends IAdapter> generator,
            final String text,
            final String buttonId) {
        String xmlFileName = ((AndroidAdapter) generator.getAdapter()).getRessourceLayoutPath() + "main.xml";
        Document doc = XMLUtils.openXML(xmlFileName);
        Namespace androidNs = doc.getRootElement().getNamespace("android");
        Element linearL = doc.getRootElement().getChild("LinearLayout");

        boolean alreadyExists = false;

        for (Element element : linearL.getChildren("Button")) {
            if (element.getAttributeValue("id", androidNs)
                    .equals("@+id/" + buttonId)) {
                alreadyExists = true;
            }
        }

        if (!alreadyExists) {
            Element newButton = new Element("Button");
            newButton.setAttribute("id", "@+id/" + buttonId, androidNs);
            newButton.setAttribute("layout_width", "match_parent", androidNs);
            newButton.setAttribute("layout_height", "wrap_content", androidNs);
            newButton.setAttribute("text", text, androidNs);

            linearL.addContent(newButton);
        }

        XMLUtils.writeXMLToFile(doc, xmlFileName);
    }

    private void updateFileAdd(File file, String find, String replace,
            String add, boolean addBefore) throws IOException {
        if (file != null && file.isFile()) {
            String strFile = TactFileUtils.fileToString(file);

            if (!strFile.contains(add)) {
                if (strFile.contains(find)) {
                    if (addBefore) {
                        strFile = strFile.replaceFirst(find,
                                add + "\n" + find);
                    } else {
                        strFile = strFile.replace(replace,
                                replace + "\n" + add);
                    }
                } else {
                    int lastBracketIndex = strFile.lastIndexOf('}');
                    strFile = insertAtPosition(strFile,
                            add,
                            lastBracketIndex);
                }

                TactFileUtils.writeStringToFile(file, strFile);
            }
        }
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        this.addLaunchActivityButton(generator);
    }
}
