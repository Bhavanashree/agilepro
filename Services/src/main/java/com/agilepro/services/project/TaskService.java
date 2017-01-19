package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.TaskStatus;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.TaskModel;
import com.agilepro.commons.models.project.TaskRecords;
import com.agilepro.persistence.entity.project.TaskEntity;
import com.agilepro.persistence.repository.project.ITaskRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class TaskService.
 */
@Service
public class TaskService extends BaseCrudService<TaskEntity, ITaskRepository>
{
	/**
	 * The ITaskRepository for executing the queries.
	 **/
	private ITaskRepository taskRepo;

	/**
	 * Instantiates a new task service.
	 */
	public TaskService()
	{
		super(TaskEntity.class, ITaskRepository.class);
	}

	/**
	 * Initialize the taskRepo.
	 */
	@PostConstruct
	private void init()
	{
		taskRepo = repositoryFactory.getRepository(ITaskRepository.class);
	}

	/**
	 * Update task changes.
	 * 
	 * @param taskChangesModel new update task changes.
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
	 * @param storyId provided story id for fetching task.
	 * @return matching records.
	 */
	public List<TaskModel> fetchTaskByStory(Long storyId)
	{
		List<TaskEntity> tasks = taskRepo.fetchByStoryId(storyId);
		List<TaskModel> taskModels = new ArrayList<TaskModel>();
		
		tasks.forEach(entity -> taskModels.add(super.toModel(entity, TaskModel.class)));
		
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
