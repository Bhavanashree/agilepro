package com.agilepro.services.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agilepro.commons.models.projects.StorySearchResult;
import com.yukthi.webutils.repository.search.ISearchResultCustomizer;

/**
 * customized stories results.
 */
public class StorySearchCustomizer implements ISearchResultCustomizer<StorySearchResult>
{
	/**
	 * The Constant titleComparator.
	 **/
	private static final Comparator<StorySearchResult> TITLE_COMPARATOR = new Comparator<StorySearchResult>()
	{
		@Override
		public int compare(StorySearchResult o1, StorySearchResult o2)
		{
			return o1.getTitle().compareTo(o2.getTitle());
		}
	};

	/**
	 * Customize method to get list of results in customized form adding stories
	 * to list & sorting list of stories.
	 */
	@Override
	public List<StorySearchResult> customize(List<StorySearchResult> results)
	{
		Map<Long, List<StorySearchResult>> idToChildren = new HashMap<>();
		List<StorySearchResult> childStories = null;

		for(StorySearchResult story : results)
		{
			childStories = idToChildren.get(story.getParentStoryId());

			if(childStories == null)
			{
				childStories = new ArrayList<>();
				idToChildren.put(story.getParentStoryId(), childStories);
			}

			childStories.add(story);
		}

		List<StorySearchResult> sortedResults = new ArrayList<>(results.size());

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
	public void sortChildren(Map<Long, List<StorySearchResult>> idToChildren, Long parentId, List<StorySearchResult> sortedResults, int indent)
	{
		List<StorySearchResult> lst = idToChildren.get(parentId);

		if(lst == null)
		{
			return;
		}

		Collections.sort(lst, StorySearchCustomizer.TITLE_COMPARATOR);

		for(StorySearchResult br : lst)
		{
			br.setIndent(indent);
			sortedResults.add(br);

			sortChildren(idToChildren, br.getId(), sortedResults, indent + 1);
		}
	}
}
