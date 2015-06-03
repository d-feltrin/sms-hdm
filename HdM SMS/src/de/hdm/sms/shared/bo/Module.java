package de.hdm.sms.shared.bo;

public class Module extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private Component[] component;
	private Module[] module;
	private Boolean endproductState;
	
	public Module(){
		
	}
	
	public Module(String name, Module module1, Module module2, Boolean endproductState){
		this.name = name;
		this.module[0] = module1;
		this.module[1] = module2;
		this.endproductState = endproductState;
	}
	
	public Module(String name, Module module1, Module module2, Module module3, Boolean endproductState){
		this.name = name;
		this.module[0] = module1;
		this.module[1] = module2;
		this.module[2] = module3;
		this.endproductState = endproductState;
	}
	
	public Module(String name, Component component1, Component component2, Boolean endproductState){
		this.name = name;
		this.component[0] = component1;
		this.component[1] = component2;
		this.endproductState = endproductState;
	}
	
	public Module(String name, Component component1, Component component2, Component component3, Boolean endproductState){
		this.name = name;
		this.component[0] = component1;
		this.component[1] = component2;
		this.component[2] = component3;
		this.endproductState = endproductState;
	}
	
	public Boolean getEndproductState() {
		return endproductState;
	}

	public void setEndproductState(Boolean ednproductState) {
		this.endproductState = endproductState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
