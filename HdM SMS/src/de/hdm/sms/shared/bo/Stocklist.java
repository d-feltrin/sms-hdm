package de.hdm.sms.shared.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stocklist extends BusinessObject {
	private static final long serialVersionUID = 1L;
	private String name;
	private Date creationDate;
	private Date lastModified;
	private int modifier;
	private ArrayList<ComponentGroup> componentgroupList;
	private ArrayList<Integer> amountListOfComponentGroup;
	
	private ArrayList<Component> componentList;
	private ArrayList<Integer> amountListOfComponent;
	
	public Stocklist(){

		this.componentgroupList = new ArrayList<ComponentGroup>();
		this.amountListOfComponentGroup = new ArrayList<Integer>();

		this.componentList = new ArrayList<Component>();
		this.amountListOfComponent = new ArrayList<Integer>();
	}
	
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
	
	public List<ComponentGroup> getComponentGroupList() {
		return componentgroupList;
	}
	public void setComponentGroupList(ArrayList<ComponentGroup> componentGroupList) {
		this.componentgroupList = componentGroupList;
	}
	public List<Component> getComponentList() {
		return componentList;
	}
	public void setComponentList(ArrayList<Component> componentList) {
		this.componentList = componentList;
	}
	public ArrayList<Integer> getAmountListOfComponentGroup() {
		return amountListOfComponentGroup;
	}
	public void setAmountListOfComponentGroup(ArrayList<Integer> amountListOfComponentGroup) {
		this.amountListOfComponentGroup = amountListOfComponentGroup;
	}
	public ArrayList<Integer> getAmountListOfComponent() {
		return amountListOfComponent;
	}
	public void setAmountListOfComponent(ArrayList<Integer> amountListOfComponent) {
		this.amountListOfComponent = amountListOfComponent;
	}

	
	public void addComponent(Component c , int amount){
		this.amountListOfComponent.add(amount);
		this.componentList.add(c);
		
	}
	
	public void addComponentGroup(ComponentGroup cg , int amount){
		this.amountListOfComponentGroup.add(amount);
		this.componentgroupList.add(cg);
		
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
