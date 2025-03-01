package com.networksimulator.datalink;

import com.networksimulator.physical.EndDevice;
import java.util.HashMap;
import java.util.Map;

public class Switch {
    private Map<String, Integer> macAddressTable;  // Stores MAC address to port mapping

    public Switch() {
        this.macAddressTable = new HashMap<>();
    }

    // Method to simulate receiving data
    public void receiveData(byte[] data, String sourceMac, String destMac, EndDevice device) {
        // Address learning: update MAC address table
        macAddressTable.put(sourceMac, device.getPort());  // Using the getPort() method here

        // Forward data based on destination MAC address
        if (macAddressTable.containsKey(destMac)) {
            int port = macAddressTable.get(destMac);
            sendDataToPort(data, port);
        } else {
            broadcastData(string);
        }
    }

    // Method to send data to a specific port
    private void sendDataToPort(byte[] data, int port) {
        System.out.println("Forwarding data to port " + port);
    }

    // Broadcast data to all connected devices
    private void broadcastData(byte[] data) {
        System.out.println("Broadcasting data to all ports.");
    }
}
