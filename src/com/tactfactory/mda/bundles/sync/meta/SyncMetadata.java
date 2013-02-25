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
 */
public class SyncMetadata extends BaseMetadata {
	/** Bundle name. */
	private static final String NAME = "sync";
	
	/** Sync level. */
	private Level level = Level.GLOBAL;
	/** Sync Mode. */
	private Mode mode = Mode.POOLING;
	/** Sync Priority. */
	private int priority = 1;
	
	/**
	 * Constructor.
	 */
	public SyncMetadata() {
		super();
		this.setName(NAME);
	}
	@Override
	public final HashMap<String, Object> toMap(final BaseAdapter adapter) {
		final HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.LEVEL, this.level.getValue());
		model.put(TagConstant.MODE, this.mode.getValue());
		model.put(TagConstant.PRIORITY, this.priority);
		return model;
	}
	/**
	 * @return the level
	 */
	public final Level getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public final void setLevel(final Level level) {
		this.level = level;
	}
	/**
	 * @return the mode
	 */
	public final Mode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public final void setMode(final Mode mode) {
		this.mode = mode;
	}
	/**
	 * @return the priority
	 */
	public final int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public final void setPriority(final int priority) {
		this.priority = priority;
	}

}
