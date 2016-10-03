package com.company.autobahn.server;

import com.company.autobahn.server.network.Handler;
import com.company.autobahn.server.network.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {

    public static void main(String[] args) throws Exception {
        Configuration.config();
        ServerSocket serverSocket = new ServerSocket(8080);
        Server server = Server.getServer();
        while (server.size() < Configuration.amountOfCheckpoints) {
            Socket socket = serverSocket.accept();
            System.out.println("Client is connected to Server");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int port = dataInputStream.readInt();
            int checkpointId = dataInputStream.readInt();
            if (checkpointId > 0 && checkpointId <= Configuration.amountOfCheckpoints) {
                if (!server.contains(checkpointId)) {
                    System.out.println("checkpoint+ " + checkpointId);
                    dataOutputStream.writeInt(0);

                    Socket heartBeatingSocket = new Socket(socket.getInetAddress().getHostAddress(), port);
                    Handler handler = new Handler(socket, heartBeatingSocket, checkpointId,false);
                    server.addAndRun(handler);

                    System.out.println("Server is connected to Client");
                } else {
                    dataOutputStream.writeInt(1);
                    socket.close();
                }
            } else {
                dataOutputStream.writeInt(2);
                socket.close();
            }


        }

        while(true){
            Socket socket = serverSocket.accept();

            System.out.println("Client is connected to Server");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int port = dataInputStream.readInt();
            int checkpointId = dataInputStream.readInt();
            if(server.size()<Configuration.amountOfCheckpoints) {

                if (checkpointId > 0 && checkpointId <= Configuration.amountOfCheckpoints) {
                    if (!server.contains(checkpointId)) {
                        System.out.println("checkpoint+ " + checkpointId);
                        dataOutputStream.writeInt(0);

                        Socket heartBeatingSocket = new Socket(socket.getInetAddress().getHostAddress(), port);
                        Handler handler = new Handler(socket, heartBeatingSocket, checkpointId,true);
                        server.addAndRun(handler);

                        System.out.println("Server is connected to Client");
                    } else {
                        dataOutputStream.writeInt(1);
                        socket.close();
                    }
                } else {
                    dataOutputStream.writeInt(2);
                    socket.close();
                }


            }
            else{
                dataOutputStream.writeInt(3);
            }

        }
    }
}
