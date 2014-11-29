package fr.isima.biblioapp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import fr.isima.biblioapp.client.event.AddAuteurEvent;
import fr.isima.biblioapp.client.event.AddAuteurEventHandler;
import fr.isima.biblioapp.client.presenter.AuteurPresenter;
import fr.isima.biblioapp.client.presenter.EditAuteurPresenter;
import fr.isima.biblioapp.client.presenter.Presenter;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.client.view.AuteurView;
import fr.isima.biblioapp.client.view.EditAuteurView;

public class AppController implements Presenter, ValueChangeHandler<String>{
	private static final String listAuteurToken = "listAuteur";
	private static final String addAuteurToken = "addAuteur";
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
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
		System.out.println("Value of token = " + token);
		System.out.println("Value of AppController.listAuteurToken = " + AppController.listAuteurToken);
		
	    if (token != null) {
	      Presenter presenter = null;

	      if (token.equals(AppController.listAuteurToken)) {
	        presenter = new AuteurPresenter(rpcService, eventBus, new AuteurView());
	      }
	      
	      if(token.equals(AppController.addAuteurToken)){
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
		System.out.println(History.getToken());
		
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

}
