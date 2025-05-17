package com.networksimulator.devices;

import java.util.ArrayList;
import java.util.List;

import com.networksimulator.topology.Network;

/**
 * Router is a Layer 3 device that connects different networks. It maintains a
 * list of connected networks and forwards data between them using a simple
 * routing mechanism.
 */
public class Router extends Device {

    // List of networks connected to this router.
    private List<Network> connectedNetworks;

    /**
     * Constructor for Router.
     *
     * @param name Unique identifier for the Router.
     */
    public Router(String name) {
        super(name);
        connectedNetworks = new ArrayList<>();
    }

    /**
     * Connects a network to this router. The router keeps track of all networks
     * it is connected to.
     *
     * @param network The network to be connected.
     */
    public void connectNetwork(Network network) {
        if (!connectedNetworks.contains(network)) {
            connectedNetworks.add(network);
            System.out.println("[Router: " + name + "] Connected to Network " + network.getNetworkId());
        }
    }

    /**
     * Forwards data to the intended destination device. It searches through the
     * connected networks to find the one containing the destination device. If
     * found, it forwards the data; otherwise, it prints an error message.
     *
     * @param receiver The intended destination device.
     * @param data The message to send.
     */
    @Override
    public void sendData(Device receiver, String data) {
        boolean forwarded = false;
        // Iterate over each connected network to find the destination.
        for (Network network : connectedNetworks) {
            if (network.containsDevice(receiver)) {
                System.out.println("[Router: " + name + "] Forwarding data to " + receiver.getName()
                        + " via Network " + network.getNetworkId());
                receiver.receiveData(data, this);
                forwarded = true;
                break;
            }
        }
        if (!forwarded) {
            System.out.println("[Router: " + name + "] Destination " + receiver.getName() + " not found in connected networks.");
        }
    }

    /**
     * Handles receiving data. For now, the router simply logs the receipt.
     *
     * @param data The received data.
     * @param sender The device that sent the data.
     */
    @Override
    public void receiveData(String data, Device sender) {
        System.out.println("[Router: " + name + "] Received data from " + sender.getName() + ": " + data);
    }

}
