package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddLivreEvent extends GwtEvent<AddLivreEventHandler>{

	public static Type<AddLivreEventHandler> TYPE = new Type<>();
	
	private Long numero_a;
	
	public AddLivreEvent(Long numero_a){
		this.numero_a = numero_a;
	}
	
	public Long getNumero_a(){
		return numero_a;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddLivreEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddLivreEventHandler handler) {
		handler.onAddLivre(this);
	}

}
