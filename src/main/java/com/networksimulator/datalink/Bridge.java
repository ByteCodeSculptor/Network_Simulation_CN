package com.networksimulator.datalink;

import java.util.HashMap;
import java.util.Map;

import com.networksimulator.physical.EndDevice;

public class Bridge {

    private Map<String, Integer> macAddressTable; // MAC Address to Port Mapping
    private Map<Integer, EndDevice> segment1; // Devices in Segment 1
    private Map<Integer, EndDevice> segment2; // Devices in Segment 2

    public Bridge() {
        this.macAddressTable = new HashMap<>();
        this.segment1 = new HashMap<>();
        this.segment2 = new HashMap<>();
    }

    // Connect a device to the bridge in a specific segment
    public void connectDevice(EndDevice device, int segment) {
        if (segment == 1) {
            segment1.put(device.getPort(), device);
            System.out.println(device.getName() + " connected to Bridge (Segment 1) on Port " + device.getPort());
        } else if (segment == 2) {
            segment2.put(device.getPort(), device);
            System.out.println(device.getName() + " connected to Bridge (Segment 2) on Port " + device.getPort());
        } else {
            System.out.println("Invalid segment! Use 1 or 2.");
        }
    }

    // Process received data and forward it correctly
    public void receiveData(byte[] data, String sourceMac, String destMac, EndDevice device) {
        // Learn the source MAC address
        macAddressTable.put(sourceMac, device.getPort());

        // Check if the destination MAC is known
        if (macAddressTable.containsKey(destMac)) {
            int port = macAddressTable.get(destMac);

            // Determine which segment the destination is in
            if (segment1.containsKey(port)) {
                sendDataToSegment(data, port, 1);
            } else if (segment2.containsKey(port)) {
                sendDataToSegment(data, port, 2);
            } else {
                System.out.println("Bridge: Destination MAC found, but no matching port in either segment.");
            }
        } else {
            // If destination MAC is unknown, forward to the other segment
            forwardToOtherSegment(data, device.getPort());
        }
    }

    // Sends data to a specific port within a segment
    private void sendDataToSegment(byte[] data, int port, int segment) {
        if (segment == 1 && segment1.containsKey(port)) {
            System.out.println("Bridge: Forwarding data within Segment 1 to Port " + port);
            segment1.get(port).receiveData(data);
        } else if (segment == 2 && segment2.containsKey(port)) {
            System.out.println("Bridge: Forwarding data within Segment 2 to Port " + port);
            segment2.get(port).receiveData(data);
        }
    }

    // Forwards data to the other segment if the destination is unknown
    private void forwardToOtherSegment(byte[] data, int sourcePort) {
        System.out.println("Bridge: Forwarding data to the other segment...");
        if (segment1.containsKey(sourcePort)) {
            for (EndDevice device : segment2.values()) {
                device.receiveData(data);
            }
        } else if (segment2.containsKey(sourcePort)) {
            for (EndDevice device : segment1.values()) {
                device.receiveData(data);
            }
        }
    }

    // Display the MAC Address Table for debugging
    public void displayMacTable() {
        System.out.println("Bridge: MAC Address Table");
        for (Map.Entry<String, Integer> entry : macAddressTable.entrySet()) {
            System.out.println("MAC: " + entry.getKey() + " -> Port: " + entry.getValue());
        }
    }
}
