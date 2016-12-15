package hu.unideb.inf.rft.nvkshop.service.impl.util;

import org.springframework.scheduling.quartz.QuartzJobBean;

import hu.unideb.inf.rft.nvkshop.jobs.DiscountActivatorQuartzJob;
import hu.unideb.inf.rft.nvkshop.jobs.DiscountDeactivatorQuartzJob;

public enum DiscountJobType {

	ACTIVATION("activation",DiscountActivatorQuartzJob.class), DEACTIVATION("deactivation",DiscountDeactivatorQuartzJob.class);

	private String key;

	private Class<? extends QuartzJobBean> jobClass;

	private DiscountJobType(String key, Class<? extends QuartzJobBean> jobClass) {
		this.key = key;
		this.jobClass = jobClass;
	}

	public String getKey() {
		return key;
	}

	public Class<? extends QuartzJobBean> getJobClass() {
		return jobClass;
	}

}