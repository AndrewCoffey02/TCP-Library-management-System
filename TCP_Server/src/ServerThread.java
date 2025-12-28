import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.OutputDeviceAssigned;

public class ServerThread extends Thread {

	private Socket socket;
	public LibraryLists lists;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message = "";
	private String UserID = "";
	private int answer = 0;
	public ServerThread(Socket s, LibraryLists l)
	{
		socket = s;
		lists = l;
	}
	
	public void run()
	{
		//3. get Input and Output streams
		try 
		{
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject("Welcome to library management system.\n");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try{
			
			// Register / Login before entering loop.
			if (lists.usersIsEmpty()) {
				out.writeObject("3");
				out.writeObject("It seems that there is no accounts on the database. "
								+ "Please register with the library:");
				registerUser();
			} 
			else {
				out.writeObject("0");
				do {
					out.writeObject("Would you like to login or register?:\n"
							+ "1. Login.\n"
							+ "2. Register\n"
							+ "\nenter number:");
					message = (String)in.readObject();
					
					if(message.equals("2")) {
						registerUser();
						break;
					}
					else if(message.equals("1")) {
						loginUser();
						break;
					}
					else {
						out.writeObject("Invalid response, try again.");
					}
				} while(true);
			}
			
			do{
				out.writeObject("\nPlease enter one of the options:\n"
								+ "1. Create book record\n"
								+ "2. view book list\n"
								+ "3. assign borrow request\n"
								+ "4. view your assigned book records (Librarian only)\n"
								+ "5. Update password\n\n"
								+ "enter number:");

				message = (String)in.readObject();
				
				if (message.equals("1")) {
					createBookRecord();
				}
				else if (message.equals("2")) {
					out.writeObject(lists.printBooks());
				}
				else if (message.equals("3")) {
					borrowRequest();
				}
				else if (message.equals("4")) {
					assignedBorrowRequests();
				}
				else if (message.equals("5")) {
					processRequest();
				}
				else if (message.equals("6")) {
					out.writeObject(lists.printUsers());
				}
				else {
					out.writeObject("Sorry, that response is invalid.");
				}
			}while(true);
		}
		catch(ClassNotFoundException | IOException ioException){
			return;
		}
	}
	
	public void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println(msg);
			// System.out.println(socket.getInetAddress()+"/"+socket.getPort()+": "+msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public void registerUser() {
		try {
			out.writeObject("Enter a student ID number: ");
			message = (String)in.readObject();
			String StudentID = message;
			
			out.writeObject("Enter email: ");
			message = (String)in.readObject();
			String Email = message;
			
			out.writeObject("Enter name: ");
			message = (String)in.readObject();
			String Name = message;
			
			out.writeObject("Enter 1 for Student or enter 2 for Librarian: ");
			message = (String)in.readObject();
			String Occupation = message;
			
			out.writeObject("Enter department: ");
			message = (String)in.readObject();
			String Dept = message;
			
			out.writeObject("Enter password: ");
			message = (String)in.readObject();
			String Password = message;
			
			message = lists.addUser(Name, StudentID, Email, Password, Dept, Occupation);
			
			UserID = lists.returnUserID(Email, Password); // Use ID for other functions
			out.writeObject(message);
			
		} catch (ClassNotFoundException | IOException classnot) {
			return;
		}
	}
	
	public void loginUser() {
		try {
			out.writeObject("Enter email: ");
			message = (String)in.readObject();
			String Email = message;
			
			out.writeObject("Enter password: ");
			message = (String)in.readObject();
			String Password = message;
			
			if(lists.successfulLogin(Email, Password)) {
				out.writeObject("Successful login.");
				UserID = lists.returnUserID(Email, Password);  // Use ID for other functions
			}
			else {
				out.writeObject("Unsuccessful login, please try again.");
			}
			
		}
		catch (ClassNotFoundException | IOException classnot) {
			return;
		}
	}
	public void createBookRecord() {
		try {
			out.writeObject("Enter book name: ");
			message = (String)in.readObject();
			String name = message;
			
			lists.addBook(name, UserID);
			out.writeObject("book added!");
		}
		catch (ClassNotFoundException | IOException classnot) {
			return;
		}
	}
	
	// Input correct book ID to assign to librarian.
	public void borrowRequest() {
		try {
			out.writeObject("Enter Record ID: ");
			message = (String)in.readObject();
			String ID = message;
			
			out.writeObject(lists.assignBorrowRequest(ID));
		}
		catch (ClassNotFoundException | IOException classnot) {
			return;
		}
	}
	
	//print out any assigned requests if the user is a librarian.
	public void assignedBorrowRequests() {
		try {
			out.writeObject(lists.printRequests(UserID));
		}
		catch (IOException classnot) {
			return;
		}
	}
	public void processRequest() {
		try {
			out.writeObject("Would you like to process a book?(yes/no): ");
			message = (String)in.readObject();
			
			if(message.equals("yes")) {
				out.writeObject("Enter Record ID: ");
				message = (String)in.readObject();
				String ID = message;
				
				String res = lists.checkProcess(message);
				if(res.equals("1")) {
					out.writeObject("1");
					out.writeObject("Would you like to verify the book borrow?(yes/no): ");
					message = (String)in.readObject();
				}
				else if(res.equals("2")) {
					out.writeObject("2");
					out.writeObject("Would you like to verify the return?(yes/no): ");
					message = (String)in.readObject();
				}
				else {
					out.writeObject("Sorry, this is an invalid record ID. Please start again.");
					return;
				}
				if(message.equals("yes")) {
					lists.authoriseProcess(ID);
					out.writeObject("Book has been processed.");
				}
				else {
					out.writeObject("returned to home screen.");
				}
			}
			else if(message.equals("no")) {
				out.writeObject("Ok.");
			}
			else {
				out.writeObject("Sorry that is an invalid response...");
			}
		}
		catch (ClassNotFoundException | IOException classnot) {
			return;
		}
	}
}
