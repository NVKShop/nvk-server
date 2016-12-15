package hu.unideb.inf.rft.nvkshop.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;

import hu.unideb.inf.rft.nvkshop.entities.product.Discount;
import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;
import hu.unideb.inf.rft.nvkshop.jobs.DiscountActivatorQuartzJob;
import hu.unideb.inf.rft.nvkshop.jobs.DiscountDeactivatorQuartzJob;
import hu.unideb.inf.rft.nvkshop.repositories.ScheduledDiscountDao;
import hu.unideb.inf.rft.nvkshop.service.ScheduledDiscountService;
import hu.unideb.inf.rft.nvkshop.service.impl.util.DiscountJobSchedulingHelper;
import hu.unideb.inf.rft.nvkshop.service.impl.util.DiscountJobType;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduledDiscountServiceImpl implements ScheduledDiscountService {

	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	@Autowired
	private ScheduledDiscountDao scheduledDiscountDao;

	@Override
	public void createScheduledDiscountJob(List<Discount> discounts, Date from, Date until) {
		ScheduledDiscount scheduledDiscount = createScheduledJobDiscount(discounts, from, until);

		scheduledDiscount = scheduledDiscountDao.save(scheduledDiscount);

		Pair<JobDetail, SimpleTrigger> activatorJob = createJob(scheduledDiscount, from, DiscountJobType.ACTIVATION);

		Pair<JobDetail, SimpleTrigger> deactivatorJob = createJob(scheduledDiscount, until,
				DiscountJobType.DEACTIVATION);

		try {
			schedulerFactory.getScheduler().scheduleJob(activatorJob.getLeft(), activatorJob.getRight());
			schedulerFactory.getScheduler().scheduleJob(deactivatorJob.getLeft(), deactivatorJob.getRight());
			schedulerFactory.afterPropertiesSet();
		} catch (Exception e) {
			log.info("Error happened in job scheduling");
			// TODO email küldése az adminisztrátoroknak
		}
	}

	@Override
	public void unScheduleDiscount(ScheduledDiscount discount) {
		String activatorTriggerName = DiscountJobSchedulingHelper.buildDiscountTriggerName(discount,
				DiscountJobType.ACTIVATION);
		String deactivatorTriggerName = DiscountJobSchedulingHelper.buildDiscountTriggerName(discount,
				DiscountJobType.DEACTIVATION);
		try {
			schedulerFactory.getScheduler().unscheduleJob(new TriggerKey(activatorTriggerName));
			schedulerFactory.getScheduler().unscheduleJob(new TriggerKey(deactivatorTriggerName));
		} catch (SchedulerException e) {
			// TODO email küldése az adminisztrátoroknak
		}
	}

	private Pair<JobDetail, SimpleTrigger> createJob(ScheduledDiscount scheduledDiscount, Date at,
			DiscountJobType jobType) {
		JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
		jobDetail.setJobClass(jobType.getJobClass());
		String jobName = DiscountJobSchedulingHelper.buildDiscountJobName(scheduledDiscount, jobType);
		jobDetail.setName(jobName);
		jobDetail.setDurability(true);
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(DiscountJobSchedulingHelper.SCHEDULED_DISCOUNT_ID_PARAM_NAME, scheduledDiscount.getId());
		jobDetail.setJobDataMap(jobDataMap);
		jobDetail.afterPropertiesSet();

		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		String groupName = DiscountJobSchedulingHelper.buildDiscountGroupName(scheduledDiscount, jobType);
		trigger.setGroup(groupName);
		String triggerName = DiscountJobSchedulingHelper.buildDiscountTriggerName(scheduledDiscount, jobType);
		trigger.setName(triggerName);
		trigger.setRepeatCount(0);
		trigger.setJobDetail(jobDetail.getObject());
		trigger.setStartTime(at);
		trigger.afterPropertiesSet();
		return Pair.of(jobDetail.getObject(), trigger.getObject());
	}

	private ScheduledDiscount createScheduledJobDiscount(List<Discount> discounts, Date from, Date until) {
		ScheduledDiscount scheduledDiscount = ScheduledDiscount.builder().starts(from).expires(until)
				.discounts(discounts).build();
		return scheduledDiscount;
	}

}
