package hu.unideb.inf.rft.nvkshop.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import hu.unideb.inf.rft.nvkshop.LoggabeBaseServiceImpl;
import hu.unideb.inf.rft.nvkshop.entities.security.UserPasswordRecovery;
import hu.unideb.inf.rft.nvkshop.entities.security.UserRegistrationRequest;
import hu.unideb.inf.rft.nvkshop.repositories.UserPasswordRecoveryDao;
import hu.unideb.inf.rft.nvkshop.repositories.UserRegistrationRequestDao;
import hu.unideb.inf.rft.nvkshop.service.DeletedEntityException;
import hu.unideb.inf.rft.nvkshop.service.Settings;
import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;
import hu.unideb.inf.rft.nvkshop.validation.exception.ValidationException;
import hu.unideb.inf.rft.nvkshop.validation.userregistration.UserValidator;

@Service
public class UserRegistrationRequestServiceImpl extends LoggabeBaseServiceImpl implements UserRegistrationRequestService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Configuration freemarker;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Settings settings;

	/** Buffer size */
	private static final int BUFFER_SIZE = 2048;

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(UserRegistrationRequest.class);

	@Autowired
	private UserRegistrationRequestDao userRegistrationRequestDao;

	@Autowired
	private UserValidator validator;

	@Autowired
	private UserPasswordRecoveryDao passwordRecoveryDao;

	@Override
	public UserRegistrationRequest findByActivationCode(String activationCode) {
		return userRegistrationRequestDao.findByActivationCode(activationCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void attemptRegistration(UserRegistrationRequest request) throws ValidationException {
		request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
		request.setActivationCode(UUID.randomUUID().toString());

		validator.validate(request);

		userRegistrationRequestDao.save(request);
	}

	@Override
	@Transactional
	public void sendRegistrationRequest(Long id) {

		UserRegistrationRequest request = userRegistrationRequestDao.findOne(id);
		if (request == null) {
			log.warn("Unable to send registration validation message to user. Id = {}", id);
		}
		try {
			// TODO: setteld be az aktiválási kódot.
			request.setActivationCode("");
			doSendRegistrationRequest(request);
		} catch (IOException | MessagingException | TemplateException e) {
			// throw new MessageException(e);
		}

		log.warn("Tried to send registration activation link but the user was activated or deleted. Id={}, email={}", request.getId(),
				request.getEmail());
	}

	/**
	 * Send a registration request by the related object. Freemarker use the given template to create a html email.
	 * 
	 * @param request the user registration request
	 * @throws IOException
	 * @throws MessagingException
	 * @throws TemplateException
	 */
	private void doSendRegistrationRequest(UserRegistrationRequest request) throws IOException, MessagingException, TemplateException {

		String activationCode = request.getActivationCode();
		String locale = settings.getDefaultLanguage();
		String url = settings.getBaseUrl() + "/registration/activation.html?activationCode=" + activationCode + "&locale=" + locale;

		Map<String, Object> model = new HashMap<>();
		model.put("url", url);
		model.put("userName", request.getUserName());
		String email = request.getEmail();

		try (StringWriter writer = new StringWriter(BUFFER_SIZE)) {
			Template template;
			template = freemarker.getTemplate("user-registration-activation-message.html", Locale.forLanguageTag(locale));
			template.process(model, writer);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(settings.getFromAddress());
			helper.addTo(email);
			helper.setSubject(messageSource.getMessage("mail.subjectRegistrationActivation", null, Locale.forLanguageTag(locale)));
			helper.setText(writer.toString(), true);

			log.info("Sending registration activation link to user email={}", email);
			mailSender.send(mimeMessage);
			// TODO: én itt szoktam átállítani az elküldött/nem elküldött flag-et
		} catch (Exception e) {
			log.warn("Failed to send registration activation message to user. Email = {}", email);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserPasswordRecovery findUserPasswordRecoveryByActivationCode(String activationCode) {

		UserPasswordRecovery passwordRecovery = passwordRecoveryDao.findByActivationCode(activationCode);

		if (passwordRecovery == null || passwordRecovery.getDueDate().after(new Date())) {
			throw new DeletedEntityException();
		}

		return passwordRecovery;
	}

	@Override
	public void remove(UserRegistrationRequest request) {
		userRegistrationRequestDao.delete(request);
	}

	@Override
	public UserRegistrationRequest findByEmail(String email) {
		return userRegistrationRequestDao.findByEmail(email);
	}

	@Override
	public UserRegistrationRequest findByUserName(String userName) {
		return userRegistrationRequestDao.findByUserName(userName);
	}

}
