package com.networksimulator.devices;

import com.networksimulator.network.Network2;

public interface ForwardingDevice {
    void forwardPacket(Network2 fromNetwork, Network2 toNetwork, String packet);
}
