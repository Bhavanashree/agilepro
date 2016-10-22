package com.agilepro.controller.response;

import java.util.List;
import com.agilepro.commons.models.project.StoryNoteModel;
import com.yukthi.webutils.common.models.BasicReadResponse;

public class StoryNoteReadResponse extends BasicReadResponse
{
	private List<StoryNoteModel> publishedNotes;
	
	private StoryNoteModel draftNote;

	public StoryNoteReadResponse(List<StoryNoteModel> publishedNotes, StoryNoteModel draftNote) {
		super();
		this.publishedNotes = publishedNotes;
		this.draftNote = draftNote;
	}

	public List<StoryNoteModel> getPublishedNotes() {
		return publishedNotes;
	}

	public void setPublishedNotes(List<StoryNoteModel> publishedNotes) {
		this.publishedNotes = publishedNotes;
	}

	public StoryNoteModel getDraftNote() {
		return draftNote;
	}

	public void setDraftNote(StoryNoteModel draftNote) {
		this.draftNote = draftNote;
	}
	
	
}
