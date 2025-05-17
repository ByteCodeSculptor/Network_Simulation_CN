package com.networksimulator.devices;

import java.util.HashMap;
import java.util.Map;

/**
 * Switch is a Layer 2 device that performs MAC address learning and forwarding.
 * It maintains a MAC table (using device names as MAC addresses for simulation)
 * to decide whether to forward a frame to a specific device or flood it if the
 * destination is unknown means to all devices.
 */
public class Switch extends Device {

    // MAC table: Maps device names (acting as MAC addresses) to the corresponding Device objects.
    private Map<String, Device> macTable;

    /**
     * Constructor for the Switch.
     *
     * @param name Unique identifier for the Switch.
     */
    public Switch(String name) {
        super(name);
        macTable = new HashMap<>();
    }

    /**
     * Adds a device to the switch. Establishes a bidirectional connection
     * between the switch and the device.
     *
     * @param device The device to connect.
     */
    public void addConnectedDevice(Device device) {
        if (!connectedDevices.contains(device)) {
            connectedDevices.add(device);
            device.connectTo(this);
            System.out.println("[Switch: " + name + "] Device " + device.getName() + " connected.");
        }
    }

    /**
     * The switch's sendData method simulates forwarding a frame. In this
     * simplified simulation, the method checks the MAC table for the
     * destination. If known, it forwards the data directly; otherwise, it
     * floods the frame.
     *
     * Note: In a complete implementation, frames would carry both source and
     * destination MAC addresses. Here, we use the receiver parameter to denote
     * the intended destination.
     *
     * @param receiver The intended destination device.
     * @param data The message to send.
     */
    @Override
    public void sendData(Device receiver, String data) {
        System.out.println("[Switch: " + name + "] Processing frame for " + receiver.getName());

        // Check if the destination is known in the MAC table.
        if (macTable.containsKey(receiver.getName())) {
            Device destDevice = macTable.get(receiver.getName());
            System.out.println("[Switch: " + name + "] Forwarding frame to " + destDevice.getName());
            destDevice.receiveData(data, this);
        } else {
            // Destination unknown; flood the frame to all connected devices except the sender.
            System.out.println("[Switch: " + name + "] Destination unknown. Flooding frame to all the devices of the network except the sender...");
            System.out.println("Destination device accepted the frame and remaining devices rejected the frame");
            floodFrame(data, this);
        }
    }

    /**
     * The switch's receiveData method simulates the process of learning the
     * source MAC address and then handling the frame.
     *
     * @param data The received data.
     * @param sender The device that sent the data.
     */
    @Override
    public void receiveData(String data, Device sender) {
        // Learn the sender's MAC address (here, using the sender's name).
        if (!macTable.containsKey(sender.getName())) {
            macTable.put(sender.getName(), sender);
            System.out.println("[Switch: " + name + "] Learned MAC of " + sender.getName());
        }

        // For simulation, simply print receipt of the data.
        System.out.println("[Switch: " + name + "] Received data from " + sender.getName() + ": " + data);
        // In a full implementation, the switch would examine the frame's destination MAC.
        // For demonstration, we'll flood the frame after learning.
        floodFrame(data, sender);
    }

    /**
     * Floods the frame to all connected devices except the sender.
     *
     * @param data The data to be flooded.
     * @param sender The device that originally sent the data.
     */
    public void floodFrame(String data, Device sender) {
        for (Device device : connectedDevices) {
            // Do not send the frame back to the original sender.
            if (!device.getName().equals(sender.getName())) {
                device.receiveData(data, this);
            }
        }
    }
    //need some updates from here if required

    /**
     * Prints the current MAC table to the terminal.
     */
    public void printMACTable(String receiver) {
        System.out.println("[Switch: " + "InterNetworkSwitch" + "] MAC Table:");
        if (macTable.isEmpty()) {
            System.out.println(receiver);//updated here
        } else {
            for (Map.Entry<String, Device> entry : macTable.entrySet()) {
                System.out.println("  MAC: " + entry.getKey() + " => Device: " + entry.getValue().getName());
            }
        }
    }

    //need some updates to here if required
    public void mainSendData(String receiver, String data) {
        System.out.println("[Switch: " + "InterConnectionSwitch" + "] Processing frame for " + receiver);

        // Check if the destination is known in the MAC table.
        if (macTable.containsKey(receiver)) {
            Device destDevice = macTable.get(receiver);
            System.out.println("[Switch: " + "InterConnectionSwitch" + "] Forwarding frame to " + destDevice.getName());
            destDevice.receiveData(data, this);
        } else {
            // Destination unknown; flood the frame to all connected devices except the sender.
            System.out.println("[Switch: " + "InterConnectionSwitch" + "] Destination unknown. Flooding frame to all the devices of the network except the sender...");
            System.out.println("Destination device accepted the frame and remaining devices rejected the frame");
            floodFrame(data, this);
        }
    }

    
}
