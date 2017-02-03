package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.agilepro.commons.models.sprint.SprintDropDown;
import com.agilepro.commons.models.sprint.SprintModel;
import com.agilepro.persistence.entity.project.SprintEntity;
import com.agilepro.persistence.repository.project.ISprintRepository;
import com.yukthi.utils.DateUtil;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class SprintService is responsible to save,read,update and delete the
 * sprints.
 */
@Service
public class SprintService extends BaseCrudService<SprintEntity, ISprintRepository>
{
	/**
	 * Instantiates a new sprint service.
	 */
	public SprintService()
	{
		super(SprintEntity.class, ISprintRepository.class);
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
		List<SprintEntity> sprintEntity = repository.fetchAllSprint(sprintName);

		if(sprintEntity != null)
		{
			for(SprintEntity entity : sprintEntity)
			{
				sprintModels.add(super.toModel(entity, SprintModel.class));
			}
		}

		return sprintModels;
	}

	/**
	 * Fetch sprint according to project.
	 * 
	 * @param projectId
	 *            provided project id for fetching the sprint.
	 * @return the matching records.
	 */
	public List<SprintDropDown> fetchSprintDropDown(Long projectId)
	{
		return repository.fetchSprintByProjId(projectId, DateUtil.trimTime(new Date()) );
	}

	/**
	 * Deletes all records.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
