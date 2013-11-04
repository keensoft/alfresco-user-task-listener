package es.keensoft.ext.task;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Dummy implementation for DEMO purposes only (in memory, no thread safe...)
 */
public class DummyExternalTaskSystem implements ExternalTaskSystem {
	
	private static final Log logger = LogFactory.getLog(DummyExternalTaskSystem.class);
	
	private static Map<String, TaskEntry> externalSystemDummy = new HashMap<String, TaskEntry>();
	
	@Override
	public void createTask(String id, String assignee, DelegateTask task) {
		
		TaskEntry taskEntry = new TaskEntry();
		taskEntry.setId(id);
		taskEntry.setAssignee(assignee);
		externalSystemDummy.put(id, taskEntry);
		
		logger.info(externalSystemDummy);
		
	}
	
	@Override
	public void completeTask(String id, DelegateTask task) {
		
		externalSystemDummy.remove(id);
		
		logger.info(externalSystemDummy);
		
	}

}
