package com.agilepro.commons.controllers.notification;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.yukthi.webutils.common.annotations.IgnoreField;
import com.yukthi.webutils.common.annotations.Model;
import com.yukthi.webutils.common.annotations.NonDisplayable;

/**
 * The Class AgileProMailTemplateModel.
 */
@Model
public class AgileProMailTemplateModel
{
	/**
	 * Id of the mail template.
	 */
	@NonDisplayable
	private Long id;

	/**
	 * Version of mail tample entity.
	 */
	@NonDisplayable
	private Integer version;

	/**
	 * Name of template used for building this data object.
	 */
	@NotNull
	@Size(min = 1, max = 100)
	private String templateName;

	/**
	 * Subject template of the mail.
	 */
	@NotNull
	@Size(min = 1, max = 1000)
	private String subjectTemplate;

	/**
	 * Content template of the mail.
	 */
	@NotNull
	@Size(min = 1)
	private String contentTemplate;

	/**
	 * Customization object that can be use by application to set customization
	 * parameters that will be used for template processing.
	 */
	@IgnoreField
	private Object customization;

	/**
	 * The receiptInfo info.
	 **/
	@IgnoreField
	private RecipientInfo toRecipientInfo;
	
	@IgnoreField
	private RecipientInfo ccRecipientInfo;

	public RecipientInfo getToRecipientInfo()
	{
		return toRecipientInfo;
	}

	public void setToRecipientInfo(RecipientInfo toRecipientInfo)
	{
		this.toRecipientInfo = toRecipientInfo;
	}

	public RecipientInfo getCcRecipientInfo()
	{
		return ccRecipientInfo;
	}

	public void setCcRecipientInfo(RecipientInfo ccRecipientInfo)
	{
		this.ccRecipientInfo = ccRecipientInfo;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * Gets the template name.
	 *
	 * @return the template name
	 */
	public String getTemplateName()
	{
		return templateName;
	}

	/**
	 * Sets the template name.
	 *
	 * @param templateName
	 *            the new template name
	 */
	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	/**
	 * Gets the subject template.
	 *
	 * @return the subject template
	 */
	public String getSubjectTemplate()
	{
		return subjectTemplate;
	}

	/**
	 * Sets the subject template.
	 *
	 * @param subjectTemplate
	 *            the new subject template
	 */
	public void setSubjectTemplate(String subjectTemplate)
	{
		this.subjectTemplate = subjectTemplate;
	}

	/**
	 * Gets the content template.
	 *
	 * @return the content template
	 */
	public String getContentTemplate()
	{
		return contentTemplate;
	}

	/**
	 * Sets the content template.
	 *
	 * @param contentTemplate
	 *            the new content template
	 */
	public void setContentTemplate(String contentTemplate)
	{
		this.contentTemplate = contentTemplate;
	}

	/**
	 * Gets the customization.
	 *
	 * @return the customization
	 */
	public Object getCustomization()
	{
		return customization;
	}

	/**
	 * Sets the customization.
	 *
	 * @param customization
	 *            the new customization
	 */
	public void setCustomization(Object customization)
	{
		this.customization = customization;
	}
}
