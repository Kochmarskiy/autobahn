package com.company.autobahn.server.mail;

import com.company.autobahn.server.Configuration;
import com.company.autobahn.server.model.Bill;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class GmailMailSender implements MailSender{

    private static Properties mailServerProperties;

    public GmailMailSender(){
        init();
    }

    /**
     *
     * Return takes place immediately.
     * @param email driver's email
     * @param bill bill that will be sent to driver
     */
       public void sendMessage(final String email,final Bill bill) {
           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       String subject = "Bill";
                       String emailBody = generateEmailBody(bill);
                       Session session = Session.getDefaultInstance(mailServerProperties, null);
                       MimeMessage mimeMessage = generateMimeMessage(session, email, emailBody, subject);

                       Transport transport = session.getTransport("smtp");
                       transport.connect("smtp.gmail.com", (String)Configuration.mailProperties.get("login"), (String)Configuration.mailProperties.get("password"));
                       transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                   } catch (NoSuchProviderException e) {
                       e.printStackTrace();
                   } catch (MessagingException e) {
                       e.printStackTrace();
                   }
               }
           });
           thread.start();


       }

    private String generateEmailBody(Bill bill){
        String emailBody =
                    "Hello, "+"you were driving from " +
                    "checkpoint "+bill.getFirstChackpointId()+ " to checkpoint "+bill.getLastCheckpointId()+" " +
                    "and total cost is "+bill.getCost()+" " +
                    "start was on "+bill.getStartDate()+" " +
                            "finish was on "+bill.getFinalDate();

        return emailBody;
    }
    private MimeMessage generateMimeMessage(Session session,String email,String emailBody,String subject) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(Configuration.mailProperties.getProperty("email")));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(emailBody, "text/html");
        return mimeMessage;
    }
    public void init(){
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
    }

}
