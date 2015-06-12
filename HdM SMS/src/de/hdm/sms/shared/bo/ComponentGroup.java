package de.hdm.sms.shared.bo;

public class ComponentGroup extends BusinessObject {

	private String name;
	private static final long serialVersionUID = 1L;
	
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


}
