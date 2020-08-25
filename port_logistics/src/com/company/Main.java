package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.print("Welcome to Port Logistics\n" +
                "Please type your command\n" +
                "You can type\n" +
                "receive_ship\n" +
                "unload\n" +
                "train_send\n" +
                "show " + "\n" +
                "Please type go in the End\n");
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        String commandLine = "";
        while (nextLine != null && !nextLine.equals("go")) {

            if (commandLine != ""){
                commandLine =  commandLine + "," + nextLine;
            }
            else{
                commandLine =  commandLine + nextLine;
            }

            nextLine = scanner.nextLine();

        }
        System.out.print("Your command is："+commandLine + "\n");

        Process process = new Process(commandLine);

        process.show();

    }
}
