package com.company.autobahn.client.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientRunnable implements  Runnable {
    private Socket heartBeatingSocket;
    private Socket socket;

    public ClientRunnable(Socket socket, Socket heartbatingSocket) {
        this.heartBeatingSocket = heartbatingSocket;
        this.socket = socket;

    }

    public void run() {
        try {
            startHeartBeating();
            delayUntilAllIsConnected();
            System.out.println("System is working");
            startExchange();
        }
        catch (SocketException e){
            System.out.println("Connection closed");
            System.exit(0);
        }
        catch(IOException e){
            e.printStackTrace();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    private void startHeartBeating(){
        HeartBeatingHandler heartBeatingHandler = new HeartBeatingHandler(heartBeatingSocket);
        Thread thread = new Thread(heartBeatingHandler);
        thread.start();
    }
    private void delayUntilAllIsConnected() throws InterruptedException, IOException {
        socket.getInputStream().read();
    }
    private void startExchange() throws IOException {
        Scanner scConsole = new Scanner(System.in);
        Scanner sc = new Scanner(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        while(true) {
            System.out.println("Enter condition(0 - start,1 - exit) and driver id separating by the space");
            int condition = scConsole.nextInt();
            int driverId = scConsole.nextInt();
            dataOutputStream.writeInt(condition);
            dataOutputStream.writeInt(driverId);
            System.out.println(sc.nextLine());

        }

    }


}