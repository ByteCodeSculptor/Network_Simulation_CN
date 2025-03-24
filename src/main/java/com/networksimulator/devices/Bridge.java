package com.networksimulator.devices;

import java.util.HashMap;
import java.util.Map;

/**
 * Bridge is a Layer 2 device that connects multiple LAN segments. It learns the
 * MAC addresses (here, simulated by device names) of connected devices and uses
 * this information to forward frames selectively. If the destination device is
 * unknown, the bridge floods the frame to all connected devices.
 */
public class Bridge extends Device {

    // Forwarding table: Maps a device's name (acting as its MAC address) to the Device object.
    private Map<String, Device> macTable;

    /**
     * Constructor for Bridge.
     *
     * @param name Unique identifier for the Bridge.
     */
    public Bridge(String name) {
        super(name);
        macTable = new HashMap<>();
    }

    /**
     * Adds a device to the Bridge's list of connected devices. This method
     * establishes a bidirectional connection between the Bridge and the device.
     *
     * @param device The device to be connected.
     */
    public void addConnectedDevice(Device device) {
        if (!connectedDevices.contains(device)) {
            connectedDevices.add(device);
            device.connectTo(this);
            System.out.println("[Bridge: " + name + "] Device " + device.getName() + " connected.");
        }
    }

    /**
     * Forwards data through the Bridge. If the destination device is known in
     * the MAC table, the frame is forwarded directly. Otherwise, the frame is
     * flooded to all connected devices.
     *
     * @param receiver The intended destination device.
     * @param data The message to forward.
     */
    @Override
    public void sendData(Device receiver, String data) {
        System.out.println("[Bridge: " + name + "] Processing frame for " + receiver.getName());

        // Check if the destination device is known (learned previously)
        if (macTable.containsKey(receiver.getName())) {
            Device destDevice = macTable.get(receiver.getName());
            System.out.println("[Bridge: " + name + "] Forwarding frame to " + destDevice.getName());
            destDevice.receiveData(data, this);
        } else {
            // If destination is unknown, flood the frame to all connected devices.
            System.out.println("[Bridge: " + name + "] Destination unknown, flooding frame...");
            
            System.out.println("Destination device accepted the frame and remaining devices rejected the frame");
            floodFrame(data, null);
        }
    }

    /**
     * Receives data on the Bridge. When data is received, the Bridge learns the
     * sender's MAC (device name) and logs the reception.
     *
     * @param data The received data.
     * @param sender The device that sent the data.
     */
    @Override
    public void receiveData(String data, Device sender) {
        // Learn the sender's MAC address if not already in the table.
        if (!macTable.containsKey(sender.getName())) {
            macTable.put(sender.getName(), sender);
            System.out.println("[Bridge: " + name + "] Learned MAC of " + sender.getName());
        }
        // Log the reception of data.
        System.out.println("[Bridge: " + name + "] Received data from " + sender.getName() + ": " + data);
    }

    /**
     * Floods the frame to all connected devices. Optionally, a device can be
     * excluded from receiving the flooded frame.
     *
     * @param data The data to be flooded.
     * @param excludeDevice Device to exclude from the flood (can be null).
     */
    public void floodFrame(String data, Device excludeDevice) {
        for (Device device : connectedDevices) {
            if (excludeDevice == null || !device.getName().equals(excludeDevice.getName())) {
                device.receiveData(data, this);
            }
        }
    }

}
