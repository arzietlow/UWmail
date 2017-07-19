///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             UWmailDB.java
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

import java.util.Iterator;

/**
 * This class represents the database for the UWmail email system.
 * Conversations in the inbox are initialized from the data files using this
 * class. It is composed of both the inbox and trash lists.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class UWmailDB<E> {
	private ListADT<Conversation> inbox;
	private ListADT<Conversation> trash;

	/**
	 * Constructs a new UWmailDB object used only once throughout the program
	 */
	public UWmailDB() {
		this.inbox = new DoublyLinkedList<Conversation>();
		this.trash = new DoublyLinkedList<Conversation>(); 
	}

	/**
	 * The getter method for the current size of the inbox
	 * @return the number of conversation objects within the inbox list
	 */
	public int size() {
		return inbox.size();
	}

	/**
	 *addEmail is used in initializing the conversations in the Inbox at the 
	 *beginning of the program. Each Email object is placed into the
	 *conversation to which it belongs. If no match is found for an email, a 
	 *new Conversation is created to store the email.
	 *
	 * @param (e) the Email object to be added to the inbox
	 */
	public void addEmail(Email e) {
		if (inbox.size() == 0) {
			Conversation toAdd = new Conversation(e);
			inbox.add(toAdd);
		}
		else {
			Conversation correctConv; 
			Iterator<Conversation> inboxItr = inbox.iterator();
			boolean hasFound = false;
			while ((inboxItr.hasNext() && (!hasFound))) {
				Conversation tempConv = inboxItr.next();
				Iterator<Email> emailItr = tempConv.iterator();
				while (emailItr.hasNext()) {
					Email tempEmail = emailItr.next();
					ListADT<String> tempRefs = tempEmail.getReferences();
					if (tempRefs != null) {
						if (tempRefs.contains(e.getMessageID())) {
							hasFound = true;
							correctConv = tempConv;
							correctConv.add(e);
							break;
						}
					}
				}
			}
			if (!hasFound) {
				Conversation toAdd = new Conversation(e);
				inbox.add(toAdd);
			}
		}
	}

	/**
	 * Getter method for the current inbox list
	 * @return the current list of conversations in the inbox variable
	 */
	public ListADT<Conversation> getInbox() {
		return this.inbox;
	}

	/**
	 * Getter method for the current trash list
	 * @return the current list of conversations in the trash variable
	 */
	public ListADT<Conversation> getTrash() {
		return this.trash;
	}

	/**
	 *Removes a Conversation item at a specified position in the inbox to
	 *the end of the list of Conversations known as the trash.
	 *
	 * @param (idx) The position within the inbox list at which to remove
	 */
	public void deleteConversation(int idx) {
		if (idx >= inbox.size()) throw new IndexOutOfBoundsException();
		else if (idx < 0) throw new IndexOutOfBoundsException();
		else {
			Conversation remove = inbox.get(idx);
			trash.add(remove);
			inbox.remove(idx);
		}
	}

}
