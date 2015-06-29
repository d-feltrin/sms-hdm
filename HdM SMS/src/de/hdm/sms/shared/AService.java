package de.hdm.sms.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Product;
import de.hdm.sms.shared.bo.User;

@RemoteServiceRelativePath("aservice")
public interface AService extends RemoteService {
	void insertComponent(Component c);

	void insertComponentGroup(ComponentGroup cg);

	ArrayList<Component> loadAllComponents();

	ArrayList<ComponentGroup> loadAllComponentGroups();

	Component getOneComponentIdByName(String selectedComponent);

	void deleteComponentById(int deleteComponentId);

	void updateComponentById(Component c);

	void updateComponentGroupById(ComponentGroup cg);

	void insertUser(User u);

	ArrayList<User> loadAllUsers();

	User getOneUserIdByEmailAdress(String selectedUser);

	void deleteUserById(int deleteUserId);

	void updateUserById(User u);
	User getLastModifierOfComponent(Component c);

	void insertProduct(Product p);

	ArrayList<Product> loadAllProducts();

	Product getOneProductById(int tempId);

	User getLastModifierOfProduct(int tempid);

	void updateProduct(Product p);

	void deleteProduct(Product p);

	User getOneUserById(int tempUserId);

	ArrayList<ComponentGroup> loadAllComponentGroupsIncludingRelations();

}
