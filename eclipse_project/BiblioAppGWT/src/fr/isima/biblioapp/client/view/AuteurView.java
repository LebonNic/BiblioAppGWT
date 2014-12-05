package fr.isima.biblioapp.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.presenter.AuteurPresenter;

public class AuteurView extends Composite implements AuteurPresenter.Display{
	
	private final static int SPACING = 5;
	private TextBox searchBar;
	private final Button searchButton;
	private final Button addButton;
	private final Button deleteButton;
	private FlexTable auteursTab;
	
	public AuteurView(){
		 HorizontalPanel hPanel = new HorizontalPanel();
		 HorizontalPanel searchPanel = new HorizontalPanel();
		 VerticalPanel vPanel = new VerticalPanel();
		 
		 hPanel.setSpacing(AuteurView.SPACING);
		 searchPanel.setSpacing(AuteurView.SPACING);
		 vPanel.setSpacing(AuteurView.SPACING);
		 
		 //Centrage du layout vertical
		 vPanel.setWidth("100%");
		 vPanel.setHeight("100%");
		 vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		 vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		 vPanel.add(new HTML("<h1>Gestion des auteurs</h1>"));
		 addButton = new Button("Ajouter");
		 hPanel.add(addButton);
		 
		 deleteButton = new Button("Supprimer");
		 hPanel.add(deleteButton);
		 
		 searchButton = new Button("Rechercher");
		 searchBar = new TextBox();
		 searchPanel.add(searchBar);
		 searchPanel.add(searchButton);
		 
		 auteursTab = new FlexTable();
		 auteursTab.setWidth("32em");
		 auteursTab.setCellSpacing(0);
		 auteursTab.setCellSpacing(5);
		 auteursTab.setCellPadding(3);
		 vPanel.add(searchPanel);
		 vPanel.add(auteursTab);
		 vPanel.add(hPanel);
		 
		 initWidget(vPanel);
	}

	@Override
	public HasClickHandlers getAddButton() {
		return addButton;
	}

	@Override
	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	@Override
	public HasClickHandlers getList() {
		return auteursTab;
	}

	@Override
	public void setData(List<String> data) {
		auteursTab.removeAllRows();
		auteursTab.setWidget(0, 1, new HTML("<b>Nom</b>"));
		auteursTab.setWidget(0, 2, new HTML("<b>Prénom</b>"));
		auteursTab.setWidget(0, 3, new HTML("<b>Adresse</b>"));
	    for (int i = 0; i < data.size(); ++i) {
	    	String [] parts = data.get(i).split(";");
	    	String nom = parts[0];
	    	String prenom = parts[1];
	    	String adresse = parts[2];
	    	auteursTab.getRowFormatter().addStyleName(i + 1, "cw-auteursTabRowsStyle");
	    	auteursTab.setWidget(i + 1, 0, new CheckBox());
	    	auteursTab.setText(i + 1, 1, nom);
	    	auteursTab.setText(i + 1, 2, prenom);
	    	auteursTab.setText(i + 1, 3, adresse);
	    }
	}

	@Override
	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
	    HTMLTable.Cell cell = auteursTab.getCellForEvent(event);
	    
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
	    
	    for (int i = 1; i < auteursTab.getRowCount(); ++i) {
	      CheckBox checkBox = (CheckBox)auteursTab.getWidget(i, 0);
	      if (checkBox.getValue()) {
	        selectedRows.add(i - 1);
	      }
	    }
	    
	    return selectedRows;
	}
	
	public Widget asWidget(){
		return this;
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	@Override
	public String getSearchedValue() {
		return searchBar.getText();
	}

	@Override
	public HasKeyUpHandlers getSearchBar() {
		return searchBar;
	}

}
