package com.agilepro.services.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.persistence.entity.project.StoryAttachmentEntity;
import com.agilepro.persistence.repository.admin.IStoryAttachmentRepository;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class StoryAttachmentService extends BaseCrudService<StoryAttachmentEntity, IStoryAttachmentRepository>
{
	public StoryAttachmentService()
	{
		super(StoryAttachmentEntity.class, IStoryAttachmentRepository.class);
	}
}
