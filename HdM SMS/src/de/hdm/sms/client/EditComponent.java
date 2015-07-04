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
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.User;

public class EditComponent extends VerticalPanel {
	private final ListBox listOfComponents = new ListBox();
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private String selectedComponent;
	private TextBox nameOfComponent = new TextBox();
	private final Label labelOfNameComponentTextBox = new Label("Name");
	private final Label labelOfDescriptionComponentTextBox = new Label(
			"Beschreibung");
	private final Label labelOfMaterialDescriptionComponentTextBox = new Label(
			"Materialbeschreibung");
	private TextBox descriptionOfComponent = new TextBox();
	private TextBox materialDescriptionOfComponent = new TextBox();
	private TextBox idOfComponent = new TextBox();
	private String idOfComponentString;
	private VerticalPanel componentItemPanel = new VerticalPanel();
	private Button deleteComponentButton = new Button("Bauteil löschen");
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private HorizontalPanel contentPanel = new HorizontalPanel();
	private Button editComponentButton = new Button("Bauteil editieren");
	private LoginInfo loginInfo;
	private User u = new User();
	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
	private VerticalPanel InfoPanel = new VerticalPanel();
	private int tempId;

	public EditComponent() {

	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	private void getLastModifier(Component c) {
		asyncObj.getLastModifierOfComponent(c, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(User result) {
				InfoPanel.add(new Label("Letzter Bearbeiter"));
				InfoPanel.add(new Label(result.getFirstName() + " "
						+ result.getLastName()));

			}
		});
	}

	private void deleteComponent(int DeleteComponentId) {
		asyncObj.deleteComponentById(DeleteComponentId,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Bauteil erfolgreich gelöscht");
						RootPanel.get("rightside").clear();
					}
				});
	}

	private void updateComponent(Component c) {
		asyncObj.updateComponentById(c, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Das Bauteil wurde editiert");
				RootPanel.get("rightside").clear();

			}
		});
	}

	private void loadAllComponents() {

		listOfComponents.setSize("180px", "35px");
		// ListOfComponents.addStyleName("mainmenu-dropdown");
		listOfComponents.addItem("---");

		asyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Component> result) {
				for (int i = 0; i < result.size(); i++) {

					listOfComponents.addItem(" - " + result.get(i).getId() + ":"
							+ result.get(i).getName());

				}
				RootPanel.get("rightside").clear();
				RootPanel.get("rightside").add(new Label("Bauteil auswaehlen"));
				RootPanel.get("rightside").add(listOfComponents);
			}
		});
		
	}
	private String getIDbyDropDownText(String selectedComponent) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedComponent.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}
public User getUserIdByEMailAdress(String eMailAdress) {
	asyncObj.getOneUserIdByEmailAdress(eMailAdress,
			new AsyncCallback<User>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(User result) {
					if(result.geteMailAdress() != null) {
					u.setId(result.getId());
					u.setFirstName(result.getFirstName());
					u.setLastName(result.getLastName());
					u.seteMailAdress(result.geteMailAdress());
					loadAllComponents();
					
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
	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		InfoPanel.setStylePrimaryName("infopanel");
		componentItemPanel.setStylePrimaryName("contentpanel");
		

		
		
		listOfComponents.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				RootPanel.get("rightside").clear();
				selectedComponent = listOfComponents
						.getItemText(listOfComponents.getSelectedIndex());
				tempId = Integer.parseInt(getIDbyDropDownText(selectedComponent));

				asyncObj.getOneComponentIdById(tempId,
						new AsyncCallback<Component>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Component result) {
								getLastModifier(result);
								idOfComponentString = String.valueOf(result
										.getId());
								idOfComponent.setText(idOfComponentString);
								nameOfComponent.setText(result.getName());
								descriptionOfComponent.setText(result
										.getDescription());
								materialDescriptionOfComponent.setText(result
										.getMaterialDescription());
								componentItemPanel
										.add(new Label("Artikelnummer: "
												+ idOfComponentString));
								componentItemPanel
										.add(labelOfNameComponentTextBox);
								componentItemPanel.add(nameOfComponent);
								componentItemPanel
										.add(labelOfDescriptionComponentTextBox);
								componentItemPanel.add(descriptionOfComponent);
								componentItemPanel
										.add(labelOfMaterialDescriptionComponentTextBox);
								componentItemPanel
										.add(materialDescriptionOfComponent);
								componentItemPanel.add(buttonPanel);
								InfoPanel.add(new Label("Erstellungsdatum"));

								InfoPanel.add(new Label(dF.format(result
										.getCreationdate())));
								InfoPanel.add(new Label("Bearbeitungsdatum"));
								InfoPanel.add(new Label(dF.format(result
										.getLastModified())));
								contentPanel.add(componentItemPanel);
								contentPanel.add(InfoPanel);
								RootPanel.get("rightside").clear();
								RootPanel.get("rightside").add(contentPanel);
								buttonPanel.add(editComponentButton);
								buttonPanel.add(deleteComponentButton);
								deleteComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												deleteComponent(Integer
														.parseInt(idOfComponentString));

											}
										});

								editComponentButton
										.addClickHandler(new ClickHandler() {

											@Override
											public void onClick(ClickEvent event) {
												if (nameOfComponent.getValue()
														.isEmpty()
														|| descriptionOfComponent
																.getValue()
																.isEmpty()
														|| materialDescriptionOfComponent
																.getValue()
																.isEmpty()) {
													Window.alert("Bitte Felder befüllen!");
												} else {
													Component c = new Component();
													c.setId(Integer
															.parseInt(idOfComponentString));
													c.setName(nameOfComponent
															.getText());
													c.setMaterialDescription(materialDescriptionOfComponent
															.getText());
													c.setDescription(descriptionOfComponent
															.getText());
													c.setModifier(u.getId());
													updateComponent(c);
												}

											}
										});

							}
						});

			}
		});
	}
}