package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.TagSearchQuery;
import com.agilepro.commons.models.customer.TagSearchResult;
import com.agilepro.persistence.entity.admin.TagEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.LovQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.common.models.ValueLabel;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ITagRepository.
 * 
 * @author Pritam
 */
public interface ITagRepository extends IWebutilsRepository<TagEntity>
{
	/**
	 * Find tag.
	 *
	 * @param searchQuery
	 *            the search query
	 * @return the list
	 */
	@RestrictBySpace
	@SearchQueryMethod(name = "tagSearch", queryModel = TagSearchQuery.class)
	@OrderBy("name")
	public List<TagSearchResult> findTag(SearchQuery searchQuery);

	@LovQuery(name = "tagLov", valueField = "id", labelField = "name")
	public List<ValueLabel> findTagLov();

	/**
	 * Delete All.
	 */
	public void deleteAll();
}
