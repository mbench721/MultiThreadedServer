package server;

//Java implementation of  Server side 
//It contains two classes : Server and ClientHandler 
//Save file as Server.java 

import java.io.*; 
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.User;

import java.net.*; 

//Server class 
public class Server  
{ 
	static final int NUM_CLIENTS = 2;
	public static ExecutorService userExecutor = Executors.newFixedThreadPool(NUM_CLIENTS);
	// Vector to store active clients 
	static Vector<ClientHandler> ar = new Vector<>(); 


	public static void main(String[] args) throws IOException, ClassNotFoundException  
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(1234); 
		Socket s; 

		// running infinite loop for getting 
		// client request 
		while (true)  
		{ 
			// Accept the incoming request 
			s = ss.accept(); 

			System.out.println("New client request received : " + s); 

			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

			System.out.println("Creating a new handler for this client..."); 
			// Create a new handler object for handling this request. 
			ObjectInputStream inStream = new ObjectInputStream(s.getInputStream());
			User u = (User) inStream.readObject();			
			ClientHandler mtch = new ClientHandler(s,u.getName(), dis, dos); 

			// Create a new Thread with this object. 
			System.out.println("Adding this client to active client list"); 
			// add this client to active clients list 
			ar.add(mtch); 
			userExecutor.execute(mtch);
		} 
	} 
} 
