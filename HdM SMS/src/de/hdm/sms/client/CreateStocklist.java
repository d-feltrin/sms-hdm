package de.hdm.sms.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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

import de.hdm.sms.client.CreateComponentGroup.NumbersOnly;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Stocklist;
import de.hdm.sms.shared.bo.User;

public class CreateStocklist extends VerticalPanel {
	private final AServiceAsync asyncObj = GWT.create(AService.class);

	private ArrayList<Component> allComponents = new ArrayList<Component>();
	private ArrayList<ComponentGroup> allComponentGroups = new ArrayList<ComponentGroup>();

	private final Stocklist newStocklist = new Stocklist();

	private int ComponentListBoxID = 0; // help Variable to determine if part to
										// add
										// is component or componentgroup

	// GUI Objects
	private final HorizontalPanel PanelNewName = new HorizontalPanel();
	private final TextBox textboxNewComponentgroupName = new TextBox();

	private final HorizontalPanel PanelTableOfElements = new HorizontalPanel();
	private final FlexTable flextableComponentgroupElements = new FlexTable();

	private final HorizontalPanel PanelAddElementtoNewComponentgroup = new HorizontalPanel();
	private final ListBox listboxListOfAddableElements = new ListBox();
	private final TextBox textboxAmountOfElementToAdd = new TextBox();
	private final Button buttonAddElementToComponentgroup = new Button("Bauteil/-gruppe hinzufuegen");

	private final HorizontalPanel PanelSubmit = new HorizontalPanel();
	private final Button buttonCreateNewComponengroup = new Button("Stueckliste anlegen");

	private LoginInfo loginInfo;

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {
		listboxListOfAddableElements.addItem("----");
		loadComponentsANDComponentGroup();

		RootPanel.get("rightside").add(PanelAddElementtoNewComponentgroup);

		// Panel: Name
		PanelNewName.add(new Label("Stocklistname"));
		PanelNewName.add(textboxNewComponentgroupName);
		RootPanel.get("rightside").add(PanelNewName);

		// Panel: Table
		flextableComponentgroupElements.setText(0, 0, "Typ");
		flextableComponentgroupElements.setText(0, 1, "ArtikelNr.");
		flextableComponentgroupElements.setText(0, 2, "Name");
		flextableComponentgroupElements.setText(0, 3, "Anzahl");
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

		// Panel: Submit
		buttonCreateNewComponengroup.addClickHandler(new ClickHandler() {

			// SUBMITUTTON
			@Override
			public void onClick(ClickEvent event) {

				if (textboxNewComponentgroupName.getText().isEmpty()) {
					Window.alert("Bitte geben Sie einen Namen fuer die neue Stueckliste ein");
				} else {

					newStocklist.setName(textboxNewComponentgroupName.getText());
					// go through each row
					for (int i = 0; i < flextableComponentgroupElements.getRowCount(); i++) {
						// get type
						String type = flextableComponentgroupElements.getFlexCellFormatter().getElement(i, 0)
								.getInnerHTML();
						// if is componentgroup
						if (type.equalsIgnoreCase("Baugruppe")) {
							int IDOFComponentgroupToAdd = Integer.parseInt(flextableComponentgroupElements
									.getFlexCellFormatter().getElement(i, 1).getInnerHTML());
							int AmountOfRow = Integer.parseInt(((TextBox) flextableComponentgroupElements.getWidget(i,
									3).asWidget()).getText());

							for (ComponentGroup cg : allComponentGroups) {
								if (cg.getId() == IDOFComponentgroupToAdd){
									newStocklist.addComponentGroup(cg, AmountOfRow);
								}
							}

						} else if (type.equalsIgnoreCase("Bauteil")) {
							int IDOFComponentToAdd = Integer.parseInt(flextableComponentgroupElements
									.getFlexCellFormatter().getElement(i, 1).getInnerHTML());
							int AmountOfRow = Integer.parseInt(((TextBox) flextableComponentgroupElements.getWidget(i,
									3).asWidget()).getText());

							for (Component c : allComponents) {
								if (c.getId() == IDOFComponentToAdd)
									newStocklist.addComponent(c, AmountOfRow);
							}
						}
					}

					newStocklist.setModifier(loginInfo.getUser().getId());
					
					asyncObj.insertStocklist(newStocklist, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							System.out.println("hallo");
							Window.alert("Stückliste " + newStocklist.getName() + " erfolgreich angelegt.");
							RootPanel.get("rightside").clear();

						}

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("welt");
							// TODO Auto-generated method stub
							System.out.println(caught.toString());

						}
					});
				}
			}
		});
		PanelSubmit.add(buttonCreateNewComponengroup);
		RootPanel.get("rightside").add(PanelSubmit);
	}

	private void loadComponentsANDComponentGroup() {
		asyncObj.loadAllComponentGroups(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {

				listboxListOfAddableElements.addItem("Baugruppe");
				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
					listboxListOfAddableElements.addItem(" - " + componentgroup.getId() + ":"
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
