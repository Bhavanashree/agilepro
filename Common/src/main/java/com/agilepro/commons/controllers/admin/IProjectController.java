package com.agilepro.commons.controllers.admin;

import java.util.List;

import com.agilepro.commons.models.customer.ProjectModel;
import com.yukthi.webutils.common.RemoteService;
import com.yukthi.webutils.common.models.BaseResponse;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;

/**
 * The Interface IProjectController.
 *
 * @param <RT>
 *            the generic type
 *            
 * @author Pritam
 */
@RemoteService
public interface IProjectController<RT>
{

	/**
	 * Save.
	 *
	 * @param projectModel
	 *            the project model
	 * @param request
	 *            the request
	 * @return the basic save response
	 */
	public BasicSaveResponse save(ProjectModel projectModel, RT request);

	/**
	 * Read.
	 *
	 * @param id
	 *            the id
	 * @return the basic read response
	 */
	public BasicReadResponse<ProjectModel> read(Long id);

	/**
	 * Update.
	 *
	 * @param projectModel
	 *            the project model
	 * @param request
	 *            the request
	 * @return the base response
	 */
	public BaseResponse update(ProjectModel projectModel, RT request);

	/**
	 * Delete.
	 *
	 * @param id
	 *            the id
	 * @return the base response
	 */
	public BaseResponse delete(Long id);

	/**
	 * Fetch projects.
	 *
	 * @return the basic read response
	 */
	public BasicReadResponse<List<ProjectModel>> fetchProjects();

	/**
	 * Delete all.
	 *
	 * @return the base response
	 */
	public BaseResponse deleteAll();
}
