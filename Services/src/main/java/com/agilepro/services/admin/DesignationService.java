package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.persistence.entity.admin.DesignationEntity;
import com.agilepro.persistence.repository.admin.IDesignationRepository;
import com.yukthi.webutils.annotations.LovMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Designation service implementation for cloud biller which helps in setting
 * roles.
 * 
 * @author bhavana
 */
@Service
public class DesignationService extends BaseCrudService<DesignationEntity, IDesignationRepository>
{
	/**
	 * Instantiates a new Designation Service.
	 */
	public DesignationService()
	{
		super(DesignationEntity.class, IDesignationRepository.class);
	}

	/**
	 * fetching external roles.
	 * 
	 * @return external roles
	 */
	@LovMethod(name = "externalRoleList")
	public List<ValueLabel> fetchExternalRoles()
	{
		List<ValueLabel> valueLabel = new ArrayList<>();
		ValueLabel value = null;
		for(UserRole role : UserRole.values())
		{
			// add external roles
			if(!role.isInternal())
			{
				value = new ValueLabel(role.name(), role.name());
				valueLabel.add(value);
			}
		}

		return valueLabel;
	}

	/**
	 * Deletes all entity.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
