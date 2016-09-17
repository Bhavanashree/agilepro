//package com.agilepro.services.common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.agilepro.commons.TaskSearchStatus;
//import com.agilepro.commons.models.project.StoryAndTaskResult;
//import com.agilepro.services.project.StoryService;
//import com.agilepro.services.project.TaskService;
//import com.yukthi.webutils.repository.search.ISearchResultCustomizer;
//
///**
// * The Class TaskSearchCustomizer.
// */
//public class TaskSearchCustomizer
//{
//	/**
//	 * The story service.
//	 **/
//	@Autowired
//	private StoryService storyService;
//
//	/**
//	 * The task service.
//	 **/
//	@Autowired
//	private TaskService taskService;
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.yukthi.webutils.repository.search.ISearchResultCustomizer#customize(
//	 * java.util.List)
//	 */
//	@Override
//	public List<StoryAndTaskResult> customize(List<StoryAndTaskResult> storyResults)
//	{
//		List<StoryAndTaskResult> storiesAndTask = new ArrayList<StoryAndTaskResult>();
//
//		List<StoryAndTaskResult> taskResults;
//
//		for(StoryAndTaskResult story : storyResults)
//		{
//			story.setType(TaskSearchStatus.STORY);
//			storiesAndTask.add(story);
//			taskResults = taskService.searchByStory(story.getId());
//
//			for(StoryAndTaskResult task : taskResults)
//			{
//				
//				task.setType(TaskSearchStatus.TASK);
//				task.setIndent(1);
//				storiesAndTask.add(task);
//			}
//		}
//
//		return storiesAndTask;
//	}
//}
