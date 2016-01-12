/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.updater;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.platform.IAdapter;

public interface IUpdater {
    void execute(BaseGenerator<? extends IAdapter> generator);
}
