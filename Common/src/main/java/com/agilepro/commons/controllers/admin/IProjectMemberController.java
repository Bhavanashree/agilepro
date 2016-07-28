package com.agilepro.commons.controllers.admin;

import java.util.List;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IProjectMemberController.
 * 
 * @author Pritam
 */
@RemoteService
public interface IProjectMemberController
{
	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the basic save response
	 */
	public BasicSaveResponse save(ProjectMemberModel model);

	/**
	 * Fetch project members.
	 *
	 * @param projectId
	 *            the project id
	 * @return the basic read response
	 */
	public BasicReadResponse<List<ProjectMemberModel>> fetchProjectMembers(Long projectId);

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
}
