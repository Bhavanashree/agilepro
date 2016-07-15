package com.agilepro.services.admin;

import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.admin.TagEntity;
import com.agilepro.persistence.repository.admin.ITagRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class TagService.
 * 
 * @author Pritam
 */
@Service
public class TagService extends BaseCrudService<TagEntity, ITagRepository>
{
	public TagService()
	{
		super(TagEntity.class, ITagRepository.class);
	}
	
	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
