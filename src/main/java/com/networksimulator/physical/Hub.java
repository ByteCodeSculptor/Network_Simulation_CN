package com.networksimulator.physical;

import java.util.ArrayList;
import java.util.List;

public class Hub {
    private List<EndDevice> connectedDevices;

    public Hub() {
        connectedDevices = new ArrayList<>();
    }

    // Method to add a device to the hub
    public void addDevice(EndDevice device) {
        connectedDevices.add(device);
    }

    // Method to broadcast data to all connected devices
    public void broadcastData(byte[] data) {
        for (EndDevice device : connectedDevices) {
            device.receiveData(data);
        }
    }
}
