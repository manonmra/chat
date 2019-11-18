package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class ListeningThread extends Thread {

	Socket client;
	JTextArea jta;
	boolean running = true;
	
	ListeningThread(Socket s, JTextArea jta){
		client = s;
		this.jta = jta;
	}
	

	public void setRunning(boolean b) {
		running = b;
	}

	
	public void run() {
		BufferedReader socIn = null;
		try {
			socIn = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(running) {
			String line = null;
			try {
				line = socIn.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("echo : " + line);
			jta.append(line);
			jta.append("\r\n");
		}
	}
}
