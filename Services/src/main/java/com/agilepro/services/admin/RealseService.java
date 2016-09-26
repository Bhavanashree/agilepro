package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.RealseEntity;
import com.agilepro.persistence.repository.admin.IRealseRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class RealseService extends BaseCrudService<RealseEntity, IRealseRepository>
{
	public RealseService()
	{
		super(RealseEntity.class, IRealseRepository.class);
	}
	
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
