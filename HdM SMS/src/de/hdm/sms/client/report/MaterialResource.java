package de.hdm.sms.client.report;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm.sms.shared.bo.Product;

public class MaterialResource extends VerticalPanel {
	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private VerticalPanel PanelToGenerateProductResources = new VerticalPanel();
	private ListBox ListBoxOfChoseableProducts = new ListBox();
	private TextBox AmountOfChosenProducts = new TextBox();
	private Button buttonToGenerateReport = new Button(
			"Materialbedarf berechnen");
	

	public MaterialResource() {

	}

	private String getIDbyDropDownText(String selectedProduct) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedProduct.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
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
				}
			}
		});

	}

	// Load
	public void onLoad() {
		loadAllProducts();
		// RootPanel Matching
		RootPanel.get("rightside").clear();
		PanelToGenerateProductResources.add(new Label(
				"Produkt zur Materialbedarfsrechnung auswählen"));
		PanelToGenerateProductResources.add(ListBoxOfChoseableProducts);
		PanelToGenerateProductResources.add(new Label("Anzahl eingeben"));
		PanelToGenerateProductResources.add(AmountOfChosenProducts);
		PanelToGenerateProductResources.add(buttonToGenerateReport);
		
		RootPanel.get("rightside").add(PanelToGenerateProductResources);

		// Buttonclickhandler
		buttonToGenerateReport.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});
	}

}
