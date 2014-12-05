package fr.isima.biblioapp.client.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.isima.biblioapp.shared.persistence.Auteur;
import fr.isima.biblioapp.shared.persistence.Livre;

public interface BiblioAppServiceAsync {
	void addAuteur(String nom, String prenom, String adresse, AsyncCallback<Auteur> callback);
	void getAuteur(Long numero_a, AsyncCallback<Auteur> callback);
	void getAllAuteurs(AsyncCallback<ArrayList<Auteur>> callback);
	void searchAuteurs(String nom, AsyncCallback<ArrayList<Auteur>> callback);
	void updateAuteur(Long numero_a, String nom, String prenom, String adresse, AsyncCallback<Boolean> callback);
	void deleteAuteur(Long numero_a, AsyncCallback<Boolean> callback);
	void deleteAuteurs(List<Long> numeros_a, AsyncCallback<ArrayList<Auteur>> callback);
	
	void addLivre(String titre, double prix, String resume, Long numero_a, AsyncCallback<Livre> callback);
	void getLivre(Long numero_l, AsyncCallback<Livre> callback);
	void getLivresFrom(Long numero_a, AsyncCallback<ArrayList<Livre>> callback);
	void getAllLivres(AsyncCallback<ArrayList<Livre>> callback);
	void searchLivres(String titre, AsyncCallback<ArrayList<Livre>> callback);
	void updateLivre(Long numero_l, String titre, double prix, String resume, Long numero_a, AsyncCallback<Boolean> callback);
	void deleteLivre(Long numero_l, AsyncCallback<Boolean> callback);
	void deleteLivres(List<Long> numeros_l, AsyncCallback<ArrayList<Livre>> callback);
}
