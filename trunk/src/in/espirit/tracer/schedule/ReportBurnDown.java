package in.espirit.tracer.schedule;

import java.util.ArrayList;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.ReportDao;
import in.espirit.tracer.util.DateUtils;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ReportBurnDown implements Job {
	
	final Logger logger = Logger.getLogger(ReportBurnDown.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("from the job plugin for reporting > " + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm:ss")); // for testing purposes.
		logger.info("Report Burn Down Job Started at " + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm:ss"));
		
		try {
			ArrayList<String> mlList = MilestoneDao.getMilestoneNames();
			String date = DateUtils.getDatetimeInFormat("yyyy-MM-dd");
			int prog = 0;
			boolean flag = false;
			
			for(String ml : mlList) {
				prog = 100 - MilestoneDao.calcProgress(ml);		  // subtracting from 100 as 100 is for period 		
				flag = ReportDao.insertBurnDownData(date, ml, prog);
				if (!flag) {
					logger.error("Report - Burn down is not properly created for the date" + date + " milestone - " + ml);					
				}
			}			
		} catch (Exception e) {
			logger.fatal("Error with getting milestone names" + e.getMessage());
		}
		
			
		logger.info("Report Burn Down Job Completed at " + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm:ss"));
	}

	
	
}
