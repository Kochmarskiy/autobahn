package com.company.autobahn.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;


class HeartBeatingHandler implements Runnable {
    private Socket heartBeatingSocket;
    private int checkpointId;

    public HeartBeatingHandler(Socket heartBeatingSocket, int checkpointId) {
        this.heartBeatingSocket = heartBeatingSocket;
        this.checkpointId = checkpointId;
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
            closingDetected();

        } catch (SocketException e) {
            closingDetected();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    private void closingDetected(){
        Server server = Server.getServer();
        server.remove(checkpointId);
    }
}

