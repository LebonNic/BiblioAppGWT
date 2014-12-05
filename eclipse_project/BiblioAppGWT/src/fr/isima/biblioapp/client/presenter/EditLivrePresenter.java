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

import fr.isima.biblioapp.client.event.UpdateAuteurEvent;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.shared.persistence.Livre;

public class EditLivrePresenter implements Presenter {
	
	public interface Display{
		HasClickHandlers getSaveButton();
	    HasClickHandlers getCancelButton();
	    HasValue<String> getTitleTextBox();
	    HasValue<String> getPriceTextBox();
	    HasValue<String> getSummaryTextArea();
	    Widget asWidget();
	}
	
	private final BiblioAppServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;
	private final Boolean isPresenterCreatedForUpdate;
	private Livre livre;
	
	public EditLivrePresenter(Long numero_a, BiblioAppServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		isPresenterCreatedForUpdate = false;
		livre = new Livre("", 0.0, "", numero_a);
	}
	
	public EditLivrePresenter(BiblioAppServiceAsync rpcService, HandlerManager eventBus, Display view, Long numero_l) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		isPresenterCreatedForUpdate = true;
		this.rpcService.getLivre(numero_l, new AsyncCallback<Livre>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving book");
				
			}

			@Override
			public void onSuccess(Livre result) {
				EditLivrePresenter.this.livre = result;
				EditLivrePresenter.this.display.getTitleTextBox().setValue(result.getTitre());
				EditLivrePresenter.this.display.getPriceTextBox().setValue(String.valueOf(result.getPrix()));
				EditLivrePresenter.this.display.getSummaryTextArea().setValue(result.getResume());
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(this.display.asWidget());
	}
	
	private void bind(){
		display.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doSave();
				
			}
		});
		
		display.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new UpdateAuteurEvent(EditLivrePresenter.this.livre.getNumero_a()));
				
			}
		});
		
	}
	
	private void doSave(){
		livre.setTitre(display.getTitleTextBox().getValue());
		livre.setPrix(Double.parseDouble(display.getPriceTextBox().getValue()));
		livre.setResume(display.getSummaryTextArea().getValue());
		
		if(this.isPresenterCreatedForUpdate){
			this.rpcService.updateLivre(
					livre.getNumero_l(), 
					livre.getTitre(), 
					livre.getPrix(), 
					livre.getResume(), 
					livre.getNumero_a(), 
					new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error updating book");
					
						}

						@Override
						public void onSuccess(Boolean result) {
							eventBus.fireEvent(new UpdateAuteurEvent(EditLivrePresenter.this.livre.getNumero_a()));
					
						}
					});
		}
		else{
			this.rpcService.addLivre(
					livre.getTitre(), 
					livre.getPrix(), 
					livre.getResume(), 
					livre.getNumero_a(), 
					new AsyncCallback<Livre>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error adding book");
							
						}

						@Override
						public void onSuccess(Livre result) {
							eventBus.fireEvent(new UpdateAuteurEvent(EditLivrePresenter.this.livre.getNumero_a()));
							
						}
			});
			
		}
	}

}
