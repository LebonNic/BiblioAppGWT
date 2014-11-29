package fr.isima.biblioapp.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.event.AddAuteurEvent;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.shared.persistence.Auteur;

public class AuteurPresenter implements Presenter {
	
	public interface Display {
	    HasClickHandlers getAddButton();
	    HasClickHandlers getDeleteButton();
	    HasClickHandlers getList();
	    void setData(List<String> data);
	    int getClickedRow(ClickEvent event);
	    List<Integer> getSelectedRows();
	    Widget asWidget();
	  }
	
	private List<Auteur> listAuteurs;
	private final BiblioAppServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	
	public AuteurPresenter(BiblioAppServiceAsync rpcService, HandlerManager eventBus, Display view){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	@Override
	public void go(HasWidgets container) {
		bind();
	    container.clear();
	    container.add(display.asWidget());
	    fetchAuteursList();
	}


	private void fetchAuteursList() {
		rpcService.getAllAuteurs(new AsyncCallback<ArrayList<Auteur>>() {
			
			@Override
			public void onSuccess(ArrayList<Auteur> result) {
				listAuteurs = result;
				List<String> data = new ArrayList<>();
				
				for(Auteur a : listAuteurs){
					data.add(a.getNom());
				}
				
				display.setData(data);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching auteurs list");
				
			}
		});
		
	}

	private void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddAuteurEvent());
			}
		});
		
	}

}
