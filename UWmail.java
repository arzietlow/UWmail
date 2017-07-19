///////////////////////////////////////////////////////////////////////////////
//                   
// Title:            UWmail
// Files:            UWmail.java, UWmailDB.java, DoublyLinkedList.java
//					 ListADT.java, DoublyLinkedListIterator.java, Email.java,
//					 Conversation.java, Listnode.java, ProcessEmail.java,
//					 MalformedEmailException.java
//
// Semester:         CS 367 - Fall 2015
//
// Author:           Andrew Zietlow
// Email:            arzietlow@wisc.edu
// CS Login:         azietlow
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Scanner;
import java.lang.Integer;
import java.lang.NumberFormatException;

import java.util.Enumeration;
import java.util.Iterator; 
import java.io.InputStream;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * This class contains the main algorithm that executes the rest of the program.
 * It reads the .zip file, calls the methods to initialize Email objects using
 * that file, displays all output to the user, and processes input commands.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class UWmail {
	private static UWmailDB uwmailDB = new UWmailDB();
	private static final Scanner stdin = new Scanner(System.in);

	public static void main(String args[]) {
		if (args.length != 1) {
			System.out.println("Usage: java UWmail [EMAIL_ZIP_FILE]");
			System.exit(0);
		}
		loadEmails(args[0]);

		displayInbox();
	}

	/**
	 * Initializes all Email objects by reading the given .zip file for 
	 * available .txt files. If found, the data within that file is 
	 * used to call a method in ProcessEmail.java that extracts the data
	 * and creates new Emails.
	 *
	 * @param (fileName) the .zip file that contains all Email objects in their
	 *.txt format
	 */
	private static void loadEmails(String fileName) {
		try (ZipFile zf = new ZipFile(fileName);) {
			Enumeration<? extends ZipEntry> entries = zf.entries();
			while(entries.hasMoreElements()) {
				ZipEntry ze = entries.nextElement();
				if(ze.getName().endsWith(".txt")) {
					InputStream in = zf.getInputStream(ze);
					try {
						Email toLoad = ProcessEmail.createEmail(in);
						uwmailDB.addEmail(toLoad);
					}
					catch (MalformedEmailException e) {
						//do nothing, continue to next email if avaiable
					}
				}
			}
		} catch (ZipException e) {
			System.out.println("A .zip format error has occurred for the "
					+ "file.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("An I/O error has occurred for the file.");
			System.exit(0);
		} catch (SecurityException e) {
			System.out.println("Unable to obtain read access for the file.");
			System.exit(0);
		}
	}

	/**
	 * Prints the list of conversations within the database's "inbox" field
	 * that are currently available for viewing. Processes user commands.
	 */
	private static void displayInbox(){
		boolean done = false;
		System.out.println("Inbox:");
		System.out.println("------------------------------------------------"
				+ "--------------------------------");
		if (uwmailDB.size() == 0) {
			System.out.println("No conversations to show.");
		}
		ListADT<Conversation> inbox = uwmailDB.getInbox();
		Iterator<Conversation> inboxItr = inbox.iterator();
		String[] subjs = new String[uwmailDB.size()];
		String[] date = new String[uwmailDB.size()];
		int s = 0;
		while (inboxItr.hasNext()) {
			Conversation temp = inboxItr.next();
			date[s] = ProcessEmail.displayDate(temp.get(0));
			subjs[s] = temp.get(temp.size()).getSubject();
			s++;
		}
		for (int i = 0; i < uwmailDB.size(); i++) {
			System.out.print("[" + i + "]");
			System.out.print(subjs[i] + " ");
			System.out.println("(" + date[i] + ")");
		}
		while (!done) 
		{
			System.out.print("Enter option ([#]Open conversation, [T]rash, " + 
					"[Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) 
			{
				int val = 0;
				boolean isNum = true;
				try {
					val = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					isNum = false;
				}

				if(isNum) {
					if(val < 0) {
						System.out.println("The value can't be negative!");
						continue;
					} else if (val >= uwmailDB.size()) {
						System.out.println("Not a valid number!");
						continue;
					} else {
						displayConversation(val);
						continue;
					}

				}

				if(input.length() > 1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)) {
				case 'T':
				case 't':
					displayTrash();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

	/**
	 * Prints the list of conversations within the database's "trash" field
	 * that are currently available for viewing. Processes user commands.
	 */
	private static void displayTrash(){
		boolean done = false;
		ListADT<Conversation> trash = uwmailDB.getTrash();
		System.out.println("Trash:");
		System.out.println("-----------------------------------------------"
				+ "---------------------------------");
		if (trash.size() == 0) {
			System.out.println("No conversations to show.");
		}
		if (trash.size() != 0) {
			Iterator<Conversation> trashItr = trash.iterator();
			String[] subjs = new String[trash.size()];
			String[] date = new String[trash.size()];
			int s = 0;
			while (trashItr.hasNext()) {
				Conversation temp = trashItr.next();
				date[s] = ProcessEmail.displayDate(temp.get(0));
				subjs[s] = temp.get(temp.size()).getSubject();
				s++;
			}
			for (int i = 0; i < trash.size(); i++) {
				System.out.print("[" + i + "]");
				System.out.print(subjs[i] + " ");
				System.out.println("(" + date[i] + ")");
			}
		}
		//	}
		while (!done) 
		{
			System.out.print("Enter option ([I]nbox, [Q]uit): ");
			String input = stdin.nextLine();


			if (input.length() > 0) 
			{
				if(input.length()>1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)){
				case 'I':
				case 'i':
					displayInbox();
					break;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

	/**
	 * Given the index of a conversation in the inbox list, prints out that
	 * conversation's newest email to the user and processes input.
	 *
	 * @param (val) the index within the inbox list where the relevant
	 * Conversation is located
	 */
	private static void displayConversation(int val) {
		if ((val < 0) || (val >= uwmailDB.size())) {
			displayInbox();
		}
		ListADT<Conversation> inbox = uwmailDB.getInbox();
		Iterator<Conversation> inboxItr = inbox.iterator();
		Conversation toPrint = null;
		int pos = 0;
		//Searches the "inbox" list for the correct Conversation object
		while ((inboxItr.hasNext()) && (pos < val + 1)) {
			toPrint = inboxItr.next();
			pos++;
		}
		boolean done = false;
		int curr = toPrint.getCurrent();
		Email newest = toPrint.get(curr);
		System.out.println("SUBJECT:" + newest.getSubject());
		System.out.println("-------------------------------------"
				+ "-------------------------------------------");
		ProcessEmail.printEmail(newest, toPrint, curr);
		while (!done) 
		{
			System.out.print("Enter option ([N]ext email, [P]revious email, "
					+ "[J]Next conversation, [K]Previous\nconversation, [I]nbox"
					+ ", [#]Move to trash, [Q]uit): ");
			String input = stdin.nextLine();

			if (input.length() > 0) 
			{

				if(input.length()>1)
				{
					System.out.println("Invalid command!");
					continue;
				}

				switch(input.charAt(0)){
				case 'P':
				case 'p':
					toPrint.moveCurrentForward();
					displayConversation(val);
					break;
				case 'N':
				case 'n':
					toPrint.moveCurrentBack();
					displayConversation(val);
					break;
				case 'J':
				case 'j':
					val = val + 1;
					displayConversation(val);
					break;

				case 'K':
				case 'k':
					val = val - 1;
					displayConversation(val);
					break;

				case 'I':
				case 'i':
					displayInbox();
					return;

				case 'Q':
				case 'q':
					System.out.println("Quitting...");
					done = true;
					break;

				case '#':
					uwmailDB.deleteConversation(val);
					displayInbox();
					return;

				default:  
					System.out.println("Invalid command!");
					break;
				}
			} 
		} 
		System.exit(0);
	}

}

