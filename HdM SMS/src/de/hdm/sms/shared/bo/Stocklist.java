package de.hdm.sms.shared.bo;

import java.util.Date;
import java.util.List;

public class Stocklist extends BusinessObject {
	private static final long serialVersionUID = 1L;
	private String name;
	private Date creationDate;
	private int lastEditor;
	private List<ComponentGroup> componentGroupList;
	private List<Component> componentList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getLastEditor() {
		return lastEditor;
	}
	public void setLastEditor(int lastEditor) {
		this.lastEditor = lastEditor;
	}
	public List<ComponentGroup> getComponentGroupList() {
		return componentGroupList;
	}
	public void setComponentGroupList(List<ComponentGroup> componentGroupList) {
		this.componentGroupList = componentGroupList;
	}
	public List<Component> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}

}
