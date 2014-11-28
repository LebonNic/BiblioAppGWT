package fr.isima.biblioapp.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.isima.biblioapp.shared.persistence.Auteur;
import fr.isima.biblioapp.shared.persistence.Livre;

@RemoteServiceRelativePath("biblioAppService")
public interface BiblioAppService extends RemoteService{
	Auteur addAuteur(String nom, String prenom, String adresse);
	Auteur getAuteur(Long numero_a);
	ArrayList<Auteur> getAllAuteurs();
	ArrayList<Auteur> searchAuteurs(String nom);
	Boolean updateAuteur(Long numero_a, String nom, String prenom, String adresse);
	Boolean deleteAuteur(Long numero_a);
	
	Livre addLivre(String titre, double prix, String resume, Long numero_a);
	Livre getLivre(Long numero_l);
	ArrayList<Livre> getAllLivres();
	ArrayList<Livre> searchLivres(String titre);
	Boolean updateLivre(Long numero_l, String titre, double prix, String resume, Long numero_a);
	Boolean deleteLivre(Long numero_l);

}
