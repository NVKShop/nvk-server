package hu.unideb.inf.rft.nvkshop.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hu.unideb.inf.rft.nvkshop.service.UserRegistrationRequestService;

@DisallowConcurrentExecution
public class UserRegistrationActivationEmailSenderJob extends QuartzJobBean {

	@Autowired
	private UserRegistrationRequestService userRegistrationRequestService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Long userRegistrationRequestId = QuartzJobUtil.getParameter(context,
					QuartzJobParameter.USER_REGISTRATION_REQUEST_ID);
			
			
			userRegistrationRequestService.sendRegistrationRequest(userRegistrationRequestId);
		} catch (RuntimeException e) {
			throw new JobExecutionException(e);
		}
	}

}
