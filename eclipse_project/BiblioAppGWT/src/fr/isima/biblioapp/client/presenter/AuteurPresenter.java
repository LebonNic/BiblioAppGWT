package fr.isima.biblioapp.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import fr.isima.biblioapp.client.event.AddAuteurEvent;
import fr.isima.biblioapp.client.event.UpdateAuteurEvent;
import fr.isima.biblioapp.client.service.BiblioAppServiceAsync;
import fr.isima.biblioapp.shared.persistence.Auteur;

public class AuteurPresenter implements Presenter {
	
	public interface Display {
	    HasClickHandlers getAddButton();
	    HasClickHandlers getDeleteButton();
	    HasClickHandlers getSearchButton();
	    HasKeyUpHandlers getSearchBar();
	    HasClickHandlers getList();
	    void setData(List<String> data);
	    int getClickedRow(ClickEvent event);
	    List<Integer> getSelectedRows();
	    String getSearchedValue();
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
	
	private void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddAuteurEvent());
			}
		});
		
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				deleteSelectedAuteurs();
			}
		});
		
		display.getList().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);
		        
		        if (selectedRow >= 0) {
		        	Long numero_a = listAuteurs.get(selectedRow).getNumero_a();
		        	eventBus.fireEvent(new UpdateAuteurEvent(numero_a));
		        }
				
			}
		});
		
		display.getSearchButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				searchAuteurs();
			}
		});
		
		display.getSearchBar().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				searchAuteurs();
				
			}
		});
		
	}

	private void fetchAuteursList() {
		rpcService.getAllAuteurs(new AsyncCallback<ArrayList<Auteur>>() {
			
			@Override
			public void onSuccess(ArrayList<Auteur> result) {
				listAuteurs = result;
				List<String> data = AuteurPresenter.formatData(listAuteurs);
				display.setData(data);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching auteurs list");
			}
		});
		
	}
	
	private void deleteSelectedAuteurs(){
		List<Integer> selectedRows = display.getSelectedRows();
	    ArrayList<Long> numeros_a = new ArrayList<Long>();
	    
	    for(Integer i : selectedRows){
	    	numeros_a.add(listAuteurs.get(i).getNumero_a());
	    }
	    
	    rpcService.deleteAuteurs(numeros_a, new AsyncCallback<ArrayList<Auteur>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error deleting selected auteurs");
			}

			@Override
			public void onSuccess(ArrayList<Auteur> result) {
				listAuteurs = result;
				List<String> data = AuteurPresenter.formatData(listAuteurs);
				display.setData(data);
			}
		});
		
	}
	
	private void searchAuteurs(){
		String nom = display.getSearchedValue();
		rpcService.searchAuteurs(nom, new AsyncCallback<ArrayList<Auteur>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error searching auteurs");
				
			}

			@Override
			public void onSuccess(ArrayList<Auteur> result) {
				listAuteurs = result;
				List<String> data = AuteurPresenter.formatData(listAuteurs);
				display.setData(data);
			}
		});
		
	}
	
	private static List<String> formatData(List<Auteur> list){
		List<String> data = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		for(Auteur a : list){
			sb.setLength(0);
			sb.append(a.getNom());
			sb.append("; ");
			sb.append(a.getPrenom());
			sb.append("; ");
			sb.append(a.getAdresse());
			data.add(sb.toString());
		}
		
		return data;
	}

}
