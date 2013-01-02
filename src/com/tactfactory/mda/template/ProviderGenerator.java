/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.util.List;

import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public class ProviderGenerator extends BaseGenerator {
	
	public ProviderGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		super(metas, adapter);
	}
	
	public void generateProvider() {
		
	}
}