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
	private Label productName = new Label("Produktname");
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
	private Button editButton = new Button("Produkt editieren");
	private Button deleteButton = new Button("Produkt loeschen");

	// Der Konstruktor von @EditProduct
	public EditProduct() {

	}

	// Mithilfe dieser Methode wird überprüft, ob der aktuell über Google
	// eingeloggte Benutzer bereits im Stücklistenmanagementsystem angelegt ist.
	// Falls der Benutzer noch nicht hinterlegt ist, wird die Klasse @CreateUser
	// geladen.
	// Falls der Benutzer bereits im System angelegt ist, wird das @User Objekt
	// befüllt. Des Weiteren
	// wird die Methode loadAllProducts gestartet.
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

	// Diese Methode enthält den @AsyncCallback zum updaten eines Tupels
	// in der Datenbank. Hier wird das Objekt p, eine Instanz der Klasse
	// @Product weitergegeben. Nach erfolgreichem Update wird das DIV namens
	// "rightside" gecleart.
	private void updateProduct(Product p) {
		asyncObj.updateProduct(p, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Produkt erfolgreich editiert");
				RootPanel.get("rightside").clear();

			}
		});
	}

	// Diese Methode enthält den @AsyncCallback zum löschen eines Tupels
	// in der Datenbank. Hier wird das Objekt p, eine Instanz der Klasse
	// @Product weitergegeben. Nach erfolgreichem löschen wird das DIV namens
	// "rightside" gecleart.
	public void deleteProduct(Product p) {
		asyncObj.deleteProduct(p, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Produkt wurde geloescht");
				RootPanel.get("rightside").clear();

			}
		});
	}

	// Der letzte Bearbeiter des Produkts wird aus der Datenbank gelesen. Es
	// wird der Integer namens tempid übergeben. Der @AsyncCallback enthält ein
	// Objekt der Klasse @User.
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

	// Diese Methode wird dazu verwendet, um die ID aus der ListBox zu lesen. Es
	// wird zuerst vor dem Doppelpunkt gesplittet. Danach wird das Leerzeichen,
	// sowie die Zeichen vor dem Leerzeichen abgeschnitten. Somit erhält man die
	// "reine" ID ohne weitere Zeichen.
	private String getIDbyDropDownText(String selectedProduct) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedProduct.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}

	// Die Loginemailadresse wird von der Klasse @Startside über diesen setter
	// in @CreateProduct "hereingelassen". Somit enthalt das Objekt @loginInfo
	// die E-Mail Adresse des Benutzers und ist somit essentiell, um die Methode
	// getUserIdByEMailAdress auszuführen.
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	// Hier wird die Listbox erstellt. Es wird eine SELECT-Anfrage an den Server
	// gesendet. Zurück erhält er in einer Arrayliste von dem Typ
	// @Product alle Produkte, um diese in der ListBox anzeigen zu
	// können. Danach wird das Label für die ListBox, sowie die ListBox selbst
	// dem RootPanel zugeordnet.
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
		RootPanel.get("rightside").add(new Label("Produkt auswaehlen"));
		RootPanel.get("rightside").add(listOfProducts);
	}

	// Die onLoad Methode. Zuallererst wird geprüft, ob der Benutzer schon im
	// System vorhanden ist. Des Weiteren werden dem contentPanel die
	// notwendigen Label, Felder sowie Buttons zugeordnet. Des weiteren werden
	// den einzelnen Panels CSS-Styles zugeordnet.
	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		InfoPanel.setStylePrimaryName("infopanel");
		contentPanel.setStylePrimaryName("contentpanel");

		// ChangeHandler für die ListBox
		// Das DIV rightside wird gecleart. Des Weiteren wird die ID mithilfe
		// der Methode getIDbyDropDownText erörtert.
		// Der Darauffolgende @AsyncCallback dient zum herausziehen des Tupels
		// aus der Datenbank. Hier wird mit der ID gearbeitet. Es wird ein
		// Objekt der Klasse @Product befüllt.
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
								// Dem infoPanel werden die notwendigen Label
								// und die notwendigen Daten, sowie dr Letzte
								// BEarbeiter zugeordnet.
								InfoPanel.add(new Label("Erstellungsdatum"));
								InfoPanel.add(new Label(dF.format(result
										.getCreationDate())));
								InfoPanel.add(new Label("Bearbeitungsdatum"));
								InfoPanel.add(new Label(dF.format(result
										.getModificationDate())));
								InfoPanel.add(new Label("Letzter Bearbeiter"));
								getLastModifier(p.getModifier());
								mainPanel.add(new Label(
										"Zugeordnete Baugruppe: "
												+ result.getComponentGroupName()));
								// Die TextBox wird befüllt, sowie die einzelnen
								// Panels befüllt.
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
								// Der @ClickHandler zum UPDATEN des Tupels in
								// der Datenbank.
								editButton.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										if (productNameTextbox.getValue()
												.isEmpty()) {
											Window.alert("Bitte Produktnamen eingeben");
										} else {
											p.setProductName(productNameTextbox
													.getValue());
											p.setModifier(u.getId());
											updateProduct(p);

										}

									}
								});
								// Der Löschen-Button, welcher die Methode
								// deleteProduct mit dem Übergabeparameter p,
								// ein Objekt aus der Klasse @Product startet.
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
