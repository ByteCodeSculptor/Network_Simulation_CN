package com.networksimulator.physical;

public class Connection {
    private EndDevice device1;
    private EndDevice device2;

    // Constructor to initialize the devices involved in the connection
    public Connection(EndDevice device1, EndDevice device2) {
        this.device1 = device1;
        this.device2 = device2;
    }

    // Method to simulate the transmission of data
    public void transmitData(byte[] data) {
        // Here, we simulate data being passed from one device to another
        // In this case, device1 sends data to device2 (or vice versa, since they are connected)
        device2.receiveData(data);  // Device2 receives the data sent by device1
    }
}
