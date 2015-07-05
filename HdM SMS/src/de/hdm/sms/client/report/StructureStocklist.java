package de.hdm.sms.client.report;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.ReportService;
import de.hdm.sms.shared.bo.Stocklist;

public class StructureStocklist extends VerticalPanel {

	private final AServiceAsync asyncObj = GWT.create(AService.class);
	private final ListBox ListOfShowableStocklists = new ListBox();
	private final Button buttonToGenerateStructureStocklist = new Button(
			"Strukturstückliste anzeigen");
	private final VerticalPanel PanelToAddStructureStocklistData = new VerticalPanel();
	private ArrayList<Stocklist> allStocklists = new ArrayList<Stocklist>();

	private void loadStocklist() {

		asyncObj.loadAllStocklistsIncludingRelations(new AsyncCallback<ArrayList<Stocklist>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<Stocklist> StockLists) {
				ListOfShowableStocklists.addItem("Stueckliste");
				for (Stocklist stocklist : StockLists) {
					ListOfShowableStocklists.addItem(" - " + stocklist.getId()
							+ ":" + stocklist.getName());
					allStocklists.add(stocklist);
				}
			}
		});
	}

	public StructureStocklist() {

	}

	// Get the Id by splitting the Listbox value
	private String getIDbyDropDownText(String selectedStocklist) {

		// get ID by ListBox text
		// Splitt " - 569:Bla" into " - 569", "Bla"
		String[] SplitStepOne = selectedStocklist.split(":");

		// Splitt " - 569" into " - ", "569"
		String[] SplitStepTwo = SplitStepOne[0].split(" ");

		return SplitStepTwo[1];
	}

	// Load
	public void onLoad() {
		loadStocklist();
		ListOfShowableStocklists.setSize("180px", "35px");

		// RootPanel Matching
		RootPanel.get("rightside").clear();
		PanelToAddStructureStocklistData.add(new Label(
				"Bitte eine Strukturstückliste auswählen"));
		PanelToAddStructureStocklistData.add(ListOfShowableStocklists);
		PanelToAddStructureStocklistData
				.add(buttonToGenerateStructureStocklist);

		RootPanel.get("rightside").add(PanelToAddStructureStocklistData);
		buttonToGenerateStructureStocklist.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

			}
		});
	}
}
