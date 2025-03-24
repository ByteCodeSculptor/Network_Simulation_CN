package com.networksimulator.network;

import com.networksimulator.devices.Device;

/**
 * NetworkLayer simulates the routing and forwarding of packets at the Network layer.
 * In this simplified simulation, it logs the sending of a packet.
 */
public class NetworkLayer {
    private Device device;

    /**
     * Constructor that associates the network layer with a device.
     * @param device The device using this network layer.
     */
    public NetworkLayer(Device device) {
        this.device = device;
    }

    /**
     * Sends a packet to a specified destination.
     * For simulation purposes, the packet is a string with a header.
     * @param destination The destination device name.
     * @param data The payload data.
     */
    public void sendPacket(String destination, String data) {
        String packet = "[Packet] To: " + destination + " | Data: " + data;
        System.out.println("[NetworkLayer: " + device.getName() + "] Sending packet: " + packet);
        // In a complete simulation, this method would interact with routing and forwarding logic.
    }
}
