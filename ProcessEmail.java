///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  UWmail.java
// File:             ProcessEmail.java
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

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * This is a helper class used for initializing the Email objects that are to
 * be placed in the inbox. It is also used to help display Email objects in 
 * the correct format when a conversation is viewed, or when the inbox/trash
 * are being viewed.
 *
 * <p>Bugs: none known
 *
 * @author Andy Zietlow
 */
public class ProcessEmail {

	/**
	 * This method is used to read the initialization files ending in .txt
	 * for valid input that would correspond to the information held within
	 * an Email object. If it finds valid input, it stores the information
	 * in variables that are then used in constructing a new Email object.
	 *
	 * @param (mailFile) Not actually a file. Represents the stream of input
	 * created by reading the files contained within the zip file.
	 * @return a new Email object for each valid InputStream
	 */
	public static Email createEmail(InputStream mailFile)
			throws MalformedEmailException {
		final int DATE_START = 6;//The index of a line where Date is to be read
		final int NUM_REQUIRED_FIELDS = 5;//Number of least possible fields for
		//a valid Email object

		//Each of the following variables are filled in as the InputStream
		//is read line-by-line
		Date date = null;//Exact time an Email was created
		String messageID = null;//Unique ID associated with an Email 
		String subject = null;//Topic the message body of Email refers to
		String from = null;//Email address that Email was sent from
		String to = null;//Email address that Email was sent to
		String inReplyTo = null;//MessageID of Email this Email replies to
		ListADT<String> body = new DoublyLinkedList<String>();//Message body
		ListADT<String> references = new DoublyLinkedList<String>();//The list
		//of MessageIDs of Emails this Email may be connected to

		boolean[] hasFields = new boolean[NUM_REQUIRED_FIELDS];//Used to verify 
		//that the minimum required fields have been filled

		Scanner sc = null;//Used for reading InputStream
		Email result = null;//The returned Email object
		boolean malformed = false;//Used for exception throwing
		sc = new Scanner(mailFile);
		while (sc.hasNext()) {
			String currLine = sc.nextLine();
			String[] tokens = currLine.split("[:]");
			String param = "";
			ListADT<String> listParam = new DoublyLinkedList<String>();
			int i;
			switch (tokens[0]) {
			case "In-Reply-To": 
				inReplyTo = tokens[1];
				if ((inReplyTo == null) || (inReplyTo.length() == 0)) {
					malformed = true;
				}
				break;

			case "References": 
				for (i = 1; i < tokens.length; i++) {
					param += tokens[i];
				}
				String[] refs = param.split("[,]");
				for (i = 0; i < refs.length; i++) {
					listParam.add(refs[i]);
				}
				references = listParam;
				if ((references == null) || (references.size() == 0)) {
					malformed = true;
				}
				break;

			case "Date": 
				hasFields[0] = true;
				param = currLine.substring(DATE_START);
				DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy "
						+ "HH:mm:ss Z");
				try {
					Date parsedDate = df.parse(param);
					date = parsedDate;
					if ((date == null) || (date.toString().length() == 0)) {
						malformed = true;
					}
				} 
				catch (ParseException e) {
					malformed = true;
				}
				break;

			case "Message-ID": 
				hasFields[1] = true;
				messageID = tokens[1];
				if ((messageID == null) || (messageID.length() == 0)) {
					malformed = true;
				}
				break;

			case "Subject": 
				hasFields[2] = true;
				if (tokens.length == 3) {
					subject = tokens[2];
				}
				else if (tokens.length == 2) {
					subject = tokens[1];
				}
				if ((subject == null) || (subject.length() == 0)) {
					malformed = true;
				}
				break;

			case "From": 
				hasFields[3] = true;
				from = tokens[1];
				if ((from == null) || (from.length() == 0)) {
					malformed = true;
				}
				break;

			case "To": 
				hasFields[4] = true;
				to = tokens[1];
				if ((to == null) || (to.length() == 0)) {
					malformed = true;
				}
				break;

			default:
				body.add(currLine);
				if ((body == null) || (body.size() == 0)) {
					malformed = true;
				}
				break;
				//I know the check for == null doesn't need to happen, this is
				//a planned improvement
			}
			if (malformed) {
				sc.close();
				throw new MalformedEmailException();
			}
		}
		sc.close();
		//Email creation using variables that have been set
		boolean isValid = true;
		for (int i = 0; i < hasFields.length; i++) {
			if (hasFields[i] == false) isValid = false;
		}
		if (isValid) {
			if (inReplyTo != null) {
				if (references != null) {
					result = new Email(date, messageID, subject, from, to, body,
							inReplyTo, references);
				}
				else throw new MalformedEmailException();
			}
			else {
				result = new Email(date, messageID, subject, from, to, body);
			}
		}
		else throw new MalformedEmailException();
		return result;
	}

	/**
	 *Determines an Email object's date in the correct format for inbox, trash
	 *and conversation viewing display
	 *
	 * @param (email) the Email object to determine the date string of
	 * @return the given Email object's date, displayed as "MMM dd"
	 */
	public static String displayDate(Email email) {
		String date = "";
		String month = "";
		String result;
		String dateString = email.getDate();
		Scanner sc = new Scanner(dateString);
		sc.next();
		if (sc.hasNext()) month = sc.next();
		if (sc.hasNext()) date = sc.next();
		result = month + " " + date;
		sc.close();
		return result;
	}

	/**
	 * Displays an Email object's relevant fields in conversation view format
	 * given the context of that email's respective Converstaion object
	 *
	 * @param (email) the Email object to be displayed
	 * @param (conv) the Conversation object to which (email) belongs
	 * @param (curr) the index of (conv) at which (email) belongs
	 */
	public static void printEmail(Email email, Conversation conv, int curr) {
		boolean isReply = false;
		if (email.getInReplyTo() != null) isReply = true;
		if (isReply) {
			for (int i = email.getReferences().size(); i > 0; i--) {
				int tempCurr = curr + i;
				Email tempEmail = conv.get(tempCurr);
				System.out.print(tempEmail.getFrom() + " | ");
				ListADT<String> body = tempEmail.getBody();
				String bodyStr = body.get(0);
				System.out.print(bodyStr + " | ");
				System.out.println(displayDate(tempEmail));
				System.out.println("-------------------------------------"
						+ "-------------------------------------------");
			}
		}
		System.out.println("From:" + email.getFrom());
		System.out.println("To:" + email.getTo());
		System.out.println(displayDate(email));
		System.out.println("");
		for(Object o : email.getBody()){
			System.out.println(o);
		}
		System.out.println("-------------------------------------"
				+ "-------------------------------------------");
		if (curr > 0) {
			for (int i = curr - 1; i > -1; i--) {
				Email tempEmail = conv.get(i);
				System.out.print(tempEmail.getFrom() + " | ");
				ListADT<String> body = tempEmail.getBody();
				String bodyStr = body.get(0);
				System.out.print(bodyStr + " | ");
				System.out.println(displayDate(tempEmail));
				System.out.println("-------------------------------------"
						+ "-------------------------------------------");
			}
		}
	}

}
