package com.agilepro.controller.pokergame;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_RUNNING_NOTES;
import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_SAVE;

import java.util.List;

import static com.agilepro.commons.IAgileproActions.ACTION_TYPE_FETCH_RUNNING_NOTES;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilepro.commons.models.pokergame.RunningNotesModel;
import com.agilepro.services.pokergame.RunningNotesService;
import com.yukthi.webutils.annotations.ActionName;
import com.yukthi.webutils.common.models.BasicReadResponse;
import com.yukthi.webutils.common.models.BasicSaveResponse;
import com.yukthi.webutils.controllers.BaseController;

/**
 * Running notes controller for receiving request for running notes and sending back the response.
 * 
 * @author Pritam.
 */
@RestController
@ActionName(ACTION_PREFIX_RUNNING_NOTES)
@RequestMapping(value = "/runningNotes")
public class RunningNotesController extends BaseController
{
	/**
	 * Running notes service.
	 */
	@Autowired
	private RunningNotesService runningNotesService;
	
	@ActionName(ACTION_TYPE_SAVE)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BasicSaveResponse saveRunningNotes(@RequestBody @Valid RunningNotesModel runningNotesModel)
	{
		return  new BasicSaveResponse(runningNotesService.saveNewNote(runningNotesModel));
	}
	
	@ActionName(ACTION_TYPE_FETCH_RUNNING_NOTES)
	@RequestMapping(value = "/fetchRunningNotes", method = RequestMethod.GET)
	@ResponseBody
	public BasicReadResponse<List<RunningNotesModel>> fetchRunnginNotes(@RequestParam(value = "mappingId") Long mappingId, @RequestParam(value = "isBug") Boolean isBug)
	{
		return new BasicReadResponse<List<RunningNotesModel>>(runningNotesService.fetchRunningNotes(isBug, mappingId));	
	}
}
