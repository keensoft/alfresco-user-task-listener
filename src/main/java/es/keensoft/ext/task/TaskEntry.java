package es.keensoft.ext.task;

public class TaskEntry {
	
	private String id;
	private String assignee;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	@Override
	public String toString() {
		return "[" + id + "," + assignee + "]";
	}

}
