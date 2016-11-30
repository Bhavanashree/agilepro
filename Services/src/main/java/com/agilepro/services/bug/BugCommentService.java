package com.agilepro.services.bug;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.bug.BugCommentsModel;
import com.agilepro.persistence.entity.bug.BugCommentsEntity;
import com.agilepro.persistence.repository.bug.IBugCommentRepositoy;
import com.yukthi.persistence.repository.RepositoryFactory;
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
	 * The repository factory.
	 */
	@Autowired
	private RepositoryFactory repositoryFactory;

	/**
	 * The bug repo.
	 **/
	private IBugCommentRepositoy bugCommentRepo;

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
	 * Initialize the iprojectMemberRepository.
	 */
	@PostConstruct
	private void init()
	{
		bugCommentRepo = repositoryFactory.getRepository(IBugCommentRepositoy.class);
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
	 * Fetch comments by bug id.
	 *
	 * @param bug
	 *            the bug
	 * @return the list
	 */
	public List<BugCommentsModel> fetchcommentsByBugId(Long bug)
	{
		List<BugCommentsModel> comments = null;

		bugCommentRepo = repositoryFactory.getRepository(IBugCommentRepositoy.class);
		List<BugCommentsEntity> commententity = bugCommentRepo.fetchbugById(bug);

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
