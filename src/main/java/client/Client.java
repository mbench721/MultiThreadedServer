package client;

//Java implementation for multithreaded chat client 
//Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner;

import main.User;

public class Client  
{ 
	final static int ServerPort = 1234; 

	public static void main(String args[]) throws UnknownHostException, IOException  
	{ 
		final Scanner scn = new Scanner(System.in); 

		// getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 
		// InetAddress ip = InetAddress.getByName("pimbench");
		Socket s = new Socket(ip, ServerPort); 
		ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
		System.out.println("Enter UserName");
		String name = scn.nextLine();
		User user = new User(name);
		System.out.println("Object to be written = " + user);
		outputStream.writeObject(user);
		// establish the connection 


		// obtaining input and out streams 
		final DataInputStream dis = new DataInputStream(s.getInputStream()); 
		final DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 


		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable()  
		{ 
			public void run() { 
				while (true) { 

					// read the message to deliver. 
					String msg = scn.nextLine(); 

					try { 
						// write on the output stream 
						dos.writeUTF(msg); 
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		}); 

		// readMessage thread 
		Thread readMessage = new Thread(new Runnable()  
		{ 
			public void run() { 

				while (true) { 
					try { 
						// read the message sent to this client 
						String msg = dis.readUTF(); 
						System.out.println(msg); 
					} catch (IOException e) { 

						e.printStackTrace(); 
					} 
				} 
			} 
		}); 

		sendMessage.start(); 
		readMessage.start(); 
	} 
} 