package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.admin.HolidayModel;
import com.agilepro.persistence.entity.admin.HolidayEntity;
import com.agilepro.persistence.repository.admin.IHolidayRepository;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * The Class HolidayService.
 */
@Service
public class HolidayService extends BaseCrudService<HolidayEntity, IHolidayRepository>
{
	/**
	 * Instantiates a new release service.
	 */
	public HolidayService()
	{
		super(HolidayEntity.class, IHolidayRepository.class);
	}

	/**
	 * Fetch holidays.
	 *
	 * @return the list
	 */
	public List<HolidayModel> fetchHolidays()
	{
		List<HolidayModel> holidayModels = new ArrayList<HolidayModel>();

		repository.fetchHoliday().forEach(entity -> holidayModels.add(super.toModel(entity, HolidayModel.class)));
		return holidayModels;
	}
}
