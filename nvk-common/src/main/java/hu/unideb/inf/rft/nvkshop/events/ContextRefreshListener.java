package hu.unideb.inf.rft.nvkshop.events;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import hu.unideb.inf.rft.nvkshop.jobs.DummyQuartzJob;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	    JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
	    jobDetail.setJobClass(DummyQuartzJob.class);
	    jobDetail.setName("dummyJob");
	    jobDetail.setDurability(true);
	    jobDetail.setJobDataMap(new JobDataMap());
	    jobDetail.afterPropertiesSet();

	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean	();
	    trigger.setBeanName("dummyJob");
	    trigger.setGroup("dummyGroup");
	    trigger.setRepeatCount(0);
	    trigger.setJobDetail(jobDetail.getObject());
	    trigger.setStartTime(new Date(System.currentTimeMillis()+50000));
	    trigger.afterPropertiesSet();
	    
	    try {
			schedulerFactory.getScheduler().scheduleJob(jobDetail.getObject(), trigger.getObject());
			schedulerFactory.afterPropertiesSet();
		} catch (Exception e) {
			log.info("Error happened in job scheduling");
		}
	}

}
