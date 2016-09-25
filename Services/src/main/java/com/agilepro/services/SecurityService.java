package com.agilepro.services;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.IAgileproCommonConstants;
import com.agilepro.commons.UserRole;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.controller.IAgileProConstants;
import com.yukthi.persistence.utils.PasswordEncryptor;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.WebutilsConfiguration;
import com.yukthi.webutils.common.models.ActiveUserModel;
import com.yukthi.webutils.extensions.ExtensionEntityDetails;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.UserRoleEntity;
import com.yukthi.webutils.repository.file.FileEntity;
import com.yukthi.webutils.security.ISecurityService;
import com.yukthi.webutils.security.UserDetails;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.services.UserService;

/**
 * Security service implementation for cloud biller which helps in
 * authentication and authorization.
 * 
 * @author akiran
 */
@Service
public class SecurityService implements ISecurityService
{
	/**
	 * Roles to be used for APIs which don't have @{@link Secured} annotation.
	 */
	// private static final Set<UserRole> DEFAULT_API_ROLES =
	// CommonUtils.toSet(UserRole.ADMINISTRATOR);

	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * The User Role Service.
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * The current user service.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The configuration.
	 */
	@Autowired
	private WebutilsConfiguration configuration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.webutils.security.ISecurityService#authenticate(java.lang.
	 * String, java.lang.String, java.util.Map)
	 */
	@Override
	public UserDetails authenticate(String userName, String password, Map<String, String> attributes)
	{
		String ownerIdStr = (attributes != null) ? attributes.get(IAgileproCommonConstants.REQ_PARAM_CUSTOMER_ID) : null;
		String userSpace = IAgileProConstants.ADMIN_USER_SPACE;
		long customerId = -1;

		// if customer id is specified use the customer space for authentication
		if(ownerIdStr != null)
		{
			try
			{
				long reqOwnerId = Long.parseLong(ownerIdStr);

				if(reqOwnerId > 0)
				{
					userSpace = IAgileProConstants.customerSpace(reqOwnerId);
					customerId = reqOwnerId;
				}
			} catch(Exception ex)
			{
				throw new InvalidRequestParameterException("Invalid customer id specified - {}", ownerIdStr);
			}
		}

		String dbPassword = userService.getPassword(userName, userSpace);

		// if user is not found or if password does not match
		if(dbPassword == null || !PasswordEncryptor.isSamePassword(dbPassword, password))
		{
			return null;
		}

		UserEntity user = userService.getUser(userName, userSpace);
		return new CbillerUserDetails(user.getId(), customerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.webutils.security.ISecurityService#isAuthorized(com.yukthi.
	 * webutils.security.UserDetails, java.lang.reflect.Method)
	 */
	@Override
	public boolean isAuthorized(Method method)
	{
		/*
		 * Authorization secured = method.getAnnotation(Authorization.class);
		 * 
		 * if(secured == null) { return true; }
		 * 
		 * UserDetails userDetails = currentUserService.getCurrentUserDetails();
		 * 
		 * Set<UserRole> requiredRoles = CommonUtils.toSet(secured.roles());
		 * 
		 * /* //if the method is not marked as secured (will be the case for
		 * apis exposed by webutils framework) // then use default api roles
		 * if(secured == null) { //TODO: Webutils needs finer roles. For
		 * example, project extensions should be configurable at /* Webutils api
		 * should have common annotation which can tell how to fetch parameter
		 * required for security check. For example, for extensions api how to
		 * fetch which entity type and entity id to be fetched / requiredRoles =
		 * DEFAULT_API_ROLES; } / //fetch user roles //TODO: There might be
		 * owner specific roles Map<UserRoleKey, UserRoleEntity> userRoles =
		 * userRoleService.getUserRoleMap(userDetails.getUserId());
		 * 
		 * //TODO: OWner type and id should not be fetched from target method
		 * UserRoleKey requiredRole = new UserRoleKey(Object.class.getName(),
		 * 0L, null);
		 * 
		 * //ensure at lease one required role is present for the the current
		 * user for(UserRole reqRole : requiredRoles) {
		 * if(userRoles.containsKey(requiredRole.setRole(reqRole))) { return
		 * true; } }
		 * 
		 * return false;
		 */
		return true;
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param fileEntity
	 *            the file entity
	 * @return true, if is authorized
	 */
	@Override
	public boolean isAuthorized(FileEntity fileEntity)
	{
		// ensure only owner customer is able to access this file
		CbillerUserDetails userDetails = (CbillerUserDetails) currentUserService.getCurrentUserDetails();
		return (fileEntity.getCustomAttribute1().equals("" + userDetails.getCustomerId()));
	}

	@Override
	public boolean isExtensionAuthorized(ExtensionEntityDetails extensionPoint)
	{
		// if(ProjectPropertyEntity.class.equals(extensionPoint.getEntityType()))
		// {
		// return false;
		// }
		//
		return true;
	}

	/**
	 * Adds the security customization.
	 *
	 * @param fileEntity
	 *            the file entity
	 */
	@Override
	public void addSecurityCustomization(FileEntity fileEntity)
	{
		// set current customer id on file entity
		CbillerUserDetails userDetails = (CbillerUserDetails) currentUserService.getCurrentUserDetails();
		fileEntity.setCustomAttribute1("" + userDetails.getCustomerId());
	}

	/**
	 * Adds all implicit roles of "role" recursively to roles.
	 *
	 * @param role
	 *            the role
	 * @param roles
	 *            the roles
	 */
	private void addImplicitRoles(UserRole role, Set<UserRole> roles)
	{
		// if no implicit roles are defined
		if(role.getImplicitRoles() == null)
		{
			return;
		}

		// add implicit roles recursively
		for(UserRole irole : role.getImplicitRoles())
		{
			roles.add(irole);
			addImplicitRoles(irole, roles);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.security.ISecurityService#getActiverUser()
	 */
	@Override
	public ActiveUserModel getActiverUser()
	{
		// fetch current user entity
		UserDetails currentUser = currentUserService.getCurrentUserDetails();
		// UserEntity userEntity = userService.fetch(currentUser.getUserId());
		UserEntity userEntity = userService.fetch(currentUser.getUserId());

		// build active user information
		ActiveUserModel activeUser = new ActiveUserModel();
		activeUser.setDisplayName(userEntity.getDisplayName());
		activeUser.setJsDateFormat(configuration.getJsDateFormat());
		activeUser.setUserId(userEntity.getId());

		// Add basic role names (without ownership consideration) of active user
		List<UserRoleEntity> userRoles = userRoleService.getUserRoles(currentUser.getUserId());
		Set<UserRole> roles = new HashSet<>();

		// if roles are present
		if(userRoles != null)
		{
			UserRole role = null;

			// add roles directly and add implicit roles recursively
			for(UserRoleEntity roleEntity : userRoles)
			{
				role = (UserRole) roleEntity.getRole();
				roles.add(role);

				addImplicitRoles(role, roles);
			}
		}

		// TODO: Adds roles from designation

		activeUser.setRoles(roles);
		return activeUser;
	}

	@Override
	public String getUserSpaceIdentity()
	{
		CbillerUserDetails user = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		if(user == null || user.getCustomerId() <= 0)
		{
			return IAgileProConstants.ADMIN_USER_SPACE;
		}

		return IAgileProConstants.customerSpace(user.getCustomerId());
	}
}
