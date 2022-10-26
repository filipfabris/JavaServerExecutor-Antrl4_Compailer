package hr.tel.fer.lab1.client;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket serverConnection = new Socket("localhost", 4444);
		PrintWriter pwOut = new PrintWriter(serverConnection.getOutputStream(), true);
		BufferedReader brIn = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));

		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		System.out.println("Start from User side");
		System.out.println("echo:" + brIn.readLine());
		
		while ((userInput = consoleInput.readLine()) != null) {
			pwOut.println(userInput);
			
			StringBuilder sb = new StringBuilder();
			String strFromServer = null;
			while((strFromServer = brIn.readLine()) != null) {
				sb.append(strFromServer);
				sb.append('\n');
			}
			
			System.out.println("echo: " + sb.toString());
			
			if (userInput.equalsIgnoreCase("EXIT"))
				break;
		}
		pwOut.close();
		brIn.close();
		serverConnection.close();
		System.out.println("End from user side!");
	}
}
