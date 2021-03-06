package hu.unideb.inf.rft.nvkshop.events;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import hu.unideb.inf.rft.nvkshop.eventhandling.EmailSenderEventHandler;
import hu.unideb.inf.rft.nvkshop.eventhandling.EmailSendingEvent;
import hu.unideb.inf.rft.nvkshop.service.Settings;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailSenderEventHandlerImpl implements EmailSenderEventHandler {
	private static final int BUFFER_SIZE = 2048;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Configuration freemarker;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Settings settings;

	@Override
	@EventListener(classes = EmailSendingEvent.class)
	public void sendMail(EmailSendingEvent event) {
		String locale = settings.getDefaultLanguage();

		Template template = null;
		try (StringWriter writer = new StringWriter(BUFFER_SIZE)) {
			template = freemarker.getTemplate(event.getEventType().getTemplateName(), Locale.forLanguageTag(locale));

			template.process(event.getPayload(), writer);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(settings.getFromAddress());
			helper.addTo(event.getTo());
			// FIXME: proper subject handling
			helper.setSubject("NVKShop account");
			helper.setText(writer.toString(), true);

			mailSender.send(mimeMessage);
			log.info("Email send to user {} with the template {}", event.getTo(),
					event.getEventType().getTemplateName());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			log.error("The template {} is invalid", template.getName());
		}
	}

}
