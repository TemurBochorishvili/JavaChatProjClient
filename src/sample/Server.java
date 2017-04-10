package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(7777)){

            while (true){

                Socket socket = serverSocket.accept();
                Thread thread;

                thread = new Thread(()-> {

                    try{

                        Scanner scanner = new Scanner(socket.getInputStream());
                        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                        String msg;

                        while((msg = scanner.nextLine()) != null)

                            pw.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}