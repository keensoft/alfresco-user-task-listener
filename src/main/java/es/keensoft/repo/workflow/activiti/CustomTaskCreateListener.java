package es.keensoft.repo.workflow.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.repo.workflow.activiti.script.ActivitiScriptBase;

import es.keensoft.ext.task.ExternalTaskSystem;

public class CustomTaskCreateListener extends ActivitiScriptBase implements TaskListener, CustomListener {
	
	private static final long serialVersionUID = 1L;
	private ExternalTaskSystem externalTaskSystem;
	
	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateTask task) {
				
		externalTaskSystem.createTask(task.getExecutionId(), task.getAssignee(), task);
		
    	List<String> taskExecutionIdsCreated;
		if (task.getVariable(CustomTaskStatus.CREATEDSENT.name()) == null) {
			taskExecutionIdsCreated = new ArrayList<String>();
    	} else {
    		taskExecutionIdsCreated = (List<String>) task.getVariable(CustomTaskStatus.CREATEDSENT.name());
    	}
		taskExecutionIdsCreated.add(task.getExecutionId());
		task.setVariable(CustomTaskStatus.CREATEDSENT.name(), taskExecutionIdsCreated);
		
	}

	public ExternalTaskSystem getExternalTaskSystem() {
		return externalTaskSystem;
	}

	public void setExternalTaskSystem(ExternalTaskSystem externalTaskSystem) {
		this.externalTaskSystem = externalTaskSystem;
	}
	
}
