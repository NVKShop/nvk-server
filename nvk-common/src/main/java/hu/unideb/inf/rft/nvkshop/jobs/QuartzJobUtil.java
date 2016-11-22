package hu.unideb.inf.rft.nvkshop.jobs;

import org.quartz.JobExecutionContext;

public class QuartzJobUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getParameter(JobExecutionContext ctx, QuartzJobParameter key) {
		return (T) ctx.getJobDetail().getJobDataMap().get(key.getParamName());
	}

}
