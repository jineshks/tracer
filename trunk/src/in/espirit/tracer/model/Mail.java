package in.espirit.tracer.model;

import java.util.ArrayList;

public class Mail {
	
	private String from;
	private ArrayList<String> to;
	private String subject;
	private String message;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setTo(ArrayList<String> to) {
		this.to = to;
	}
	public ArrayList<String> getTo() {
		return to;
	}
	
	

}
