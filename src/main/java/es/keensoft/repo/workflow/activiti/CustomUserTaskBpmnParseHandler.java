package es.keensoft.repo.workflow.activiti;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomUserTaskBpmnParseHandler extends AbstractBpmnParseHandler<UserTask> {
	
	private static final Log logger = LogFactory.getLog(CustomUserTaskBpmnParseHandler.class);
	
	private TaskListener completeTaskListener;
	private TaskListener createTaskListener;
	private TaskListener assignmentTaskListener;
	private ExecutionListener multiInstanceExecutionListener;

	protected Class<? extends BaseElement> getHandledType() {
		return UserTask.class;
	}
	
	protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
		
		ActivityImpl activity = findActivity(bpmnParse, userTask.getId());
		ActivityBehavior activitybehaviour = activity.getActivityBehavior();
		
		if (activitybehaviour instanceof UserTaskActivityBehavior) {
			
			addListeners((UserTaskActivityBehavior) activity.getActivityBehavior());
			
		} else if (activitybehaviour instanceof MultiInstanceActivityBehavior) {
			
			MultiInstanceActivityBehavior multiInstance = (MultiInstanceActivityBehavior) activitybehaviour;
			
			// Alfresco HEAD will handle multiInstance, but in 4.2.d MultiInstaceActivityBehavior has no access to InnerActivityBehavior, 
			// so we need some hacking!
			try {
				
			    Field innerActivityBehavior = MultiInstanceActivityBehavior.class.getDeclaredField("innerActivityBehavior");
			    innerActivityBehavior.setAccessible(true);
			    if (innerActivityBehavior.get(multiInstance) instanceof UserTaskActivityBehavior) {
			    	addListeners((UserTaskActivityBehavior) innerActivityBehavior.get(multiInstance));
			    }
			    
			} catch (Exception e) {
				logger.warn("No listener has been added on multiInstanceActivity " + userTask.getName());
			}
			
			// No taken tasks doesn’t inform COMPLETED task event, so we need to fix it out of the box
			activity.addExecutionListener(ExecutionListener.EVENTNAME_END, multiInstanceExecutionListener);
			
		}
	}

	protected void addListeners(UserTaskActivityBehavior activityBehavior) {
		if (createTaskListener != null) {
			addTaskListenerAsLast(createTaskListener, TaskListener.EVENTNAME_CREATE, activityBehavior);
		}
		if (completeTaskListener != null) {
			addTaskListenerAsLast(completeTaskListener, TaskListener.EVENTNAME_COMPLETE, activityBehavior);
		}
		if (assignmentTaskListener != null) {
			addTaskListenerAsLast(assignmentTaskListener, TaskListener.EVENTNAME_ASSIGNMENT, activityBehavior);
		}
	}

	protected void addTaskListenerAsLast(TaskListener taskListener, String eventName, UserTaskActivityBehavior userTask) {
		
		List<TaskListener> taskEventListeners = userTask.getTaskDefinition().getTaskListeners().get(eventName);
		
		if (taskEventListeners == null) {
			taskEventListeners = new ArrayList<TaskListener>();
			userTask.getTaskDefinition().getTaskListeners().put(eventName, taskEventListeners);
		}
		taskEventListeners.add(taskListener);
	}

	public void setCompleteTaskListener(TaskListener completeTaskListener) {
		this.completeTaskListener = completeTaskListener;
	}

	public void setCreateTaskListener(TaskListener createTaskListener) {
		this.createTaskListener = createTaskListener;
	}

	public TaskListener getAssignmentTaskListener() {
		return assignmentTaskListener;
	}

	public void setAssignmentTaskListener(TaskListener assignmentTaskListener) {
		this.assignmentTaskListener = assignmentTaskListener;
	}

	public ExecutionListener getMultiInstanceExecutionListener() {
		return multiInstanceExecutionListener;
	}

	public void setMultiInstanceExecutionListener(
			ExecutionListener multiInstanceExecutionListener) {
		this.multiInstanceExecutionListener = multiInstanceExecutionListener;
	}
	
}