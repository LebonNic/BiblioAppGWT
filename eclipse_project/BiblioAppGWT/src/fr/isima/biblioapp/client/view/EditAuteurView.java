package fr.isima.biblioapp.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
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

	private final static int SPACING = 5;
	private final TextBox firstName;
	private final TextBox lastName;
	private final TextBox address;
	private final Button saveButton;
	private final Button cancelButton;
	private FlexTable livresTab;
	private final Button addLivreButton;
	private final Button deleteLivreButton;
	
	private static HorizontalPanel addLabel(String title, Widget widget){
		HorizontalPanel hpanel = new HorizontalPanel();
		Label label = new Label(title);
		hpanel.setSpacing(EditAuteurView.SPACING);
		hpanel.add(label);
		hpanel.add(widget);
		return hpanel;
	}
	  
	public EditAuteurView(){
		HorizontalPanel hPanel = new HorizontalPanel();
		VerticalPanel vPanel = new VerticalPanel();
		firstName = new TextBox();
		lastName = new TextBox();
		address = new TextBox();
		saveButton = new Button("Sauvegarder");
		cancelButton = new Button("Annuler");
		addLivreButton = new Button("Ajouter");
		deleteLivreButton = new Button("Supprimer");
		livresTab = new FlexTable();
		livresTab.setWidth("32em");
		livresTab.setCellSpacing(0);
		livresTab.setCellSpacing(5);
		livresTab.setCellPadding(3);
		 
		//Centrage du layout vertical (vPanel)
		vPanel.setWidth("100%");
		vPanel.setHeight("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vPanel.setSpacing(EditAuteurView.SPACING);
		vPanel.add(new HTML("<h1>Edition d'un auteur</h1>"));
		
		// Labélisation des Textbox
		vPanel.add(EditAuteurView.addLabel("Nom : ", lastName));
		vPanel.add(EditAuteurView.addLabel("Prenom : ", firstName));
		vPanel.add(EditAuteurView.addLabel("Adresse : ", address));
		
		hPanel.add(saveButton);
		hPanel.add(cancelButton);
		hPanel.setSpacing(EditAuteurView.SPACING);
		vPanel.add(hPanel);
		vPanel.add(new HTML("<h2>Livres écris par l'auteur</h2>"));
		vPanel.add(livresTab);
		HorizontalPanel tmpHPanel = new HorizontalPanel();
		tmpHPanel.add(addLivreButton);
		tmpHPanel.add(deleteLivreButton);
		vPanel.add(tmpHPanel);
		
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

	@Override
	public HasClickHandlers getList() {
		return livresTab;
	}

	@Override
	public void setData(List<String> data) {
		livresTab.removeAllRows();
		livresTab.setWidget(0, 1, new HTML("<b>Titre</b>"));
		livresTab.setWidget(0, 2, new HTML("<b>Prix</b>"));
	    for (int i = 0; i < data.size(); ++i) {
	    	String [] parts = data.get(i).split(";");
	    	String titre = parts[0];
	    	String prix = parts[1];
	    	livresTab.getRowFormatter().addStyleName(i + 1, "cw-livresTabRowsStyle");
	    	livresTab.setWidget(i + 1, 0, new CheckBox());
	    	livresTab.setText(i + 1, 1, titre);
	    	livresTab.setText(i + 1, 2, prix);
	    }
	}

	@Override
	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
	    HTMLTable.Cell cell = livresTab.getCellForEvent(event);
	    
	    if (cell != null) {
	      // Suppress clicks if the user is actually selecting the 
	      //  check box
	      //
	      if (cell.getCellIndex() > 0) {
	        selectedRow = cell.getRowIndex() - 1;
	      }
	    }
	    
	    return selectedRow;
	}

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();
	    
	    for (int i = 1; i < livresTab.getRowCount(); ++i) {
	      CheckBox checkBox = (CheckBox)livresTab.getWidget(i, 0);
	      if (checkBox.getValue()) {
	        selectedRows.add(i - 1);
	      }
	    }
	    
	    return selectedRows;
	}

	@Override
	public HasClickHandlers getAddLivreButton() {
		return addLivreButton;
	}

	@Override
	public HasClickHandlers getDeleteLivreButton() {
		return deleteLivreButton;
	}

}
