/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EchoServerMultiThreaded  {

	public static ArrayList<PrintStream> outStreams = new ArrayList<PrintStream>(); 
	/**
	 * main method
	 * @param EchoServer port
	 * 
	 **/
	public static void main(String args[]){ 
		ServerSocket listenSocket;
		

		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
			System.out.println("Server ready123..."); 
			while (true) {
				Socket clientSocket = listenSocket.accept();
				PrintStream out = new PrintStream(clientSocket.getOutputStream());
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ClientThread ct = new ClientThread(clientSocket);
				ct.start();
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
	
	public static void sendAll(String message) {
		System.out.println("sendAll" + outStreams.size());
		for(PrintStream out : outStreams) {
			out.println(message);
		}
		
	}
}


