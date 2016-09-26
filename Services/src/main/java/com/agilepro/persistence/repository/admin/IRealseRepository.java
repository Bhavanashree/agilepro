package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.RealseSearchQuery;
import com.agilepro.commons.models.customer.RealseSearchResult;
import com.agilepro.persistence.entity.admin.RealseEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface IRealseRepository extends IWebutilsRepository<RealseEntity>
{
	@RestrictBySpace
	@SearchQueryMethod(name = "realseSearch", queryModel = RealseSearchQuery.class)
	@OrderBy("name")
	public List<RealseSearchResult> findProjects(SearchQuery searchQuery);

	public void deleteAll();
}
