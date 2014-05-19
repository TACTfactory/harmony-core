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

public final class SourceFile implements IUpdater {
    private final String templateSource;
    private final String fileDestination;
    private final boolean overwrite;
    
    public String getTemplateSource() {
        return templateSource;
    }
    public String getFileDestination() {
        return fileDestination;
    }
    public boolean isOverwrite() {
        return overwrite;
    }
    
    public SourceFile(String source, String destination) {
        this(source, destination, false);
    }
    
    public SourceFile(String source, String destination, boolean overwrite) {
        this.templateSource = source;
        this.fileDestination = destination;
        this.overwrite = overwrite;
    }
}
