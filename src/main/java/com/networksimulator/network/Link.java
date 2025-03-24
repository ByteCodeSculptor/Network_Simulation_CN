package com.networksimulator.network;

import com.networksimulator.devices.Device;

/**
 * Link represents a physical connection between two devices.
 * It is used to simulate the transmission of data between devices.
 * In this simplified simulation, the link forwards data from the sender
 * to the receiver.
 */
public class Link {
    private Device deviceA;
    private Device deviceB;

    /**
     * Constructor to initialize the link with two devices.
     * @param deviceA One end of the link.
     * @param deviceB The other end of the link.
     */
    public Link(Device deviceA, Device deviceB) {
        this.deviceA = deviceA;
        this.deviceB = deviceB;
    }

    /**
     * Transmits data from the sender to the other end of the link.
     * @param sender The device sending the data.
     * @param data The data to be transmitted.
     */
    public void transmit(Device sender, String data) {
        if (sender.equals(deviceA)) {
            deviceB.receiveData(data, sender);
        } else if (sender.equals(deviceB)) {
            deviceA.receiveData(data, sender);
        } else {
            System.out.println("[Link] Error: Sender is not connected to this link.");
        }
    }
}
