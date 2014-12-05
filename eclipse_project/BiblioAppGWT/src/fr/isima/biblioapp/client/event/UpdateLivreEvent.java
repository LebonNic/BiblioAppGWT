package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateLivreEvent extends GwtEvent<UpdateLivreEventHandler>{

	public static Type<UpdateLivreEventHandler> TYPE = new Type<>();
	
	private Long numero_l;
	
	public UpdateLivreEvent(Long numero_l){
		this.numero_l = numero_l;
	}
	
	public Long getNumero_l(){
		return this.numero_l;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateLivreEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateLivreEventHandler handler) {
		handler.onUpdateLivre(this);
	}

}
