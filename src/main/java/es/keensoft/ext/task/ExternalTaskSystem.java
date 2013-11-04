package es.keensoft.ext.task;

import org.activiti.engine.delegate.DelegateTask;

public interface ExternalTaskSystem {
	
	public void createTask(String id, String assignee, DelegateTask task);
	public void completeTask(String id, DelegateTask task);

}
