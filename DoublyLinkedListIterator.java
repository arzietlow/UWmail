///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             DoublyLinkedListIterator.java
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
import java.util.NoSuchElementException;

/**
 * This class constructs an iterator object that is used to 
 * traverse any DoublyLinkedList of objects that has been created. 
 * It is used in iterating through Emails within a Conversation and also 
 * Conversations within the inbox.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class DoublyLinkedListIterator<E> implements Iterator<E>{

	private Listnode<E> currNode;//The node currently being pointed at

	/**
	 * Constructs a new Iterator and sets its "head" field to the passed
	 * Listnode
	 * @param (head) the Listnode that represents the beginning of the 
	 * particular list to be traversed
	 */
	public DoublyLinkedListIterator(Listnode<E> head) {
		currNode = head;
	}

	/**
	 * This method is used to make sure the next() method won't throw an 
	 * exception
	 * @return Whether or not the list has more elements
	 */
	public boolean hasNext() {
		return currNode != null;
	}

	/**
	 * Returns whichever element is currently being pointed at, and advances
	 * the pointer along the list.
	 * @return (E) the element that was previously under focus
	 */
	public E next() {
		if (currNode == null) throw new NoSuchElementException();
		E result = currNode.getData();
		currNode = currNode.getNext();
		return result;
	}

	/**
	 * not available
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
