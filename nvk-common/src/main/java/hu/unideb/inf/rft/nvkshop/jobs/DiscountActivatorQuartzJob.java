package hu.unideb.inf.rft.nvkshop.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import hu.unideb.inf.rft.nvkshop.service.DiscountService;
import hu.unideb.inf.rft.nvkshop.service.impl.util.DiscountJobSchedulingHelper;


@Component
public class DiscountActivatorQuartzJob extends QuartzJobBean {
	
	@Autowired
	private DiscountService discountService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Long scheduledDiscountId = (Long) context.get(DiscountJobSchedulingHelper.SCHEDULED_DISCOUNT_ID_PARAM_NAME);
		discountService.activateDiscount(scheduledDiscountId);
	}
	
}