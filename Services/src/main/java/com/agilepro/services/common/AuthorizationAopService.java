package com.agilepro.services.common;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agilepro.commons.UserRole;
import com.agilepro.services.SecurityService;
import com.yukthi.webutils.common.models.ActiveUserModel;
import com.yukthi.webutils.security.UnauthorizedException;

/**
 * AOP for controller methods which uses @{@link Authorization} annotation. This
 * service ensures current user has required role to invoke the method and the
 * user is authorized to access target entity.
 * 
 * @author akiran
 */
@Aspect
@Component
public class AuthorizationAopService
{
	/**
	 * Used to access current active user with corresponding roles.
	 */
	@Autowired
	private SecurityService securityService;

	/**
	 * Check for roles.
	 * Checks if the current user has sufficient roles for accessing target
	 * @param authorization the authorization
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void checkForRoles(Authorization authorization)
	{
		ActiveUserModel activeUser = securityService.getActiverUser();

		// if roles are specified
		if(authorization.roles().length > 0)
		{
			Set<UserRole> currentUserRoles = (Set) activeUser.getRoles();
			boolean found = false;

			// check if the current user has at least one role among specified
			// roles
			for(UserRole role : authorization.roles())
			{
				if(currentUserRoles.contains(role))
				{
					found = true;
					break;
				}
			}

			// if no role is found
			if(!found)
			{
				throw new UnauthorizedException("Current user does not have sufficient roles for current operation");
			}
		}
	}

	/**
	 * Check authorization.
	 * AOP method which performs the authorization check
	 *
	 * @param joinPoint the join point
	 * @param authorization the authorization
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("execution(@com.agilepro.services.common.Authorization * *(..))  && @annotation(authorization)")
	public Object checkAuthorization(ProceedingJoinPoint joinPoint, Authorization authorization) throws Throwable
	{
		// ensure user has sufficient roles
		checkForRoles(authorization);

		/*
		Class<? extends CbillerCrudService<?, ?>> serviceType = (Class) authorization.service();
		String entityIdExpression = authorization.entityIdExpression();

		// ensure user is authorized to access target entity
		if(!CbillerCrudService.class.equals(serviceType) && StringUtils.isNotBlank(entityIdExpression))
		{
			Object args[] = joinPoint.getArgs();

			CbillerCrudService<?, ?> service = applicationContext.getBean(serviceType);
			Object entityId = null;

			// fetch the entity id from the parameters based on specified entity
			// id expression
			try
			{
				entityId = PropertyUtils.getProperty(new Context(args), entityIdExpression);
			} catch(Exception ex)
			{
				throw new InvalidStateException(ex, "An error occurred while evaluating entity id expression - {} [For method: {}.{}()]", entityIdExpression, joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			}

			if(entityId == null)
			{
				throw new InvalidArgumentException("Entity expression resulted in null value [Method: {}.{}(), Expression: {}, Value: {}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), entityIdExpression, entityId);
			}

			// ensure valid id is obtained
			if(!(entityId instanceof Long))
			{
				throw new InvalidConfigurationException("Entity expression resulted in non long value [Method: {}.{}(), Expression: {}, Value: {}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), entityIdExpression, entityId);
			}

			// invoked service to check for authorization
			service.checkAuthorization((Long) entityId);
		}
		
		*/

		return joinPoint.proceed();
	}
}
