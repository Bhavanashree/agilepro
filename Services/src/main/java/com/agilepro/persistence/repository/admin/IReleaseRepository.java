package com.agilepro.persistence.repository.admin;

import java.util.Date;
import java.util.List;

import com.agilepro.commons.models.customer.ReleaseSearchQuery;
import com.agilepro.commons.models.customer.ReleaseSearchResult;
import com.agilepro.persistence.entity.admin.ReleaseEntity;
import com.yukthi.persistence.repository.annotations.Condition;
import com.yukthi.persistence.repository.annotations.Operator;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;

/**
 * The Interface IReleaseRepository.
 * 
 * @author Pritam
 */
public interface IReleaseRepository extends IWebutilsRepository<ReleaseEntity>
{
	@RestrictBySpace
	@SearchQueryMethod(name = "releaseSearch", queryModel = ReleaseSearchQuery.class)
	@OrderBy("name")
	public List<ReleaseSearchResult> findProjects(SearchQuery searchQuery);

	@RestrictBySpace
	@OrderBy("startDate")
	public List<ReleaseEntity> fetchAllReleaseByDate(@Condition(value = "endDate", op = Operator.GE) Date date);

	public void deleteAll();
}
