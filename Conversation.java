///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             Conversation.java
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
 * This class is used for the creation of Conversation objects, each of 
 * which stores a list of Email objects. Conversations are used to group 
 * Email objects together in a relevant listing, and to make that ordering
 * easy to navigate.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class Conversation implements Iterable<Email> {

	private int curr;//Index variable of current Email in view (>= 0)
	private int numItems;//Number of Emails in list (>= 0)
	private ListADT<Email> emailList;//The list of Emails for this Conversation

	/**
	 * Constructs a new Conversation object, given an Email object that may
	 * serve as the conversation's first element
	 *@param (e) the Email object to be stored in the Conversation's first index
	 */
	public Conversation(Email e) {
		this.emailList = new DoublyLinkedList<Email>();
		this.emailList.add(e);
		this.numItems = 1;
		this.curr = 0;
	}

	/**
	 * Getter method for a Conversation object's curr field
	 * @return the index of the Email object that is to be focused upon in the
	 * conversation view
	 */
	public int getCurrent() {
		return this.curr;
	}

	/**
	 * Modifier method for a Conversation object's curr field. Only moves the
	 * pointer in a valid numerical direction (per the nature of list indices)
	 */
	public void moveCurrentBack() {
		if (this.curr > 0) {
			this.curr = this.curr - 1;
		}
	}

	/**
	 * Modifier method for a Conversation object's curr field. Only moves the
	 * pointer in a valid numerical direction (per the nature of list indices)
	 */
	public void moveCurrentForward() {
		if (this.curr < this.numItems - 1) {
			this.curr = this.curr + 1;
		}
	}

	/**
	 * Getter method for a Conversation object's size field
	 * @return the number of Email objects that are currently being stored in 
	 * this Conversation object
	 */
	public int size() {
		return this.numItems;
	}

	/**
	 * Getter method for an Email object at a given index of this Conversation
	 * object's list of emails
	 * @param (n) the index at which to get an Email object
	 * @return the Email in this Conversation's list of Emails at index (n)
	 */
	public Email get(int n) {
		return this.emailList.get(n);
	}

	/**
	 * Adds an Email to this Conversation object's list of emails
	 * @param (e) the Email object to add to the end of this Conversation
	 * object's list of Emails
	 */
	public void add(Email e) {
		this.emailList.add(e);
		this.numItems++;
	}

	/**
	 * Calls a method to construct a new iterator that can be used to traverse
	 * this Conversation object's list of Email objects
	 */
	public Iterator<Email> iterator() {
		return this.emailList.iterator();
	}

}
