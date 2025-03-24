package com.networksimulator.network;

import com.networksimulator.devices.Device;

/**
 * PhysicalLayer simulates the physical transmission of data as bits.
 * In this simplified simulation, PhysicalLayer is responsible for interfacing
 * with the Link to send data.
 */
public class PhysicalLayer {
    private Device device;

    /**
     * Constructor that associates the physical layer with a device.
     * @param device The device using this physical layer.
     */
    public PhysicalLayer(Device device) {
        this.device = device;
    }

    /**
     * Sends data through the specified link.
     * In a real system, this would involve bit-level encoding and transmission delays.
     * Here, we simply call the link's transmit method.
     * @param link The link over which to send the data.
     * @param data The data to be transmitted.
     */
    public void send(Link link, String data) {
        System.out.println("[PhysicalLayer: " + device.getName() + "] Sending data through link...");
        link.transmit(device, data);
    }
}
