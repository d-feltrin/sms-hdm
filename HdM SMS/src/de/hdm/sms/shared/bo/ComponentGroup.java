package de.hdm.sms.shared.bo;


import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ComponentGroup extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private String ComponentGroupName;
	private int componentId;
	private int modifier;
	
	private ArrayList<ComponentGroup> componentgroupList;
	private ArrayList<Integer> amountListOfComponentGroup;
	
	private ArrayList<Component> componentList;
	private ArrayList<Integer> amountListOfComponent;
	
	private Timestamp creationDate;
	private Timestamp modificationDate;

	
	public ComponentGroup(String _ComponentGroupName) {
		this.ComponentGroupName = _ComponentGroupName;
		
		this.componentgroupList = new ArrayList<ComponentGroup>();
		this.amountListOfComponentGroup = new ArrayList<Integer>();

		this.componentList = new ArrayList<Component>();
		this.amountListOfComponent = new ArrayList<Integer>();
	}
	
	/**
	 * Should only be used by Product 
	 *
	 * @param  ID  ID of.....
	 */
	public ComponentGroup(int ID){
		this.setComponentId(ID);
	}

	public String getName() {
		return ComponentGroupName;
	}

	public void setName(String name) {
		this.ComponentGroupName = name;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public List<ComponentGroup> getComponentgroupList() {
		return componentgroupList;
	}

	public void setComponentgroupList(ArrayList<ComponentGroup> componengroupList) {
		this.componentgroupList = componengroupList;
	}

	public List<Component> getComponentList() {
		return componentList;
	}

	public void setComponentList(ArrayList<Component> componentList) {
		this.componentList = componentList;
	}

	public List<Integer> getAmountListOfComponentGroup() {
		return amountListOfComponentGroup;
	}

	public void setAmountListOfComponentGroup(
			ArrayList<Integer> amountListOfComponentGroup) {
		this.amountListOfComponentGroup = amountListOfComponentGroup;
	}

	public List<Integer> getAmountListOfComponent() {
		return amountListOfComponent;
	}

	public void setAmountListOfComponent(ArrayList<Integer> amountListOfComponent) {
		this.amountListOfComponent = amountListOfComponent;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	
	public void addComponent(Component c , int amount){
		this.amountListOfComponent.add(amount);
		this.componentList.add(c);
		
	}
	
	public void addComponentGroup(ComponentGroup cg , int amount){
		this.amountListOfComponent.add(amount);
		this.componentgroupList.add(cg);
		
	}
	

}
