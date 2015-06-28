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

	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm:ss");

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

		HorizontalPanel PanelTableOfElements = new HorizontalPanel();
		FlexTable flextableComponentgroupElements = new FlexTable();
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

	}

	public User getUserIdByEMailAdress(String eMailAdress) {

		asyncObj.getUserByEmail(eMailAdress, new AsyncCallback<User>() {

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
		asyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {

				listboxListOfGroupsToEdit.addItem("Baugruppe");
				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
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

						for (Component component : Components) {
							allComponents.add(component);
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