package de.hdm.sms.shared.bo;

import java.util.ArrayList;

public class Module extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Component> component = new ArrayList<Component>();
	private ArrayList<Module> module = new ArrayList<Module>();
	private Boolean endproductState;
	
	public Module(){
		
	}
	
	public Module(String name, Module module1, Module module2, Boolean endproductState){
		this.name = name;
		this.module.add(module1);
		this.module.add(module2);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Module module1, Module module2, Module module3, Boolean endproductState){
		this.name = name;
		this.module.add(module1);
		this.module.add(module2);
		this.module.add(module3);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Component component1, Component component2, Boolean endproductState){
		this.name = name;
		this.component.add(component1);
		this.component.add(component2);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Component component1, Component component2, Component component3, Boolean endproductState){
		this.name = name;
		this.component.add(component1);
		this.component.add(component2);
		this.component.add(component3);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Module module1, Component component1, Boolean endproductState){
		this.name = name;
		this.module.add(module1);
		this.component.add(component1);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Module module1, Module module2, Component component1, Boolean endproductState){
		this.name = name;
		this.module.add(module1);
		this.module.add(module2);
		this.component.add(component1);
		this.endproductState = endproductState;
	}
	
	public Module(String name, Module module1, Component component1, Component component2, Boolean endproductState){
		this.name = name;
		this.module.add(module1);
		this.component.add(component1);
		this.component.add(component2);
		this.endproductState = endproductState;
	}
	
	public String getModuleName(int index){
		
		String name = "";
		Module m1 = module.get(index);
		name = m1.getName();
		return name;
	}
	
	public String getComponentName(int index){
		
		String name = "";
		Component c1 = component.get(index);
		name = c1.getName();
		return name;
	}
	
	public Boolean getEndproductState() {
		return endproductState;
	}

	public void setEndproductState(Boolean endproductState) {
		this.endproductState = endproductState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Module> getModule() {
		return this.module;
	}
	
	public ArrayList<Component> getComponent() {
		return this.component;
	}

}
