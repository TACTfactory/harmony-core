/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.meta;

import java.util.HashMap;

import com.tactfactory.mda.bundles.sync.annotation.Sync.Level;
import com.tactfactory.mda.bundles.sync.annotation.Sync.Mode;
import com.tactfactory.mda.meta.BaseMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/**
 * MetaData for Sync description.
 * @author gregg
 *
 */
public class SyncMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "sync";
	
	/** Sync level. */
	public Level level = Level.GLOBAL;
	/** Sync Mode. */
	public Mode mode = Mode.POOLING;
	/** Sync Priority. */
	public int priority = 1;
	
	/**
	 * Constructor.
	 */
	public SyncMetadata() {
		super();
		this.name = NAME;
	}
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.LEVEL, this.level.getValue());
		model.put(TagConstant.MODE, this.mode.getValue());
		model.put(TagConstant.PRIORITY, this.priority);
		return model;
	}

}
