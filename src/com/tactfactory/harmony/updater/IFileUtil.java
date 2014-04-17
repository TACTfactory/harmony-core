package com.tactfactory.harmony.updater;

public interface IFileUtil {
    void open(String file);
    void save();
    String addElement(String key, String value);
    void mergeFiles(String from, String to);
}
