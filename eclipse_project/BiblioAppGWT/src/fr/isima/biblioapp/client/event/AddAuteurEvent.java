package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddAuteurEvent extends GwtEvent<AddAuteurEventHandler>{
	
	public static Type<AddAuteurEventHandler> TYPE = new Type<>();

	@Override
	public Type<AddAuteurEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddAuteurEventHandler handler) {
		handler.onAddAuteur(this);
	}

}
