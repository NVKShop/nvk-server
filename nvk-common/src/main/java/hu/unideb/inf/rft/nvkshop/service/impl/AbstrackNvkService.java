package hu.unideb.inf.rft.nvkshop.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.util.Assert;

public class AbstrackNvkService {

	protected Date dateOf(ZonedDateTime time) {
		Assert.notNull(time);
		return Date.from(time.toInstant());
	}

	protected ZonedDateTime now() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault());
	}

}
