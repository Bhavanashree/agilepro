package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.agilepro.commons.models.bug.BugAttachmentModel;
import com.agilepro.persistence.entity.bug.BugAttachmentEntity;
import com.agilepro.persistence.repository.bug.IBugAttachmentRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class BugAttachmentService.
 */
@Service
public class BugAttachmentService extends BaseCrudService<BugAttachmentEntity, IBugAttachmentRepository>
{
	/**
	 * Instantiates a new bug attachment service.
	 */
	public BugAttachmentService()
	{
		super(BugAttachmentEntity.class, IBugAttachmentRepository.class);
	}

	/**
	 * Fetch attachments.
	 *
	 * @param bugId
	 *            the bug id
	 * @return the list
	 */
	public List<BugAttachmentModel> fetchAttachments(Long bugId)
	{
		List<BugAttachmentModel> bugAttachmentModels = new ArrayList<BugAttachmentModel>();

		repository.fetchAttachmentByBugId(bugId).forEach(entity -> bugAttachmentModels.add(super.toModel(entity, BugAttachmentModel.class)));

		bugAttachmentModels.forEach(model -> model.setLinkForDisplay(StringUtils.abbreviate(model.getLink(), 30)));

		return bugAttachmentModels;
	}
}
