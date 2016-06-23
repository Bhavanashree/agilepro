package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.TagsEntity;
import com.agilepro.persistence.repository.admin.ITagsRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class TagsService extends BaseCrudService<TagsEntity, ITagsRepository>
{
	public TagsService()
	{
		super(TagsEntity.class, ITagsRepository.class);
	}
	
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
