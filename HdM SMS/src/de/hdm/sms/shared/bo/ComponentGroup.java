package de.hdm.sms.shared.bo;

import java.util.Date;

public class ComponentGroup extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private int componentId;
	private int componentId2;
	private String lastEditor;
	private String tag;
	private int amount;
	
public ComponentGroup(){
		
	}

	public ComponentGroup(String name){
		this.name = name;
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public String getLastEditor() {
		return lastEditor;
	}

	public void setLastEditor(String lastEditor) {
		this.lastEditor = lastEditor;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getComponentId2() {
		return componentId2;
	}

	public void setComponentId2(int componentId2) {
		this.componentId2 = componentId2;
	}


}
