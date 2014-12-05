package fr.isima.biblioapp.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.event.AddLivreEvent;
import fr.isima.biblioapp.client.event.AuteurAddedEvent;
import fr.isima.biblioapp.client.event.AuteurUpdatedEvent;
import fr.isima.biblioapp.client.event.EditAuteurCancelledEvent;
import fr.isima.biblioapp.client.event.UpdateLivreEvent;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.shared.persistence.Auteur;
import fr.isima.biblioapp.shared.persistence.Livre;

public class EditAuteurPresenter implements Presenter {
	
	public interface Display {
	    HasClickHandlers getSaveButton();
	    HasClickHandlers getCancelButton();
	    HasValue<String> getFirstName();
	    HasValue<String> getLastName();
	    HasValue<String> getAddress();
	    HasClickHandlers getList();
	    void setData(List<String> data);
	    int getClickedRow(ClickEvent event);
	    List<Integer> getSelectedRows();
	    HasClickHandlers getAddLivreButton();
	    HasClickHandlers getDeleteLivreButton();
	    Widget asWidget();
	  }
	
	private Auteur auteur;
	private List<Livre> listLivres;
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
				Window.alert("Error retrieving author");
			}
		});
		refreshAuthorBooks(numero_a);
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
		
		display.getDeleteLivreButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doDeleteAuthorBooks();
			}
		});
		
		display.getAddLivreButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddLivreEvent(EditAuteurPresenter.this.auteur.getNumero_a()));
				
			}
		});
		
		display.getList().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);
		        
		        if (selectedRow >= 0) {
		        	Long numero_l = listLivres.get(selectedRow).getNumero_l();
		        	eventBus.fireEvent(new UpdateLivreEvent(numero_l));
		        }
				
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
													 Window.alert("Error updating author");
													
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
												Window.alert("Error adding author");
												
											}

											@Override
											public void onSuccess(Auteur result) {
												eventBus.fireEvent(new AuteurAddedEvent());
												
											}
			});
			
		}
	}
	
	private void doDeleteAuthorBooks(){
		List<Integer> selectedRows = display.getSelectedRows();
	    ArrayList<Long> numeros_l = new ArrayList<Long>();
	    
	    for(Integer i : selectedRows){
	    	numeros_l.add(listLivres.get(i).getNumero_l());
	    }
	    
	    this.rpcService.deleteLivres(numeros_l, new AsyncCallback<ArrayList<Livre>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error deleting selected books");
				
			}

			@Override
			public void onSuccess(ArrayList<Livre> result) {
			}
		});
	    
	    refreshAuthorBooks(auteur.getNumero_a());
	}
	
	private static List<String> formatData(List<Livre> list){
		List<String> data = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		for(Livre l : list){
			sb.setLength(0);
			sb.append(l.getTitre());
			sb.append("; ");
			sb.append(l.getPrix());
			data.add(sb.toString());
		}
		
		return data;
	}
	
	private void refreshAuthorBooks(Long numero_a){
		this.rpcService.getLivresFrom(numero_a, new AsyncCallback<ArrayList<Livre>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving author's books");
			}

			@Override
			public void onSuccess(ArrayList<Livre> result) {
				EditAuteurPresenter.this.listLivres = result;
				List<String> data = EditAuteurPresenter.formatData(EditAuteurPresenter.this.listLivres);
				display.setData(data);
			}
		});
	}

}
