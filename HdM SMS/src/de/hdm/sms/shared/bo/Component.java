package de.hdm.sms.shared.bo;


import java.sql.Timestamp;



public class Component extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String description;
	private String name;
	private String material;
	private int modifier;

	private Timestamp creationdate;
	private Timestamp lastModified;


	public Component(){
		
	}
	
	public Component(int _id){
		this.setId(_id);
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

	public void setModifier(int i) {
		this.modifier = i;
		
	}

	public int getModifier() {
		return this.modifier;
	}


	public Timestamp getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Timestamp timestamp) {
		this.creationdate = timestamp;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp timestamp) {
		this.lastModified = timestamp;
	}




}
