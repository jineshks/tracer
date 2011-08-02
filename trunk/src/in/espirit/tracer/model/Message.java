package in.espirit.tracer.model;

import java.util.Date;

/**
 * @author Bineesh
 *
 */

public class Message {

	private String text;

	private String sentBy;

	private Date sentDateTime;

	public String getText() {

		return text;

	}

	public void setText(String inText) {

		text = inText;

	}

	public String getSentBy() {

		return sentBy;

	}

	public void setSentBy(String insentBy) {

		sentBy = insentBy;

	}

	public Date getSentDateTime() {

		return sentDateTime;

	}

	public void setSentDateTime(Date inSentDateTime) {

		sentDateTime = inSentDateTime;

	}

}
