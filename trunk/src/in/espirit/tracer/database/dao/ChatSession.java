package in.espirit.tracer.database.dao;

import in.espirit.tracer.model.Message;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Bineesh
 *
 */

public class ChatSession {

	private String name;

	/**
	 * The list of messages in the user session.
	 */
	private Vector<Message> messages = new Vector<Message>();

	public void postMessage(Message inMessage) {

		messages.add(inMessage);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*
	public Vector getPastMessages() {
		// TODO Auto-generated method stub
		return messages;
	}*/
	
	 /**
	   * This method is for getting the message when the user in the chat session.
	   * @param inDateTime - chat message after current time -live.
	   */
	
	public Vector getMessages(Date inDateTime) {
		Vector<Message> al = new Vector<Message>();
		for (Iterator it = messages.iterator(); it.hasNext();) {
			Message message = (Message) it.next();
			if (message.getSentDateTime().after(inDateTime)) {
				al.add(message);
			}
		}
		return al;
	}
	
	 /**
	   * This method is for checking any message came from other users when the user in other chat session.
	   * @param lastUpdateDateTime - last chat message received time.
	   */

	public boolean isUppdatedMessage(Date lastUpdateDateTime) {
		boolean newMessage = false;
		if (lastUpdateDateTime == null && messages.size() > 0) {
			return true;
		}
		for (Iterator it = messages.iterator(); it.hasNext();) {
			Message message = (Message) it.next();
			if (message.getSentDateTime().after(lastUpdateDateTime)) {
				newMessage = true;
				break;
			}
		}
		return newMessage;
	}

}
