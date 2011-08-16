package in.espirit.tracer.schedule;

import in.espirit.tracer.action.BaseActionBean;

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
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class LoadServlet extends GenericServlet {


	@Override
	public void init() throws ServletException {		
	
		final Logger logger = Logger.getLogger(BaseActionBean.class.getName());

		SchedulerFactory sf=new StdSchedulerFactory();
		Scheduler sched = null;
		try {
			logger.debug("Loading the load up servlet for starting the quartz plugin");
			sched = sf.getScheduler();

			JobDetail job = newJob(ReportBurnDown.class)
			.withIdentity("job1", "group1")
			.build();
			CronTrigger trigger = null;

			trigger = newTrigger()
			.withIdentity("trigger1", "group1")
			.withSchedule(cronSchedule("0/20 * * * * ?"))   // As of now restricted to run every 10 minutes..later will be changed to a specific time.
			//.withSchedule(cronSchedule("0 0 0 * * ?"))
			.build();
			
			sched.scheduleJob(job, trigger);
			sched.start();
		} catch (SchedulerException e1) {
			logger.fatal(e1.getMessage());
			e1.printStackTrace();
		}
		catch (ParseException e) {
			logger.fatal(e.getMessage());
		}
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1)
	throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
