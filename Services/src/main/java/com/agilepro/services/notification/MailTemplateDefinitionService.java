package com.agilepro.services.notification;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.notification.MailTemplateDefinitionModel;
import com.agilepro.commons.models.project.SprintModel;
import com.agilepro.persistence.entity.notification.MailTemplateDefinitionEntity;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.entity.project.StoryEntity;
import com.agilepro.persistence.repository.notification.IMailTemplateDefinitionRepository;
import com.agilepro.persistence.repository.project.ISprintRepository;
import com.agilepro.persistence.repository.project.IStoryRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.services.BaseCrudService;

@Service
public class MailTemplateDefinitionService extends BaseCrudService<MailTemplateDefinitionEntity, IMailTemplateDefinitionRepository>
{
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;
	
	private IMailTemplateDefinitionRepository mailRepo;

	public MailTemplateDefinitionService()
	{
		super(MailTemplateDefinitionEntity.class, IMailTemplateDefinitionRepository.class);
	}

	/**
	 * Initialize the iprojectMemberRepository.
	 */
	@PostConstruct
	private void init()
	{
		mailRepo = repositoryFactory.getRepository(IMailTemplateDefinitionRepository.class);
	}
	
	public List<MailTemplateDefinitionModel> fetchAllMailDefinitionTemplate()
	{
		List<MailTemplateDefinitionModel> mailModels = null;

		List<MailTemplateDefinitionEntity> mailentity = mailRepo.fetchAllMailDefinition();

		if(mailentity != null)
		{
			mailModels = new ArrayList<MailTemplateDefinitionModel>(mailentity.size());

			for(MailTemplateDefinitionEntity entity : mailentity)
			{
				mailModels.add(super.toModel(entity, MailTemplateDefinitionModel.class));
			}
		}

		return mailModels;
	}
	
}
