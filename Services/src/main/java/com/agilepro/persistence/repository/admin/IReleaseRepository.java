package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.customer.ReleaseSearchQuery;
import com.agilepro.commons.models.customer.ReleaseSearchResult;
import com.agilepro.persistence.entity.admin.ReleaseEntity;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface IReleaseRepository extends IWebutilsRepository<ReleaseEntity>
{
	@RestrictBySpace
	@SearchQueryMethod(name = "releaseSearch", queryModel = ReleaseSearchQuery.class)
	@OrderBy("name")
	public List<ReleaseSearchResult> findProjects(SearchQuery searchQuery);

	@RestrictBySpace
	public List<ReleaseEntity> fetchAllRelease();
	
	public void deleteAll();
}
