package com.agilepro.persistence.entity.bug;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.bug.BugCommentsModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.annotations.ExtendableEntity;
import com.yukthi.webutils.repository.WebutilsExtendableEntity;

/**
 * The Class BugComment.
 */
@ExtendableEntity(name = "BugComment")
@Table(name = "COMMENTS")
public class BugCommentsEntity extends WebutilsExtendableEntity
{

	/**
	 * The content.
	 **/
	@Column(name = "content")
	private String content;

	/**
	 * The employee.
	 **/
	@Column(name = "BUG_COMMENTED_BY")
	private String commentedBy;

	/**
	 * comments.
	 **/
	@ManyToOne
	@PropertyMapping(type = BugCommentsModel.class, from = "bugId", subproperty = "id")
	@Column(name = "BUG_ID")
	private BugEntity bug;

	/**
	 * The owner.
	 **/
	@ManyToOne
	@PropertyMapping(type = BugCommentsModel.class, from = "employeeId", subproperty = "id")
	@Column(name = "EMPLOYEE_ID")
	private EmployeeEntity employees;

	/**
	 * The comment status.
	 **/
	@Column(name = "COMMENT_STATUS")
	private String commentStatus;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * Gets the commented by.
	 *
	 * @return the commented by
	 */
	public String getCommentedBy()
	{
		return commentedBy;
	}

	/**
	 * Sets the commented by.
	 *
	 * @param commentedBy
	 *            the new commented by
	 */
	public void setCommentedBy(String commentedBy)
	{
		this.commentedBy = commentedBy;
	}

	/**
	 * Gets the bug.
	 *
	 * @return the bug
	 */
	public BugEntity getBug()
	{
		return bug;
	}

	/**
	 * Sets the bug.
	 *
	 * @param bug
	 *            the new bug
	 */
	public void setBug(BugEntity bug)
	{
		this.bug = bug;
	}

	/**
	 * Gets the employees.
	 *
	 * @return the employees
	 */
	public EmployeeEntity getEmployees()
	{
		return employees;
	}

	/**
	 * Sets the employees.
	 *
	 * @param employees
	 *            the new employees
	 */
	public void setEmployees(EmployeeEntity employees)
	{
		this.employees = employees;
	}

	/**
	 * Gets the comment status.
	 *
	 * @return the comment status
	 */
	public String getCommentStatus()
	{
		return commentStatus;
	}

	/**
	 * Sets the comment status.
	 *
	 * @param commentStatus
	 *            the new comment status
	 */
	public void setCommentStatus(String commentStatus)
	{
		this.commentStatus = commentStatus;
	}
}
