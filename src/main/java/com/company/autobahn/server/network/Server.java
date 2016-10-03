package com.company.autobahn.server.network;


public abstract class Server {
private static Server server;
    public static Server getServer(){
        if(server==null){
            synchronized (Server.class){
                if(server==null){
                    server = new DefaultServer();
                }
            }
        }

        return server;
    }
    public abstract void addAndRun(Handler handler);
    public abstract void remove(int checkointId);
    public abstract int size();
    public abstract boolean contains(int checkpointId);




}
