package de.hdm.sms.server;

import de.hdm.sms.client.GreetingService;
import de.hdm.sms.shared.FieldVerifier;
import de.hdm.sms.shared.bo.Component;
import de.hdm.sms.shared.bo.Module;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private String username = new String(); // Testzweck
	
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	@Override
	public String getName(String name) throws IllegalArgumentException {
		 
		if (!FieldVerifier.isValidName(name)) {
			 
			throw new IllegalArgumentException(
					"Der Name muss mindestens 4 zeichen lang sein!");
		}


		name = escapeHtml(name);
		
		username= name;

		return name + ", <br>";
	}	
	
	@Override
	public String getTypeOfComponent(String name) throws IllegalArgumentException {
		 
		//Hier muss eine Methode rein, welche testet, ob der name ein Bauteil oder eine Baugruppe ist, dh. while bis zum erbrechen
		//Für den Test ist es ein Bauteil //Testzweck
			
			return "module";
			 //return "component";
	}	
	
	@Override
	public String getName() throws IllegalArgumentException {
		 
		return this.username + ", <br>";
	}	

	@Override
	public String createUser(String name, String email,
			String keyword) throws IllegalArgumentException {
		
		name = escapeHtml(name);
		email = escapeHtml(email);
		keyword = escapeHtml(keyword);
		
		username = name;

		return "Name:" + name + "<br> Email:" + email + "<br>Passwort:" + keyword + "<br><br>";

	}
	
	@Override
	public String createElement(String name, String description,
			String material) throws IllegalArgumentException {
		
		name = escapeHtml(name);
		description = escapeHtml(description);
		material = escapeHtml(material);

		return "<b>Bauteil</b><br><br>Name:" + name + "<br> Beschreibung:" + description + "<br>Material:" + material + "<br><br>";

	}
	
	@Override
	public String search(String name) throws IllegalArgumentException {
		
		/*Component component = new Component("Brett","Ist schön und flauschig","Holz"); // Testzweck
		
		if(name.equals(component.getName())){
			return "j";
		}
		else
		{
			return "n";
		}*/
		
		Module module = new Module(); // Testzweck
		module.setName("Tisch");
		
		if(name.equals(module.getName())){
			return "j";
		}
		else
		{
			return "n";
		}

	}
	
	@Override
	public String createModule(String name, String component1, String component2, 
			String component3,  
			Boolean endproduct) throws IllegalArgumentException {
		
		String endproductStatus = "Als kein Endprodukt gekennzeichnet";
		name = escapeHtml(name);
		
		if(component1 == "empty"){
			component1 = "";
		}
		else
		{
			component1 = escapeHtml(component1);
			component1 = component1 + "<br>";

		}
		
		if(component2 == "empty"){
			component2 = "";
		}
		else
		{
			component2 = escapeHtml(component2);
			component2 = component2 + "<br>";

		}
		
		if(component3 == "empty"){
			component3 = "";
		}
		else
		{
			component3 = escapeHtml(component3);
			component3 = component3 + "<br>";

		}

		if(endproduct){
			endproductStatus = "Als Endprodukt gekennzeichnet";
		}
		
		return "<b>Baugruppe</b><br><br>Name:" + name + "<br> Komponenten:<br>" + component1 
				+ component2 + component3 + endproductStatus + "<br><br>";

	}
	
}
