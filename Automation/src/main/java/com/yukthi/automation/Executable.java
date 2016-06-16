package com.yukthi.automation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a step/validation as executable, so that they can be found dynamically.
 * @author akiran
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Executable
{
	/**
	 * Provides name of executable.
	 * @return Executable name.
	 */
	public String value();
}
