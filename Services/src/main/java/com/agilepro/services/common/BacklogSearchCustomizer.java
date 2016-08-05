package com.agilepro.services.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agilepro.commons.models.projects.BacklogSearchResult;
import com.yukthi.webutils.repository.search.ISearchResultCustomizer;

/**
 * customized stories results are obtained from StorySearchCustomizer.
 */
public class BacklogSearchCustomizer implements ISearchResultCustomizer<BacklogSearchResult>
{
	/**
	 * The Constant titleComparator.
	 **/
	private static final Comparator<BacklogSearchResult> TITLE_COMPARATOR = new Comparator<BacklogSearchResult>()
	{
		@Override
		public int compare(BacklogSearchResult o1, BacklogSearchResult o2)
		{
			return o1.getTitle().compareTo(o2.getTitle());
		}
	};

	/**
	 * Customize method to get list of results in customized form adding stories
	 * to list & sorting list of stories.
	 */
	@Override
	public List<BacklogSearchResult> customize(List<BacklogSearchResult> results)
	{
		Map<Long, List<BacklogSearchResult>> idToChildren = new HashMap<>();
		List<BacklogSearchResult> childStories = null;

		for(BacklogSearchResult story : results)
		{
			childStories = idToChildren.get(story.getParentStoryId());

			if(childStories == null)
			{
				childStories = new ArrayList<>();
				idToChildren.put(story.getParentStoryId(), childStories);
			}

			childStories.add(story);
		}

		List<BacklogSearchResult> sortedResults = new ArrayList<>(results.size());

		// sort parent stories
		sortChildren(idToChildren, null, sortedResults, 0);

		return sortedResults;
	}

	/**
	 * Sort children.
	 *
	 * @param idToChildren
	 *            the id to children
	 * @param parentId
	 *            the parent id
	 * @param sortedResults
	 *            the sorted results
	 * @param indent
	 *            the indent
	 * 
	 */
	public void sortChildren(Map<Long, List<BacklogSearchResult>> idToChildren, Long parentId, List<BacklogSearchResult> sortedResults, int indent)
	{
		List<BacklogSearchResult> lst = idToChildren.get(parentId);

		if(lst == null)
		{
			return;
		}

		Collections.sort(lst, BacklogSearchCustomizer.TITLE_COMPARATOR);

		for(BacklogSearchResult br : lst)
		{
			br.setIndent(indent);
			sortedResults.add(br);

			sortChildren(idToChildren, br.getId(), sortedResults, indent + 1);
		}
	}
}
