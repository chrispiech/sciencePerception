package models;

import java.util.List;

public class Student {

	private String name;
	private String school;
	private String id;
	private List<String> preSelf;
	private List<String> postSelf;
	private List<String> preCs;
	private List<String> postCs;
	
	public Student(String name, String school) {
		this.name = name;
		this.school = school;
		this.id = name + "." + school;
	}
	
	public String getId() {
		return id;
	}
	public List<String> getPreSelf() {
		return preSelf;
	}
	public void setPreSelf(List<String> preSelf) {
		this.preSelf = preSelf;
	}
	public List<String> getPostSelf() {
		return postSelf;
	}
	public void setPostSelf(List<String> postSelf) {
		this.postSelf = postSelf;
	}
	public List<String> getPreCs() {
		return preCs;
	}
	public void setPreCs(List<String> preCs) {
		this.preCs = preCs;
	}
	public List<String> getPostCs() {
		return postCs;
	}
	public void setPostCs(List<String> postCs) {
		this.postCs = postCs;
	}
	public boolean hasPost() {
		return postCs != null && postSelf != null;
	}
	public String getName() {
		return name;
	}
	public String getSchool() {
		return school;
	}

	public List<String> getList(String tableName) {
		if(tableName.equals("preCs")) return getPreCs();
		if(tableName.equals("postCs")) return getPostCs();
		if(tableName.equals("preSelf")) return getPreSelf();
		if(tableName.equals("postSelf")) return getPostSelf();
		throw new RuntimeException("unknown table: " + tableName);
	}

	
}
