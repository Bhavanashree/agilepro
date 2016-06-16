/*///*
 * The MIT License (MIT)
 * Copyright (c) 2015 "Yukthi Techsoft Pvt. Ltd." (http://yukthi-tech.co.in)

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
* The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE * SOFTWARE.
 */

package com.agilepro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.services.common.AdminExtension;
import com.yukthi.webutils.controllers.IExtensionContextProvider;
import com.yukthi.webutils.extensions.ExtensionDetails;
import com.yukthi.webutils.extensions.ExtensionEntityDetails;
import com.yukthi.webutils.services.CurrentUserService;

/**
 * {@link IExtensionContextProvider} plugin which provides extension owner
 * details for specified extension poin.
 * 
 * @author akiran
 **/
@Component
public class CbillerExtensionContextProvider implements IExtensionContextProvider
{
	/**
	 * Service to fetch current user.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * The project property type service.
	 **/
	// @Autowired
	// private ProjectPropertyTypeService projectPropertyTypeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yukthi.webutils.controllers.IExtensionContextProvider#
	 * getExtensionDetails(java.lang.String,
	 * com.yukthi.webutils.extensions.ExtensionEntityDetails)
	 */
	@Override
	public ExtensionDetails getExtensionDetails(String extensionName, ExtensionEntityDetails extensionPointDetails)
	{
		// check if target is admin extension
		boolean isAdminExtension = (extensionPointDetails.getEntityType().getAnnotation(AdminExtension.class) != null);

		// for admin extension there will no owner
		if(isAdminExtension)
		{
			return new ExtensionDetails();
		}

		// in other cases (non admin extension) return customer as the extension
		// owner
		CbillerUserDetails userDetails = (CbillerUserDetails) currentUserService.getCurrentUserDetails();
		return new ExtensionDetails(CustomerEntity.class, userDetails.getCustomerId());
	}

	// @Override
	// public String getExtensionName(Object modelObj)
	// {
	// if(modelObj instanceof ProjectPropertyModel)
	// {
	// long propertyTypeId = ((ProjectPropertyModel)
	// modelObj).getPropertyTypeId();
	// ProjectPropertyTypeEntity propertyType =
	// projectPropertyTypeService.fetch(propertyTypeId);
	// return propertyType.getName() +
	// ProjectPropertyTypeService.EXT_NAME_SUFFIX;
	// }
	//
	// return IExtensionContextProvider.super.getExtensionName(modelObj);
	// }
}
