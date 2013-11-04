package es.keensoft.repo.workflow.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.repo.workflow.activiti.script.ActivitiScriptBase;

import es.keensoft.ext.task.ExternalTaskSystem;

public class CustomTaskCompleteListener extends ActivitiScriptBase implements TaskListener, CustomListener {
	
	private static final long serialVersionUID = 1L;
	private ExternalTaskSystem externalTaskSystem;
	
	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask task) {
		
		externalTaskSystem.completeTask(task.getExecutionId(), task);
    	
    	List<String> taskExecutionIdsCompleted;
		if (task.getVariable(CustomTaskStatus.COMPLETEDSENT.name()) == null) {
			taskExecutionIdsCompleted = new ArrayList<String>();
    	} else {
    		taskExecutionIdsCompleted = (List<String>) task.getVariable(CustomTaskStatus.COMPLETEDSENT.name());
    	}
		taskExecutionIdsCompleted.add(task.getExecutionId());
		task.setVariable(CustomTaskStatus.COMPLETEDSENT.name(), taskExecutionIdsCompleted);
		
	}

	public ExternalTaskSystem getExternalTaskSystem() {
		return externalTaskSystem;
	}

	public void setExternalTaskSystem(ExternalTaskSystem externalTaskSystem) {
		this.externalTaskSystem = externalTaskSystem;
	}

}
