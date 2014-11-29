package fr.isima.biblioapp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.event.AuteurAddedEvent;
import fr.isima.biblioapp.client.event.AuteurUpdatedEvent;
import fr.isima.biblioapp.client.event.EditAuteurCancelledEvent;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.shared.persistence.Auteur;

public class EditAuteurPresenter implements Presenter {
	
	public interface Display {
	    HasClickHandlers getSaveButton();
	    HasClickHandlers getCancelButton();
	    HasValue<String> getFirstName();
	    HasValue<String> getLastName();
	    HasValue<String> getAddress();
	    Widget asWidget();
	  }
	
	Auteur auteur;
	private final BiblioAppServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private final Boolean isPresenterCreatedForUpdate;
	
	public EditAuteurPresenter(BiblioAppServiceAsync rpcService, HandlerManager eventBus, Display view){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.auteur = new Auteur();
		this.isPresenterCreatedForUpdate = false;
	}
	
	public EditAuteurPresenter(BiblioAppServiceAsync rpcService, HandlerManager eventBus, Display view, Long numero_a){
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.isPresenterCreatedForUpdate = true;
		this.rpcService.getAuteur(numero_a, new AsyncCallback<Auteur>() {
			
			@Override
			public void onSuccess(Auteur result) {
				EditAuteurPresenter.this.auteur = result;
				EditAuteurPresenter.this.display.getLastName().setValue(auteur.getNom());
				EditAuteurPresenter.this.display.getFirstName().setValue(auteur.getPrenom());
				EditAuteurPresenter.this.display.getAddress().setValue(auteur.getAdresse());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving auteur");
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
	    container.add(display.asWidget());
	}

	private void bind() {
		display.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doSave();
			}
		});
		
		display.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new EditAuteurCancelledEvent());
				
			}
		});
		
	}
	
	private void doSave(){
		this.auteur.setNom(this.display.getLastName().getValue());
		this.auteur.setPrenom(this.display.getFirstName().getValue());
		this.auteur.setAdresse(this.display.getAddress().getValue());
		
		if(this.isPresenterCreatedForUpdate){
			this.rpcService.updateAuteur(	auteur.getNumero_a(), 
											auteur.getNom(), 
											auteur.getPrenom(), 
											auteur.getAdresse(), 
											new AsyncCallback<Boolean>() {

												@Override
												public void onFailure(Throwable caught) {
													 Window.alert("Error updating auteur");
													
												}

												@Override
												public void onSuccess( Boolean result) {
													eventBus.fireEvent(new AuteurUpdatedEvent());
													
												}
			});
		}
		else{
			this.rpcService.addAuteur(	auteur.getNom(), 
										auteur.getPrenom(), 
										auteur.getAdresse(), 
										new AsyncCallback<Auteur>() {

											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("Error adding auteur");
												
											}

											@Override
											public void onSuccess(Auteur result) {
												eventBus.fireEvent(new AuteurAddedEvent());
												
											}
			});
			
		}
	}

}
