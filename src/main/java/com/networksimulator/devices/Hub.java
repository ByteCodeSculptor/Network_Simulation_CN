package com.networksimulator.devices;

/**
 * Hub is a physical layer device that simulates a broadcast medium. It receives
 * data from a connected device and then broadcasts it to all other connected
 * devices.
 */
public class Hub extends Device {

    /**
     * Constructor for Hub.
     *
     * @param name Unique identifier for the Hub.
     */
    public Hub(String name) {
        super(name);
    }

    /**
     * Adds a device to the Hub. This method registers a new device on the Hub
     * and establishes a bidirectional connection.
     *
     * @param device The device to be connected to the Hub.
     */
    public void addDevice(Device device) {
        if (!connectedDevices.contains(device)) {
            connectedDevices.add(device);
            // Establish bidirectional connection
            device.connectTo(this);
            System.out.println("[Hub: " + name + "] Device " + device.getName() + " connected.");
        }
    }

    /**
     * Overrides sendData method. Although Hubs typically do not initiate
     * transmission, this implementation allows the Hub to broadcast data if
     * needed.
     *
     * @param receiver Not used in Hub since it broadcasts to all devices.
     * @param data The message to broadcast.
     */
    @Override
    public void sendData(Device receiver, String data) {
        System.out.println("[Hub: " + name + "] Broadcasting data: " + data + " to all the next devices");
        broadcastData(data, null);
    }

    /**
     * Overrides receiveData method. When the Hub receives data from a device,
     * it broadcasts the data to all other connected devices.
     *
     * @param data The received data.
     * @param sender The device that sent the data.
     */
    @Override
    public void receiveData(String data, Device sender) {
        System.out.println("[Hub: " + name + "] Received data from " + sender.getName() + ". Broadcasting...");
        broadcastData(data, sender);
    }

    /**
     * Helper method to broadcast data to all connected devices except the
     * sender.
     *
     * @param data The data to broadcast.
     * @param sender The original sender (if any), which should not receive the
     * broadcast.
     */
    public void broadcastData(String data, Device sender) {
        for (Device device : connectedDevices) {
            // If sender is null (Hub-initiated transmission) or this device is not the sender, forward the data.
            if (sender == null || !device.getName().equals(sender.getName())) {
                device.receiveData(data, this);
            }
        }
    }
}
