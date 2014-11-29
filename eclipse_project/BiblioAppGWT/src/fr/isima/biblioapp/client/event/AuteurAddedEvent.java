package fr.isima.biblioapp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuteurAddedEvent extends GwtEvent<AuteurAddedEventHandler>{

	public static Type<AuteurAddedEventHandler> TYPE = new Type<>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuteurAddedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuteurAddedEventHandler handler) {
		handler.onAuteurAdded(this);
	}

}
