package com.tactfactory.harmony.platform.android.updater;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IAddMethod;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AddMethodAndroid implements IAddMethod{

    private final String methodName;
    private final String templatePath;
    private final String sourceFile;

    public AddMethodAndroid(String methodName, String templatePath, String sourceFile) {
        this.methodName = methodName;
        this.templatePath = templatePath;
        this.sourceFile = sourceFile;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        File file = new File(this.sourceFile);

        if (file != null && file.isFile()) {
            String strFile = TactFileUtils.fileToString(file);

            if (!strFile.contains(this.methodName + "(")) {
                try {
                    final Template tpl = generator.getCfg().getTemplate(
                            this.templatePath);

                    StringWriter out = new StringWriter();

                    tpl.process(generator.getDatamodel(), out);

                    strFile = strFile.substring(0,
                            strFile.lastIndexOf('}'));

                    strFile = strFile.concat(out.toString() + "\n}");

                    TactFileUtils.writeStringToFile(file, strFile);
                } catch (IOException | TemplateException e) {
                    ConsoleUtils.displayError(e);
                }
            }
        }
    }

}
