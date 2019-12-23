package com.nearbyapp.maysa.nearbyapp.datamodels;

public class ConnectionModel {

    private int type;

    public ConnectionModel(boolean isConnected) {
        this.isConnected = isConnected;
    }

    private boolean isConnected;

    public ConnectionModel(int type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public int getType() {
        return type;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
