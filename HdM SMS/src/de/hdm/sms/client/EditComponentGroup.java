package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
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
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.User;

public class EditComponentGroup extends VerticalPanel {

	private final AServiceAsync asyncObj = GWT.create(AService.class);

	private ArrayList<Component> allComponents = new ArrayList<Component>();
	private ArrayList<ComponentGroup> allComponentGroups = new ArrayList<ComponentGroup>();
	private ArrayList<User> allUsers = new ArrayList<User>();

	// GUI Objects
	// Select (FIRSTPAGE)
	private final HorizontalPanel PanelSelectGroupToEdit = new HorizontalPanel();
	private final ListBox listboxListOfGroupsToEdit = new ListBox();
	private final Button buttonEditGroup = new Button("Editieren");

	// EDIT (SECONDPAGE)
	// Panel: Info ComponentName
	private final TextBox textboxCGInfo_Name = new TextBox();

	// Panel: List
	private final HorizontalPanel PanelTableOfElements = new HorizontalPanel();
	private final FlexTable flextableComponentgroupElements = new FlexTable();

	// Panel: Add Element
	private final HorizontalPanel PanelAddElementtOComponentgroup = new HorizontalPanel();
	private final ListBox listboxListOfAddableElements = new ListBox();
	private final TextBox textboxAmountOfElementToAdd = new TextBox();
	private final Button buttonAddElementToComponentgroup = new Button("Bauteil/-gruppe hinzufügen");

	// Panel Funcitons
	private final HorizontalPanel PanelFunctions = new HorizontalPanel();
	private final Button buttonUpdateComponentGroup = new Button("Übernehmen");
	private final Button buttonDeleteComponentGroup = new Button("Löschen");
	private final Button buttonAbortComponentGroup = new Button("Abbrechen");

	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

	private ComponentGroup OriginalComponentGroupToEdit = new ComponentGroup();

	private int ComponentListBoxID = 0; // help Variable to determine if part to
										// add is component or componentgroup

	private LoginInfo loginInfo;
	private User u = new User();

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getOneUserIdByEmailAdress(eMailAdress, new AsyncCallback<User>() {

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
					loadComponentsANDComponentGroup();
					RootPanel.get("rightside").clear();
					RootPanel.get("rightside").add(PanelSelectGroupToEdit);

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

		// Load

		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		OriginalComponentGroupToEdit.setModifier(u.getId());

		loadAllUser();

		// Panel: Select ComponentgroupToEdit
		PanelSelectGroupToEdit.add(new Label("Wahlen Sie die Baugruppe aus, die Sie editieren moechten:"));
		PanelSelectGroupToEdit.add(listboxListOfGroupsToEdit);
		PanelSelectGroupToEdit.add(buttonEditGroup);

		buttonEditGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listboxListOfGroupsToEdit.getItemText(listboxListOfGroupsToEdit.getSelectedIndex()).equals("Baugruppe"))
					Window.alert("Bitte eine Baugruppe auswaehlen");
				else {
					// get selected Item
					String IdOfSelectedItem = getELementTypeIdName(listboxListOfGroupsToEdit.getItemText(listboxListOfGroupsToEdit.getSelectedIndex()))[1];
					for (ComponentGroup componentGroup : allComponentGroups) {

						if (componentGroup.getId() == Integer.valueOf(IdOfSelectedItem)) {
							LoadElementEdit(componentGroup); // load selected
																// item
							break;
						}

					}
				}
			}
		});

	}

	private void LoadElementEdit(ComponentGroup ComponentGroupToEdit) {
		RootPanel.get("rightside").clear();

		OriginalComponentGroupToEdit = ComponentGroupToEdit; // save for compare

		VerticalPanel PanelCGInfo_ID = new VerticalPanel();
		PanelCGInfo_ID.add(new Label("Baugruppennummer:"));
		PanelCGInfo_ID.add(new Label(String.valueOf(ComponentGroupToEdit.getId())));

		RootPanel.get("rightside").add(PanelCGInfo_ID);

		VerticalPanel PanelCGInfo_Name = new VerticalPanel();
		PanelCGInfo_Name.add(new Label("Name:"));
		textboxCGInfo_Name.setText(ComponentGroupToEdit.getComponentGroupName());
		PanelCGInfo_Name.add(textboxCGInfo_Name);

		RootPanel.get("rightside").add(PanelCGInfo_Name);

		VerticalPanel PanelCGInfo_CreeationDate = new VerticalPanel();
		PanelCGInfo_CreeationDate.add(new Label("Erstellungsdatum:"));
		PanelCGInfo_CreeationDate.add(new Label(dF.format(ComponentGroupToEdit.getCreationDate())));

		RootPanel.get("rightside").add(PanelCGInfo_CreeationDate);

		VerticalPanel PanelCGInfo_LastModifier = new VerticalPanel();
		PanelCGInfo_LastModifier.add(new Label("Bearbeitungsdatum"));
		PanelCGInfo_LastModifier.add(new Label(dF.format(ComponentGroupToEdit.getModificationDate())));

		RootPanel.get("rightside").add(PanelCGInfo_LastModifier);

		VerticalPanel PanelCGInfo_LastEditor = new VerticalPanel();
		PanelCGInfo_LastEditor.add(new Label("Letzter Bearbeiter:"));
		String lastModifier = "Unknown";
		for (User user : allUsers) {
			if (user.getId() == ComponentGroupToEdit.getModifier()) {
				lastModifier = user.getFirstName() + " " + user.getLastName();
				break;
			}
		}
		PanelCGInfo_LastEditor.add(new Label(lastModifier));
		PanelCGInfo_LastEditor.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGInfo_LastEditor);

		// Build List
		flextableComponentgroupElements.setText(0, 0, "Typ");
		flextableComponentgroupElements.setText(0, 1, "ArtikelNr.");
		flextableComponentgroupElements.setText(0, 2, "Name");
		flextableComponentgroupElements.setText(0, 3, "Anzahl");

		for (int i = 0; i < ComponentGroupToEdit.getComponentgroupList().size(); i++) {

			// Get Row index where to add new row
			int rowNumToInsert = flextableComponentgroupElements.getRowCount();

			// Type
			flextableComponentgroupElements.setText(rowNumToInsert, 0, "Baugruppe");

			// ID
			flextableComponentgroupElements.setText(rowNumToInsert, 1, String.valueOf(ComponentGroupToEdit.getComponentgroupList().get(i).getId()));

			// Name
			flextableComponentgroupElements.setText(rowNumToInsert, 2, ComponentGroupToEdit.getComponentgroupList().get(i).getComponentGroupName());

			// Amount
			TextBox Amount = new TextBox();
			Amount.setText(String.valueOf(ComponentGroupToEdit.getAmountListOfComponentGroup().get(i)));

			flextableComponentgroupElements.setWidget(rowNumToInsert, 3, Amount);
		}

		for (int i = 0; i < ComponentGroupToEdit.getComponentList().size(); i++) {
			// Get Row index where to add new row
			int rowNumToInsert = flextableComponentgroupElements.getRowCount();

			// Type
			flextableComponentgroupElements.setText(rowNumToInsert, 0, "Bauteil");

			// ID
			flextableComponentgroupElements.setText(rowNumToInsert, 1, String.valueOf(ComponentGroupToEdit.getComponentList().get(i).getId()));

			// Name
			flextableComponentgroupElements.setText(rowNumToInsert, 2, ComponentGroupToEdit.getComponentList().get(i).getName());

			// Amount
			TextBox Amount = new TextBox();
			Amount.setText(String.valueOf(ComponentGroupToEdit.getAmountListOfComponent().get(i)));

			flextableComponentgroupElements.setWidget(rowNumToInsert, 3, Amount);
		}

		PanelTableOfElements.add(flextableComponentgroupElements);
		RootPanel.get("rightside").add(PanelTableOfElements);

		// Panel: Add new Element
		buttonAddElementToComponentgroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int selectedID = listboxListOfAddableElements.getSelectedIndex();
				if (listboxListOfAddableElements.getItemText(selectedID).equals("----")
						|| listboxListOfAddableElements.getItemText(selectedID).equals("Bauteil")
						|| listboxListOfAddableElements.getItemText(selectedID).equals("Baugruppe")) {

					Window.alert("Bitte waehlen Sie ein gueltiges Bauteil bzw. Bauelement aus!");

				} else if (textboxAmountOfElementToAdd.getText().equals("")) {
					Window.alert("Bitte die Anzahl des hinzuzufuegenden Bauteils/Baugruppe eintragen!");
				} else if (Integer.parseInt(textboxAmountOfElementToAdd.getText()) < 1) {
					Window.alert("Anzahl des hinzuzufuegenden Bauteils/Baugruppe darf nicht unter 1 sein!");
				} else {
					// Evertthing okay, start to add
					int rowNumToInsertNewRow = flextableComponentgroupElements.getRowCount();

					// Get Type, ID and Name by Dropdowntext
					String[] PropertiesOfElementToAdd = getELementTypeIdName(listboxListOfAddableElements.getItemText(selectedID));

					// Search in List if Element is already added
					boolean alreadyInList = false;
					int alreadyInListRowNumber = -1;

					for (int i = 1; i < flextableComponentgroupElements.getRowCount(); i++) {
						if (flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 1).getInnerHTML()
								.equals(PropertiesOfElementToAdd[1])
								&& flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 2).getInnerHTML()
										.equals(PropertiesOfElementToAdd[2])) {
							alreadyInList = true;
							alreadyInListRowNumber = i;
						}
					}

					if (alreadyInList) {
						// Add Amount to Row
						String textboxAmountOfExistingRow = ((TextBox) flextableComponentgroupElements.getWidget(alreadyInListRowNumber, 3)
								.asWidget()).getText();
						int oldAmount = Integer.parseInt(textboxAmountOfExistingRow);
						int AmountToAdd = Integer.parseInt(textboxAmountOfElementToAdd.getText());

						int sumAmount = oldAmount + AmountToAdd;

						((TextBox) flextableComponentgroupElements.getWidget(alreadyInListRowNumber, 3).asWidget()).setText(String.valueOf(sumAmount));

					} else {
						// Add Element to list
						for (int i = 0; i < PropertiesOfElementToAdd.length; i++) {
							flextableComponentgroupElements.setText(rowNumToInsertNewRow, i, PropertiesOfElementToAdd[i]);
						}
						TextBox Amount = new TextBox();
						Amount.setText(textboxAmountOfElementToAdd.getText());

						flextableComponentgroupElements.setWidget(rowNumToInsertNewRow, 3, Amount);
					}

				}
			}
		});

		PanelAddElementtOComponentgroup.add(listboxListOfAddableElements);
		PanelAddElementtOComponentgroup.add(new Label("Anzahl:"));
		textboxAmountOfElementToAdd.addKeyPressHandler(new NumbersOnly());
		PanelAddElementtOComponentgroup.add(textboxAmountOfElementToAdd);
		PanelAddElementtOComponentgroup.add(buttonAddElementToComponentgroup);
		RootPanel.get("rightside").add(PanelAddElementtOComponentgroup);

		// Panel: Functions
		buttonUpdateComponentGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Create new ComponentGroup
				ComponentGroup newComponentGroup = new ComponentGroup();

				if (textboxCGInfo_Name.getText().isEmpty()) {
					Window.alert("Bitte geben Sie einen Namen fuer die neue Baugruppe ein");
				} else {
					newComponentGroup.setId(OriginalComponentGroupToEdit.getId());
					newComponentGroup.setModifier(u.getId());
					Window.alert("Baugruppe editiert.");
					newComponentGroup.setComponentGroupName(textboxCGInfo_Name.getText());
					// go through each row
					for (int i = 0; i < flextableComponentgroupElements.getRowCount(); i++) {
						// get type
						String type = flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 0).getInnerHTML();
						// if is componentgroup
						if (type.equalsIgnoreCase("Baugruppe")) {
							int IDOFComponentgroupToAdd = Integer.parseInt(flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 1)
									.getInnerHTML());
							int AmountOfRow = Integer.parseInt(((TextBox) flextableComponentgroupElements.getWidget(i, 3).asWidget()).getText());

							for (ComponentGroup cg : allComponentGroups) {
								if (cg.getId() == IDOFComponentgroupToAdd)
									newComponentGroup.addComponentGroup(cg, AmountOfRow);
							}

						} else if (type.equals("Bauteil")) {
							int IDOFComponentToAdd = Integer.parseInt(flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 1)
									.getInnerHTML());
							int AmountOfRow = Integer.parseInt(((TextBox) flextableComponentgroupElements.getWidget(i, 3).asWidget()).getText());

							for (Component c : allComponents) {
								if (c.getId() == IDOFComponentToAdd)
									newComponentGroup.addComponent(c, AmountOfRow);
							}
						}
					}

					AsyncCallback doNothingAsyncCallback = new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					};

					if (OriginalComponentGroupToEdit.getComponentGroupName() != newComponentGroup.getComponentGroupName()) {
						newComponentGroup.setModifier(u.getId());
						OriginalComponentGroupToEdit.setModifier(u.getId());
						asyncObj.updateComponentGroupById(newComponentGroup, doNothingAsyncCallback);
					} else {
						newComponentGroup.setModifier(u.getId());
						OriginalComponentGroupToEdit.setModifier(u.getId());
						asyncObj.updateComponentGroupById(newComponentGroup, doNothingAsyncCallback);
					}
					// Update existing Amounts of ComponentGroups
					for (int i = 0; i < OriginalComponentGroupToEdit.getAmountListOfComponentGroup().size(); i++) {
						if (OriginalComponentGroupToEdit.getAmountListOfComponentGroup().get(i) != newComponentGroup.getAmountListOfComponentGroup()
								.get(i)) {
							if (newComponentGroup.getAmountListOfComponentGroup().get(i) > 0) // Update
																								// only
																								// if
																								// Amount
																								// is
																								// over
																								// 1
							{
								asyncObj.updateCGElementAmount(OriginalComponentGroupToEdit, OriginalComponentGroupToEdit.getComponentgroupList()
										.get(i).getId(), 'G', newComponentGroup.getAmountListOfComponentGroup().get(i), doNothingAsyncCallback);
							} else {
								asyncObj.deleteCGElement(OriginalComponentGroupToEdit, OriginalComponentGroupToEdit.getComponentgroupList().get(i)
										.getId(), 'G', doNothingAsyncCallback);
							}

						}

					}

					// Update existing Amounts of Components
					for (int i = 0; i < OriginalComponentGroupToEdit.getAmountListOfComponent().size(); i++) {
						if (OriginalComponentGroupToEdit.getAmountListOfComponent().get(i) != newComponentGroup.getAmountListOfComponent().get(i)) {
							if (newComponentGroup.getAmountListOfComponent().get(i) > 0) // Update
							// only
							// if
							// Amount
							// is
							// over
							// 1
							{
								asyncObj.updateCGElementAmount(OriginalComponentGroupToEdit, OriginalComponentGroupToEdit.getComponentList().get(i)
										.getId(), 'C', newComponentGroup.getAmountListOfComponent().get(i), doNothingAsyncCallback);
							} else {
								asyncObj.deleteCGElement(OriginalComponentGroupToEdit,
										OriginalComponentGroupToEdit.getComponentList().get(i).getId(), 'C', doNothingAsyncCallback);
							}

						}

					}

					// Insert new ComponentGroups
					if (OriginalComponentGroupToEdit.getComponentgroupList().size() < newComponentGroup.getComponentgroupList().size()) {
						// if original size is
						// smaller
						for (int i = OriginalComponentGroupToEdit.getComponentgroupList().size(); i < newComponentGroup.getComponentgroupList()
								.size(); i++) {
							if (OriginalComponentGroupToEdit.getId() != newComponentGroup.getComponentgroupList().get(i).getId()) {
								//Only Insert new Component Group if ID is not Equal to ID of OriginalComponentGroup
								OriginalComponentGroupToEdit.setModifier(u.getId());
								asyncObj.insertCGElement(OriginalComponentGroupToEdit, newComponentGroup.getComponentgroupList().get(i).getId(), 'G',
										newComponentGroup.getAmountListOfComponentGroup().get(i), doNothingAsyncCallback);
							}
						}
					}
					// Insert new Components
					if (OriginalComponentGroupToEdit.getComponentList().size() < newComponentGroup.getComponentList().size()) {
						// if original size is smaller
						for (int i = OriginalComponentGroupToEdit.getComponentList().size(); i < newComponentGroup.getComponentList().size(); i++) {
							asyncObj.insertCGElement(OriginalComponentGroupToEdit, newComponentGroup.getComponentList().get(i).getId(), 'C',
									newComponentGroup.getAmountListOfComponent().get(i), doNothingAsyncCallback);

						}
					}
				}
				// Clear
				RootPanel.get("rightside").clear();
			}
		});

		buttonDeleteComponentGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				asyncObj.deleteComponentGroupById(OriginalComponentGroupToEdit, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {

						Window.alert("Baugruppe gelöscht");
						RootPanel.get("rightside").clear();

					}
				});

			}
		});
		PanelFunctions.add(buttonUpdateComponentGroup);
		PanelFunctions.add(buttonDeleteComponentGroup);
		PanelFunctions.add(buttonAbortComponentGroup);
		RootPanel.get("rightside").add(PanelFunctions);

	}

	private void loadComponentsANDComponentGroup() {
		asyncObj.loadAllComponentGroupsIncludingRelations(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {
				listboxListOfAddableElements.setSize("180px", "35px");
				listboxListOfAddableElements.addItem("----");
				listboxListOfAddableElements.addItem("Baugruppe");
				listboxListOfGroupsToEdit.setSize("180px", "35px");
				listboxListOfGroupsToEdit.addItem("Baugruppe");

				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
					listboxListOfGroupsToEdit.addItem(" - " + componentgroup.getId() + ":" + componentgroup.getComponentGroupName());
					listboxListOfAddableElements.addItem(" - " + componentgroup.getId() + ":" + componentgroup.getComponentGroupName());

				}

				asyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ArrayList<Component> Components) {

						ComponentListBoxID = listboxListOfAddableElements.getItemCount();
						// Window.alert(String.valueOf((listOfAddableParts.getItemCount())));
						listboxListOfAddableElements.addItem("Bauteil");
						for (Component component : Components) {

							allComponents.add(component);
							listboxListOfAddableElements.addItem(" - " + component.getId() + ":" + component.getName());
						}
					}
				});

			}

		});
	}

	private void loadAllUser() {
		asyncObj.loadAllUsers(new AsyncCallback<ArrayList<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<User> result) {
				for (int i = 0; i < result.size(); i++) {
					allUsers.add(result.get(i));
				}

			}
		});
	}

	private String[] getELementTypeIdName(String DropDownText) {

		String[] Element = new String[3];

		if (listboxListOfAddableElements.getSelectedIndex() < ComponentListBoxID) {
			Element[0] = "Baugruppe";

		} else {
			Element[0] = "Bauteil";
		}

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = DropDownText.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		Element[1] = SplitStepTwo[1];

		Element[2] = DropDownText.substring(SplitStepOne[0].length() + 1);
		return Element;
	}

	class NumbersOnly implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {

			if (!Character.isDigit(event.getCharCode()) && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE) {
				textboxAmountOfElementToAdd.cancelKey();
			}
		}
	}
}