package com.company.autobahn.server.dao;

import com.company.autobahn.server.model.CheckpointRecord;

import java.util.Collection;

/**
 * Created by Кочмарский on 01.10.2016.
 */
public interface CheckpointRecordDAO {

    public Collection<CheckpointRecord> getCheckpointRecordsOf(int driverId);
    public void addCheckpointRecord(CheckpointRecord checkpointRecord);
    public void deleteCheckpointRecordsOf(int driverId);
}
