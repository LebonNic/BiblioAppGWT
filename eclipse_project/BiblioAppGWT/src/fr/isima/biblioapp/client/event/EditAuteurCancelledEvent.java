package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditAuteurCancelledEvent extends GwtEvent<EditAuteurCancelledEventHandler>{

	public static Type<EditAuteurCancelledEventHandler> TYPE = new Type<>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditAuteurCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditAuteurCancelledEventHandler handler) {
		handler.onEditAuteurCancelled(this);
	}

}
