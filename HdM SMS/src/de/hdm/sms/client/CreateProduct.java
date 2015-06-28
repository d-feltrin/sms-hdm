package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Product;
import de.hdm.sms.shared.bo.User;

public class CreateProduct extends VerticalPanel {

	private Label nameLabel = new Label("Produktname");
	private Label componentGroupLabel = new Label(
			"Enderzeugnis zur Zuordnung auswählen");
	private ListBox listOfComponentGroups = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private User u = new User();
	private Product p = new Product();
	private LoginInfo loginInfo;
	private VerticalPanel contentPanel = new VerticalPanel();
	private TextBox productName = new TextBox();
	private Button createProductButton = new Button("Produkt anlegen");
	private String selectedComponentGroup;

	public CreateProduct() {

	}

	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getOneUserIdByEmailAdress(eMailAdress, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(User result) {
				u.setId(result.getId());
				u.setFirstName(result.getFirstName());
				u.setLastName(result.getLastName());
				u.seteMailAdress(result.geteMailAdress());

			}

		});
		return u;

	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	private String getIDbyDropDownText(String selectedComponentGroup) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedComponentGroup.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}

	private void loadAllComponentGroups() {

		listOfComponentGroups.setSize("180px", "35px");
		listOfComponentGroups.addItem("---");

		asyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> result) {
				for (int i = 0; i < result.size(); i++) {
					listOfComponentGroups.addItem(" - " + result.get(i).getId()
							+ ":" + result.get(i).getComponentGroupName());

				}

			}
		});
		contentPanel.add(componentGroupLabel);
		contentPanel.add(listOfComponentGroups);

	}

	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		loadAllComponentGroups();
		contentPanel.add(nameLabel);
		contentPanel.add(productName);
		contentPanel.add(createProductButton);
		RootPanel.get("rightside").add(contentPanel);
		listOfComponentGroups.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				selectedComponentGroup = listOfComponentGroups
						.getItemText(listOfComponentGroups.getSelectedIndex());

			}
		});
		createProductButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (productName.getValue().isEmpty()) {
					Window.alert("Bitte Produktnamen eingeben");
				} else {

					p.setComponentId(Integer
							.parseInt(getIDbyDropDownText(selectedComponentGroup)));
					p.setProductName(productName.getText());
					p.setModifier(u.getId());
					asyncObj.insertProduct(p, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim speichern des Produkts");

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Produkt erfolgreich angelegt");
							RootPanel.get("rightside").clear();

						}
					});

				}

			}
		});
	}

}
