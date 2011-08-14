package in.espirit.tracer.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/reports/{type}")
public class ReportsActionBean extends BaseActionBean {
	private static final String URL = "/WEB-INF/jsp/reports/burndown.jsp";
	private String type;

	@DefaultHandler
	public Resolution open() throws Exception {
		if (type ==null ) {
			type = "burndown";
		}
		return new ForwardResolution(URL);	
	}
	
	public Resolution getData() throws Exception {  
		
		String data = getJSON(type);
		System.out.println("data :"+data);
		return new StreamingResolution("text", data);
	}
	
	private String getJSON(String type) {
		String pie = "{"+
		  "\"cols\": ["+
		        "{\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},"+
		        "{\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}"+
		      "],"+
		  "\"rows\": ["+
		        "{\"c\":[{\"v\":\"Mushrooms\",\"f\":null},{\"v\":3,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Onions\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Olives\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Zucchini\",\"f\":null},{\"v\":1,\"f\":null}]},"+
		        "{\"c\":[{\"v\":\"Pepperoni\",\"f\":null},{\"v\":2,\"f\":null}]}"+
		      "]"+
		"}";
		String line = "{\"cols\":[{\"id\":\"\",\"label\":\"x\",\"pattern\":\"\",\"type\":\"string\"},{\"id\":\"\",\"label\":\"Cats\",\"pattern\":\"\",\"type\":\"number\"},{\"id\":\"\",\"label\":\"Blanket 1\",\"pattern\":\"\",\"type\":\"number\"},{\"id\":\"\",\"label\":\"Blanket 2\",\"pattern\":\"\",\"type\":\"number\"}],\"rows\":[{\"c\":[{\"v\":\"A\",\"f\":null},{\"v\":1,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"B\",\"f\":null},{\"v\":2,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"C\",\"f\":null},{\"v\":4,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"D\",\"f\":null},{\"v\":8,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"E\",\"f\":null},{\"v\":7,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"F\",\"f\":null},{\"v\":7,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"G\",\"f\":null},{\"v\":8,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"H\",\"f\":null},{\"v\":4,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"I\",\"f\":null},{\"v\":2,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"J\",\"f\":null},{\"v\":3.5,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"K\",\"f\":null},{\"v\":3,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"L\",\"f\":null},{\"v\":3.5,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]},{\"c\":[{\"v\":\"M\",\"f\":null},{\"v\":1,\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null}]},{\"c\":[{\"v\":\"N\",\"f\":null},{\"v\":1,\"f\":null},{\"v\":0.5,\"f\":null},{\"v\":1,\"f\":null}]}],\"p\":null}";
		
		if(type.equalsIgnoreCase("velocity")){
			return pie;
		}else{
			return line;
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
