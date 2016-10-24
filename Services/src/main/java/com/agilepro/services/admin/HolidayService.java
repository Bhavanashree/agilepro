package com.agilepro.services.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.agilepro.commons.ListOfdays;
import com.agilepro.commons.UserRole;
import com.agilepro.persistence.entity.admin.HolidayEntity;
import com.agilepro.persistence.repository.admin.IHolidayRepository;
import com.yukthi.webutils.annotations.LovMethod;
import com.yukthi.webutils.common.models.ValueLabel;
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

	@LovMethod(name = "listOfDaysList")
	public List<ValueLabel> fetchDays()
	{
		List<ValueLabel> valueLabel = new ArrayList<>();
		ValueLabel value = null;
		for(ListOfdays day : ListOfdays.values())
		{
			value = new ValueLabel(day.name(), day.name());
			valueLabel.add(value);
		}

		return valueLabel;
	}
}
