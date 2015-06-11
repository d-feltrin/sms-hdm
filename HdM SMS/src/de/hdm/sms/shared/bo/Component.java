package de.hdm.sms.shared.bo;

public class Component extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String description;
	private String name;
	private String material;
	
	public Component(){
		
	}
	
	public Component(String name, String description, String material){
		this.name = name;
		this.description = description;
		this.material = material;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterialDescription() {
		return material;
	}

	public void setMaterialDescription(String material) {
		this.material = material;
	}

}
