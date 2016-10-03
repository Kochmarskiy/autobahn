package com.company.autobahn.server;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public final class Configuration {
    public static final int amountOfCheckpoints= 3;
    public static final String path = "prices.txt";
    public static final Properties mailProperties = new Properties();
    static {
        try{
            mailProperties.load(ClassLoader.getSystemResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final Properties dbProperties = new Properties();
    static {
        try{
            dbProperties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private static ServerAddress serverAddress =    new ServerAddress(dbProperties.getProperty("host"),Integer.valueOf(dbProperties.getProperty("port")));
    public static final MongoClient mongoClient = new MongoClient(serverAddress, new ArrayList<MongoCredential>(){{
        add(MongoCredential.createCredential(
                        dbProperties.getProperty("login"),
                        dbProperties.getProperty("dbname"),
                        dbProperties.getProperty("password").toCharArray()
                )
        );
      }});
    public static void config(){

    }

}
