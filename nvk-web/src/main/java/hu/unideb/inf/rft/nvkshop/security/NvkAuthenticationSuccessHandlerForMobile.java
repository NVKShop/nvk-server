package hu.unideb.inf.rft.nvkshop.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.Assert;
import org.springframework.web.util.CookieGenerator;

public class NvkAuthenticationSuccessHandlerForMobile extends SavedRequestAwareAuthenticationSuccessHandler {

	/** Cookie name of the last user */
	public static final String COOKIE_LAST_USERNAME = "LAST_USERNAME";

	/** SLF4J Logger */
	private final Logger log = LoggerFactory.getLogger(NvkAuthenticationSuccessHandlerForMobile.class);

	/**
	 * NVK authentication success handler for mobile.
	 */
	public NvkAuthenticationSuccessHandlerForMobile() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		Assert.notNull(userDetails);

		setupCookies(response, userDetails.getUsername());
		super.onAuthenticationSuccess(request, response, authentication);
	}

	/**
	 * Setup cookies.
	 * 
	 * @param response the HTTP response
	 * @param username the user name
	 */
	private void setupCookies(HttpServletResponse response, String username) {

		log.debug("Setting cookie LAST_USERNAME: username={}", username);

		CookieGenerator lastUsernameCookie = new CookieGenerator();
		lastUsernameCookie.setCookieName(COOKIE_LAST_USERNAME);
		lastUsernameCookie.setCookieMaxAge(Integer.MAX_VALUE);
		lastUsernameCookie.addCookie(response, username);
	}
}
