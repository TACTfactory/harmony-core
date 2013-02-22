/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * To mark a entity for synchronize
 * between local and remote persistence
 * the @Sync annotation is used.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Inherited
public @interface Sync {
	
	/** Synchronization level. */
	public enum Level {
		GLOBAL(0),
		SESSION(1);
		
		private int value;
		private Level(final int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
		
		public static Level fromValue(final int value) {
			Level ret = null;
			for (final Level type : Level.values()) {
				if (value == type.value) {
					ret = type;
				}    
			}
			
			return ret;
		}
		
		public static Level fromName(final String name) {
			String realName;
			Level ret;
			if (name.lastIndexOf('.') > 0) {
				// Take only what comes after the last dot
				realName = name.substring(name.lastIndexOf('.') + 1); 
			} else {
				realName = name;
			}
			ConsoleUtils.displayDebug("Sync for Security : " + realName);
			try {
				final Field field = Level.class.getField(realName);	
				if (field.isEnumConstant()) {
					ConsoleUtils.displayDebug("Found Security : " + realName);
					ret = (Level) field.get(Level.class);
				} else {
					ret = null;
				}
			} catch (final NoSuchFieldException e) {
				ret = null;
			} catch (final IllegalAccessException e) {
				ret = null;
			}
			return ret;
		}
	}
	
	/** Mode of trigger for synchronization. */
	public enum Mode {
		POOLING(1),
		REAL_TIME(2),
		PUSH(3);
		
		private int value;
		private Mode(final int value) {
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}
		
		public static Mode fromValue(final int value) {
			Mode ret = null;
			for (final Mode type : Mode.values()) {
				if (value == type.value) {
					ret = type;
				}    
			}
			
			return ret;
		}
		
		public static Mode fromName(final String name) {
			String realName;
			Mode ret;
			if (name.lastIndexOf('.') > 0) {
				// Take only what comes after the last dot
				realName = name.substring(name.lastIndexOf('.') + 1); 
			} else {
				realName = name;
			}
			ConsoleUtils.displayDebug("Sync for Security : " + realName);
			try {
				final Field field = Mode.class.getField(realName);	
				if (field.isEnumConstant()) {
					ConsoleUtils.displayDebug("Found Security : " + realName);
					ret = (Mode) field.get(Mode.class);
				} else {
					ret = null;
				}
			} catch (final NoSuchFieldException e) {
				ret = null;
			} catch (final IllegalAccessException e) {
				ret = null;
			}
			
			return ret;
		}
	}
	
	/** Priority level. */
	public static class Priority {
		public static final int NORMALE = 5;
		public static final int HIGH = 0;
		public static final int LOW = 9;
		
		public static int fromName(final String name) {
			String realName;
			int ret = 0;
			if (name.lastIndexOf('.') > 0) {
				// Take only what comes after the last dot
				realName = name.substring(name.lastIndexOf('.') + 1); 
			} else {
				realName = name;
			}
			ConsoleUtils.displayDebug("Sync for Security : " + realName);
			try {
				final Field field = Priority.class.getField(realName);	
				ConsoleUtils.displayDebug("Found Security : " + realName);
				ret = field.getInt(Priority.class);
				
			} catch (final NoSuchFieldException e) {
				ret = 0;
			} catch (final IllegalAccessException e) {
				ret = 0;
			}
			return ret;
		}
	}
	
	/**
	 * Synchronization Level of entity.
	 */
	Level level() default Level.GLOBAL;
	
	/**
	 * Synchronization mode of entity.
	 */
	Mode mode() default Mode.POOLING;
	
	/**
	 * Synchronization priority of entity.
	 */
	int priority() default 1;
}
