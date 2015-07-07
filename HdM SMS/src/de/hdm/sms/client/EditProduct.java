package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.Product;
import de.hdm.sms.shared.bo.User;

public class EditProduct extends VerticalPanel {
	private Label productName = new Label("Enderzeugnisname");
	private TextBox productNameTextbox = new TextBox();
	private ListBox listOfProducts = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private VerticalPanel InfoPanel = new VerticalPanel();
	private LoginInfo loginInfo;
	private String selectedProduct;
	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
	private HorizontalPanel contentPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private int tempId;
	private Product p = new Product();
	private User u = new User();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button editButton = new Button("Enderzeugnis editieren");
	private Button deleteButton = new Button("Enderzeugnis löschen");

	public EditProduct() {

	}

	// CHeck if User is registered in the system
	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getOneUserIdByEmailAdress(eMailAdress,
				new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(User result) {
						if (result.geteMailAdress() != null) {

							u.setId(result.getId());
							u.setFirstName(result.getFirstName());
							u.setLastName(result.getLastName());
							u.seteMailAdress(result.geteMailAdress());
							loadAllProducts();
						} else {
							Window.alert("Bitte registrieren Sie sich zuerst!");
							RootPanel.get("rightside").clear();
							CreateUser cU = new CreateUser();
							cU.setLoginInfo(loginInfo);
							RootPanel.get("rightside").add(cU);
						}

					}

				});
		return u;

	}

	// Fill the new Product Attributes in the Database
	private void updateProduct(Product p) {
		asyncObj.updateProduct(p, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Enderzeugnis erfolgreich editiert");
				RootPanel.get("rightside").clear();

			}
		});
	}

	// Delete the Product
	public void deleteProduct(Product p) {
		asyncObj.deleteProduct(p, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Enderzeugnis wurde gelöscht");
				RootPanel.get("rightside").clear();

			}
		});
	}

	// get the modifier
	private void getLastModifier(int tempid) {
		asyncObj.getLastModifierOfProduct(tempid, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(User result) {
				InfoPanel.add(new Label(result.getFirstName() + " "
						+ result.getLastName()));

			}
		});
	}

	// Get the Id by splitting the Listbox value
	private String getIDbyDropDownText(String selectedProduct) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedProduct.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}

	// Get user informations
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	// Fill the Listbox with all Products
	private void loadAllProducts() {
		listOfProducts.setSize("180px", "35px");
		listOfProducts.addItem("---");
		asyncObj.loadAllProducts(new AsyncCallback<ArrayList<Product>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Product> result) {
				for (int i = 0; i < result.size(); i++) {
					listOfProducts.addItem(" - " + result.get(i).getId() + ":"
							+ result.get(i).getProductName());
				}
			}
		});
		// Clear the RootPanel and add the ListBox to the RootPanel
		RootPanel.get("rightside").clear();
		RootPanel.get("rightside").add(new Label("Enderzeugnis auswählen"));
		RootPanel.get("rightside").add(listOfProducts);
	}

	// Load
	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		InfoPanel.setStylePrimaryName("infopanel");
		contentPanel.setStylePrimaryName("contentpanel");

		// Changehandler of ListBox
		listOfProducts.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedProduct = listOfProducts.getItemText(listOfProducts
						.getSelectedIndex());
				tempId = Integer.parseInt(getIDbyDropDownText(selectedProduct));

				asyncObj.getOneProductById(tempId,
						new AsyncCallback<Product>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Product result) {
								p.setId(result.getId());
								p.setModifier(result.getModifier());
								// Panel: Fill the Panels
								InfoPanel.add(new Label("Erstellungsdatum"));
								InfoPanel.add(new Label(dF.format(result
										.getCreationDate())));
								InfoPanel.add(new Label("Bearbeitungsdatum"));
								InfoPanel.add(new Label(dF.format(result
										.getModificationDate())));
								InfoPanel.add(new Label("Letzter Bearbeiter"));
								getLastModifier(p.getModifier());
								mainPanel.add(new Label("Enderzeugnisnummer"));
								mainPanel.add(new Label(""+p.getId()));
								mainPanel.add(new Label(
										"Zugeordnete Baugruppe: "
												+ result.getComponentGroupName()));

								productNameTextbox.setText(result
										.getProductName());
								buttonPanel.add(editButton);
								buttonPanel.add(deleteButton);
								mainPanel.add(productName);
								mainPanel.add(productNameTextbox);
								mainPanel.add(buttonPanel);
								contentPanel.add(mainPanel);
								contentPanel.add(InfoPanel);
								RootPanel.get("rightside").add(contentPanel);

								// Edit ClickHandler
								editButton.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										if (productNameTextbox.getValue()
												.isEmpty()) {
											Window.alert("Bitte Enderzeugnisnamen eingeben");
										} else {
											p.setProductName(productNameTextbox
													.getValue());
											p.setModifier(u.getId());
											updateProduct(p);

										}

									}
								});
								// ClickHandler of deleteButton
								deleteButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												deleteProduct(p);

											}
										});
							}
						});

			}
		});

	}
}
