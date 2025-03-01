package com.networksimulator.datalink;

import com.networksimulator.physical.EndDevice;
import java.util.HashMap;
import java.util.Map;

public class Bridge {
    private Map<String, Integer> macAddressTable;  // Stores MAC address to port mapping

    public Bridge() {
        this.macAddressTable = new HashMap<>();
    }

    // Method to simulate receiving data and learning the MAC address
    public void receiveData(byte[] data, String sourceMac, String destMac, EndDevice device) {
        // Address learning: update MAC address table
        macAddressTable.put(sourceMac, device.getPort());  // Using the getPort() method here

        // Forward data based on destination MAC address
        if (macAddressTable.containsKey(destMac)) {
            int port = macAddressTable.get(destMac);
            sendDataToPort(data, port);
        } else {
            forwardToOtherSegment(data);
        }
    }

    // Method to send data to a specific port (in case of destination found)
    private void sendDataToPort(byte[] data, int port) {
        System.out.println("Bridge: Forwarding data to port " + port);
    }

    // Forward data to another network segment
    private void forwardToOtherSegment(byte[] data) {
        System.out.println("Bridge: Forwarding data to other network segment.");
    }
}
