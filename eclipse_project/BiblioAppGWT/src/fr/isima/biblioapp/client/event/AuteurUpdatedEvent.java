package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuteurUpdatedEvent extends GwtEvent<AuteurUpdatedEventHandler>{

	public static Type<AuteurUpdatedEventHandler> TYPE = new Type<>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuteurUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuteurUpdatedEventHandler handler) {
		handler.onAuteurUpdated(this);
	}

}
