/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.UUID;

import javax.swing.*;



public class EchoClient {

	/**
	 *  main method
	 *  accepts a connection, receives a message from client then sends an echo to the client
	 **/
	public static void main(String[] args) throws IOException {
		
		/*PrintStream socOut = null;
		BufferedReader stdIn = null;
		BufferedReader socIn = null;*/

		if (args.length != 2) {
			System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
			System.exit(1);
		}

		try {
			// creation socket ==> connexion
			final Socket echoSocket = new Socket(args[0],new Integer(args[1]).intValue());

			JFrame frame = new JFrame("Chat");
			JTextField message = new JTextField(50);
			JTextArea chat = new JTextArea(16,50);
			JButton deconnexion = new JButton("Se déconnecter");
			chat.setEditable(false);

			final ListeningThread lt = new ListeningThread(echoSocket, chat);
			final WritingThread wt = new WritingThread(echoSocket, message);
			
			deconnexion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int input = JOptionPane.showConfirmDialog(frame, "Etes-vous sur de vouloir quitter le chat?", "Déconnexion", JOptionPane.YES_NO_OPTION);
					if(input == 0) {
						try {
							wt.setRunning(false);
							lt.setRunning(false);
							lt.stop();
							wt.stop();
							echoSocket.getInputStream().close();
							echoSocket.getOutputStream().close();
							//echoSocket.close();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			
			
			frame.getContentPane().add(message, BorderLayout.SOUTH);
			frame.getContentPane().add(new JScrollPane(chat), BorderLayout.CENTER);
			frame.getContentPane().add(deconnexion, BorderLayout.NORTH);
			
			frame.pack();
			frame.setVisible(true);
			lt.start();
			wt.start();

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + args[0]);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to:"+ args[0]);
			System.exit(1);
		}
	}
}


