package com.agilepro.controller.project;

import static com.agilepro.commons.IAgileproActions.ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE;

import com.yukthi.webutils.annotations.ActionName;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ActionName(ACTION_PREFIX_STORY_ATTACHMENT_MESSAGE)
@RequestMapping("/storyAttachment")
public class StoryAttachmentController 
{

}
