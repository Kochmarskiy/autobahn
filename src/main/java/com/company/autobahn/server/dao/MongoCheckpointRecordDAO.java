package com.company.autobahn.server.dao;

import com.company.autobahn.server.Configuration;
import com.company.autobahn.server.model.CheckpointRecord;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;


public class MongoCheckpointRecordDAO implements CheckpointRecordDAO {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    public  MongoCheckpointRecordDAO(){
        init(Configuration.dbProperties);
    }

    @Override
    public Collection<CheckpointRecord> getCheckpointRecordsOf(int driverId) {
        Document document = new Document();
        document.put("driverId",driverId);
        FindIterable<Document> result = collection.find(document).sort(new Document("timestamp",1));
        Collection<CheckpointRecord> checkpointRecords = new ArrayList<>();
        for(Document doc : result){
           int checkpointId = (Integer)doc.get("checkpointId");
            Date timestamp = (Date)doc.get("timestamp");
            checkpointRecords.add(new CheckpointRecord(driverId,checkpointId,timestamp));
        }
        return checkpointRecords;


    }

    @Override
    public void addCheckpointRecord(CheckpointRecord checkpointRecord) {
        Document document = new Document();
        document.put("driverId",checkpointRecord.getDriverId());
        document.put("checkpointId",checkpointRecord.getCheckpointId());
        document.put("timestamp",checkpointRecord.getTimestamp());
        collection.insertOne(document);
    }

    @Override
    public void deleteCheckpointRecordsOf(int driverId) {
        Document document = new Document();
        document.put("driverId",driverId);
        collection.deleteMany(document);

    }

    private void init(final Properties prop){
        mongoClient = Configuration.mongoClient;
        db = mongoClient.getDatabase(prop.getProperty("dbname"));
        collection = db.getCollection("checkpointrecord");
    }
}
