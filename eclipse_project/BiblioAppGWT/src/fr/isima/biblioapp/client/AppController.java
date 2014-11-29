package fr.isima.biblioapp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import fr.isima.biblioapp.client.presenter.AuteurPresenter;
import fr.isima.biblioapp.client.presenter.Presenter;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.client.view.AuteurView;

public class AppController implements Presenter, ValueChangeHandler<String>{
	private static final String listAuteurToken = "listAuteur";
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

}
