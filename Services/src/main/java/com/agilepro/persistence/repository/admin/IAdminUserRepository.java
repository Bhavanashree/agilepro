package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.commons.models.admin.AdminUserSearchQuery;
import com.agilepro.commons.models.admin.AdminUserSearchResult;
import com.yukthi.persistence.repository.annotations.OrderBy;
import com.yukthi.persistence.repository.search.SearchQuery;
import com.yukthi.webutils.annotations.SearchQueryMethod;
import com.yukthi.webutils.repository.IWebutilsRepository;
import com.yukthi.webutils.repository.UserEntity;

/**
 * The Interface IAdminUserRepository.
 */
public interface IAdminUserRepository extends IWebutilsRepository<UserEntity>
{
	/**
	 * Delete all.
	 */
	public void deleteAll();
	
	/**
	 * Find admin user.
	 *
	 * @param searchQuery the search query
	 * @return the list
	 */
	@SearchQueryMethod(name = "adminUserSearch", queryModel = AdminUserSearchQuery.class)
	@OrderBy("userName")
	public List<AdminUserSearchResult> findAdminUser(SearchQuery searchQuery);
}
