package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
	 * The holiday repo.
	 **/
	private IHolidayRepository holidayRepo;

	/**
	 * Instantiates a new release service.
	 */
	public HolidayService()
	{
		super(HolidayEntity.class, IHolidayRepository.class);
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init()
	{
		holidayRepo = repositoryFactory.getRepository(IHolidayRepository.class);
	}

	/**
	 * Fetch holidays.
	 *
	 * @return the list
	 */
	public List<HolidayModel> fetchHolidays()
	{
		List<HolidayModel> holidayModels = new ArrayList<HolidayModel>();

		holidayRepo.fetchHoliday().forEach(entity -> holidayModels.add(super.toModel(entity, HolidayModel.class)));
		return holidayModels;
	}
}
