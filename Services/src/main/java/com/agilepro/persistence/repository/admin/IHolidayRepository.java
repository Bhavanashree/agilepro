package com.agilepro.persistence.repository.admin;

import java.util.List;

import com.agilepro.persistence.entity.admin.HolidayEntity;
import com.yukthi.webutils.annotations.RestrictBySpace;
import com.yukthi.webutils.repository.IWebutilsRepository;

public interface IHolidayRepository  extends IWebutilsRepository<HolidayEntity>
{
	@RestrictBySpace
	public List<HolidayEntity> fetchHoliday();
}
