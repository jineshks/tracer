package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ActivityDao;
import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Activity;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedOutput;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/rss/{section}")
public class RSSFeedActionBean extends BaseActionBean {
	
	String section;
	private static final String URL = "/WEB-INF/jsp/rss.jsp";
	
	@DefaultHandler
	public Resolution open() throws Exception {
		
		 if (section == null) {			 
			 return new ForwardResolution(URL);
		 }
		
		 String feedType = "rss_2.0";    // The feed specification can be even atom also. refer to ROME documentation for list of valid entries.
		 SyndFeed feed = new SyndFeedImpl();
         feed.setFeedType(feedType);
         String appHome = CustomDao.getResourceMessage("applicationhome");
         SyndImage im;
         im = new SyndImageImpl();
         
         im.setTitle("Tracer - web based agile project management system");
         im.setLink(appHome);
         im.setUrl(appHome + "/images/logo-rss.png");      
         feed.setImage(im);
         
      
         feed.setLink(appHome);
         
         List<SyndEntryImpl> entries = new ArrayList<SyndEntryImpl>();
         SyndEntryImpl entry;
         SyndContent description;
         
		 if (section.equalsIgnoreCase("ticket")) {
			 feed.setTitle("Tracer Application - Ticket List");
			 feed.setDescription("Listing of tickets in the tracer application via feeds");
			 
			 // URL should be created by this example http://localhost:8080/tracer/rss/ticket?owner=xbmdeso&milestone=Sprint%20B&priority=P5
			 String owner = this.getContext().getRequest().getParameter("owner");
			 String milestone = this.getContext().getRequest().getParameter("milestone");
			 String priority = this.getContext().getRequest().getParameter("priority");
			 String importance = this.getContext().getRequest().getParameter("importance");
			 
			 String status = this.getContext().getRequest().getParameter("status");
			 String reporter = this.getContext().getRequest().getParameter("reporter");
			 String parentTicket = this.getContext().getRequest().getParameter("parentTicket");			 
			 String sortBy = this.getContext().getRequest().getParameter("sortBy");	 
			 String type = this.getContext().getRequest().getParameter("type");
			 
			 String currentmilestone = this.getContext().getRequest().getParameter("currentmilestone");
			 if (currentmilestone != null) {
				 //if (currentmilestone.equalsIgnoreCase("")) {
					 milestone = MilestoneDao.getCurrentMilestone();
					 feed.setTitle("Tracer Application - " + milestone + " Ticket List");
					 feed.setDescription("Listing of tickets in the current milestone - " + milestone +  " via feeds");
				 //}				            
			 }
			 
			 if (type == null) { 
				 type = "all";  //Include all defects, task and requirements if nothing is specified.
			 }
			 ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	         tickets.addAll(TicketDao.getRSSTickets(type,owner, priority, status,milestone,reporter,importance, parentTicket, sortBy));

	         for(Ticket t : tickets) {
	        	 entry = new SyndEntryImpl();
	             entry.setTitle(t.getTitle());
	             entry.setLink(appHome + "/" + t.getType() + "/" + t.getId());
	             entry.setAuthor(t.getReporter());
	             
	             description = new SyndContentImpl();
	             description.setType("text/plain");
	             description.setValue(t.getDesc());
	             entry.setDescription(description);
	             entries.add(entry);
	         }	                       
	     		 
		 }
		 else if (section.equalsIgnoreCase("activitystream")) {
			 feed.setTitle("Tracer Application - Activity Stream");
		
			String date = this.getContext().getRequest().getParameter("date");
			String query_date = "";
			if (date.equalsIgnoreCase("today")) {
				 feed.setDescription("Listing of activity stream in the tracer application for today via feeds");
				 query_date = DateUtils.getDate("yyyy-MM-dd", 0);
			}
			else if(date.equalsIgnoreCase("yesterday")) {
				 feed.setDescription("Listing of activity stream in the tracer application for yesterday via feeds");
				 query_date = DateUtils.getDate("yyyy-MM-dd", -1);
			}
			else if(date.equalsIgnoreCase("daybeforeyesterday")) {
				 feed.setDescription("Listing of activity stream in the tracer application for day before yesterday via feeds");
				 query_date = DateUtils.getDate("yyyy-MM-dd", -2);
			}
								
			 ArrayList<Activity> activities = new ArrayList<Activity>();
			 activities.addAll(ActivityDao.getRSSActivities(query_date));

	         for(Activity a : activities) {
	        	 entry = new SyndEntryImpl();
	             entry.setTitle(a.getActivity());
	             entry.setLink(appHome + ActivityDao.changeActivityDesc(a.getActivity(), "link"));
	             
	             //entry.setAuthor(a.getUserName());
	             entry.setPublishedDate(DateUtils.convertStringToCalendar(a.getTimeStamp(), "yyyy-MM-dd HH:mm").getTime());
	             
	             //description = new SyndContentImpl();
	             //description.setType("text/plain");
	             //description.setValue(a.getActivity());
	             //entry.setDescription(description);
	             entries.add(entry);
	         }	   		
		}
		 feed.setEntries(entries);               
    
        SyndFeedOutput output = new SyndFeedOutput();        
        StreamingResolution rs = new StreamingResolution("application/rss+xml" , output.outputString(feed));	
		return rs;
	}



	public String getSection() {
		return section;
	}



	public void setSection(String section) {
		this.section = section;
	}	
}
