/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Enum Generator class.
 */
public class EnumGenerator extends BaseGenerator<IAdapter> {
	/** Constructor.
	 * @param adapt Adapter used by this generator
	 * @throws Exception if adapter is null
	 */
	public EnumGenerator(final IAdapter adapt) throws Exception {
		super(adapt);
	}

	/**
	 * Add getValue and fromValue to enum who declares an ID
	 * and don't have theses methods.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Decorate enums...");

		List<IUpdater> updaters = new ArrayList<IUpdater>();
		
		Iterable<EnumMetadata> enums = this.getAppMetas().getEnums().values();
		
		for (final EnumMetadata enumMeta : enums) {

			if (enumMeta.getIdName() != null) {
			    List<IUpdater> enumsUpdaters =
			            this.getAdapter().getAdapterProject()
                                .updateEnum(enumMeta, this.getCfg());
                
			    if (enumsUpdaters != null) {
                    updaters.addAll(enumsUpdaters);
                }
			}
		}
		
		this.processUpdater(updaters);
	}
}
