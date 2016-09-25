package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import com.agilepro.commons.models.project.StoryAttachmentModel;
import com.agilepro.persistence.entity.project.StoryAttachmentEntity;
import com.agilepro.persistence.repository.admin.IStoryAttachmentRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class StoryAttachmentService.
 * 
 * @author Pritam
 */
@Service
public class StoryAttachmentService extends BaseCrudService<StoryAttachmentEntity, IStoryAttachmentRepository>
{
	/**
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The istory attachment repository.
	 **/
	private IStoryAttachmentRepository istoryAttachmentRepository;

	/**
	 * Instantiates a new story attachment service.
	 */
	public StoryAttachmentService()
	{
		super(StoryAttachmentEntity.class, IStoryAttachmentRepository.class);
	}
	
	/**
	 * Inits the istoryAttachmentRepository.
	 */
	@PostConstruct
	public void init()
	{
		istoryAttachmentRepository = repositoryFactory.getRepository(IStoryAttachmentRepository.class);
	}
	
	/**
	 * Fetch attachments.
	 *
	 * @param storyId the story id
	 * @return the list
	 */
	public List<StoryAttachmentModel> fetchAttachments(Long storyId)
	{
		List<StoryAttachmentModel> storyAttachmentModels = new ArrayList<StoryAttachmentModel>();
		
		istoryAttachmentRepository.fetchAttachmentByStoryId(storyId).forEach(entity -> storyAttachmentModels.add(super.toModel(entity, StoryAttachmentModel.class)));
		
		storyAttachmentModels.forEach(model -> model.setLinkForDisplay(StringUtils.abbreviate(model.getLink(), 30)));
		
		return storyAttachmentModels;
	}
}
