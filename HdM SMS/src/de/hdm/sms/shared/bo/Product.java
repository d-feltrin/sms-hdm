package de.hdm.sms.shared.bo;

public class Product extends ComponentGroup {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID of Componentgroup
	 *
	 * @param  ID  ID of.....
	 */
	public Product() {
		
	}
	public Product(int ComponentGroupID){
		super(ComponentGroupID);
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getComponentGroupId() {
		return componentGroupId;
	}
	public void setComponentGroupId(int componentGroupId) {
		this.componentGroupId = componentGroupId;
	}
	private String productName;
	private int componentGroupId;

}
