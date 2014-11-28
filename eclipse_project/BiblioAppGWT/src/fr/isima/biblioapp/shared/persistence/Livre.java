package fr.isima.biblioapp.shared.persistence;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Livre implements Serializable{

	@Ignore
	private static final long serialVersionUID = 1L;

	@Id
	private Long _numero_l;
	
	@Index
	private String _titre;
	private double _prix;
	private String _resume;
	
	@Index
	private Long _numero_a;
	
	public Livre(){
	}
	
	public Livre(String titre, double prix, String resume, Long numero_a){
		this._titre = titre;
		this._prix = prix;
		this._resume = resume;
		this._numero_a = numero_a;
	}
	
	public Long getNumero_l() {
		return _numero_l;
	}
	
	public String getTitre(){
		return this._titre;
	}
	
	public void setTitre(String titre){
		this._titre = titre;
	}
	
	public double getPrix() {
		return _prix;
	}

	public void setPrix(double prix) {
		this._prix = prix;
	}

	public String getResume() {
		return _resume;
	}

	public void setResume(String resume) {
		this._resume = resume;
	}
	
	public Long getNumero_a() {
		return _numero_a;
	}
	
	public void setNumero_a(Long numero_a){
		this._numero_a = numero_a;
	}

}
