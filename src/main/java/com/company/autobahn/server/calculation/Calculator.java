package com.company.autobahn.server.calculation;

import com.company.autobahn.server.RoadPrice;
import com.company.autobahn.server.model.CheckpointRecord;

import java.util.Collection;

/**
 * Created by Кочмарский on 02.10.2016.
 */
public class Calculator {
    private static RoadPrice roadPrice = RoadPrice.getRoadPrice();

    /**
     * Returns a cost that contains the sum of all check point's prices.
     * @param checkpointRecords collection of check point records which belongs to certain driver.
     * @return Cost
     */
    public static int calculateCost(Collection<CheckpointRecord> checkpointRecords){
        int cost=0;
        for(CheckpointRecord checkpointRecord : checkpointRecords){
            cost+=roadPrice.getPriceToNext(checkpointRecord.getCheckpointId());
        }
        return cost;
    }
}
