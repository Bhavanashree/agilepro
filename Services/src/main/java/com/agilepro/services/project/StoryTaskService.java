package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.StoryTaskModel;
import com.agilepro.commons.models.project.TaskRecords;
import com.agilepro.persistence.entity.project.StoryTaskEntity;
import com.agilepro.persistence.repository.project.IStoryTaskRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class TaskService.
 */
@Service
public class StoryTaskService extends BaseCrudService<StoryTaskEntity, IStoryTaskRepository>
{
	/**
	 * The ITaskRepository for executing the queries.
	 **/
	private IStoryTaskRepository taskRepo;

	/**
	 * Instantiates a new task service.
	 */
	public StoryTaskService()
	{
		super(StoryTaskEntity.class, IStoryTaskRepository.class);
	}

	/**
	 * Initialize the taskRepo.
	 */
	@PostConstruct
	private void init()
	{
		taskRepo = repositoryFactory.getRepository(IStoryTaskRepository.class);
	}

	/**
	 * Update task changes.
	 * 
	 * @param taskChangesModel
	 *            new update task changes.
	 */
	public void updateTaskChanges(TaskChangesModel taskChangesModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Map<Long, TaskRecords> changes = taskChangesModel.getTaskChanges();

			for(Long key : changes.keySet())
			{
				TaskRecords taskRecords = changes.get(key);

				Integer actualTime = taskRecords.getActualTime();

				if(actualTime != null)
				{
					if(actualTime <= 0)
					{
						throw new IllegalStateException("Actual time should be greater than 0");
					}

					taskRepo.addExtraTime(key, taskRecords.getActualTime());
				}

				if(taskRecords.getTaskStatus() != null)
				{
					taskRepo.updateTaskStatus(key, taskRecords.getTaskStatus());
				}
			}

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while updating task - " + taskChangesModel, ex);
		}
	}

	/**
	 * Update task status to completed if story status is changed to completed.
	 * 
	 * @param storyId
	 *            task under story id should be updated.
	 * @param status
	 *            completed story status.
	 */
	public void updateTaskStatusByStory(Long storyId, TaskStatus status)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			taskRepo.updateTaskStatusByStory(storyId, status);

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while updating task of provided story", ex);
		}
	}

	/**
	 * Fetch task by story id.
	 * 
	 * @param storyId
	 *            provided story id for fetching task.
	 * @return matching records.
	 */
	public List<StoryTaskModel> fetchTaskByStory(Long storyId)
	{
		List<StoryTaskEntity> tasks = taskRepo.fetchByStoryId(storyId);
		List<StoryTaskModel> taskModels = new ArrayList<StoryTaskModel>();

		tasks.forEach(entity -> taskModels.add(super.toModel(entity, StoryTaskModel.class)));

		return taskModels;
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
