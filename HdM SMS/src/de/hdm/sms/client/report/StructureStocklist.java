package de.hdm.sms.client.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.shared.AService;
import de.hdm.sms.shared.AServiceAsync;
import de.hdm.sms.shared.ReportService;

public class StructureStocklist extends VerticalPanel {


	private final ListBox ListOfShowableStocklists = new ListBox();
	private final Button buttonToGenerateStructureStocklist = new Button();
	private final VerticalPanel PanelToAddStructureStocklistData = new VerticalPanel();

	public StructureStocklist() {

	}

	// Load
	public void onLoad() {
		// RootPanel Matching
		RootPanel.get("rightside").clear();
		PanelToAddStructureStocklistData.add(new Label(
				"Struktustückliste anzeigen"));
		PanelToAddStructureStocklistData.add(ListOfShowableStocklists);
		PanelToAddStructureStocklistData
				.add(buttonToGenerateStructureStocklist);

		RootPanel.get("rightside").add(PanelToAddStructureStocklistData);
	}
}
