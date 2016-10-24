package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.bug.BugAttachmentModel;
import com.agilepro.commons.models.project.StoryAttachmentModel;
import com.agilepro.persistence.entity.bug.BugAttachmentEntity;
import com.agilepro.persistence.repository.bug.IBugAttachmentRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class BugAttachmentService.
 */
@Service
public class BugAttachmentService extends BaseCrudService<BugAttachmentEntity, IBugAttachmentRepository>
{
	/**
	 * The repository factory.
	 **/
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The bug attachment repository.
	 **/
	private IBugAttachmentRepository bugAttachmentRepository;

	/**
	 * Instantiates a new bug attachment service.
	 */
	public BugAttachmentService()
	{
		super(BugAttachmentEntity.class, IBugAttachmentRepository.class);
	}

	/**
	 * Inits the bugAttachmentRepo.
	 */
	@PostConstruct
	public void init()
	{
		bugAttachmentRepository = repositoryFactory.getRepository(IBugAttachmentRepository.class);
	}

	/**
	 * Fetch attachments.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<BugAttachmentModel> fetchAttachments(Long bugId)
	{
		List<BugAttachmentModel> bugAttachmentModels = new ArrayList<BugAttachmentModel>();

		bugAttachmentRepository.fetchAttachmentByBugId(bugId).forEach(entity -> bugAttachmentModels.add(super.toModel(entity, BugAttachmentModel.class)));

		bugAttachmentModels.forEach(model -> model.setLinkForDisplay(StringUtils.abbreviate(model.getLink(), 30)));

		return bugAttachmentModels;
	}
}
