package com.networksimulator.network;

import com.networksimulator.devices.Device;

/**
 * DataLinkLayer simulates framing and error detection/correction at the Data Link layer.
 * It prepares frames (with headers) and sends them via the PhysicalLayer.
 */
public class DataLinkLayer {
    private Device device;

    /**
     * Constructor that associates the data link layer with a device.
     * @param device The device using this data link layer.
     */
    public DataLinkLayer(Device device) {
        this.device = device;
    }

    /**
     * Sends a frame to the specified destination device using the provided link.
     * In this simplified simulation, a frame is constructed as a string that includes a header.
     * @param link The link over which the frame is sent.
     * @param destination The intended destination device (by name, for simulation purposes).
     * @param data The payload to send.
     */
    public void sendFrame(Link link, String destination, String data) {
        String frame = "[Frame] To: " + destination + " | Data: " + data;
        System.out.println("[DataLinkLayer: " + device.getName() + "] Sending frame: " + frame);
        // Create an instance of PhysicalLayer to send the frame.
        PhysicalLayer physicalLayer = new PhysicalLayer(device);
        physicalLayer.send(link, frame);
    }
}
