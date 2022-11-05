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

		while (true) {

			System.out.print("> ");
			userInput = consoleInput.readLine();
			
			if(userInput == null || userInput.isEmpty()) {
				System.out.println("Ending program");
				break;
			}

			pwOut.println(userInput);

			StringBuilder sb = new StringBuilder();
			String strFromServer = null;
			Thread.sleep(200);

			while (brIn.ready()) {
				strFromServer = brIn.readLine();
				sb.append(strFromServer);
				sb.append('\n');
			}

			System.out.println(sb.toString());
			if (userInput.equalsIgnoreCase("EXIT"))
				break;
		}

		pwOut.close();
		brIn.close();
		serverConnection.close();
		System.out.println("End from user side!");
	}
}
