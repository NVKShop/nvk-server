package hu.unideb.inf.rft.nvkshop.service;

import hu.unideb.inf.rft.nvkshop.entities.security.IdentifyableUserBaseEntity;

public interface IdentifyableUserBaseEntityService {

	IdentifyableUserBaseEntity findByUserName(String userName);
	
}
