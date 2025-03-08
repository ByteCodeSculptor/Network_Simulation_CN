package com.networksimulator.physical;

import java.util.ArrayList;
import java.util.List;

public class Hub {

    private List<EndDevice> connectedDevices;

    // Constructor to initialize the Hub
    public Hub() {
        connectedDevices = new ArrayList<>();
    }

    // Add an EndDevice to the Hub
    public void addDevice(EndDevice device) {
        connectedDevices.add(device);
        System.out.println(device.getName() + " connected to the Hub.");
    }

    // Broadcast data to all connected devices, except the sender
    public void broadcastData(byte[] data, EndDevice sender) {
        System.out.println("Hub: Broadcasting data from " + sender.getName());
        for (EndDevice device : connectedDevices) {
            if (!device.equals(sender)) { // Prevent sender from receiving its own data
                device.receiveData(data);
            }
        }
    }

    // Get list of connected devices (for debugging purposes)
    public List<EndDevice> getConnectedDevices() {
        return connectedDevices;
    }
}
