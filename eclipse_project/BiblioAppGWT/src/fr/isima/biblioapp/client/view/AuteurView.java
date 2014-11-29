package fr.isima.biblioapp.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.presenter.AuteurPresenter;

public class AuteurView extends Composite implements AuteurPresenter.Display{
	
	private final Button addButton;
	private final Button deleteButton;
	private FlexTable auteursTab;
	
	public AuteurView(){
		 HorizontalPanel hPanel = new HorizontalPanel();
		 VerticalPanel vPanel = new VerticalPanel();
		 
		 //Centrage du layout vertical
		 vPanel.setWidth("100%");
		 vPanel.setHeight("100%");
		 vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		 vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		 
		 addButton = new Button("Ajouter");
		 hPanel.add(addButton);
		 
		 deleteButton = new Button("Supprimer");
		 hPanel.add(deleteButton);
		 
		 auteursTab = new FlexTable();
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
	    
	    for (int i = 0; i < data.size(); ++i) {
	    	auteursTab.setWidget(i, 0, new CheckBox());
	    	auteursTab.setText(i, 1, data.get(i));
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
	        selectedRow = cell.getRowIndex();
	      }
	    }
	    
	    return selectedRow;
	}

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();
	    
	    for (int i = 0; i < auteursTab.getRowCount(); ++i) {
	      CheckBox checkBox = (CheckBox)auteursTab.getWidget(i, 0);
	      if (checkBox.getValue()) {
	        selectedRows.add(i);
	      }
	    }
	    
	    return selectedRows;
	}
	
	public Widget asWidget(){
		return this;
	}

}
