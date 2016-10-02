package com.agilepro.persistence.entity.project;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.models.project.StoryNoteModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

@Table(name = "STORY_NOTE")
public class StoryNoteEntity extends WebutilsEntity
{
	@ManyToOne
	@PropertyMapping(type = StoryNoteModel.class, from="storyId", subproperty="id")
	private StoryEntity story; 
}
