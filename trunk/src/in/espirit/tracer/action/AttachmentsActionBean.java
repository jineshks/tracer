package in.espirit.tracer.action;

import in.espirit.tracer.model.Ticket;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/attachments")
public class AttachmentsActionBean extends BaseActionBean{
	
	private FileBean attachment;
	
	private Ticket ticket;
	
	@DefaultHandler
	public Resolution submit() {
		if(attachment !=null) {
			System.out.println("getting the values");
			
			return new StreamingResolution("text/html", "uploading over");
		}
		else {return null;}
			
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
	
	
	


}
