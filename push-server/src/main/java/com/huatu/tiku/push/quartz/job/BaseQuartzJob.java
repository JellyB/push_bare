package com.huatu.tiku.push.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author biguodong
 */
public interface BaseQuartzJob extends Job{

	String CourseBizData = "CourseInfo";

	String BizDataClass = "bizDataClass";
	/**
	 * execute
	 * @param executionContext
	 * @throws JobExecutionException
	 */
	@Override
	void execute(JobExecutionContext executionContext) throws JobExecutionException;
}

