package com.company.autobahn.server.network;

import com.company.autobahn.server.Configuration;
import com.company.autobahn.server.calculation.Calculator;
import com.company.autobahn.server.dao.AccountDAO;
import com.company.autobahn.server.dao.CheckpointRecordDAO;
import com.company.autobahn.server.dao.MongoAccountDAO;
import com.company.autobahn.server.dao.MongoCheckpointRecordDAO;
import com.company.autobahn.server.mail.GmailMailSender;
import com.company.autobahn.server.mail.MailSender;
import com.company.autobahn.server.model.Account;
import com.company.autobahn.server.model.Bill;
import com.company.autobahn.server.model.CheckpointRecord;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;


 public class Handler implements Runnable {
     /**
      * Main socket that is used to communicate with check point by  sending and receiving commands.
      */
   private Socket socket;
     /**
        Check point with whom server communicates.
      */
   private int checkpointId;
     /*
        Supported socket that is in control of connection availability
      */
    private Socket heartBeatingSocket;
    private Server server = Server.getServer();
    private AccountDAO accountDAO = new MongoAccountDAO();
    private CheckpointRecordDAO checkpointRecordDAO = new MongoCheckpointRecordDAO();
     /**
      * Notices if connection is new or renovated.
      */
    private boolean renovated;
    private MailSender mailSender = new GmailMailSender();

    public Handler(Socket socket, Socket heartBeatingSocket, int checkointId,boolean renovated){
         this.checkpointId = checkointId;
         this.socket = socket;
         this.heartBeatingSocket = heartBeatingSocket;
        this.renovated = renovated;

    }
    public void run() {

       try {
          startHeartBeating();

          delayUntilAllIsConnected();
          startExchange();
       } catch (InterruptedException e) {
          e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public boolean getStatus(){
      return !Server.getServer().contains(checkpointId);
    }
   public int getCheckpointId(){
      return this.checkpointId;
   }
   private void startHeartBeating(){
      HeartBeatingHandler heartBeatingHandler = new HeartBeatingHandler(heartBeatingSocket,checkpointId);
      Thread thread = new Thread(heartBeatingHandler);
      thread.start();
   }

     /**
      * Make thread be asleep until all check points are connected.
      * @throws InterruptedException
      * @throws IOException
      */
   private void delayUntilAllIsConnected() throws InterruptedException, IOException {
      while (!renovated && server.size() < Configuration.amountOfCheckpoints)
            Thread.sleep(1000);
       socket.getOutputStream().write(11);
     }

     /**
      * Receives two values from check point:
      * Condition that notices either driver is continue driving or leaving toll zone.
      * Driver's id
      * @throws IOException
      */
   private void startExchange() throws IOException {
    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
   try(PrintWriter pw = new PrintWriter(socket.getOutputStream(),true)) {
       while (server.contains(checkpointId)) {
           int condition = dataInputStream.readInt();
           int driverId = dataInputStream.readInt();
           if(condition!=0 && condition!=1) pw.println("Incorrect condition");
           if (condition == 0) {
               Account account = accountDAO.getAccountOf(driverId);
               System.out.println(account);
               if (account!= null) {
                checkpointRecordDAO.addCheckpointRecord(new CheckpointRecord(driverId,checkpointId,new Date()));
                   pw.println("Record is added");

               } else {
                   pw.println("Account is not found");
               }
           }
           if (condition == 1) {
               Date finalDate = new Date();
               Collection<CheckpointRecord> checkpointRecords = checkpointRecordDAO.getCheckpointRecordsOf(driverId);
               int cost = Calculator.calculateCost(checkpointRecords);
               checkpointRecordDAO.deleteCheckpointRecordsOf(driverId);
               CheckpointRecord firstCheckpointRecord = checkpointRecords.iterator().next();
               Bill bill = new Bill(cost,firstCheckpointRecord.getTimestamp(),finalDate,firstCheckpointRecord.getCheckpointId(),checkpointId);
               mailSender.sendMessage(accountDAO.getAccountOf(driverId).getEmail(),bill);
               pw.println();
           }
       }
   }

   }
}
