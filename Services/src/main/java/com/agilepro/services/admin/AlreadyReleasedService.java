package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.AlreadyReleasedEntity;
import com.agilepro.persistence.repository.admin.IAlreadyReleasedRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class AlreadyReleasedService extends BaseCrudService<AlreadyReleasedEntity, IAlreadyReleasedRepository>
{
	public AlreadyReleasedService()
	{
		super(AlreadyReleasedEntity.class, IAlreadyReleasedRepository.class);
	}
}
