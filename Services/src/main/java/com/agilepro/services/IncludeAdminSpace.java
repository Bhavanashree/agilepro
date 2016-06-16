package com.agilepro.services;

import static com.agilepro.controller.IRealEstateServerConstants.ADMIN_USER_SPACE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yukthi.persistence.repository.annotations.DefaultCondition;
import com.yukthi.persistence.repository.annotations.JoinOperator;
import com.yukthi.persistence.repository.annotations.MethodConditions;

/**
 * This annotation will add expand search to admin space along with current user space.
 * @author akiran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@MethodConditions(conditions = @DefaultCondition(field = "spaceIdentity", value = ADMIN_USER_SPACE, joinOperator = JoinOperator.OR))
public @interface IncludeAdminSpace
{
}
