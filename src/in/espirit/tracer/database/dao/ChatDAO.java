package in.espirit.tracer.database.dao;


import in.espirit.tracer.model.Message;
import in.espirit.tracer.model.User;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;


/**
 * @author Bineesh
 *
 */
public final class ChatDAO {


 
  private static ChatDAO instance;



  private LinkedHashMap<String, ChatSession> chatSessions = new LinkedHashMap<String, ChatSession>();


  /**
   * Collection of User objects of currently logged in users.
   */
  private Vector<User> users = new Vector<User>();



  private ChatDAO() {
  } 


  public static ChatDAO getInstance() {

    
    if (instance == null) {
      instance = new ChatDAO();      
    }
    return instance;

  } 


  /**
   * Adds a user to the list of logged on users.
   *
   * @param inUser The user to log in.
   */
  public synchronized void logUserIn(User inUser) {
   if(!users.contains(inUser)){	   
    users.add(inUser);   
    ChatSession chatSession= new ChatSession();
   }   
  } 
  
  /**
   * Remove a user logged out users.
   *
   * @param inUser The user to logged out.
   */
  public synchronized void removeUser(User outUser) {      
    users.remove(outUser);    
  } 
  
  /**
   * Adds a user in to the chat session.
   *
   * @param userChatSession -Unique chat session.
   */
  public synchronized void addChatSession(String userChatSession) {
	ChatSession chatSession= new ChatSession();	
	if(!chatSessions.containsKey(userChatSession)){
		chatSessions.put(userChatSession,chatSession);
	}	  	
}

//logged-in user list
public synchronized Vector<User> getOnlineUserList() {
	    return users;
  } 

public synchronized void sentMessage(String inRoom, Message inMessage) {	
	ChatSession chatSession = (ChatSession)chatSessions.get(inRoom);
	chatSession.postMessage(inMessage);
  } 

/**
 * Get the chat session message from date :inDate.
 *
 * @param inDate -chat sent time.
 */
public synchronized Vector<Message> getMessages(String userChatSession, Date inDate) {
	ChatSession chatSession = (ChatSession)chatSessions.get(userChatSession);
    return chatSession.getMessages(inDate);
  } 

/**
 * Check any new message comes in the session after :lastUpdateDateTime
 *
 * @param lastUpdateDateTime -last chat received time.
 */
public synchronized boolean isUppdatedMessage(String inRoom, Date lastUpdateDateTime){
	ChatSession chatSession = (ChatSession)chatSessions.get(inRoom);	
	return chatSession.isUppdatedMessage(lastUpdateDateTime);
}

 
} 