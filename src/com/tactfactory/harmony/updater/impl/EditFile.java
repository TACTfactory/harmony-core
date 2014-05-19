/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.updater.impl;

import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.old.IFileUtil;

public final class EditFile implements IUpdater {
    private final String from;
    private final String to;
    private final IFileUtil fileUtil;
    
    public EditFile(String from, String to, IFileUtil util) {
        this.from = from;
        this.to = to;
        this.fileUtil = util;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public IFileUtil getFileUtil() {
        return fileUtil;
    }
}
