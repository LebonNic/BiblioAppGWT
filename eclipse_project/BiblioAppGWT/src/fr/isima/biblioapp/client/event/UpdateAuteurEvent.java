package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateAuteurEvent extends GwtEvent<UpdateAuteurEventHandler>{

	public static Type<UpdateAuteurEventHandler> TYPE = new Type<>();
	private Long numero_a;
	
	public Long getNumero_a(){
		return this.numero_a;
	}
	
	public UpdateAuteurEvent(Long numero_a){
		this.numero_a = numero_a;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateAuteurEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateAuteurEventHandler handler) {
		handler.onUpdateAuteur(this);
	}

}
