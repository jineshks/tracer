package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.ChatDAO;
import in.espirit.tracer.database.dao.UserDao;
import in.espirit.tracer.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.log4j.Logger;

/**
 * @author Bineesh
 * 
 */

@UrlBinding("/chatUsers")
public class ChatUserListAction extends BaseActionBean {

	private static Logger logger = Logger.getLogger(ChatUserListAction.class
			.getName());

	@DefaultHandler
	public Resolution users() {

		if (logger.isDebugEnabled()) {
			logger.debug("Getting loggned user list");
		}

		HttpSession session = this.getContext().getRequest().getSession();
		String userChatArea = this.getContext().getRequest().getParameter("userChatArea");
		ChatDAO dao = ChatDAO.getInstance();
		Vector<User> loggedInUsers = dao.getOnlineUserList();
		ArrayList<User> registredUsers = null;
		try {
			registredUsers = UserDao.getUserList();
		} catch (Exception e) {
			logger.debug("Error getting user list:" + e);
			e.printStackTrace();
		}
		String loggedUser = getContext().getLoggedUser();
		StringBuffer chatUsers = new StringBuffer(4000);
		String userBubbleColor = "item green";
		for (User inUser : loggedInUsers) {

			if (logger.isDebugEnabled()) {
				logger.debug("Getting loggned user list:" + inUser);
			}

			if (!inUser.getUserName().equals(loggedUser)) {
				// Creating a unique chat session between two users
				int i = loggedUser.compareTo(inUser.getUserName());
				String chatSession = i > 0 ? loggedUser + "-"
						+ inUser.getUserName() : inUser.getUserName() + "-"
						+ loggedUser;

				// Adding chat session in memory.
				ChatDAO.getInstance().addChatSession(chatSession);

				if (logger.isDebugEnabled()) {
					logger.debug("Created chat session between the users:"
							+ loggedUser + "&" + inUser);
				}

				Date lastChatSessionDateTime = (Date) session
						.getAttribute(loggedUser + chatSession
								+ "-lastDateTime");

				if (logger.isDebugEnabled()) {
					logger.debug("Last chat session DateTime between the users:"
							+ loggedUser
							+ "&"
							+ inUser
							+ " :"
							+ lastChatSessionDateTime);
				}

				boolean IsNewMessage = false;

				if (!chatSession.equals(userChatArea) || userChatArea.equals("")) {
					IsNewMessage = dao.isUppdatedMessage(chatSession,
							lastChatSessionDateTime);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("New message added in chat session? "
							+ IsNewMessage);
				}
				// If new message comes from other user ,the user chating with
				// other user session notify with color
				if (IsNewMessage) {
					userBubbleColor = "item orange"; // Need to change with 'blink' notification instead of color change.?
				}
				chatUsers.append("<div class=\""
						+ userBubbleColor
						+ "\" id="
						+ chatSession
						+ " name="
						+ inUser.getUserName()
						+ " "
						+ "onclick="
						+ "javascript:getChatSession(this)"
						+ ">"
						+ "<span class=\"photo\"> <img width=\"48px\" height=\"48px\" src=\"images/w48.png\" alt=\"jane\"> </span>"
						+ inUser.getUserName() + "</div>");
				// default chat user list color
				userBubbleColor = "item green";
			}
		}
		// For showing offline users
		for (User registredUser : registredUsers) {
			
			if (!loggedInUsers.contains(registredUser)) {
				chatUsers.append("<div class=\"item white\">"
						+ "<span class=\"photo\"> <img width=\"48px\" height=\"48px\" src=\"images/w48.png\"> </span>"
						+ registredUser.getUserName() + "</div>");
			}

		}
		return new StreamingResolution("text/html", chatUsers.toString());
	}

}
