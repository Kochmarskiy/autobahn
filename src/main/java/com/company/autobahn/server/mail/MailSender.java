package com.company.autobahn.server.mail;

import com.company.autobahn.server.model.Bill;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * Created by Кочмарский on 02.10.2016.
 */
public interface MailSender {
    /**
     *
     * @param email driver's email
     * @param bill bill that will be sent to driver
     */
    public void sendMessage(String email,Bill bill);
}
