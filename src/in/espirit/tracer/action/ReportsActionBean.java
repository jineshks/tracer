package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.ReportDao;
import in.espirit.tracer.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/reports/{type}/{milestone}")
public class ReportsActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/reports/burndown.jsp";
	private static final String VELOCITY = "/WEB-INF/jsp/reports/velocity.jsp";
	private static final String EFFICIENCY = "/WEB-INF/jsp/reports/efficiency.jsp";
	private String type;
	private String milestone;

	@DefaultHandler
	public Resolution open() throws Exception {
		if (type ==null ) {
			type = "burndown";
		}else if(type.equalsIgnoreCase("velocity")){
			type = "velocity";
			return new ForwardResolution(VELOCITY);	
		}else if(type.equalsIgnoreCase("efficiency")){
			return new ForwardResolution(EFFICIENCY);	
		}	
		if ((type.equalsIgnoreCase("burndown")) && (milestone == null)) {
			milestone = MilestoneDao.getCurrentMilestone();
		}		
		return new ForwardResolution(URL);	
	}
	
	public Resolution getData() throws Exception {  
		String data = null;
		if (type.equalsIgnoreCase("burndown")) {
			data = getBurnDownJSON(milestone);
		}
		else {
			data = getVeolocityJSON();
		}
		return new StreamingResolution("text", data);
	}
	
	private String getBurnDownJSON(String milestone) {
		String line = null;
		try {
			if (milestone == null) {
				milestone = MilestoneDao.getCurrentMilestone();
			}			
			//System.out.println(milestone);
			//get the starting to ending days of a milestone
			String[] startEndDates = MilestoneDao.getMilestoneDates(milestone);
			//System.out.println(startEndDates[0]);
			//System.out.println(startEndDates[1]);
			//get the dates in between starting and ending dates which becomes the x axis.
			ArrayList<String> period = DateUtils.getInBetweenDates(startEndDates[0],startEndDates[1]);	
			
			line = "{" +
			"\"cols\":[" +
				"{\"id\":\"\",\"label\":\"x\",\"pattern\":\"\",\"type\":\"string\"}," +
				"{\"id\":\"\",\"label\":\"Ideal\",\"pattern\":\"\",\"type\":\"number\"}," +
				"{\"id\":\"\",\"label\":\"Actual\",\"pattern\":\"\",\"type\":\"number\"}," +
				"{\"id\":\"\",\"label\":\"Target\",\"pattern\":\"\",\"type\":\"number\"}" +
			"]," +
			"\"rows\":[";
			
			String rows = "";
			
			HashMap<String, Integer> actualData = ReportDao.getBurnDownData(milestone);
			
			
			
			for(int i=0;i<period.size();i++) {
				rows += "{\"c\":[{\"v\":\"" + period.get(i) + "\",\"f\":null},{\"v\":" + (100 - (100*i/(period.size()-1))) + ",\"f\":null},{\"v\":" + actualData.get(period.get(i)) + ",\"f\":null},{\"v\":4,\"f\":null}]},";
			}
					
			if (rows.length()>0) {
				line+= rows.substring(0, rows.length()-1);
				line+=    "],"+
				"\"p\":null}";				
			}			
		} catch(ParseException e) {
			logger.fatal("Getting burndown detail json failed with parsing of start and end dates - " + e.getMessage());		
		}	
		catch (Exception e) {
			logger.fatal("Getting burndown detail json failed with error - " + e.getMessage());
		}
		
		
		
		
		return line;
	}
	
	private String getVeolocityJSON() {
		String bar="";
		try {
			Map<String, Integer> result = ReportDao.getSprintStoryPoint();
			String rows = "";
			bar = "{"+
			  "\"cols\": ["+
			        "{\"id\":\"\",\"label\":\"Sprint\",\"pattern\":\"\",\"type\":\"string\"},"+
			        "{\"id\":\"\",\"label\":\"Story points\",\"pattern\":\"\",\"type\":\"number\"}"+
			        "],"+
					"\"rows\": [";
			for(Entry<String, Integer> entry : result.entrySet()) {
				rows += "{\"c\":[{\"v\":\"" + entry.getKey() + "\",\"f\":null},{\"v\":" + entry.getValue() + ",\"f\":null}]},";	
			}
			if (rows.length()>0) {
				bar+= rows.substring(0, rows.length()-1);
				bar+=    "]"+
				"}";				
			}
			
		} catch (Exception e) {
			logger.fatal("Error in getting the spring story point");
		}
	
		return bar;	
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getMilestone() {
		return milestone;
	}

}

/*
private String getJSON(String type) {
		String bar="";
		try {
			Map<String, Integer> result = ReportDao.getSprintStoryPoint();
			String rows = "";
			bar = "{"+
			  "\"cols\": ["+
			        "{\"id\":\"\",\"label\":\"Sprint\",\"pattern\":\"\",\"type\":\"string\"},"+
			        "{\"id\":\"\",\"label\":\"Story points\",\"pattern\":\"\",\"type\":\"number\"}"+
			        "],"+
					"\"rows\": [";
			for(Entry<String, Integer> entry : result.entrySet()) {
				String key = entry.getKey();
				Integer val = entry.getValue();
				System.out.println(key + "---" + val);
				rows += "{\"c\":[{\"v\":\"" + entry.getKey() + "\",\"f\":null},{\"v\":" + entry.getValue() + ",\"f\":null}]},";	
			}
			if (rows.length()>0) {
				bar+= rows.substring(0, rows.length()-1);
				bar+=    "]"+
				"}";				
			}
			
		} catch (Exception e) {
			logger.fatal("Error in getting the spring story point");
		}
	
		/*
		
		String bar = "{"+
		  "\"cols\": ["+
		        "{\"id\":\"\",\"label\":\"Sprint\",\"pattern\":\"\",\"type\":\"string\"},"+
		        "{\"id\":\"\",\"label\":\"Story points\",\"pattern\":\"\",\"type\":\"number\"}"+
		      "],"+
		  "\"rows\": ["+
		        "{\"c\":[{\"v\":\"Sprint A\",\"f\":null},{\"v\":33,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint B\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint C\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint D\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Sprint E\",\"f\":null},{\"v\":2,\"f\":null}]}"+
		      "]"+
		"}";
		
		 ending of inside comment
		
		String line = "{" +
						"\"cols\":[" +
							"{\"id\":\"\",\"label\":\"x\",\"pattern\":\"\",\"type\":\"string\"}," +
							"{\"id\":\"\",\"label\":\"Ideal\",\"pattern\":\"\",\"type\":\"number\"}," +
							"{\"id\":\"\",\"label\":\"Actual\",\"pattern\":\"\",\"type\":\"number\"}," +
							"{\"id\":\"\",\"label\":\"Target\",\"pattern\":\"\",\"type\":\"number\"}" +
						"]," +
						"\"rows\":[" +
							"{\"c\":[{\"v\":\"A\",\"f\":null},{\"v\":30,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"B\",\"f\":null},{\"v\":28,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"C\",\"f\":null},{\"v\":26,\"f\":null},{\"v\":30,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"D\",\"f\":null},{\"v\":24,\"f\":null},{\"v\":28,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"E\",\"f\":null},{\"v\":22,\"f\":null},{\"v\":26,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"F\",\"f\":null},{\"v\":20,\"f\":null},{\"v\":26,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"G\",\"f\":null},{\"v\":18,\"f\":null},{\"v\":24,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"H\",\"f\":null},{\"v\":16,\"f\":null},{\"v\":20,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"I\",\"f\":null},{\"v\":14,\"f\":null},{\"v\":16,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"J\",\"f\":null},{\"v\":12,\"f\":null},{\"v\":14,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"K\",\"f\":null},{\"v\":10,\"f\":null},{\"v\":10,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"L\",\"f\":null},{\"v\":8,\"f\":null},{\"v\":8,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"M\",\"f\":null},{\"v\":6,\"f\":null},{\"v\":4,\"f\":null},{\"v\":4,\"f\":null}]}," +
							"{\"c\":[{\"v\":\"N\",\"f\":null},{\"v\":0,\"f\":null},{\"v\":4,\"f\":null},{\"v\":4,\"f\":null}]}" +
						"]," +
						"\"p\":null}";
		
		if(type.equalsIgnoreCase("burndown")){
			return line;
		}else{
			return bar;
		}
	}
*/