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

public final class DeleteFile implements IUpdater {
    private final String path;
    
    public DeleteFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}