package com.agilepro.services.pokergame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.RunningNotesModel;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.entity.admin.ProjectMemberEntity;
import com.agilepro.persistence.entity.pokergame.RunningNotesEntity;
import com.agilepro.persistence.repository.pokergame.IRunningNotesRepository;
import com.agilepro.services.admin.EmployeeService;
import com.agilepro.services.admin.ProjectMemberService;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.UserService;

/**
 * Running notes service class to access running notes table.
 * 
 * @author Pritam.
 */
@Service
public class RunningNotesService extends BaseCrudService<RunningNotesEntity, IRunningNotesRepository>
{
	/**
	 * User service.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Project members service.
	 */
	@Autowired
	private ProjectMemberService projectMemberService;
	
	/**
	 * Employee service.
	 */
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * Instantiate new RunningNotesService.
	 */
	public RunningNotesService()
	{
		super(RunningNotesEntity.class, IRunningNotesRepository.class);
	}
	
	/**
	 * Save running notes.
	 * 
	 * @param runningNotesModel new model for saving running notes.
	 * @return newly created id.
	 */
	public Long saveNewNote(RunningNotesModel runningNotesModel)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			Long employeeId = userService.fetch(runningNotesModel.getActiveUserId()).getBaseEntityId();
			Long projectMemberId = projectMemberService.getProjectMemberId(runningNotesModel.getProjectId(), employeeId);
			
			runningNotesModel.setProjectMemberId(projectMemberId);
			runningNotesModel.setDate(new Date());
			
			RunningNotesEntity runningNotesEntity = super.save(runningNotesModel);
			
			transaction.commit();
			
			return runningNotesEntity.getId();
		}
		catch(Exception ex)
		{
			throw new IllegalStateException("An error occurred  while saving  running notes - " + runningNotesModel, ex);
		}
	}
	
	/**
	 * Fetch Running notes for the selected story or bug.
	 * 
	 * @param isBug signifies whether the item is bug or story.
	 * @param mappingId id of story or bug.
	 * @return matching records.
	 */
	public List<RunningNotesModel> fetchRunningNotes(Boolean isBug, Long mappingId)
	{
		List<RunningNotesModel> runningNotesModels = new ArrayList<RunningNotesModel>();
		List<RunningNotesEntity> runningNotesEntities = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		
		if(isBug)
		{
			runningNotesEntities = repository.fetchRunningNotesByBug(mappingId);
		}
		else
		{
			runningNotesEntities = repository.fetchRunningNotesByStory(mappingId);
		}
		
		if(runningNotesEntities != null)
		{
			runningNotesEntities.forEach(entity -> 
			{
				Date date = entity.getDate();
				RunningNotesModel model = super.toModel(entity, RunningNotesModel.class);
				model.setDisplayDate(dateFormat.format(date));
				model.setTime(timeFormat.format(date));
				
				ProjectMemberEntity projectMemberEntity = projectMemberService.fetch(model.getProjectMemberId());
				EmployeeEntity employee = employeeService.fetch(projectMemberEntity.getEmployee().getId());
				
				model.setProjectMemberName(employee.getName());
				runningNotesModels.add(model);
			});
		}
		
		return runningNotesModels;
	}
}
