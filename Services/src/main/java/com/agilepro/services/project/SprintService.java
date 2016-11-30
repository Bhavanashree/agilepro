package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.sprint.SprintDropDown;
import com.agilepro.commons.models.sprint.SprintModel;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.repository.project.ISprintRepository;
import com.yukthi.persistence.repository.RepositoryFactory;
import com.yukthi.webutils.annotations.LovMethod;
import com.yukthi.webutils.annotations.RequestParam;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class SprintService is responsible to save,read,update and delete the
 * sprints.
 */
@Service
public class SprintService extends BaseCrudService<SprintEntity, ISprintRepository>
{
	/**
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The sprint repository.
	 **/
	private ISprintRepository sprintRepository;

	/**
	 * Instantiates a new sprint service.
	 */
	public SprintService()
	{
		super(SprintEntity.class, ISprintRepository.class);
	}

	@PostConstruct
	private void initSprintRepo()
	{
		sprintRepository = repositoryFactory.getRepository(ISprintRepository.class);
	}

	/**
	 * Fetch sprint.
	 *
	 * @param sprintName
	 *            the sprint name
	 * @return the list
	 */
	public List<SprintModel> fetchAllSprint(String sprintName)
	{
		List<SprintModel> sprintModels = new ArrayList<SprintModel>();
		List<SprintEntity> sprintEntity = sprintRepository.fetchAllSprint(sprintName);

		if(sprintEntity != null)
		{
			for(SprintEntity entity : sprintEntity)
			{
				sprintModels.add(super.toModel(entity, SprintModel.class));
			}
		}

		return sprintModels;
	}

	public List<SprintDropDown> fetchSprintDropDown(Long projectId)
	{
		return sprintRepository.fetchSprintByProjId(projectId, new Date());
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{

		repository.deleteAll();
	}
}
