package de.hdm.sms.client.report;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Stocklist;
import de.hdm.sms.shared.bo.User;

public class StructureStocklist extends VerticalPanel {

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

	DateTimeFormat dF = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm:ss");

	Tree tree = new Tree();
	TreeItem rootTreeItem = new TreeItem();
	
	// Load
	public void onLoad() {
		// Load
		loadStocklist();
		loadComponentsANDComponentGroup();
		loadAllUser();

		
		// Panel: Select StocklistToEdit
		PanelSelectStocklistToEdit.add(new Label("Wahlen Sie die Stueckliste aus, die Sie editieren moechten:"));
		PanelSelectStocklistToEdit.add(listboxListOfStocklistsToEdit);
		PanelSelectStocklistToEdit.add(buttonEditStocklist);

		buttonEditStocklist.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (listboxListOfStocklistsToEdit.getItemText(listboxListOfStocklistsToEdit.getSelectedIndex()).equals(
						"Stueckliste"))
					Window.alert("Bitte eine Stueckliste auswaehlen");
				else {
					// get selected Item
					String IdOfSelectedItem = getELementTypeIdName(listboxListOfStocklistsToEdit
							.getItemText(listboxListOfStocklistsToEdit.getSelectedIndex()))[1];
					for (Stocklist stocklist : allStocklists) {

						if (stocklist.getId() == Integer.valueOf(IdOfSelectedItem)) {
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

	private void loadComponentsANDComponentGroup() {
		asyncObj.loadAllComponentGroupsIncludingRelations(new AsyncCallback<ArrayList<ComponentGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<ComponentGroup> ComponentGroups) {

				for (ComponentGroup componentgroup : ComponentGroups) {
					allComponentGroups.add(componentgroup);
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

	

	private void LoadStocklistToEdit(Stocklist stocklist_) {
		RootPanel.get("rightside").clear();

		HorizontalPanel PanelSLInfo_Name = new HorizontalPanel();
		PanelSLInfo_Name.add(new Label("Name:"));
		PanelSLInfo_Name.add(new Label(stocklist_.getName()));
		RootPanel.get("rightside").add(PanelSLInfo_Name);

		HorizontalPanel PanelSLInfo_CreeationDate = new HorizontalPanel();
		PanelSLInfo_CreeationDate.add(new Label("Creationdate:"));
		PanelSLInfo_CreeationDate.add(new Label(dF.format(stocklist_.getCreationDate())));
		RootPanel.get("rightside").add(PanelSLInfo_CreeationDate);

		HorizontalPanel PanelSLInfo_LastEditor = new HorizontalPanel();
		PanelSLInfo_LastEditor.add(new Label("Modifier:"));
		String lastModifier = "Unknown";
		for (User user : allUsers) {
			if (user.getId() == stocklist_.getModifier()) {
				lastModifier = user.getFirstName() + " " + user.getLastName();
				break;
			}
		}
		PanelSLInfo_LastEditor.add(new Label(lastModifier));
		RootPanel.get("rightside").add(PanelSLInfo_LastEditor);

		HorizontalPanel PanelSLInfo_LastModified = new HorizontalPanel();
		PanelSLInfo_LastModified.add(new Label("Last Modified:"));
		PanelSLInfo_LastModified.add(new Label(dF.format(stocklist_.getCreationDate())));
		RootPanel.get("rightside").add(PanelSLInfo_LastModified);

		// Tree
		// Add all Components
		rootTreeItem.setText(stocklist_.getName());

		for (int j = 0; j < stocklist_.getComponentList().size(); j++) {

			TreeItem componentItem = new TreeItem();

			String componentItemText = "BT - ";
			componentItemText += stocklist_.getAmountListOfComponent().get(j).toString();
			componentItemText += "x - ";
			componentItemText += stocklist_.getComponentList().get(j).getName();
			componentItem.setText(componentItemText);
			componentItem.setTitle("BT" + stocklist_.getComponentList().get(j).getId());
			rootTreeItem.addItem(componentItem);
		}

		// Add all ComponentGroups
		for (int j = 0; j < stocklist_.getComponentGroupList().size(); j++) {

			TreeItem componenGroupitem = new TreeItem();

			String componentGroupItemText = "BG - ";
			componentGroupItemText += stocklist_.getAmountListOfComponentGroup().get(j).toString();
			componentGroupItemText += "x - ";
			componentGroupItemText += stocklist_.getComponentGroupList().get(j).getComponentGroupName();
			componenGroupitem.setText(componentGroupItemText);
			componenGroupitem.setTitle("BG" + stocklist_.getComponentGroupList().get(j).getId());
			componenGroupitem = fillTreeItem(componenGroupitem, stocklist_.getComponentGroupList().get(j),1);
			rootTreeItem.addItem(componenGroupitem);
		}

		tree.addItem(rootTreeItem);

		HTML treeHtml = new HTML(tree.toString());
		RootPanel.get("rightside").add(treeHtml);

	}

	private TreeItem fillTreeItem(TreeItem treeItem, ComponentGroup cg, int amount) {

		for (int i = 0; i < cg.getComponentgroupList().size(); i++) {
			TreeItem componenGroupItem = new TreeItem();

			String item = "BG - ";
			item += (cg.getAmountListOfComponentGroup().get(i).intValue() * amount);
			item += "x - ";
			item += cg.getComponentgroupList().get(i).getComponentGroupName();
			componenGroupItem.setText(item);

			componenGroupItem = fillTreeItem(componenGroupItem, cg.getComponentgroupList().get(i),(cg.getAmountListOfComponentGroup().get(i).intValue() * amount));

			treeItem.addItem(componenGroupItem);
		}

		for (int i = 0; i < cg.getComponentList().size(); i++) {
			TreeItem componenItem = new TreeItem();
			String item = "BT - ";
			item +=  (cg.getAmountListOfComponent().get(i).intValue() * amount);
			item += "x - ";
			item += cg.getComponentList().get(i).getName();
			componenItem.setText(item);
			treeItem.addItem(componenItem);
		}

		return treeItem;
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
					listboxListOfStocklistsToEdit.addItem(" - " + stocklist.getId() + ":" + stocklist.getName());
					allStocklists.add(stocklist);
				}
			}
		});
	}

	private String[] getELementTypeIdName(String DropDownText) {

		String[] Element = new String[3];

		Element[0] = "";

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = DropDownText.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		Element[1] = SplitStepTwo[1];

		Element[2] = DropDownText.substring(SplitStepOne[0].length() + 1);
		return Element;
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

}
