package _02_Chat_Application;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp implements ActionListener {

	public static void main(String[] args) {
		new ChatApp();
	}

	boolean shouldSend = false;

	public ChatApp() {
		int typeDecider = JOptionPane.showConfirmDialog(null,
				"If you want to host a server click yes. Otherwise click no", "Server or Client",
				JOptionPane.YES_OPTION);
		if (typeDecider == JOptionPane.YES_OPTION) {
			try {
				ServerSocket server = new ServerSocket(
						Integer.parseInt(JOptionPane.showInputDialog("What port do you want to host this server?")),
						100);
				server.setSoTimeout(200000000);
				JFrame frame = new JFrame("Server");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Your server is fully set up at the ip " + InetAddress.getLocalHost().getHostAddress()
						+ " and at port number " + server.getLocalPort() + ". Waiting for clinet to connect");
				panel.add(label);
				frame.add(panel);
				frame.pack();
				Socket connection = server.accept();
				
				System.out.println("YAY");
				DataOutputStream os = new DataOutputStream(connection.getOutputStream());
				DataInputStream is = new DataInputStream(connection.getInputStream());
				panel.remove(label);
				JTextField field = new JTextField(20);
				JButton button = new JButton("Send");
				JTextArea area = new JTextArea();
				area.setEditable(false);
				button.addActionListener(this);
				panel.add(field);
				panel.add(button);
				panel.add(area);
				frame.add(panel);
				frame.pack();
				frame.setVisible(true);
				while (connection.isConnected()) {
					if (shouldSend&!(field.getText().equals(""))) {
						os.writeUTF(field.getText());
						area.append("\n You said: "+field.getText());
						field.setText("");		
						shouldSend = false;
						frame.pack();
					}
					else {
						shouldSend = false;
					}
					
					if(is.available()>0) {
					area.append("\n Client said: "+is.readUTF());
					frame.pack();
					}
					}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to connect or lost connection");
				System.exit(0);
			}
			finally{
				JOptionPane.showMessageDialog(null, "Your connection was lost!");
				System.exit(0);
			}
		} else if (typeDecider == JOptionPane.NO_OPTION) {
			try {

				String ip = JOptionPane.showInputDialog("What IP are you trying to connect to");
				int port = Integer.parseInt(JOptionPane.showInputDialog("What is the port?"));
				Socket client = new Socket(ip, port);
				DataOutputStream osc = new DataOutputStream(client.getOutputStream());
				DataInputStream isc = new DataInputStream(client.getInputStream());
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel panel = new JPanel();
				JTextField field = new JTextField(20);
				JButton button = new JButton("Send");
				JTextArea area = new JTextArea();
				area.setEditable(false);
				button.addActionListener(this);
				panel.add(field);
				panel.add(button);
				panel.add(area);
				frame.add(panel);
				frame.pack();
				frame.setVisible(true);
				while (client.isConnected()) {
					if (shouldSend&!(field.getText().equals(""))) {
						osc.writeUTF(field.getText());
						area.append("\n You said: "+field.getText());
						field.setText("");		
						shouldSend = false;
						frame.pack();
					}
					else {
						shouldSend = false;
					}
					if(isc.available()>0) {
						area.append("\n Server said: "+isc.readUTF());
						frame.pack();
						}
					}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to connect or lost connection");
				System.exit(0);
			} // 192.168.86.244
			finally{
				JOptionPane.showMessageDialog(null, "Your connection was lost!");
				System.exit(0);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("earser");
		shouldSend = true;

	}
}
