package com.agilepro.services.pokergame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agilepro.commons.models.pokergame.RunningNotesModel;
import com.agilepro.persistence.entity.pokergame.RunningNotesEntity;
import com.agilepro.persistence.repository.pokergame.IRunningNotesRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.webutils.services.BaseCrudService;

/**
 * Running notes service class to access running notes table.
 * 
 * @author Pritam.
 */
@Service
public class RunningNotesService extends BaseCrudService<RunningNotesEntity, IRunningNotesRepository>
{
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
			runningNotesEntities.forEach(entity -> runningNotesModels.add(super.toModel(entity, RunningNotesModel.class)));
		}
		
		return runningNotesModels;
	}
}
