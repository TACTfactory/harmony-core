/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.updater.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Command of generator for delete file.
 *
 * @author Erwan LeHuitouze <erwan.lehuitouze@tactfactory.com>
 * @author Mickael Gaillard <mickael.gaillard@tactfactory.com>
 */
public final class DeleteFile implements IUpdater {

    private final String path;

    /**
     * Constructor of the command generator.
     *
     * @param path String path to delete.
     */
    public DeleteFile(String path) {
        this.path = path;
    }

    /**
     * The path of the folder to create.
     *
     * @return String path of the folder.
     */
    public String getPath() {
        return path;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        File file = new File(this.getPath());

        if (file.exists()) {
            if (file.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    ConsoleUtils.displayError(e);
                }
            } else {
                file.delete();
            }
        }
    }
}