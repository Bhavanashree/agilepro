package com.agilepro.services.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.agilepro.commons.UserRole;

/**
 * Marks target method as secured, so that users with specified roles only can
 * access this methods.
 * 
 * @author akiran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorization
{
	/**
	 * Users with having at lease one of these roles only will be allowed to invoke target api.
	 * @return Roles required by this method
	 */
	public UserRole[] roles() default {};	
	
	/**
	 * Entity id expression.
	 * Specifies expression for fetching target entity id of the current request. Expression should start with "parameters". 
	 * Following are some of the possible expressions:
	 * 	parameters[0]
	 *  parameters[0].id
	 *
	 * @return the string
	 */
	public String entityIdExpression() default "";
}
