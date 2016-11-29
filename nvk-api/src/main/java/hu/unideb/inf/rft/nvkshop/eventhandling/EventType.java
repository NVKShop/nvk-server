package hu.unideb.inf.rft.nvkshop.eventhandling;

public enum EventType implements EventTypeDescriptor {
	REGISTRATION_ACTIVATION("user-registration-activation-message.html", "mail.subjectRegistrationActivation");

	private String templateName;

	private String messageSource;

	@Override
	public String getTemplateName() {
		return templateName;
	}

	@Override
	public String getMessageSource() {
		return messageSource;
	}

	private EventType(String templateName, String messageSource) {
		this.templateName = templateName;
		this.messageSource = messageSource;
	}

}
