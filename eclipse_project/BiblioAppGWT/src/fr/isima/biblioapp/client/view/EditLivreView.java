package fr.isima.biblioapp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.presenter.EditLivrePresenter;

public class EditLivreView extends Composite implements EditLivrePresenter.Display{
	private final static int SPACING = 5;
	private final TextBox title;
	private final TextBox price;
	private final TextArea summary;
	private final Button saveButton;
	private final Button cancelButton;
	
	public EditLivreView() {
		title = new TextBox();
		price = new TextBox();
		summary = new TextArea();
		EditLivreView.addLabel("Titre : ", title);
		EditLivreView.addLabel("Prix : ", price);
		EditLivreView.addLabel("Résumé", summary);
		
		saveButton = new Button("Sauvegarder");
		cancelButton = new Button("Annuler");
		
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(EditLivreView.SPACING);
		hPanel.add(saveButton);
		hPanel.add(cancelButton);
		
		VerticalPanel layout = new VerticalPanel();
		layout.setWidth("100%");
		layout.setHeight("100%");
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		layout.setSpacing(EditLivreView.SPACING);
		layout.add(new HTML("<h1>Edition d'un livre</h1>"));
		
		layout.add(title);
		layout.add(price);
		layout.add(summary);
		layout.add(hPanel);
		
		initWidget(layout);
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
	public HasValue<String> getTitleTextBox() {
		return title;
	}

	@Override
	public HasValue<String> getPriceTextBox() {
		return price;
	}

	@Override
	public HasValue<String> getSummaryTextArea() {
		return summary;
	}
	
	@Override
	public Widget asWidget(){
		return this;
	}
	
	private static HorizontalPanel addLabel(String title, Widget widget){
		HorizontalPanel hpanel = new HorizontalPanel();
		Label label = new Label(title);
		hpanel.setSpacing(EditLivreView.SPACING);
		hpanel.add(label);
		hpanel.add(widget);
		return hpanel;
	}
	
}
