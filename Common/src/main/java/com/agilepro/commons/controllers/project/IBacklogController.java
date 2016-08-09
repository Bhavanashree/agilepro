package com.agilepro.commons.controllers.project;

import java.util.List;

import com.agilepro.commons.models.project.BacklogModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IStoryController.
 */
@RemoteService
public interface IBacklogController
{

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(BacklogModel model);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<BacklogModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the base response
	 */
	public BaseResponse update(BacklogModel model);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	public BaseResponse deleteAll();

	/**
	 * Fetch all story.
	 *
	 * @param storyTitle the story title
	 * @return the basic read response
	 */
	public BasicReadResponse<List<BacklogModel>> fetchAllStory(String storyTitle);

	/**
	 * Fetch story by sprint id.
	 *
	 * @param sprintId the sprint id
	 * @return the basic read response
	 */
	public BasicReadResponse<List<BacklogModel>> fetchStoryBySprintId(Long sprintId);
}
