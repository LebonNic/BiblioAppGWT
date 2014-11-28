package fr.isima.biblioapp.shared.persistence;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Auteur implements Serializable{
	
	@Ignore
	private static final long serialVersionUID = 1L;

	@Id
	private Long _numero_a;

	@Index
	private String _nom;
	private String _prenom;
	private String _adresse;
	
	// Obligatoire pour Objectify
	public Auteur(){
	}
	
	public Auteur(String nom, String prenom, String adresse){
		this._nom = nom;
		this._prenom = prenom;
		this._adresse = adresse;
	}
	
	public String getNom() {
		return _nom;
	}

	public void setNom(String nom) {
		this._nom = nom;
	}

	public String getPrenom() {
		return _prenom;
	}

	public void setPrenom(String prenom) {
		this._prenom = prenom;
	}

	public String getAdresse() {
		return _adresse;
	}

	public void setAdresse(String adresse) {
		this._adresse = adresse;
	}

	public Long getNumero_a() {
		return _numero_a;
	}

}
