
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.ConnectException;

public class Requester{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	LibraryLists list;
 	String message;
 	Scanner input;
	public Requester(){
		input = new Scanner(System.in);
	}
	void run()
	{
		try{
			//creating a socket to connect to the server
			requestSocket = new Socket("127.0.0.1", 2004);
			
			System.out.println("\n==Succesfully connected to server==\n");
			
			//get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//prompt for a successful connection
			transactionType("receive");
			
			//Communicating with the server
			transactionType("read");
			
			if(message.equals("3")) {
				transactionType("receive");
				for(int i=0;i < 6;i++) {
					transactionType("receive-send");
				}
			}
			else if(message.equals("0")) {
				
				do {
					transactionType("receive-send");
					if(message.equals("2")) {
						for(int i=0;i < 6;i++) {
							transactionType("receive-send");
						}
						break;
					}
					else if(message.equals("1")) {
						for(int i=0;i < 2;i++) {
							transactionType("receive-send");
							
						}
						break;
					}
					else {
						transactionType("receive");
					}
				}while(true);
			}
			transactionType("receive");
			
			do {
				transactionType("receive-send");
				if(message.equals("1")) {
					transactionType("receive-send");
					transactionType("receive");
				}
				else if(message.equals("2")) {
					transactionType("receive");
				}
				else if(message.equals("3")) {
					transactionType("receive-send");
					transactionType("receive");
				}
				else if(message.equals("4")) {
					transactionType("receive");
				}
				else if(message.equals("5")) {
					transactionType("receive-send");
					if(message.equals("yes")) {
						processRequest();
					}
					else {
						transactionType("receive");
					}
				}
				else if(message.equals("6")) {
					transactionType("receive-send");
					transactionType("receive");
				}
				else {
					transactionType("receive");
				}
				
			}while(true);
			
			
		} catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
			
		} catch (ConnectException noConnection) {
			System.err.println("No Servers online at the moment...");
			
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	
	public void processRequest() {
		transactionType("receive-send");
		if(message.equals("1")) {
			transactionType("receive-send");
		}
		else if(message.equals("yes")) {
			transactionType("receive-send");
		}
		else {
			transactionType("receive");
		}
	}
	
	public void transactionType(String option) {
		try {
			message = (String)in.readObject();
			
			if(option.equals("receive-send")) {
				System.out.println(message);
				message = input.nextLine();
				sendMessage(message);
				return;
			}
			else if(option.equals("receive")) {
				System.out.println(message);
				return;
			}
			else if(option.equals("receive-twice")) {
				System.out.println(message);
				message = (String)in.readObject();
				System.out.println(message);
				return;
			}
			else if(option.equals("read")) {
				return;
			}
			else {
				System.out.println("Unknown type found.");
				return;
			}
		}
		catch(ClassNotFoundException | IOException ioException){
			ioException.printStackTrace();
		}
	}

	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		Requester client = new Requester();
		client.run();
	}
}