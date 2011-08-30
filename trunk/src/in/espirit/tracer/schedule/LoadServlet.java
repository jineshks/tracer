package in.espirit.tracer.schedule;

import in.espirit.tracer.util.DateUtils;

import java.io.IOException;
import java.text.ParseException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class LoadServlet extends GenericServlet {
	final Logger logger = Logger.getLogger(LoadServlet.class.getName());

	@Override
	public void init() throws ServletException {		
		SchedulerFactory sf=new StdSchedulerFactory();
		Scheduler sched = null;
		try {
			logger.debug("Loading the load up servlet for starting the quartz plugin");
			//sched = sf.getScheduler("TracerScheduler");
			sched = sf.getScheduler();

			JobDetail job = newJob(ReportBurnDown.class)
			.withIdentity("job1", "group1")
			.build();
			CronTrigger trigger = null;

			trigger = newTrigger()
			.withIdentity("trigger1", "group1")
			//.withSchedule(cronSchedule("0/20 * * * * ?"))   // As of now restricted to run every 20 seconds..later will be changed to a specific time.
			.withSchedule(cronSchedule("0 50 23 * * ?"))  // Running the job every day at 23:50 time.  
			.build();
				 
			sched.scheduleJob(job, trigger);
			logger.info("Scheduler started for the job - report burndown at " + DateUtils.getDatetimeInFormat("yyyy-MM-dd HH:mm:ss"));
			sched.start();
			
			
			//sched.shutdown();  //Is it necessary to shutdown the quartz one ?			
			
		} catch (SchedulerException e1) {
			logger.fatal("In scheduler exception " + e1.getMessage());
			e1.printStackTrace();
		}
		catch (ParseException e) {
			logger.fatal("In parse Exception" + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.fatal("general exceptions" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
	throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub		
		SchedulerFactory sf=new StdSchedulerFactory();
		Scheduler sched = null;		
		try {
			sched = sf.getScheduler();			
			logger.info("shutting down the scheduler job");					
			sched.shutdown();			
			
			
		} catch (SchedulerException e) {
			logger.fatal("at shutdown scheduler exception"  + e.getMessage());
			e.printStackTrace();		}
		catch (Exception e) {
			logger.fatal("at shutdown other exception"  + e.getMessage());
			e.printStackTrace();		}
		
		super.destroy();
	}
	
	

}
