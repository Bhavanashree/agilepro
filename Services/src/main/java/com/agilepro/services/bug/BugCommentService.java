package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.bug.BugCommentsModel;
import com.agilepro.persistence.entity.bug.BugCommentsEntity;
import com.agilepro.persistence.repository.bug.IBugCommentRepositoy;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserService;

/**
 * The Class BugCommentService.
 */
@Service
public class BugCommentService extends BaseCrudService<BugCommentsEntity, IBugCommentRepositoy>
{

	/**
	 * The user service.
	 **/
	@Autowired
	private CurrentUserService useCurrentService;

	/** 
	 * The user service.
	 **/
	@Autowired
	private UserService userService;

	/**
	 * Instantiates a new bug service.
	 */
	public BugCommentService()
	{
		super(BugCommentsEntity.class, IBugCommentRepositoy.class);
	}

	/**
	 * Save bug comment.
	 *
	 * @param bugCommentModel
	 *            the bug comment model
	 * @return the bug comments entity
	 */
	public BugCommentsEntity saveBugComment(BugCommentsModel bugCommentModel)
	{
		Long userId = useCurrentService.getCurrentUserDetails().getUserId();

		String userName = userService.fetch(userId).getDisplayName();

		bugCommentModel.setCommentedBy(userName);

		return super.save(bugCommentModel);
	}

	/**
	 * Fetchcomments by bug id.
	 *
	 * @param bug
	 *            the bug
	 * @return the list
	 */
	public List<BugCommentsModel> fetchcommentsByBugId(Long bug)
	{
		List<BugCommentsModel> comments = null;

		repository = repositoryFactory.getRepository(IBugCommentRepositoy.class);
		List<BugCommentsEntity> commententity = repository.fetchbugById(bug);

		BugCommentsModel comment = null;

		if(commententity != null)
		{
			comments = new ArrayList<BugCommentsModel>(commententity.size());

			for(BugCommentsEntity entity : commententity)
			{
				comment = super.toModel(entity, BugCommentsModel.class);
				comments.add(comment);
			}
		}

		return comments;
	}
}
