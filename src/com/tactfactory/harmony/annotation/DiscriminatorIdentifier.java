package com.tactfactory.harmony.annotation;

/**
 * (Optional) This annotation is used only in the case of
 * a SingleTable inheritance mode. It will define the discriminator 
 * identifier used for this class.
 * 
 * You must put this annotation on the children of the inheritance hierarchy.  
 * 
 * By default, it will be the class name.
 *
 */
public @interface DiscriminatorIdentifier {
	/** The discriminator identifier. */
	String value();
}
