package hu.unideb.inf.rft.nvkshop.eventhandling;

import java.util.Map;

public class EmailSendingEvent {

	private EventTypeDescriptor eventType;

	private Map<String, Object> payload;

	private String to;

	public EventTypeDescriptor getEventType() {
		return eventType;
	}

	public void setEventType(EventTypeDescriptor eventType) {
		this.eventType = eventType;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public EmailSendingEvent(EventTypeDescriptor eventType, Map<String, Object> payload, String to) {
		super();
		this.eventType = eventType;
		this.payload = payload;
		this.to = to;
	}

}
