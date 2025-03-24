package com.networksimulator.devices;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a generic network device. All devices (End
 * Devices, Hubs, Switches, Routers, etc.) extend from this class.
 */
public abstract class Device { //there are both abstract and concrete methods in this calss

    protected String name; // Unique name of the device
    protected List<Device> connectedDevices; // List of directly connected devices

    /**
     * Constructor to initialize the device with a name.
     *
     * @param name Unique identifier for the device.
     */
    public Device(String name) {//this is a constructor
        this.name = name;
        this.connectedDevices = new ArrayList<>();
    }

    /**
     * Returns the name of the device.
     *
     * @return The device name.
     */
    public String getName() {
        return name;
    }

    /**
     * Connects this device to another device. Ensures a bidirectional
     * connection.
     *
     * @param device The device to connect to.
     */
    public void connectTo(Device device) {
        if (device == null) {
            System.out.println("[ERROR] Cannot connect to a null device.");
            return;
        }

        if (!connectedDevices.contains(device)) {
            connectedDevices.add(device);
            device.connectTo(this); // Ensures bidirectional connection and call the same function connectTo() again
            System.out.println("[Device: " + name + "] Connected to " + device.getName());
        }
    }

    /**
     * Abstract method to send data to another device. Implementations will
     * define how data transmission works.
     *
     * @param receiver The destination device.
     * @param data The message being sent.
     */
    public abstract void sendData(Device receiver, String data );

    /**
     * Handles receiving data from another device. Default behavior is to print
     * the received message.
     *
     * @param data The received message.
     * @param sender The sender device.
     */
    public void receiveData(String data, Device sender) {
        //
        
        //

        System.out.println("[ToDevice: " + name + "] Received from " + sender.getName() + ": " + data);
    }

    /**
     * Returns a list of directly connected devices.
     *
     * @return List of connected devices.
     */
    public List<Device> getConnectedDevices() { //this is a getter and concrete method
        
        return connectedDevices;
    }

}
