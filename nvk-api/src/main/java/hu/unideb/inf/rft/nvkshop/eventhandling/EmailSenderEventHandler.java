package hu.unideb.inf.rft.nvkshop.eventhandling;

public interface EmailSenderEventHandler {

	void sendMail(EmailSendingEvent event);
	
}
