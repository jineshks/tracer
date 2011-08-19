package in.espirit.tracer.action;

import in.espirit.tracer.model.Mail;
import in.espirit.tracer.util.MailUtils;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

@UrlBinding("/notifyEvent")
public class MailActionBean extends BaseActionBean{
	private static final String URL = "/WEB-INF/jsp/mail_template.jsp";
	private Mail mail;
	
	
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	@ValidateNestedProperties ({
		@Validate(required=true, field="from"),
		@Validate(required=true, field="to"),		
	})	
	
	
	
	@DefaultHandler
	public Resolution notifyEvent() {
		return new ForwardResolution(URL);
	}
	
	public Resolution notifyUser() {
		
		boolean flag = false;
	try {
		// String host = CustomDao.getResourceMessage("smtpHost"); - moved to sendMail
		String from = getContext().getRequest().getParameter("from")
				.trim();
		String to = getContext().getRequest().getParameter("to")
				.trim();
		String subject = getContext().getRequest().getParameter("subject")
		.trim();
		String message = getContext().getRequest().getParameter("message")
		.trim();

		Mail mail = new Mail();
		mail.setFrom(from);
		//mail.setTo(to);
		//mail.setSmtpHost(host);
		mail.setMessage(message);
		mail.setSubject(subject);
		
		flag = MailUtils.sendTextMail(mail);
	} catch (Exception e) {
		System.out.println("Error while mailing");
		e.printStackTrace();
		logger.error(e.getMessage());
	}

	if (flag) {
		return new ForwardResolution(
				DashboardActionBean.class);
	} else {
		System.out.println("Mail task is failed");
		return new ForwardResolution(DashboardActionBean.class);
	}
		
	}
	
	
	

	
	
	

}
