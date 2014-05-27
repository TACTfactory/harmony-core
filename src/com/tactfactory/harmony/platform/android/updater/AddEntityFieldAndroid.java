package com.tactfactory.harmony.platform.android.updater;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.tactfactory.harmony.updater.IAddEntityField;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AddEntityFieldAndroid implements IAddEntityField{

    private final Configuration cfg;
    private final Map<String, Object> dataModel;
    private final String templatePath;
    private final String sourceFile;
    
    public AddEntityFieldAndroid(Configuration cfg,
            Map<String, Object> dataModel, String templatePath,
            String sourceFile) {
        this.cfg = cfg;
        this.dataModel = dataModel;
        this.templatePath = templatePath;
        this.sourceFile = sourceFile;
    }
    
    @Override
    public void execute() {
        File file = new File(this.sourceFile);
        
        if (file != null && file.isFile()) {
            String strFile = TactFileUtils.fileToString(file);
            
            try {
                final Template tpl = this.cfg.getTemplate(this.templatePath);
                
                StringWriter out = new StringWriter();
                tpl.process(this.dataModel, out);
                
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
