package hu.unideb.inf.rft.nvkshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.LoggabeBaseServiceImpl;
import hu.unideb.inf.rft.nvkshop.entities.security.IdentifyableUserBaseEntity;
import hu.unideb.inf.rft.nvkshop.repositories.IdentifyableUserBaseEntityDao;
import hu.unideb.inf.rft.nvkshop.service.IdentifyableUserBaseEntityService;

@Service
public class IdentifyableUserBaseEntityServiceImpl extends LoggabeBaseServiceImpl implements IdentifyableUserBaseEntityService {

	@Autowired
	private IdentifyableUserBaseEntityDao identifyableUserBaseEntity;

	@Override
	public IdentifyableUserBaseEntity findByUserName(String userName) {
		return identifyableUserBaseEntity.findByUserName(userName);
	}

	@Override
	public IdentifyableUserBaseEntity findByEmail(String email) {
		return identifyableUserBaseEntity.findByEmail(email);
	}

}
