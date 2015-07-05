package de.hdm.sms.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Product;
import de.hdm.sms.shared.bo.Stocklist;
import de.hdm.sms.shared.bo.User;

public interface AServiceAsync {

	void insertComponent(Component c, AsyncCallback<Void> callback);

	void loadAllComponents(AsyncCallback<ArrayList<Component>> asyncCallback);

	void deleteComponentById(int deleteComponentId,
			AsyncCallback<Void> asyncCallback);

	void updateComponentById(Component c, AsyncCallback<Void> asyncCallback);

	void insertComponentGroup(ComponentGroup cg,
			AsyncCallback<Void> asyncCallback);

	void loadAllComponentGroups(
			AsyncCallback<ArrayList<ComponentGroup>> asyncCallback);

	void updateComponentGroupById(ComponentGroup cg,
			AsyncCallback<Void> asyncCallback);


	
	void insertUser(User u, AsyncCallback<Void> asyncCallback);

	void loadAllUsers(AsyncCallback<ArrayList<User>> asyncCallback);

	void getOneUserIdByEmailAdress(String selectedUser,
			AsyncCallback<User> asyncCallback);

	void deleteUserById(int deleteUserId, AsyncCallback<Void> asyncCallback);

	void updateUserById(User u, AsyncCallback<Void> asyncCallback);

	void getLastModifierOfComponent(Component c,
			AsyncCallback<User> asyncCallback);

	void insertProduct(Product p, AsyncCallback<Void> callback);

	void loadAllProducts(AsyncCallback<ArrayList<Product>> asyncCallback);

	void getOneProductById(int tempId, AsyncCallback<Product> asyncCallback);

	void getLastModifierOfProduct(int tempid, AsyncCallback<User> asyncCallback);

	void updateProduct(Product p, AsyncCallback<Void> asyncCallback);

	void deleteProduct(Product p, AsyncCallback<Void> asyncCallback);

	void getOneUserById(int tempUserId, AsyncCallback<User> asyncCallback);

	void loadAllComponentGroupsIncludingRelations(AsyncCallback<ArrayList<ComponentGroup>> asyncCallback);

	void insertCGElement(ComponentGroup cg, int ElementID, char ElementTag, int Amount, AsyncCallback<Void> doNothing);

	void updateCGElementAmount(ComponentGroup cg, int ElementID, char ElementTag, int NewAmount, AsyncCallback<Void> doNothing);

	void deleteCGElement(ComponentGroup cg, int ElementID, char ElementTag,AsyncCallback<Void> doNothing);

	void getOneComponentIdById(int tempId,AsyncCallback<Component> callback);

	void insertStocklist(Stocklist newStocklist, AsyncCallback<Void> asyncCallback);

	void loadAllStocklistsIncludingRelations(AsyncCallback<ArrayList<Stocklist>> asyncCallback);

	void deleteComponentGroupById(int id, AsyncCallback<Void> asyncCallback);

	void updateStockList(Stocklist newStocklist, AsyncCallback doNothingAsyncCallback);

	void updateAmountOfStocklistComponentGrouElement(Stocklist newStocklist, ComponentGroup original, Integer integer,
			AsyncCallback doNothingAsyncCallback);

	void updateAmountOfStocklistComponentElement(Stocklist newStocklist, Component originalComponent, Integer integer,
			AsyncCallback doNothingAsyncCallback);

	void insertComponentGroupToSocklist(Stocklist newStocklist, ComponentGroup newStockListComponentGroupToInsert,
			Integer integer, AsyncCallback doNothingAsyncCallback);

	void insertComponentToStocklist(Stocklist newStocklist, Component newStockListComponentToInsert, Integer integer,
			AsyncCallback doNothingAsyncCallback);

	void deleteStocklistComponentElement(Stocklist newStocklist, Component originalComponent, Integer integer,
			AsyncCallback doNothingAsyncCallback);

	void deleteStockListComponentGroupElement(Stocklist newStocklist, ComponentGroup originalComponentGroup,
			Integer integer, AsyncCallback doNothingAsyncCallback);


}
