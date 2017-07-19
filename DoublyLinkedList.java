///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             DoublyLinkedList.java
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
 *This class is the underlying data structure for the entire UWmail program.
 *The Inbox and Trash are lists of lists that all implement this class for their
 *functions.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class DoublyLinkedList<E> implements ListADT<E>, Iterable<E> {

	private Listnode<E> head; //the first node of the list
	private Listnode<E> tail; //the last node of the list
	private int numItems; //the number of nodes on a list (>= 0)

	/**
	 * Constructs a new DoublyLinkedList to be empty
	 */
	public DoublyLinkedList() {
		numItems = 0;
		this.head = new Listnode<E>(null);
		this.tail = head;
	}

	/**
	 * Calls a method to construct a new iterator instance that can be used
	 * to step through the elements in this particular DoulyLinkedList
	 * @return the Iterator object that is connected to this list
	 */
	public DoublyLinkedListIterator<E> iterator() {
		return new DoublyLinkedListIterator<E>(head);
	}

	/**
	 * Adds a new element to the end of this list
	 * @param (item) the element to be added
	 */
	public void add(E item) {
		Listnode<E> toAdd = new Listnode<E>(item);
		if (numItems == 0) {
			head = toAdd;
		}
		else {
			toAdd.setPrev(tail);
			tail.setNext(toAdd);
		}
		tail = toAdd;
		numItems++;
	}

	/**
	 * Adds a new element to the given position in this list
	 * @param (item) the element to be added
	 * @param (pos) the index at which to add this new element (item)
	 */
	public void add(int pos, E item) {
		if ((pos > numItems) || (pos < 0)) 
			throw new IndexOutOfBoundsException();
		else if (pos == numItems) add(item);
		else if (pos == 0) {
			Listnode<E> toAdd = new Listnode<E>(item, head, null);
			head.setPrev(toAdd);
			head = toAdd;
		}
		else {
			Listnode<E> toAdd = new Listnode<E>(item);
			Iterator<E> itr = this.iterator();
			int currPos = 0;
			Listnode<E> temp = null;
			while ((itr.hasNext()) && (currPos < pos)) {
				temp = (Listnode<E>) itr.next();
				currPos++;
			}
			toAdd.setNext(temp.getNext());
			toAdd.setPrev(temp);
			temp.getNext().setPrev(toAdd);
			temp.setNext(toAdd);
		}
		numItems++;
	}


	/**
	 * Searches through the list to find if it contains a given element
	 * @param (item) the element that is being searched for
	 * @return whether or not this particular list contains the element (item)
	 */
	public boolean contains(E item) {
		Iterator<E> itr = this.iterator();
		boolean hasFound = false;
		while (itr.hasNext()) {
			if (itr.next().equals(item)) {
				hasFound = true;
			}
		}
		return hasFound;
	}

	/**
	 * Gets the element located at a given index of the DoublyLinkedList
	 * @param (pos) the index at which to get an element from this list
	 * @return the element that is at index (pos) in this particular list
	 */
	public E get(int pos) {
		if ((pos > numItems) || (pos < 0)) throw new IndexOutOfBoundsException();
		else if (pos == 0) return head.getData();
		else if (pos == numItems - 1) return tail.getData();
		else {
			Iterator<E> itr = this.iterator();
			int currPos = 0;
			E temp = null;
			while ((itr.hasNext()) && (currPos < pos + 1)) {
				temp = itr.next();
				currPos++;
			}
			return temp;
		}
	}

	/**
	 * Used to determine when the list is empty
	 * @return whether or not this list is empty
	 */
	public boolean isEmpty() {
		return numItems == 0;
	}

	/**
	 * Removes an element from this list from the given index
	 * @param (pos) the index from which to remove 
	 * @return the element that has been removed from index (pos) of this list
	 */
	public E remove(int pos) {
		Listnode<E> temp = new Listnode<E>(null); //Listnode placeholder
		if ((pos >= numItems) || (pos < 0)) throw new IndexOutOfBoundsException();
		else if (pos == 0) {
			if (numItems > 1) {
				temp = head;
				head.getNext().setPrev(null);
				head = head.getNext();
				numItems--;
			}
			else {
				temp = head;
				head = null;
				numItems--;
			}
		}
		else if (pos == 1) {
			if (numItems > 2) {
				temp = head.getNext();
				head.setNext(temp.getNext());
				temp.getNext().setPrev(head);
				numItems--;
			}
			else if (numItems == 2) {
				temp = head.getNext();
				head.setNext(null);
				numItems--;
			}
			else {
				temp = head;
				head = null;
				numItems--;
			}
		}
		else {
			int currPos = 0;//Current index of list
			Listnode<E> temp2 = new Listnode<E>(null);//Listnode placeholder
			temp2 = head;
			while (currPos < pos - 1) {
				temp2 = temp2.getNext();
				currPos++;
			}
			temp = temp2.getNext();
			if (pos == numItems - 1) {
				temp2.setNext(null);
				tail = temp;
				numItems--;
			}
			else {
				temp2.setNext(temp2.getNext().getNext());
				temp2.getNext().setPrev(temp2);
				numItems--;
			}
		}
		return (E) temp;
	}

	/**
	 * Gets this particular list's number of elements
	 * @return the number of elements in this list
	 */
	public int size() {
		return numItems;
	}

}
