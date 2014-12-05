package fr.isima.biblioapp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import fr.isima.biblioapp.client.event.AddAuteurEvent;
import fr.isima.biblioapp.client.event.AddAuteurEventHandler;
import fr.isima.biblioapp.client.event.AddLivreEvent;
import fr.isima.biblioapp.client.event.AddLivreEventHandler;
import fr.isima.biblioapp.client.event.AuteurAddedEvent;
import fr.isima.biblioapp.client.event.AuteurAddedEventHandler;
import fr.isima.biblioapp.client.event.AuteurUpdatedEvent;
import fr.isima.biblioapp.client.event.AuteurUpdatedEventHandler;
import fr.isima.biblioapp.client.event.EditAuteurCancelledEvent;
import fr.isima.biblioapp.client.event.EditAuteurCancelledEventHandler;
import fr.isima.biblioapp.client.event.UpdateAuteurEvent;
import fr.isima.biblioapp.client.event.UpdateAuteurEventHandler;
import fr.isima.biblioapp.client.event.UpdateLivreEvent;
import fr.isima.biblioapp.client.event.UpdateLivreEventHandler;
import fr.isima.biblioapp.client.presenter.AuteurPresenter;
import fr.isima.biblioapp.client.presenter.EditAuteurPresenter;
import fr.isima.biblioapp.client.presenter.EditLivrePresenter;
import fr.isima.biblioapp.client.presenter.Presenter;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.client.view.AuteurView;
import fr.isima.biblioapp.client.view.EditAuteurView;
import fr.isima.biblioapp.client.view.EditLivreView;

public class AppController implements Presenter, ValueChangeHandler<String>{
	private static final String listAuteurToken = "listAuteur";
	private static final String addAuteurToken = "addAuteur";
	private static final String updateAuteurToken = "updateAuteur";
	private static final String addLivreToken = "addLivre";
	private static final String updateLivreToken = "updateLivre";
	private final HandlerManager eventBus;
	private final BiblioAppServiceAsync rpcService; 
	private HasWidgets container;
		
	public AppController(BiblioAppServiceAsync rpcService, HandlerManager eventBus) {
	    this.eventBus = eventBus;
	    this.rpcService = rpcService;
	    bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(
				AddAuteurEvent.TYPE,
				
				new AddAuteurEventHandler() {
					@Override
					public void onAddAuteur(AddAuteurEvent event) {
						doAddNewAuteur();
					}
				});
		
		eventBus.addHandler(
				AuteurAddedEvent.TYPE, 
				
				new AuteurAddedEventHandler() {
			
					@Override
					public void onAuteurAdded(AuteurAddedEvent event) {
						doNewAuteurAdded();
				
					}
				});
		
		eventBus.addHandler(
				EditAuteurCancelledEvent.TYPE, 
				
				new EditAuteurCancelledEventHandler() {
			
					@Override
					public void onEditAuteurCancelled(EditAuteurCancelledEvent event) {
						doEditAuteurCancelled();
					}
				});
		
		eventBus.addHandler(
				AuteurUpdatedEvent.TYPE, 
				
				new AuteurUpdatedEventHandler() {
			
					@Override
					public void onAuteurUpdated(AuteurUpdatedEvent event) {
						doAuteurUpdated();
					}
				});
		
		eventBus.addHandler(UpdateAuteurEvent.TYPE,
				new UpdateAuteurEventHandler() {
					
					@Override
					public void onUpdateAuteur(UpdateAuteurEvent event) {
						doUpdateAuteur(event.getNumero_a());
						
					}
				});
		
		eventBus.addHandler(AddLivreEvent.TYPE, new AddLivreEventHandler() {
			
			@Override
			public void onAddLivre(AddLivreEvent event) {
				doAddLivre(event.getNumero_a());
			}
		});
		
		eventBus.addHandler(UpdateLivreEvent.TYPE, new UpdateLivreEventHandler() {
			
			@Override
			public void onUpdateLivre(UpdateLivreEvent event) {
				doUpdateLivre(event.getNumero_l());
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
	    if (token != null) {
	      Presenter presenter = null;

	      if (token.equals(AppController.listAuteurToken)) {
	        presenter = new AuteurPresenter(rpcService, eventBus, new AuteurView());
	      }
	      
	      else if(token.equals(AppController.addAuteurToken)){
	    	  presenter = new EditAuteurPresenter(rpcService, eventBus, new EditAuteurView());
	      }
	      
	      if (presenter != null) {
	        presenter.go(container);
	      }
	    }
		
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		
	    if ("".equals(History.getToken())) {
	      History.newItem(AppController.listAuteurToken);
	    }
	    else {
	      History.fireCurrentHistoryState();
	    }
	    
	}
	
	private void doAddNewAuteur(){
		History.newItem(AppController.addAuteurToken);
	}
	
	private void doNewAuteurAdded(){
		History.newItem(AppController.listAuteurToken);
	}
	
	private void doUpdateAuteur(Long numero_a){
		History.newItem(AppController.updateAuteurToken, false);
		Presenter presenter = new EditAuteurPresenter(rpcService, eventBus, new EditAuteurView(), numero_a);
		presenter.go(container);
	}
	
	private void doAuteurUpdated(){
		History.newItem(AppController.listAuteurToken);
	}
	
	private void doEditAuteurCancelled(){
		History.newItem(AppController.listAuteurToken);
	}
	
	private void doAddLivre(Long numero_a){
		History.newItem(AppController.addLivreToken, false);
		 Presenter presenter = new EditLivrePresenter(numero_a, rpcService, eventBus, new EditLivreView());
		 presenter.go(container);
	}
	
	private void doUpdateLivre(Long numero_l){
		History.newItem(AppController.updateLivreToken, false);
		Presenter presenter = new EditLivrePresenter(rpcService, eventBus, new EditLivreView(), numero_l);
		presenter.go(container);
	}

}
