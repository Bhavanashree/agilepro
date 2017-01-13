package com.agilepro.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.project.StoryAndTaskResult;
import com.agilepro.commons.models.project.TaskChangesModel;
import com.agilepro.commons.models.project.TaskModel;
import com.agilepro.commons.models.project.TaskRecords;
import com.agilepro.controller.AgileProUserDetails;
import com.agilepro.persistence.entity.project.TaskEntity;
import com.agilepro.persistence.repository.project.ITaskRepository;
import com.agilepro.services.admin.CustomerService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.InvalidRequestParameterException;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.utils.WebUtils;

/**
 * The Class TaskService.
 */
@Service
public class TaskService extends BaseCrudService<TaskEntity, ITaskRepository>
{

	/**
	 * The current user service.
	 **/
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * Used to fetch customer info.
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * The story repo.
	 **/
	private ITaskRepository taskRepo;

	/**
	 * Instantiates a new sprint service.
	 */
	public TaskService()
	{
		super(TaskEntity.class, ITaskRepository.class);
	}

	/**
	 * Initialize the iprojectMemberRepository.
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
				
				if(taskRecords.getActualTime() != null)
				{
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
	 * Search by story.
	 *
	 * @param storyId
	 *            the story id
	 * @return the list
	 */
	public List<StoryAndTaskResult> searchByStory(Long storyId)
	{
		return null;
	}

	/**
	 * Delete task.
	 *
	 * @param TaskId
	 *            the task id
	 */
	public void deleteTask(long TaskId)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{

			TaskEntity taskEntity = super.repository.findById(TaskId);

			if(taskEntity == null)
			{
				throw new InvalidRequestParameterException("Invalid taskId id specified - " + TaskId);
			}

			Long tskId = taskEntity.getId();

			// delete backlogEntity
			super.deleteById(taskEntity.getId());

			transaction.commit();
		} catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred while deleting task - " + TaskId, ex);
		}
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
