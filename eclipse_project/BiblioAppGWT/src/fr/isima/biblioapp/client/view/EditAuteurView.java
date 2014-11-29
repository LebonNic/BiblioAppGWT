package fr.isima.biblioapp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.presenter.EditAuteurPresenter;;

public class EditAuteurView extends Composite implements EditAuteurPresenter.Display{

	private final TextBox firstName;
	private final TextBox lastName;
	private final TextBox address;
	private final Button saveButton;
	private final Button cancelButton;
	
	private static HorizontalPanel addLabel(String title, Widget widget){
		HorizontalPanel hpanel = new HorizontalPanel();
		Label label = new Label(title);
		hpanel.add(label);
		hpanel.add(widget);
		return hpanel;
	}
	  
	public EditAuteurView(){
		HorizontalPanel hPanel = new HorizontalPanel();
		VerticalPanel vPanel = new VerticalPanel();
		 
		//Centrage du layout vertical (vPanel)
		vPanel.setWidth("100%");
		vPanel.setHeight("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		firstName = new TextBox();
		lastName = new TextBox();
		address = new TextBox();
		saveButton = new Button("Sauvegarder");
		cancelButton = new Button("Annuler");
		
		// Labélisation des Textbox
		vPanel.add(EditAuteurView.addLabel("Nom", lastName));
		vPanel.add(EditAuteurView.addLabel("Prenom", firstName));
		vPanel.add(EditAuteurView.addLabel("Adresse", address));
		
		hPanel.add(saveButton);
		hPanel.add(cancelButton);
		vPanel.add(hPanel);
		
		initWidget(vPanel);
	}
	@Override
	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	@Override
	public HasValue<String> getFirstName() {
		return firstName;
	}

	@Override
	public HasValue<String> getLastName() {
		return lastName;
	}

	@Override
	public HasValue<String> getAddress() {
		return address;
	}
	
	public Widget asWidget(){
		return this;
	}

}
