package es.keensoft.repo.workflow.activiti;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.alfresco.repo.workflow.activiti.script.ActivitiScriptBase;

import es.keensoft.ext.task.ExternalTaskSystem;

public class MultiInstanceExecutionListener extends ActivitiScriptBase implements ExecutionListener, CustomListener {

	private static final long serialVersionUID = 1L;
	private ExternalTaskSystem externalTaskSystem;
	
	@SuppressWarnings("unchecked")
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		ExecutionListenerExecution listenerExecution = (ExecutionListenerExecution) execution;
		String taskExecutionId = listenerExecution.getId();
		
		List<String> executedCompletedIds = (List<String>) listenerExecution.getVariable(CustomTaskStatus.COMPLETEDSENT.name());
		List<String> executedCreatedIds = (List<String>) listenerExecution.getVariable(CustomTaskStatus.CREATEDSENT.name());
		
		if (executedCreatedIds != null) {
			if (executedCreatedIds.contains(taskExecutionId)) {
				if (executedCompletedIds == null || !executedCompletedIds.contains(listenerExecution.getId())) {
					// No DelegateTask object available
					externalTaskSystem.completeTask(listenerExecution.getId(), null);
				}
			}
		}
		
	}

	public ExternalTaskSystem getExternalTaskSystem() {
		return externalTaskSystem;
	}

	public void setExternalTaskSystem(ExternalTaskSystem externalTaskSystem) {
		this.externalTaskSystem = externalTaskSystem;
	}

}
