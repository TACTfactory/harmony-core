/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.sync.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * To mark a entity for synchronize between local and remote persistence the @Sync annotation is used.
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
@Inherited
public @interface Sync {
	
	/** Synchronization level */
	public enum Level {
		GLOBAL(0),
		SESSION(1);
		
		private int value;
		private Level(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
		public static Level fromValue(int value){
			for (Level type : Level.values()) {
				if (value == type.value) {
					return type;
				}    
			}
			
			return null;
		}
	}
	
	/** Mode of trigger for synchronization */
	public enum Mode {
		POOLING(1),
		REAL_TIME(2),
		PUSH(3);
		
		private int value;
		private Mode(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
		public static Mode fromValue(int value){
			for (Mode type : Mode.values()) {
				if (value == type.value) {
					return type;
				}    
			}
			
			return null;
		}
	}
	
	/**
	 * Synchronization Level of entity
	 * 
	 * @return (optional, defaults to GLOBAL) The level to use for synchronize.
	 */
	Level level() default Level.GLOBAL;
	
	/**
	 * Synchronization mode of entity
	 * 
	 * @return (optional, defaults to POOLING) The mode to use for synchronize.
	 */
	Mode mode() default Mode.POOLING;
	
	/**
	 * Synchronization priority of entity
	 * 
	 * @return (optional, defaults to 1) The ratio priority to use for synchronize.
	 */
	int priority() default 1;
}
