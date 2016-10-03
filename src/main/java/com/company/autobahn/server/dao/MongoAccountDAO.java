package com.company.autobahn.server.dao;

import com.company.autobahn.server.Configuration;
import com.company.autobahn.server.model.Account;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Кочмарский on 01.10.2016.
 */
public class MongoAccountDAO implements AccountDAO {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    public MongoAccountDAO(){
        init(Configuration.dbProperties);
    }

    @Override
    public Account getAccountOf(int driverId) {
        Document query = new Document();
        query.put("_id", driverId);
        FindIterable<Document> result = collection.find(query);
        Document doc = result.first();
        if(doc!=null) {
            String email = (String) doc.get("email");
            return new Account(driverId, email);
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        Document document = new Document();
        document.put("_id",account.getDriverId());
        document.put("email", account.getEmail());
        collection.insertOne(document);

    }

    @Override
    public void deleteAccount(Account account) {
        Document document = new Document();
        document.put("_id",account.getDriverId());
        document.put("email",account.getEmail());
        collection.deleteOne(document);
    }

    private void init(final Properties prop){
        mongoClient = Configuration.mongoClient;
        db = mongoClient.getDatabase(prop.getProperty("dbname"));
        collection = db.getCollection("account");
    }
}
