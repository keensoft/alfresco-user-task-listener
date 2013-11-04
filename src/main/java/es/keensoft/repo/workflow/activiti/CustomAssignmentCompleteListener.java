package es.keensoft.repo.workflow.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.repo.workflow.activiti.script.ActivitiScriptBase;

import es.keensoft.ext.task.ExternalTaskSystem;

public class CustomAssignmentCompleteListener extends ActivitiScriptBase implements TaskListener, CustomListener {

	private static final long serialVersionUID = 1L;
	private ExternalTaskSystem externalTaskSystem;

	@Override
	public void notify(DelegateTask task) {
		
		// UUID for task execution id
		String assigneeId = CustomTaskStatus.ASSIGNMENTSENT.name() + task.getExecutionId();
		
		if (task.getVariable(assigneeId) == null) {
			
			// New assignment
			task.setVariable(assigneeId, task.getAssignee());
			
		} else if (!task.getVariable(assigneeId).equals(task.getAssignee())) {
			
			// Reassignment
			externalTaskSystem.completeTask(task.getExecutionId(), task);
			task.setVariable(assigneeId, task.getAssignee());
			externalTaskSystem.createTask(task.getExecutionId(), task.getAssignee(), task);
			
		}
		
	}

	public ExternalTaskSystem getExternalTaskSystem() {
		return externalTaskSystem;
	}

	public void setExternalTaskSystem(ExternalTaskSystem externalTaskSystem) {
		this.externalTaskSystem = externalTaskSystem;
	}

}
