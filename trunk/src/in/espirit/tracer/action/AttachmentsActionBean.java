package in.espirit.tracer.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Attachment;
import in.espirit.tracer.model.Ticket;
import in.espirit.tracer.util.DateUtils;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/attachments/{event}/{fileName}")
public class AttachmentsActionBean extends BaseActionBean{
	
	private FileBean attachment;
	private Ticket ticket;
	private String fileName;
	
	@DefaultHandler
	public Resolution submit() {	
		
		//doesn't handle the scenario if that file is already existing. 
		
		Boolean flag = false;
		String output = "<li><p><span class='bold'>File Upload Failed!</span></p></li>";
		if(attachment !=null) {
			try {
				File temp = new File(CustomDao.getResourceMessage("filestorage")+"/"+ticket.getId() + "-" + attachment.getFileName());
				attachment.save(temp);
								
				Attachment att = new Attachment();
				att.setFileName(attachment.getFileName());
				att.setTimeStamp(DateUtils.getDatetimeInFormat("yyyy/MM/dd HH:mm"));
				att.setUserName(getContext().getLoggedUser());				
				flag = TicketDao.insertAttachments(ticket.getId(), att);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			if (flag) {
				output = "<ul><li><p><span class='bold'><a href='../attachments/download/" + ticket.getId() +"-" + attachment.getFileName()  + "'>" + attachment.getFileName() + "</a></span>" + 
			getContext().getLoggedUser() + "<span class=\"tar right\">" + DateUtils.getDatetimeInFormat("yyyy/MM/dd HH:mm") + 
			"</span></p></li></ul>";
			}
						
			return new StreamingResolution("text/html", "<script> window.top.window.responseUpload(" + output + ")</script>");
		}
		else {return null;}
			
	}

	public Resolution download() {		

		File file = new File(CustomDao.getResourceMessage("filestorage")+"/"+getFileName());		
		try {
			return new StreamingResolution( "application/octet-stream", new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.debug("File is not in location for download");
			e.printStackTrace();
		}
		// error handling parts needs to be put in
		return null;		
	}
	
		
	public void setAttachment(FileBean attachment) {
		this.attachment = attachment;
	}

	public FileBean getAttachment() {
		return attachment;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
	
		


}



