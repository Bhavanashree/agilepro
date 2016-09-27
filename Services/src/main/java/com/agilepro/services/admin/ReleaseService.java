package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.customer.ReleaseModel;
import com.agilepro.persistence.entity.admin.ReleaseEntity;
import com.agilepro.persistence.repository.admin.IProjectRepository;
import com.agilepro.persistence.repository.admin.IReleaseRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class ReleaseService extends BaseCrudService<ReleaseEntity, IReleaseRepository>
{
	private IReleaseRepository ireleaseRepository;
	
	public ReleaseService()
	{
		super(ReleaseEntity.class, IReleaseRepository.class);
	}
	
	@PostConstruct
	private void init()
	{
		ireleaseRepository = repositoryFactory.getRepository(IReleaseRepository.class);
	}
	
	public List<ReleaseModel> fetchAllRelease()
	{
		List<ReleaseEntity> releaseEntities = ireleaseRepository.fetchAllRelease();
		
		List<ReleaseModel> releaseModels = new ArrayList<ReleaseModel>(releaseEntities.size());
		
		releaseEntities.forEach(entity -> releaseModels.add(super.toModel(entity, ReleaseModel.class)));
		
		return releaseModels;
	}
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
