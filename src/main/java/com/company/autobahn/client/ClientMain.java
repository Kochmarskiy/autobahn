package com.company.autobahn.client;

import com.company.autobahn.client.network.ClientRunnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id");
        int checkpointID = sc.nextInt();
        Socket socket = new Socket("localhost",8080);

        System.out.println("Client is connected to Server");
        DataOutputStream dataOutputStream = new DataOutputStream( socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        dataOutputStream.writeInt(port);
        dataOutputStream.writeInt(checkpointID);
        int status = dataInputStream.readInt();
        if(status==1){
            System.out.println("There already is such checkpoint");
            System.exit(0);
        }
        if(status==2){
            System.out.println("Incorrect checkpoint's id");
            System.exit(0);
        }
        if(status==3){
            System.out.println("All client is connected");
            System.exit(0);
        }
        Socket heartBeatedSocket = serverSocket.accept();
        System.out.println("Server is connected to Client");
        new Thread(new ClientRunnable(socket,heartBeatedSocket)).start();
    }
}

