package _01_Intro_To_Sockets.client;

import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class ClientGreeter {

   public static void main(String [] args) {
	   
	  //1. Create a String for the ip address of the server. 
	  // If you don't know how to find a computer's ip address, ask about ifconfig on linux/mac and ipconfig on windows.
      String ip = JOptionPane.showInputDialog("What is the IP you are connecting to?");
      //2. Create an integer for the server's port number
      int port = Integer.parseInt(JOptionPane.showInputDialog("What is the port you are connecting to?"));
      //3. Surround steps 4-9 in a try-catch block that catches any IOExceptions.
    try {
    	 //4. Create an object of the Socket class. When initializing the object, pass in the ip address and the port number
 Socket sock = new Socket(ip,port);
         //5. Create a DataOutputStream object. When initializing it, use the Socket object you created in step 4 to call the getOutputStream() method.
DataOutputStream a = new DataOutputStream(sock.getOutputStream());
         //6. Use the DataOutputStream object to send a message to the server using the writeUTF(String message) method
         a.writeUTF("TESTING");
         //7. Create a DataInputStream object. When initializing it, use the Server object you created in step 4 to call the getInputStream() method.
         DataInputStream e = new DataInputStream(sock.getInputStream());
         //8. Use the DataInputStream object to print a message from the server using the readUTF() method.
         System.out.println(e.readUTF());
         //9. Close the client's server object
         sock.close();
    }catch(Exception e) {
    	
    }
   }
}
