package com.company.autobahn.client.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;


class HeartBeatingHandler implements Runnable {
    private Socket heartBeatingSocket;

    public HeartBeatingHandler(Socket heartBeatingSocket) {
        this.heartBeatingSocket = heartBeatingSocket;
    }

    public void run() {
        try {
            InputStream inputStream = heartBeatingSocket.getInputStream();
            OutputStream outputStream = heartBeatingSocket.getOutputStream();
            outputStream.write(0);
            while (inputStream.read() != -1) {
                outputStream.write(0);
                Thread.sleep(1000);
            }


        } catch (SocketException e) {
           System.out.print("Server does not responde");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

