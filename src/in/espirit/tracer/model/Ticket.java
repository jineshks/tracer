package in.espirit.tracer.model;

import java.util.ArrayList;

public class Ticket {

	private String id;
	private String title;
	private String desc;
	private String priority;
	private String status;
	private String reporter;
	private String owner;
	private String parentTicket;
	private String component;
	private String milestone;
	private String type;
	private ArrayList<Comment> comments;
	private String newComments;
	private String importance;
	private String progress;
	private String tags;
	private String phase;
	private ArrayList<Attachment> attachments;
	private double position;

	public Ticket() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getParentTicket() {
		return parentTicket;
	}

	public void setParentTicket(String parentTicket) {
		this.parentTicket = parentTicket;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public void setNewComments(String newComments) {
		this.newComments = newComments;
	}

	public String getNewComments() {
		return newComments;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getProgress() {
		return progress;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPhase() {
		return phase;
	}

	public void setAttachments(ArrayList<Attachment> attachments) {
		this.attachments = attachments;
	}

	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getPosition() {
		return position;
	}


}