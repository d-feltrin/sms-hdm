package de.hdm.sms.client;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.client.CreateComponentGroup.NumbersOnly;
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

	// Panel: List
	private final HorizontalPanel PanelTableOfElements = new HorizontalPanel();
	private final FlexTable flextableComponentgroupElements = new FlexTable();

	// Panel: Add Element
	private final HorizontalPanel PanelAddElementtoNewComponentgroup = new HorizontalPanel();
	private final ListBox listboxListOfAddableElements = new ListBox();
	private final TextBox textboxAmountOfElementToAdd = new TextBox();
	private final Button buttonAddElementToComponentgroup = new Button("Bauteil/-gruppe hinzufuegen");
	
	//Panel Funcitons
	private final HorizontalPanel PanelFunctions = new HorizontalPanel();
	private final Button buttonUpdateComponentGroup = new Button("Uebernehmen");
	private final Button buttonDeleteComponentGroup = new Button("Loeschen");
	private final Button buttonAbortComponentGroup = new Button("Abbrechen");

	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm:ss");

	private int ComponentListBoxID = 0; // help Variable to determine if part to
										// add
										// is component or componentgroup

	private LoginInfo loginInfo;
	private User u = new User();

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		loadComponentsANDComponentGroup();
		loadAllUser();

		// Panel: Name
		PanelSelectGroupToEdit.add(new Label("Wahlen Sie die Baugruppe aus, die Sie editieren moechten:"));
		PanelSelectGroupToEdit.add(listboxListOfGroupsToEdit);
		PanelSelectGroupToEdit.add(buttonEditGroup);

		buttonEditGroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listboxListOfGroupsToEdit.getItemText(listboxListOfGroupsToEdit.getSelectedIndex()).equals(
						"Baugruppe"))
					Window.alert("Bitte eine Baugruppe auswaehlen");

				String IdOfSelectedItem = getELementTypeIdName(listboxListOfGroupsToEdit
						.getItemText(listboxListOfGroupsToEdit.getSelectedIndex()))[0];
				for (ComponentGroup componentGroup : allComponentGroups) {
					if (componentGroup.getId() == Integer.valueOf(IdOfSelectedItem)) {
						LoadElementEdit(componentGroup);
						continue;
					}

				}
			}
		});

		RootPanel.get("rightside").add(PanelSelectGroupToEdit);

	}

	private void LoadElementEdit(ComponentGroup ComponentGroupToEdit) {
		RootPanel.get("rightside").clear();

		HorizontalPanel PanelCGEID = new HorizontalPanel();
		PanelCGEID.add(new Label("ID"));
		PanelCGEID.add(new Label(String.valueOf(ComponentGroupToEdit.getId())));
		PanelCGEID.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGEID);

		HorizontalPanel PanelCGEName = new HorizontalPanel();
		PanelCGEName.add(new Label("Name:"));
		PanelCGEName.add(new Label(ComponentGroupToEdit.getComponentGroupName()));
		PanelCGEName.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGEName);

		HorizontalPanel PanelCGECreated = new HorizontalPanel();
		PanelCGECreated.add(new Label("Creationdate:"));
		PanelCGECreated.add(new Label(dF.format(ComponentGroupToEdit.getCreationDate())));
		PanelCGECreated.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGECreated);

		HorizontalPanel PanelCGEModified = new HorizontalPanel();
		PanelCGEModified.add(new Label("Modifier:"));
		String lastModifier = "Unknown";
		for (User user : allUsers) {
			if (user.getId() == ComponentGroupToEdit.getModifier())
				lastModifier = user.getFirstName() + " " + user.getLastName();
		}
		PanelCGEModified.add(new Label(lastModifier));
		PanelCGEModified.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGEModified);

		flextableComponentgroupElements.setText(0, 0, "Typ");
		flextableComponentgroupElements.setText(0, 1, "ArtikelNr.");
		flextableComponentgroupElements.setText(0, 2, "Name");
		flextableComponentgroupElements.setText(0, 3, "Anzahl");

		for (int i = 0; i < ComponentGroupToEdit.getComponentgroupList().size(); i++) {
			int rowNumToInsert = flextableComponentgroupElements.getRowCount();
			flextableComponentgroupElements.setText(rowNumToInsert, 0, "Baugruppe");
			flextableComponentgroupElements.setText(rowNumToInsert, 1,
					String.valueOf(ComponentGroupToEdit.getComponentgroupList().get(i).getId()));
			flextableComponentgroupElements.setText(rowNumToInsert, 2, ComponentGroupToEdit.getComponentgroupList()
					.get(i).getComponentGroupName());

			TextBox Amount = new TextBox();
			Amount.setText(String.valueOf(ComponentGroupToEdit.getAmountListOfComponentGroup().get(i)));

			flextableComponentgroupElements.setWidget(rowNumToInsert, 3, Amount);
		}

		for (int i = 0; i < ComponentGroupToEdit.getComponentList().size(); i++) {
			int rowNumToInsert = flextableComponentgroupElements.getRowCount();
			flextableComponentgroupElements.setText(rowNumToInsert, 0, "Bauteil");
			flextableComponentgroupElements.setText(rowNumToInsert, 1,
					String.valueOf(ComponentGroupToEdit.getComponentList().get(i).getId()));
			flextableComponentgroupElements.setText(rowNumToInsert, 2, ComponentGroupToEdit.getComponentList().get(i)
					.getName());

			TextBox Amount = new TextBox();
			Amount.setText(String.valueOf(ComponentGroupToEdit.getAmountListOfComponent().get(i)));

			flextableComponentgroupElements.setWidget(rowNumToInsert, 3, Amount);
		}
		PanelTableOfElements.add(flextableComponentgroupElements);
		RootPanel.get("rightside").add(PanelTableOfElements);

		// Panel: Add
		buttonAddElementToComponentgroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listboxListOfAddableElements.getItemText(listboxListOfAddableElements.getSelectedIndex()).equals(
						"----")
						|| listboxListOfAddableElements.getItemText(listboxListOfAddableElements.getSelectedIndex())
								.equals("Bauteil")
						|| listboxListOfAddableElements.getItemText(listboxListOfAddableElements.getSelectedIndex())
								.equals("Baugruppe")) {
					Window.alert("Bitte waehlen Sie ein gueltiges Bauteil bzw. Bauelement aus!");

				} else if (textboxAmountOfElementToAdd.getText().equals("")) {
					Window.alert("Bitte die Anzahl des hinzuzufuegenden Bauteils/Baugruppe eintragen!");
				} else if (Integer.parseInt(textboxAmountOfElementToAdd.getText()) < 1
						|| Integer.parseInt(textboxAmountOfElementToAdd.getText()) > 1000) {
					Window.alert("Anzahl des hinzuzufuegenden Bauteils/Baugruppe darf nicht unter 0 sein!");
				}

				else {
					int rowNumToInsertNewRow = flextableComponentgroupElements.getRowCount();

					// Get Type, ID and Name by Dropdowntext
					String[] PropertiesOfElementToAdd = getELementTypeIdName(listboxListOfAddableElements
							.getItemText(listboxListOfAddableElements.getSelectedIndex()));

					// Search in List if Element is already added
					boolean alreadyInList = false;
					int alreadyInListRowNumber = -1;

					for (int i = 1; i < flextableComponentgroupElements.getRowCount(); i++) {
						if (flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 1).getInnerHTML()
								.equals(PropertiesOfElementToAdd[1])
								&& flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 2)
										.getInnerHTML().equals(PropertiesOfElementToAdd[2])) {
							alreadyInList = true;
							alreadyInListRowNumber = i;
						}
					}

					if (alreadyInList) {
						// Add Amount to Row
						String textboxAmountOfExistingRow = ((TextBox) flextableComponentgroupElements.getWidget(
								alreadyInListRowNumber, 3).asWidget()).getText();
						int oldAmount = Integer.parseInt(textboxAmountOfExistingRow);
						int AmountToAdd = Integer.parseInt(textboxAmountOfElementToAdd.getText());

						int sumAmount = oldAmount + AmountToAdd;

						((TextBox) flextableComponentgroupElements.getWidget(alreadyInListRowNumber, 3).asWidget())
								.setText(String.valueOf(sumAmount));

					} else {
						for (int i = 0; i < PropertiesOfElementToAdd.length; i++) {
							flextableComponentgroupElements.setText(rowNumToInsertNewRow, i,
									PropertiesOfElementToAdd[i]);
						}
						TextBox Amount = new TextBox();
						Amount.setText(textboxAmountOfElementToAdd.getText());

						flextableComponentgroupElements.setWidget(rowNumToInsertNewRow, 3, Amount);
					}

				}
			}
		});

		PanelAddElementtoNewComponentgroup.add(listboxListOfAddableElements);
		PanelAddElementtoNewComponentgroup.add(new Label("Anzahl:"));
		textboxAmountOfElementToAdd.addKeyPressHandler(new NumbersOnly());
		PanelAddElementtoNewComponentgroup.add(textboxAmountOfElementToAdd);
		PanelAddElementtoNewComponentgroup.add(buttonAddElementToComponentgroup);
		RootPanel.get("rightside").add(PanelAddElementtoNewComponentgroup);

		// Panel: Functions
		PanelFunctions.add(buttonUpdateComponentGroup);
		PanelFunctions.add(buttonDeleteComponentGroup);
		PanelFunctions.add(buttonAbortComponentGroup);
		RootPanel.get("rightside").add(PanelFunctions);

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

	private void loadComponentsANDComponentGroup() {
		asyncObj.loadAllComponentGroupsIncludingRelations(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {

				listboxListOfAddableElements.addItem("----");
				listboxListOfAddableElements.addItem("Baugruppe");
				listboxListOfGroupsToEdit.addItem("Baugruppe");

				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
					listboxListOfAddableElements.addItem(" - " + componentgroup.getId() + ":"
							+ componentgroup.getComponentGroupName());
					listboxListOfGroupsToEdit.addItem(" - " + componentgroup.getId() + ":"
							+ componentgroup.getComponentGroupName());
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

		String[] Element = new String[2];

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = DropDownText.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		Element[0] = SplitStepTwo[1];

		Element[1] = DropDownText.substring(SplitStepOne[0].length() + 1);
		return Element;
	}

	class NumbersOnly implements KeyPressHandler {

		@Override
		public void onKeyPress(KeyPressEvent event) {

			if (!Character.isDigit(event.getCharCode()) && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE) {
				// textboxAmountOfElementToAdd.cancelKey();
			}
		}
	}
}