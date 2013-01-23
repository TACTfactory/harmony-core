/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.HashMap;

import com.tactfactory.mda.plateforme.BaseAdapter;

public interface Metadata {

	public abstract HashMap<String, Object> toMap(BaseAdapter adapter);
	public abstract String getName();
}
