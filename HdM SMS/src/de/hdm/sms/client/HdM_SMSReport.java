package de.hdm.sms.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.sms.client.report.MaterialResource;
import de.hdm.sms.client.report.StructureStocklist;

public class HdM_SMSReport extends VerticalPanel implements EntryPoint {
	

	// Load
	@Override
	public void onModuleLoad() {

		// Create a menu bar
		MenuBar menu = new MenuBar(true);
		menu.setAutoOpen(true);
		menu.setWidth("150px");
		menu.setAnimationEnabled(true);
		
		// Create the Command for Stocklist
		menu.addItem("Strukturstückliste", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				StructureStocklist sS = new StructureStocklist();
				// cR.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(sS);
			}
		});
		// Create the Command for MaterialResourcePlanning
		menu.addItem("Materialbedarfsplanung", new Command() {
			@Override
			public void execute() {

				RootPanel.get("rightside").clear();
				MaterialResource mS = new MaterialResource();
				// cR.setLoginInfo(loginInfo);
				RootPanel.get("rightside").add(mS);
			}
		});
		menu.addItem("Editor", new Command() {

			@Override
			public void execute() {
				Window.open(
						"http://1-dot-stuecklistenmanagementsystem.appspot.com/HdM_SMS.html",
						"_blank", "");

			}
		});
		//Add the Reportmenu to the Panel
		
		
		RootPanel.get("leftside").add(menu);

	}
}
