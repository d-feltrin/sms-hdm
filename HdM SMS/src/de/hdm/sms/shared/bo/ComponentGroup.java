package de.hdm.sms.shared.bo;


import java.util.List;

import java.sql.Timestamp;

public class ComponentGroup extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private int componentId;
	private int componentId2;
	private int modifier;
	private String tag;
	private int amount;
	private List<ComponentGroup> componentgroupList;
	private List<Integer> amountListOfComponentGroup;
	private List<Integer> amountListOfComponent;
	private List<Component> componentList;
	private Timestamp creationDate;
	private Timestamp modificationDate;

	public ComponentGroup() {

	}

	public ComponentGroup(String name) {
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

	public List<ComponentGroup> getComponentgroupList() {
		return componentgroupList;
	}

	public void setComponentgroupList(List<ComponentGroup> componengroupList) {
		this.componentgroupList = componengroupList;
	}

	public List<Component> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}

	public List<Integer> getAmountListOfComponentGroup() {
		return amountListOfComponentGroup;
	}

	public void setAmountListOfComponentGroup(
			List<Integer> amountListOfComponentGroup) {
		this.amountListOfComponentGroup = amountListOfComponentGroup;
	}

	public List<Integer> getAmountListOfComponent() {
		return amountListOfComponent;
	}

	public void setAmountListOfComponent(List<Integer> amountListOfComponent) {
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

	

}
