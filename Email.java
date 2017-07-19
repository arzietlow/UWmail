///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             Email.java
// Semester:         CS 367 Fall 2015
//
// Author:           Andrew Zietlow arzietlow@wisc.edu
// CS Login:         azietlow
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//
// Pair Partner:     N/A
//
// External Help:   None
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Date;

/**
 * This class is used for the creation of Email objects, which are the 
 * underlying element that compose the Conversation objects. Email objects
 * are used in the displaying of Conversation objects and trash/inbox view.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class Email {
	private Date date;//The exact time and date of email creation
	private String messageID;//Unique address given to this Email
	private String subject;//Denotes the message's subject
	private String from;//Who wrote this email (an email address)
	private String to;//Who this email is intended for (an email address)
	private String inReplyTo;//MessageID of email being replied to
	private ListADT<String> body;//Message's main group of text
	private ListADT<String> references;//MessageIDs of related emails

	/**
	 * Constructs a new Email object that is not in reply to any other
	 * email.
	 */
	public Email(Date date, String messageID, String subject, String
			from, String to, ListADT<String> body) {
		this.date = date;
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.body = body;
	}

	/**
	 * Constructs a new Email object that is in reply to another email.
	 */
	public Email(Date date, String messageID, String subject, String 
			from, String to, ListADT<String> body, String 
			inReplyTo, ListADT<String> references) {
		this.date = date;
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.body = body;
		this.inReplyTo = inReplyTo;
		this.references = references;
	}

	/**
	 * Getter method for an Email object's Date field
	 * @return that particular Email's date of creation
	 */
	public String getDate() {
		return this.date.toString();
	}

	/**
	 * Getter method for an Email object's messageID field
	 * @return that particular Email's unique messageID
	 */
	public String getMessageID() {
		return this.messageID;
	}

	/**
	 * Getter method for an Email object's subject field
	 * @return that particular Email's subject title
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Getter method for an Email object's "from" field
	 * @return that particular Email's return address
	 */
	public String getFrom() {
		return this.from;
	}

	/**
	 * Getter method for an Email object's "to" field
	 * @return that particular Email's target recipient address
	 */
	public String getTo() {
		return this.to;
	}

	/**
	 * Getter method for an Email object's body field
	 * @return that particular Email's entire message body
	 */
	public ListADT<String> getBody() {
		return this.body;
	}

	/**
	 * Getter method for an Email object's inReplyTo field
	 * @return the messageID of the Email object this particular email is
	 * replying to
	 */
	public String getInReplyTo() {
		return this.inReplyTo;
	}

	/**
	 * Getter method for an Email object's references field
	 * @return that particular Email's list of messageID's corresponding
	 * to the previous emails in a given conversation
	 */
	public ListADT<String> getReferences() {
		return this.references;
	}
} 
