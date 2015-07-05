package de.hdm.sms.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.jjs.InternalCompilerException.NodeInfo;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.sms.client.CreateStocklist.NumbersOnly;
import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.LoginInfo;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Stocklist;
import de.hdm.sms.shared.bo.User;

public class EditStockList extends VerticalPanel {

	private final AServiceAsync asyncObj = GWT.create(AService.class);

	private ArrayList<Component> allComponents = new ArrayList<Component>();
	private ArrayList<ComponentGroup> allComponentGroups = new ArrayList<ComponentGroup>();
	private ArrayList<User> allUsers = new ArrayList<User>();
	private ArrayList<Stocklist> allStocklists = new ArrayList<Stocklist>();

	// GUI Objects
	// Select (FIRSTPAGE)
	private final HorizontalPanel PanelSelectStocklistToEdit = new HorizontalPanel();
	private final ListBox listboxListOfStocklistsToEdit = new ListBox();
	private final Button buttonEditStocklist = new Button("Editieren");

	// EDIT (SECONDPAGE)
	// Panel: Info ComponentName
	private final VerticalPanel PanelCGInfo_Name = new VerticalPanel();
	private final TextBox textboxCGInfo_Name = new TextBox();

	// Panel: Edit Element
	private final HorizontalPanel PanelEditElementOfStocklist = new HorizontalPanel();
	private final Label labelEditNameOfElementToEdit = new Label();
	private final TextBox textBoxAmountOfElementToEdit = new TextBox();
	private final Button buttonEditElementOfStocklist = new Button(
			"Bauteil/-gruppe editieren");
	private String ElementTitleOfElementToEdit = "";

	// Panel: Add Element
	private final HorizontalPanel PanelAddElementtOComponentgroup = new HorizontalPanel();
	private final ListBox listboxListOfAddableElements = new ListBox();
	private final TextBox textboxAmountOfElementToAdd = new TextBox();
	private final Button buttonAddElementToComponentgroup = new Button(
			"Bauteil/-gruppe hinzufuegen");

	// Panel Funcitons
	private final HorizontalPanel PanelFunctions = new HorizontalPanel();
	private final Button buttonUpdateComponentGroup = new Button("Uebernehmen");
	private final Button buttonDeleteComponentGroup = new Button("Loeschen");
	private final Button buttonAbortComponentGroup = new Button("Abbrechen");

	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

	private Stocklist OriginalStocklistToEdit = new Stocklist();

	private int idOfComponentstartInListbox = 0; // help Variable to determine
													// if part to
	// add is component or componentgroup

	Tree tree = new Tree();
	TreeItem rootTreeItem = new TreeItem();

	private LoginInfo loginInfo;
	private User u = new User();

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public void onLoad() {

		// Load
		loadStocklist();
		loadComponentsANDComponentGroup();
		getUserIdByEMailAdress(loginInfo.getEmailAddress());
		loadAllUser();

		// Panel: Select StocklistToEdit
		PanelSelectStocklistToEdit.add(new Label(
				"Wahlen Sie die Stueckliste aus, die Sie editieren moechten:"));
		PanelSelectStocklistToEdit.add(listboxListOfStocklistsToEdit);
		PanelSelectStocklistToEdit.add(buttonEditStocklist);

		buttonEditStocklist.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listboxListOfStocklistsToEdit.getItemText(
						listboxListOfStocklistsToEdit.getSelectedIndex())
						.equals("Stueckliste"))
					Window.alert("Bitte eine Stueckliste auswaehlen");
				else {
					// get selected Item
					String IdOfSelectedItem = getELementTypeIdName(listboxListOfStocklistsToEdit
							.getItemText(listboxListOfStocklistsToEdit
									.getSelectedIndex()))[1];
					for (Stocklist stocklist : allStocklists) {

						if (stocklist.getId() == Integer
								.valueOf(IdOfSelectedItem)) {
							LoadStocklistToEdit(stocklist); // load selected
															// item
							break;
						}

					}
				}
			}
		});

		RootPanel.get("rightside").add(PanelSelectStocklistToEdit);

	}

	private void LoadStocklistToEdit(Stocklist stocklist_) {
		RootPanel.get("rightside").clear();

		OriginalStocklistToEdit = stocklist_; // save for compare

		VerticalPanel PanelCGInfo_ID = new VerticalPanel();
		PanelCGInfo_ID.add(new Label("Stücklistennummer"));
		PanelCGInfo_ID.add(new Label(String.valueOf(OriginalStocklistToEdit
				.getId())));

		RootPanel.get("rightside").add(PanelCGInfo_ID);

		PanelCGInfo_Name.add(new Label("Name:"));
		textboxCGInfo_Name.setText(OriginalStocklistToEdit.getName());
		PanelCGInfo_Name.add(textboxCGInfo_Name);

		RootPanel.get("rightside").add(PanelCGInfo_Name);

		VerticalPanel PanelCGInfo_CreeationDate = new VerticalPanel();
		PanelCGInfo_CreeationDate.add(new Label("Erstellungsdatum:"));
		PanelCGInfo_CreeationDate.add(new Label(dF
				.format(OriginalStocklistToEdit.getCreationDate())));

		RootPanel.get("rightside").add(PanelCGInfo_CreeationDate);
		VerticalPanel PanelCGInfo_LastModifier = new VerticalPanel();
		PanelCGInfo_LastModifier.add(new Label("Bearbeitungsdatum:"));
		PanelCGInfo_LastModifier.add(new Label(dF
				.format(OriginalStocklistToEdit.getLastModified())));

		RootPanel.get("rightside").add(PanelCGInfo_LastModifier);

		VerticalPanel PanelCGInfo_LastEditor = new VerticalPanel();
		PanelCGInfo_LastEditor.add(new Label("Letzter Bearbeiter:"));
		String lastModifier = "Unknown";
		for (User user : allUsers) {
			if (user.getId() == OriginalStocklistToEdit.getModifier()) {
				lastModifier = user.getFirstName() + " " + user.getLastName();
				break;
			}
		}
		PanelCGInfo_LastEditor.add(new Label(lastModifier));
		PanelCGInfo_LastEditor.add(new Label(" "));
		RootPanel.get("rightside").add(PanelCGInfo_LastEditor);

		// Tree
		// Add all Components
		rootTreeItem.setText(OriginalStocklistToEdit.getName());

		for (int j = 0; j < OriginalStocklistToEdit.getComponentList().size(); j++) {

			TreeItem componentItem = new TreeItem();

			String componentItemText = "BT - ";
			componentItemText += OriginalStocklistToEdit
					.getAmountListOfComponent().get(j).toString();
			componentItemText += "x - ";
			componentItemText += OriginalStocklistToEdit.getComponentList()
					.get(j).getName();
			componentItem.setText(componentItemText);
			componentItem
					.setTitle("BT"
							+ OriginalStocklistToEdit.getComponentList().get(j)
									.getId());
			rootTreeItem.addItem(componentItem);
		}

		// Add all ComponentGroups
		for (int j = 0; j < OriginalStocklistToEdit.getComponentGroupList()
				.size(); j++) {

			TreeItem componenGroupitem = new TreeItem();

			String componentGroupItemText = "BG - ";
			componentGroupItemText += OriginalStocklistToEdit
					.getAmountListOfComponentGroup().get(j).toString();
			componentGroupItemText += "x - ";
			componentGroupItemText += OriginalStocklistToEdit
					.getComponentGroupList().get(j).getComponentGroupName();
			componenGroupitem.setText(componentGroupItemText);
			componenGroupitem.setTitle("BG"
					+ OriginalStocklistToEdit.getComponentGroupList().get(j)
							.getId());
			componenGroupitem = fillTreeItem(componenGroupitem,
					OriginalStocklistToEdit.getComponentGroupList().get(j));
			rootTreeItem.addItem(componenGroupitem);
		}

		tree.addItem(rootTreeItem);

		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				String[] Element = getTypeAmountAndNameByTreeElement(item
						.getText());

				TreeItem parent = item;
				int level = 0;
				do {
					parent = parent.getParentItem();
					level++;

				} while (parent != null);
				
				
				
				if (level == 2) {
					labelEditNameOfElementToEdit.setText(Element[0] + " - "
							+ Element[2]);
					textBoxAmountOfElementToEdit.setText(Element[1]);
					textBoxAmountOfElementToEdit.setReadOnly(false);
					buttonEditElementOfStocklist.setEnabled(true);
				} else {
					labelEditNameOfElementToEdit.setText("");
					textBoxAmountOfElementToEdit.setText("");
					textBoxAmountOfElementToEdit.setReadOnly(true);
					buttonEditElementOfStocklist.setEnabled(false);
				}
				ElementTitleOfElementToEdit = (item.getTitle());
			}
		});

		RootPanel.get("rightside").add(tree);

		// Panel: Edit Element
		labelEditNameOfElementToEdit.setText("");

		textBoxAmountOfElementToEdit.addKeyPressHandler(new NumbersOnly());
		textBoxAmountOfElementToEdit.setReadOnly(true);
		buttonEditElementOfStocklist.setEnabled(false);

		PanelEditElementOfStocklist.add(new Label(
				"Bauteil/gruppe Editieren:       "));
		PanelEditElementOfStocklist.add(labelEditNameOfElementToEdit);
		PanelEditElementOfStocklist.add(textBoxAmountOfElementToEdit);

		buttonEditElementOfStocklist.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickHandlerEditElementOfTree();
			}
		});
		PanelEditElementOfStocklist.add(buttonEditElementOfStocklist);
		RootPanel.get("rightside").add(PanelEditElementOfStocklist);

		// Panel: Add new Element
		buttonAddElementToComponentgroup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickHandlerAddElementToTree();
			}
		});

		PanelAddElementtOComponentgroup.add(new Label(
				"Bauteil/gruppe hinzufuegen:       "));
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
				clickHandlerEditStocklist();
				RootPanel.get("rightside").clear();
			}
		});

		PanelFunctions.add(buttonUpdateComponentGroup);
		PanelFunctions.add(buttonDeleteComponentGroup);
		PanelFunctions.add(buttonAbortComponentGroup);
		RootPanel.get("rightside").add(PanelFunctions);

	}

	private TreeItem fillTreeItem(TreeItem treeItem, ComponentGroup cg) {

		for (int i = 0; i < cg.getComponentgroupList().size(); i++) {
			TreeItem componenGroupItem = new TreeItem();

			String item = "BG - ";
			item += cg.getAmountListOfComponentGroup().get(i).toString();
			item += "x - ";
			item += cg.getComponentgroupList().get(i).getComponentGroupName();
			componenGroupItem.setText(item);

			componenGroupItem = fillTreeItem(componenGroupItem, cg
					.getComponentgroupList().get(i));

			treeItem.addItem(componenGroupItem);
		}

		for (int i = 0; i < cg.getComponentList().size(); i++) {
			TreeItem componenItem = new TreeItem();
			String item = "BT - ";
			item += cg.getAmountListOfComponent().get(i).toString();
			item += "x - ";
			item += cg.getComponentList().get(i).getName();
			componenItem.setText(item);
			treeItem.addItem(componenItem);
		}

		return treeItem;
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
						u.setId(result.getId());
						u.setFirstName(result.getFirstName());
						u.setLastName(result.getLastName());
						u.seteMailAdress(result.geteMailAdress());

					}

				});
		return u;

	}

	private void loadStocklist() {

		asyncObj.loadAllStocklistsIncludingRelations(new AsyncCallback<ArrayList<Stocklist>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Stocklist> StockLists) {
				listboxListOfStocklistsToEdit.addItem("Stueckliste");
				for (Stocklist stocklist : StockLists) {
					listboxListOfStocklistsToEdit.addItem(" - "
							+ stocklist.getId() + ":" + stocklist.getName());
					allStocklists.add(stocklist);
				}
			}
		});
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

				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);

					listboxListOfAddableElements.addItem(" - "
							+ componentgroup.getId() + ":"
							+ componentgroup.getComponentGroupName());

				}

				asyncObj.loadAllComponents(new AsyncCallback<ArrayList<Component>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ArrayList<Component> Components) {

						idOfComponentstartInListbox = listboxListOfAddableElements
								.getItemCount();
						// Window.alert(String.valueOf((listOfAddableParts.getItemCount())));
						listboxListOfAddableElements.addItem("Bauteil");
						for (Component component : Components) {

							allComponents.add(component);
							listboxListOfAddableElements.addItem(" - "
									+ component.getId() + ":"
									+ component.getName());
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

	private String[] getTypeAmountAndNameByTreeElement(String treeElement) {

		String[] Element = new String[3];
		Element[0] = treeElement.split("-")[0].substring(0, 2); // get type
		Element[1] = treeElement.split("-")[1].split("x")[0].substring(1); // get
																			// amount
		Element[2] = treeElement.substring(treeElement.split("-")[0].length()
				+ treeElement.split("-")[1].length() + 3);
		return Element;
	}

	private void clickHandlerEditElementOfTree() {
		if (textBoxAmountOfElementToEdit.getText().equals(""))
			Window.alert("Bitte die neue Anzahl des editierenden Bauteils/Baugruppe eintragen!");
		else {
			TreeItem TreeItemToEdit = null;
			for (int i = 0; i < rootTreeItem.getChildCount(); i++) {
				TreeItemToEdit = rootTreeItem.getChild(i);
				if (TreeItemToEdit.getTitle() == ElementTitleOfElementToEdit) {
					String[] Element = new String[3];
					Element = getTypeAmountAndNameByTreeElement(TreeItemToEdit
							.getText());

					String ElementText = "";
					ElementText += Element[0];
					ElementText += " - ";
					ElementText += textBoxAmountOfElementToEdit.getText();
					ElementText += "x - ";
					ElementText += Element[2];
					TreeItemToEdit.setText(ElementText);
				}
			}
		}

	}

	private void clickHandlerAddElementToTree() {
		int selectedID = listboxListOfAddableElements.getSelectedIndex();
		if (listboxListOfAddableElements.getItemText(selectedID).equals("----")
				|| listboxListOfAddableElements.getItemText(selectedID).equals(
						"Bauteil")
				|| listboxListOfAddableElements.getItemText(selectedID).equals(
						"Baugruppe")) {

			Window.alert("Bitte waehlen Sie ein gueltiges Bauteil bzw. Bauelement aus!");

		} else if (textboxAmountOfElementToAdd.getText().equals("")) {
			Window.alert("Bitte die Anzahl des hinzuzufuegenden Bauteils/Baugruppe eintragen!");
		} else if (Integer.parseInt(textboxAmountOfElementToAdd.getText()) < 1) {
			Window.alert("Anzahl des hinzuzufuegenden Bauteils/Baugruppe darf nicht unter 1 sein!");
		} else {
			// Evertthing okay, start to add

			// Get Type, ID and Name by Dropdowntext
			String[] PropertiesOfElementToAdd = getELementTypeIdName(listboxListOfAddableElements
					.getItemText(selectedID));

			// Search in List if Element is already added
			boolean alreadyInList = false;

			TreeItem TreeItem = null;
			for (int i = 0; i < rootTreeItem.getChildCount(); i++) {
				TreeItem = rootTreeItem.getChild(i);
				String NameOfTreeElement = getTypeAmountAndNameByTreeElement(TreeItem
						.getText())[2];
				String NameOfElementToAdd = PropertiesOfElementToAdd[2];
				if (NameOfTreeElement == NameOfElementToAdd) {
					// Item already exist
					alreadyInList = true;
					break;
				}
			}

			if (alreadyInList) {
				// Add Amount to treeitem

				String[] Element = new String[3];
				Element = getTypeAmountAndNameByTreeElement(TreeItem.getText());

				String ElementText = "";
				ElementText += Element[0];
				ElementText += " - ";

				int oldAmount = 0;
				oldAmount = Integer.valueOf(Element[1]);
				int newAmount = Integer.valueOf(textboxAmountOfElementToAdd
						.getText());
				ElementText += (oldAmount + newAmount);
				ElementText += "x - ";
				ElementText += Element[2];
				TreeItem.setText(ElementText);

			} else {
				TreeItem TreeItemToAdd = new TreeItem();

				String ElementText = "";

				ElementText += PropertiesOfElementToAdd[0];
				ElementText += " - ";
				ElementText += textboxAmountOfElementToAdd.getText();
				ElementText += "x - ";
				ElementText += PropertiesOfElementToAdd[2];

				TreeItemToAdd.setText(ElementText);
				TreeItemToAdd.setTitle(PropertiesOfElementToAdd[0]
						+ PropertiesOfElementToAdd[1]);

				rootTreeItem.addItem(TreeItemToAdd);
			}

		}
	}

	private String[] getELementTypeIdName(String DropDownText) {

		String[] Element = new String[3];

		if (listboxListOfAddableElements.getSelectedIndex() < idOfComponentstartInListbox) {
			Element[0] = "BG";

		} else {
			Element[0] = "BT";
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

			if (!Character.isDigit(event.getCharCode())
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE) {
				((TextBox)event.getSource()).cancelKey();
			}
		}
	}

	private void clickHandlerEditStocklist() {
		// Create new Stocklist
		Stocklist newStocklist = new Stocklist();

		AsyncCallback doNothingAsyncCallback = new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		};

		if (textboxCGInfo_Name.getText().isEmpty()) {
			Window.alert("Bitte geben Sie einen Namen fuer die neue Baugruppe ein");
		} else {
			newStocklist.setId(OriginalStocklistToEdit.getId());
			newStocklist.setName(textboxCGInfo_Name.getText());
			newStocklist.setModifier(u.getId());

			for (int i = 0; i < rootTreeItem.getChildCount(); i++) {
				TreeItem treeItem = rootTreeItem.getChild(i);
				String[] PropertiesOfComponentGroup = getTypeAmountAndNameByTreeElement(treeItem
						.getText());
				if (PropertiesOfComponentGroup[0] == "BG") {

					ComponentGroup cg = new ComponentGroup();
					cg.setId(Integer.valueOf(treeItem.getTitle()
							.substring(2, 4)));
					cg.setComponentGroupName(PropertiesOfComponentGroup[2]);
					newStocklist.addComponentGroup(cg,
							Integer.valueOf(PropertiesOfComponentGroup[1]));
				} else { // Component
					Component c = new Component();
					c.setId(Integer
							.valueOf(treeItem.getTitle().substring(2, 4)));
					c.setName(PropertiesOfComponentGroup[2]);
					newStocklist.addComponent(c,
							Integer.valueOf(PropertiesOfComponentGroup[1]));
				}
			}

			asyncObj.updateStockList(newStocklist, doNothingAsyncCallback);
		}

		// Update existing Amounts of ComponentGroups
		for (int i = 0; i < OriginalStocklistToEdit.getComponentGroupList()
				.size(); i++) {
			ComponentGroup originalComponentGroup = OriginalStocklistToEdit
					.getComponentGroupList().get(i);

			for (int j = 0; j < newStocklist.getComponentGroupList().size(); j++) {
				ComponentGroup newStockListComponentGroup = newStocklist
						.getComponentGroupList().get(j);

				if (originalComponentGroup.getId() == newStockListComponentGroup
						.getId()) {

					if (OriginalStocklistToEdit.getAmountListOfComponentGroup()
							.get(i) != newStocklist
							.getAmountListOfComponentGroup().get(j)) {
						if (newStocklist.getAmountListOfComponentGroup().get(j) != 0) { // delete
																						// stocklistcomponentgroupelement
																						// here
							asyncObj.updateAmountOfStocklistComponentGrouElement(
									newStocklist, originalComponentGroup,
									newStocklist
											.getAmountListOfComponentGroup()
											.get(i), doNothingAsyncCallback);
							break;
						}

						else {
							asyncObj.deleteStockListComponentGroupElement(
									newStocklist, originalComponentGroup,
									newStocklist
											.getAmountListOfComponentGroup()
											.get(i), doNothingAsyncCallback);
						}

					}
				}
			}
		}

		// Update existing Amounts of ComponentGroups
		for (int i = 0; i < OriginalStocklistToEdit.getComponentList().size(); i++) {
			Component originalComponent = OriginalStocklistToEdit
					.getComponentList().get(i);

			for (int j = 0; j < newStocklist.getComponentList().size(); j++) {
				Component newStockListComponent = newStocklist
						.getComponentList().get(j);

				if (originalComponent.getId() == newStockListComponent.getId()) {

					if (OriginalStocklistToEdit.getAmountListOfComponent().get(
							i) != newStocklist.getAmountListOfComponent()
							.get(j)) {
						if (newStocklist.getAmountListOfComponent().get(j) != 0) { // delete
																					// stocklistcomponentgroupelement
																					// here
							asyncObj.updateAmountOfStocklistComponentElement(
									newStocklist, originalComponent,
									newStocklist.getAmountListOfComponent()
											.get(i), doNothingAsyncCallback);
							break;
						} else {
							asyncObj.deleteStocklistComponentElement(
									newStocklist, originalComponent,
									newStocklist.getAmountListOfComponent()
											.get(i), doNothingAsyncCallback);
						}
					}
				}
			}
		}

		// Insert new ComponentGroups
		for (int j = 0; j < newStocklist.getComponentGroupList().size(); j++) {
			boolean isNew = true;
			ComponentGroup newStockListComponentGroupToInsert = newStocklist
					.getComponentGroupList().get(j);
			for (int i = 0; i < OriginalStocklistToEdit.getComponentGroupList()
					.size(); i++) {
				ComponentGroup originalComponentGroup = OriginalStocklistToEdit
						.getComponentGroupList().get(i);

				if (newStockListComponentGroupToInsert.getId() == originalComponentGroup
						.getId()) {
					isNew = false;
				}
			}
			if (isNew) {
				asyncObj.insertComponentGroupToSocklist(newStocklist,
						newStockListComponentGroupToInsert, newStocklist
								.getAmountListOfComponentGroup().get(j),
						doNothingAsyncCallback);
			}
		}

		// Insert new ComponentGroups
		for (int j = 0; j < newStocklist.getComponentList().size(); j++) {
			boolean isNew = true;
			Component newStockListComponentToInsert = newStocklist
					.getComponentList().get(j);
			for (int i = 0; i < OriginalStocklistToEdit.getComponentList()
					.size(); i++) {
				Component originalComponent = OriginalStocklistToEdit
						.getComponentList().get(i);

				if (newStockListComponentToInsert.getId() == originalComponent
						.getId()) {
					isNew = false;
				}
			}
			if (isNew) {
				asyncObj.insertComponentToStocklist(newStocklist,
						newStockListComponentToInsert, newStocklist
								.getAmountListOfComponent().get(j),
						doNothingAsyncCallback);
			}
		}
	}

}