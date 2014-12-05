package fr.isima.biblioapp.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import fr.isima.biblioapp.client.service.BiblioAppService;
import fr.isima.biblioapp.shared.persistence.Livre;
import fr.isima.biblioapp.shared.persistence.Auteur;

@SuppressWarnings("serial")
public class BiblioAppServiceImpl extends RemoteServiceServlet implements
		BiblioAppService {
	
	static{
		ObjectifyService.register(Auteur.class);
		ObjectifyService.register(Livre.class);
	}
	
	public BiblioAppServiceImpl(){
		/*List<Auteur> auteurs = new ArrayList<>();
		auteurs.add(new Auteur("Denisot", "Michel", "Paris"));
		auteurs.add(new Auteur("Hugo", "Victor", "Paris"));
		auteurs.add(new Auteur("Zola", "Emile", "Paris"));
		
		for(Auteur a : auteurs){
			ofy().save().entity(a).now();
		}*/
		
		/*List<Livre> livres = new ArrayList<>();
		livres.add(new Livre("Les Misérables", 25.2, "caca", 5066549580791808L));
		livres.add(new Livre("L'Homme qui rit", 9.99, "caca", 5066549580791808L));
		livres.add(new Livre("test", 100, "caca", 5066549580791808L));
		
		for(Livre l : livres){
			ofy().save().entity(l).now();
		}*/
	}
	
	private static ArrayList<Auteur> toAuteurArrayList(List<Auteur> list){
		ArrayList<Auteur> ret = new ArrayList<>();
		for(Auteur a : list){
			ret.add(a);
		}
		return ret;
	}
	
	private static ArrayList<Livre> toLivreArrayList(List<Livre> list){
		ArrayList<Livre> ret = new ArrayList<>();
		for(Livre l : list){
			ret.add(l);
		}
		return ret;
	}

	/**
	 * Ajoute un auteur dans la base de données.
	 * @param nom Nom de l'auteur.
	 * @param prenom Prenom de l'auteur.
	 * @param adresse Adresse de l'auteur.
	 * @return L'auteur nouvellement créé et ajouté à la base.
	 */
	@Override
	public Auteur addAuteur(String nom, String prenom, String adresse) {
		Auteur auteur = new Auteur(nom, prenom, adresse);
		ofy().save().entity(auteur).now();
		return auteur;
	}

	/**
	 * Récupère un auteur stocké dans la base via son identifiant.
	 * @param numero_a Identifiant de l'auteur.
	 * @return L'auteur correspondant à l'identifiant.
	 */
	@Override
	public Auteur getAuteur(Long numero_a) {
		Key<Auteur> cleAuteur = Key.create(Auteur.class, numero_a);
		Auteur auteur = ofy().load().key(cleAuteur).now();
		return auteur;
	}

	/**
	 * Récupère tous les auteurs présents dans la base.
	 * @return Une liste contenant tous les auteurs stockés dans
	 * la base de données.
	 */
	@Override
	public ArrayList<Auteur> getAllAuteurs() {
		List<Auteur> auteurs = ofy().load().type(Auteur.class).list();
		return BiblioAppServiceImpl.toAuteurArrayList(auteurs);
	}

	/**
	 * Permet de rechercher un auteur grace à son nom dans la base
	 * de données.
	 * @param nom Le nom de l'auteur recherché.
	 * @return Une liste d'auteurs dont le début du nom correspond
	 * à la chaine passée en paramètre. 
	 */
	@Override
	public ArrayList<Auteur> searchAuteurs(String nom) {
		List<Auteur> auteursTrouves = ofy().load().type(Auteur.class).filter("_nom >=", nom).filter("_nom <", nom + "\uFFFD").list();
		return BiblioAppServiceImpl.toAuteurArrayList(auteursTrouves);
	}
	
	/**
	 * Met à jour les attributs d'un auteur déjà présent dans la 
	 * base.
	 * @param numero_a Identifiant de l'auteur à mettre à jour.
	 * @param nom Nouveau nom de l'auteur.
	 * @param prenom Nouveau prenom de l'auteur.
	 * @param adresse Nouvelle adresse de l'auteur.
	 */
	@Override
	public Boolean updateAuteur(Long numero_a, String nom, String prenom,
			String adresse) {
		Boolean updated = false;
		Key<Auteur> cleAuteur = Key.create(Auteur.class, numero_a);
		Auteur auteur = ofy().load().key(cleAuteur).now();
		
		if(auteur != null){
			auteur.setNom(nom);
			auteur.setPrenom(prenom);
			auteur.setAdresse(adresse);
			ofy().save().entity(auteur).now();
			updated = true;
		}
		
		return updated;
	}

	/**
	 * Supprime un auteur à partir de son identifiant.
	 * @param numero_a Identifiant de l'auteur à supprimer.
	 */
	@Override
	public Boolean deleteAuteur(Long numero_a) {
		Boolean deleted = false;
		Key<Auteur> cleAuteur = Key.create(Auteur.class, numero_a);
		Auteur auteur = ofy().load().key(cleAuteur).now();
		
		if(auteur != null){
			Long idAuteur = auteur.getNumero_a();
			
			// Suppression des livres associés à l'auteur
			List<Livre> livres = ofy().load().type(Livre.class).filter("_numero_a ==", idAuteur).list();
			for(Livre l : livres){
				ofy().delete().entity(l).now();
			}
			
			// Suppression de l'auteur
			ofy().delete().entity(auteur).now();
			deleted = true;
		}
		
		return deleted;
	}
	
	/**
	 * Supprime une liste d'auteur de la base.
	 * @param numeros_a Liste des identifiants des auteurs à supprimer.
	 * @return La nouvelle liste des auteurs après suppression.
	 */
	@Override
	public ArrayList<Auteur> deleteAuteurs(List<Long> numeros_a) {
		
		for(Long numero_a : numeros_a){
			this.deleteAuteur(numero_a); 
		}
		
		return getAllAuteurs();
	}

	/**
	 * Ajoute un livre dans la base de données.
	 * @param titre Titre du livre.
	 * @param prix Prix du livre.
	 * @param resume Résumé du livre.
	 * @param numero_a Identifiant de l'auteur du livre.
	 * @return Le nouveau livre tout juste créé et persisté dans la
	 * base.
	 */
	@Override
	public Livre addLivre(String titre, double prix, String resume,
			Long numero_a) {
		Livre livre = new Livre(titre, prix, resume, numero_a);
		ofy().save().entity(livre).now();
		return livre;
	}

	/**
	 * Récupère un livre dans la base de données via son identifiant.
	 * @param numero_l Identifiant du livre.
	 * @return Le livre correspondant à l'identifiant.
	 */
	@Override
	public Livre getLivre(Long numero_l) {
		Key<Livre> cleLivre = Key.create(Livre.class, numero_l);
		Livre livre = ofy().load().key(cleLivre).now();
		return livre;
	}
	
	/**
	 * Récupère un liste de livres écrits par l'auteur dont l'identifiant
	 * est passé en paramètre.
	 * @param numero_a Le numéro de l'auteur dont on souhaite lister les livres
	 * @return Une liste de livres écrits par l'auteur demandé.
	 */
	@Override
	public ArrayList<Livre> getLivresFrom(Long numero_a) {
		List<Livre> livres = ofy().load().type(Livre.class).filter("_numero_a ==", numero_a).list();
		return BiblioAppServiceImpl.toLivreArrayList(livres);
	}

	/**
	 * Récupère tous livres présents dans la base de données.
	 * @return Une liste contenant tous les livres de la base.
	 */
	@Override
	public ArrayList<Livre> getAllLivres() {
		List<Livre> livres = ofy().load().type(Livre.class).list();
		return BiblioAppServiceImpl.toLivreArrayList(livres);
	}

	/**
	 * Recherche tous les livres dont le titre commence par la chaine
	 * passée en paramètre.
	 * @param titre Le titre du 
	 * @return Une liste de livres dont le titre commence par la chaine
	 * passée en paramètre.
	 */
	@Override
	public ArrayList<Livre> searchLivres(String titre) {
		List<Livre> livresTrouves = ofy().load().type(Livre.class).filter("_titre >=", titre).filter("_titre <", titre + "\uFFFD").list();
		return BiblioAppServiceImpl.toLivreArrayList(livresTrouves);
	}

	/**
	 * Met à jour les attributs d'un livre spécifié par son identifiant.
	 * @param numero_l Identifiant du livre à mettre à jour.
	 * @param titre Nouveau titre du livre.
	 * @param prix Nouveau prix du livre.
	 * @param resume Nouveau résumé du livre.
	 * @param numero_a Nouvel identifiant de l'auteur du livre.
	 */
	@Override
	public Boolean updateLivre(Long numero_l, String titre, double prix,
			String resume, Long numero_a) {
		Boolean updated = false;
		Key<Livre> cleLivre = Key.create(Livre.class, numero_l);
		Livre livre = ofy().load().key(cleLivre).now();
		
		if(livre != null){
			livre.setTitre(titre);
			livre.setPrix(prix);
			livre.setResume(resume);
			livre.setNumero_a(numero_a);
			ofy().save().entity(livre).now();
			updated = true;
		}
		
		return updated;
	}

	/**
	 * Supprime un livre de la base via son identifiant.
	 * @param numero_l Identifiant du livre à supprimer.
	 */
	@Override
	public Boolean deleteLivre(Long numero_l) {
		Boolean deleted = false;
		Key<Livre> cleLivre = Key.create(Livre.class, numero_l);
		Livre livre = ofy().load().key(cleLivre).now();
		
		if(livre != null){
			ofy().delete().key(cleLivre).now();
		}
		
		return deleted;
	}

	/**
	 * Supprime une liste de livres.
	 * @param numeros_l La liste des identifiants des livres à supprimer.
	 * @return La nouvelle liste de livre, une fois la suppression effectuée.
	 */
	@Override
	public ArrayList<Livre> deleteLivres(List<Long> numeros_l) {
		for(Long numero_l : numeros_l){
			this.deleteLivre(numero_l); 
		}
		
		return getAllLivres();
	}

}
