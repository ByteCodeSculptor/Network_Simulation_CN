package com.networksimulator.datalink;

import java.util.HashMap;
import java.util.Map;

import com.networksimulator.physical.EndDevice;

public class Switch {

    private Map<String, Integer> macAddressTable; // MAC Address to Port Mapping
    private Map<Integer, EndDevice> portToDeviceMap; // Port to Device Mapping

    public Switch() {
        this.macAddressTable = new HashMap<>();
        this.portToDeviceMap = new HashMap<>();
    }

    // Connect an EndDevice to the Switch on a specific port
    public void connectDevice(EndDevice device) {
        if (!portToDeviceMap.containsKey(device.getPort())) {
            portToDeviceMap.put(device.getPort(), device);
            System.out.println(device.getName() + " connected to Switch on Port " + device.getPort());
        } else {
            System.out.println("Port " + device.getPort() + " is already occupied!");
        }
    }

    // Simulates receiving and forwarding data
    public void receiveData(byte[] data, String sourceMac, String destMac, EndDevice device) {
        // Learn the source MAC address and associate it with the correct port
        macAddressTable.put(sourceMac, device.getPort());

        // Check if the destination MAC is known
        if (macAddressTable.containsKey(destMac)) {
            int port = macAddressTable.get(destMac);
            if (portToDeviceMap.containsKey(port)) {
                sendDataToPort(data, port);
            } else {
                System.out.println("Switch: Destination MAC found but port is not connected.");
            }
        } else {
            // If the destination MAC is unknown, broadcast the data
            broadcastData(data, device.getPort());
        }
    }

    // Sends data to a specific port (Unicast)
    private void sendDataToPort(byte[] data, int port) {
        if (portToDeviceMap.containsKey(port)) {
            System.out.println("Switch: Forwarding data to Port " + port);
            portToDeviceMap.get(port).receiveData(data);
        }
    }

    // Broadcasts data to all connected devices except the sender (Flooding)
    private void broadcastData(byte[] data, int sourcePort) {
        System.out.println("Switch: Broadcasting data (Flooding) from Port " + sourcePort);
        for (Map.Entry<Integer, EndDevice> entry : portToDeviceMap.entrySet()) {
            if (entry.getKey() != sourcePort) { // Avoid sending data back to the sender
                entry.getValue().receiveData(data);
            }
        }
    }

    // Display the MAC Address Table for debugging
    public void displayMacTable() {
        System.out.println("Switch: MAC Address Table");
        for (Map.Entry<String, Integer> entry : macAddressTable.entrySet()) {
            System.out.println("MAC: " + entry.getKey() + " -> Port: " + entry.getValue());
        }
    }
}
