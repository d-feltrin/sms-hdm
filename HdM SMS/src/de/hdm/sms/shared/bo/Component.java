package de.hdm.sms.shared.bo;

import java.sql.Date;

import de.hdm.sms.shared.LoginInfo;

public class Component extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private String description;
	private String name;
	private String material;
	private int modifier;
	private Date creationdate;
	private Date lastModified;
	
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

	public void setModifier(int i) {
		this.modifier = i;
		
	}

	public int getModifier() {
		return this.modifier;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date sqlDate) {
		this.creationdate = sqlDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}



}
