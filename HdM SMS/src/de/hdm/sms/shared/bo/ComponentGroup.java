package de.hdm.sms.shared.bo;

import java.util.Date;

public class ComponentGroup extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String name;
	
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
