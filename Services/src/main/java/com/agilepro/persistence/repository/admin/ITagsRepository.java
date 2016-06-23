package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.TagsSearchQuery;
import com.agilepro.commons.models.customer.TagsSearchResult;
import com.agilepro.persistence.entity.admin.TagsEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface ITagsRepository extends IWebutilsRepository<TagsEntity> 
{
	@RestrictBySpace
	@SearchQueryMethod(name = "tagsSearch", queryModel = TagsSearchQuery.class)
	@OrderBy("name")
	public List<TagsSearchResult> findEmployee(SearchQuery searchQuery);
	
	/**
	 * Delete All.
	 */
	public void deleteAll();
}
