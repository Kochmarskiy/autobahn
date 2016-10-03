package com.company.autobahn.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class RoadPrice {
    private static RoadPrice roadPrice;
    private Map<Integer,Integer> prices = new HashMap<>();

    private RoadPrice(String path){
        try(Scanner sc = new Scanner(ClassLoader.getSystemResourceAsStream(path) )){
                while(sc.hasNextLine()){
                    String[] args = sc.nextLine().split("=");
                    int checkpoint = Integer.valueOf(args[0]);
                    int price = Integer.valueOf(args[1]);
                    prices.put(checkpoint,price);
                }
           if(prices.size()+1!=Configuration.amountOfCheckpoints)
               throw new RuntimeException("There is wrong configuration");

        }

    }

    public int getPriceToNext(int checkpointId){
        if(checkpointId<1 || checkpointId>=Configuration.amountOfCheckpoints)
            throw new IllegalArgumentException();
        return prices.get(checkpointId);
    }


    public static RoadPrice getRoadPrice(){
        if(roadPrice==null) {
            synchronized (RoadPrice.class) {
                if(roadPrice==null){
                    roadPrice = new DefaultRoadPrice(Configuration.path);
                }
            }
        }
        return roadPrice;
    }
    private static class DefaultRoadPrice extends RoadPrice{

        public DefaultRoadPrice(String path)  {
            super(path);
        }
    }
}
