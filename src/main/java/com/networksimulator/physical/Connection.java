package com.networksimulator.physical;

public class Connection {
    private EndDevice device1;
    private EndDevice device2;

    public Connection(EndDevice device1, EndDevice device2) {
        this.device1 = device1;
        this.device2 = device2;
    }

    // Method to transmit data between two devices
    public void transmitData(byte[] data) {
        if (device2 != null) {
            device2.receiveData(data);
        }
    }
}
