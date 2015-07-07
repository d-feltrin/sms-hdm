package de.hdm.sms.client.report;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.ComponentGroup;
import de.hdm.sms.shared.bo.Product;

public class MaterialResource extends VerticalPanel {
	private final AServiceAsync asyncObj = GWT.create(AService.class);

	private ArrayList<Product> allProducts = new ArrayList<Product>();

	private ArrayList<Component> allComponents = new ArrayList<Component>();
	// private ArrayList<Integer> amountListOfComponent = new
	// ArrayList<Integer>();

	private ArrayList<ComponentGroup> allComponentGroups = new ArrayList<ComponentGroup>();
	// private ArrayList<Integer> amountListOfComponentGroup = new
	// ArrayList<Integer>();

	private ArrayList<Component> allComponentsOfProduct = new ArrayList<Component>();
	private ArrayList<Integer> amountComponentsOfProduct = new ArrayList<Integer>();

	// GUI Objects
	// Select (FIRSTPAGE)
	private VerticalPanel PanelToGenerateProductResources = new VerticalPanel();
	private ListBox ListBoxOfChoseableProducts = new ListBox();
	private TextBox textBoxAmountOfChosenProducts = new TextBox();
	private Button buttonToGenerateReport = new Button(
			"Materialbedarf berechnen");

	// Load
	public void onLoad() {
		loadAllProducts();
		loadComponentsANDComponentGroup();
		// RootPanel Matching
		RootPanel.get("rightside").clear();
		PanelToGenerateProductResources.add(new Label(
				"Produkt zur Materialbedarfsrechnung auswählen"));
		PanelToGenerateProductResources.add(ListBoxOfChoseableProducts);
		PanelToGenerateProductResources.add(new Label("Anzahl eingeben"));
		PanelToGenerateProductResources.add(textBoxAmountOfChosenProducts);
		PanelToGenerateProductResources.add(buttonToGenerateReport);

		RootPanel.get("rightside").add(PanelToGenerateProductResources);

		// Buttonclickhandler
		buttonToGenerateReport.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (ListBoxOfChoseableProducts.getItemText(
						ListBoxOfChoseableProducts.getSelectedIndex()).equals(
						"---")) {
					Window.alert("Bitte ein gueltiges Produkt auswaehlen");
				} else if (textBoxAmountOfChosenProducts.getValue().isEmpty()) {
					Window.alert("Bitte eine Anzahl eingeben");
				} else {
					// get selected Item
					int IdOfSelectedItem = getIdFromProduct(ListBoxOfChoseableProducts
							.getItemText(ListBoxOfChoseableProducts
									.getSelectedIndex()));
					for (Product product : allProducts) {

						if (product.getId() == IdOfSelectedItem) {
							LoadProductlistToGenerateResources(product); // load
																			// selected
							// item
							break;
						}

					}
				}

			}
		});
	}

	private void LoadProductlistToGenerateResources(Product product_) {
		RootPanel.get("rightside").clear();
		int amountOfProduct = Integer.valueOf(textBoxAmountOfChosenProducts
				.getText());

		VerticalPanel PanelProductInfo_Name = new VerticalPanel();
		PanelProductInfo_Name.add(new Label("Produkt:"));
		PanelProductInfo_Name.add(new Label(product_.getProductName()));

		RootPanel.get("rightside").add(PanelProductInfo_Name);

		VerticalPanel PanelProductInfo_Anzahl = new VerticalPanel();
		PanelProductInfo_Anzahl.add(new Label("Anzahl:"));
		PanelProductInfo_Anzahl.add(new Label(String.valueOf(amountOfProduct)));

		RootPanel.get("rightside").add(PanelProductInfo_Anzahl);

		ComponentGroup componentGrouToCalcResources = null;
		for (ComponentGroup componentgroup : allComponentGroups) {
			if (componentgroup.getId() == product_.getComponentGroupId()) {
				componentGrouToCalcResources = componentgroup;
				break;
			}
		}

		// Add all ComponentGroups
		loadAllComponentsOfComponentGroup(componentGrouToCalcResources,
				amountOfProduct);

		ArrayList<Component> newListOfAggregatedComponents = new ArrayList<Component>();
		ArrayList<Integer> newListOfAggregatedComponentsAmount = new ArrayList<Integer>();

		for (int i = 0; i < allComponentsOfProduct.size(); i++) {
			Component component = allComponentsOfProduct.get(i);

			boolean isAlreadyInList = false;
			int listId = -1;
			for (int j = 0; j < newListOfAggregatedComponents.size(); j++) {
				if (newListOfAggregatedComponents.get(j).getId() == component
						.getId()) {
					isAlreadyInList = true;
					listId = j;
				}
			}

			if (isAlreadyInList) {
				int oldAmount = newListOfAggregatedComponentsAmount.get(listId);
				int newAmount = oldAmount + amountComponentsOfProduct.get(i);
				newListOfAggregatedComponentsAmount.set(listId, newAmount);
			} else {
				newListOfAggregatedComponents.add(component);
				newListOfAggregatedComponentsAmount
						.add(amountComponentsOfProduct.get(i));
			}

		}

		FlexTable flextableComponentsInProduct = new FlexTable();
		flextableComponentsInProduct.setText(0, 0, "Bauteilname.");
		flextableComponentsInProduct.setText(0, 1, "Bauteilanzahl");

		for (int j = 0; j < newListOfAggregatedComponents.size(); j++) {
			int rowNumToInsert = flextableComponentsInProduct.getRowCount();
			flextableComponentsInProduct.setText(rowNumToInsert, 0,
					newListOfAggregatedComponents.get(j).getName());
			flextableComponentsInProduct.setText(rowNumToInsert, 1,
					String.valueOf(newListOfAggregatedComponentsAmount.get(j)));
		}

		RootPanel.get("rightside").add(flextableComponentsInProduct);
	}

	private void loadAllComponentsOfComponentGroup(ComponentGroup cg, int amount) {
		// Add All Components To List
		for (int i = 0; i < cg.getComponentList().size(); i++) {
			this.allComponentsOfProduct.add(cg.getComponentList().get(i));
			this.amountComponentsOfProduct.add(cg.getAmountListOfComponent()
					.get(i) * amount);
		}

		for (int i = 0; i < cg.getComponentgroupList().size(); i++) {
			loadAllComponentsOfComponentGroup(
					cg.getComponentgroupList().get(i), amount
							* cg.getAmountListOfComponentGroup().get(i));
		}

	}

	private void loadAllProducts() {
		ListBoxOfChoseableProducts.setSize("180px", "35px");
		ListBoxOfChoseableProducts.addItem("---");
		asyncObj.loadAllProducts(new AsyncCallback<ArrayList<Product>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Product> result) {
				for (int i = 0; i < result.size(); i++) {
					ListBoxOfChoseableProducts.addItem(" - "
							+ result.get(i).getId() + ":"
							+ result.get(i).getProductName());
					allProducts.add(result.get(i));
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

	private int getIdFromProduct(String dropdowntext) {
		int ProductID = -1;

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = dropdowntext.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		ProductID = Integer.valueOf(SplitStepTwo[1]);

		return ProductID;
	}

}
