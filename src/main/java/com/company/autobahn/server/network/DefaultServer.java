package com.company.autobahn.server.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


class DefaultServer extends Server {
    private static Map<Integer,Handler> handlers = new ConcurrentHashMap<>();


    @Override
    public void addAndRun(Handler handler) {
        Thread thread = new Thread(handler);
        handlers.put(handler.getCheckpointId(),handler);
        thread.start();
    }

    @Override
    public void remove(int checkointID) {
        handlers.remove(checkointID);
    }

    @Override
    public int size() {
        return handlers.size();
    }

    @Override
    public boolean contains(int checkpointId)
    {
        return handlers.containsKey(checkpointId);
    }
}
