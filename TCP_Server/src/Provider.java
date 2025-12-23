import java.io.*;
import java.net.*;

public class Provider{
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	ServerThread s;
	libraryUsers l;
	Provider(){}
	void run()
	{
		try{
			//1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			System.out.println("- server is running.");
			l = new libraryUsers();
			//2. Wait for connection
			while(true)
			{
				connection = providerSocket.accept();
				System.out.println("- a user has connected.");
				s = new ServerThread(connection, l);
				s.start();
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	public static void main(String args[])
	{
		Provider server = new Provider();
		while(true){
			server.run();
		}
	}
}
