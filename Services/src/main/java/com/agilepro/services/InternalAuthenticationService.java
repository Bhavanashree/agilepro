package com.agilepro.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.IAgileproCommonConstants;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.controller.IAgileProConstants;
import com.yukthi.persistence.utils.PasswordEncryptor;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.security.IAuthenticationService;
import com.yukthi.webutils.security.UserDetails;
import com.yukthi.webutils.services.UserService;

/**
 * Default internal authentication service.
 * @author akiran
 */
@Service
public class InternalAuthenticationService implements IAuthenticationService
{
	/**
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

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
		return new AgileProUserDetails(user.getId(), customerId);
	}
}
