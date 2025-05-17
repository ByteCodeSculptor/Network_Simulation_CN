package com.networksimulator.devices;

import com.networksimulator.network.Network2;

public class Link {
    private Network2 network1;
    private Network2 network2;
    private ForwardingDevice connectingDevice; // instead of Device

    public Link(Network2 network1, Network2 network2, ForwardingDevice connectingDevice) {
        this.network1 = network1;
        this.network2 = network2;
        this.connectingDevice = connectingDevice;
    }

    public void transferPacket(String packet) {
        connectingDevice.forwardPacket(network1, network2, packet);
    }
}
