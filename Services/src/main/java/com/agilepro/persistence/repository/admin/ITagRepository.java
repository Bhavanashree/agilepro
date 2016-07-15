package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.TagSearchQuery;
import com.agilepro.commons.models.customer.TagSearchResult;
import com.agilepro.persistence.entity.admin.TagEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface ITagRepository.
 * 
 * @author Pritam
 */
public interface ITagRepository extends IWebutilsRepository<TagEntity> 
{
	@RestrictBySpace
	@SearchQueryMethod(name = "tagSearch", queryModel = TagSearchQuery.class)
	@OrderBy("name")
	public List<TagSearchResult> findEmployee(SearchQuery searchQuery);
	
	/**
	 * Delete All.
	 */
	public void deleteAll();
}
