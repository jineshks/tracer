package in.espirit.tracer.util;

import in.espirit.tracer.database.dao.CustomDao;
import in.espirit.tracer.model.Mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class MailUtils {
	private static Logger logger = Logger.getLogger(MailUtils.class.getName());

	public static boolean sendTextMail(Mail mail){
		
		String host = CustomDao.getResourceMessage("smtpHost");
		
		logger.debug("Begin sending message");
		try{
			HtmlEmail email = new HtmlEmail();
			email.setHostName(host);
			//email.setAuthentication(username, password);
			//email.setSmtpPort(port);
			if (mail.getFrom()== null) {
				email.setFrom(CustomDao.getResourceMessage("fromaddress"));
			}
			else {
				email.setFrom(mail.getFrom());
			}
			
			//email.addTo(mail.getTo());
			//email.setTo(mail.getTo());
			
			for(String emailadd:mail.getTo()) {
				email.addTo(emailadd);
			}
						
			email.setSubject(mail.getSubject());

			//email.setTextMsg(mail.getMessage());
			email.setHtmlMsg(mail.getMessage()); // - - - This is for HTML message.
			//email.setHtmlMsg(htmlBody);

			//email.setDebug(true);

			email.send();

		} catch (EmailException mex) {
			logger.error("Failed to send email - " +  mex.getMessage());
			return false;
		}
		return true;		
	}	
}
