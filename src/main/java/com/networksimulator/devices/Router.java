package com.networksimulator.devices;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Handles RIP (Routing Information Protocol) updates. This method is a placeholder
     * By creating Dummy router with having Dummy_IP_table and exchnaging with main router
     */
        // Placeholder for RIP update handling logic.
        public static void mergeIpTables(String file1, String file2) {
            Map<String, String> combinedTable = new HashMap<>();

        // Read from both files
        try (BufferedReader br1 = new BufferedReader(new FileReader(file1));
         BufferedReader br2 = new BufferedReader(new FileReader(file2))) {

        // Read file1
        String line;
        boolean firstLine = true;
        while ((line = br1.readLine()) != null) {
            if (firstLine) { firstLine = false; continue; }
            String[] parts = line.split(",");
            if (parts.length == 2) combinedTable.put(parts[0].trim(), parts[1].trim());
        }
        // Read file2
        firstLine = true;
        while ((line = br2.readLine()) != null) {
            if (firstLine) { firstLine = false; continue; }
            String[] parts = line.split(",");
            if (parts.length == 2) combinedTable.put(parts[0].trim(), parts[1].trim());
        }
        }
         catch (Exception e) {
        System.out.println("Error reading tables: " + e.getMessage());
        return;
        }

        // Write combined map back to both files
        try (PrintWriter writer1 = new PrintWriter(new FileWriter(file1));
         PrintWriter writer2 = new PrintWriter(new FileWriter(file2))) {
        writer1.println("Device,IP Address");
        writer2.println("Device,IP Address");
        for (Map.Entry<String, String> entry : combinedTable.entrySet()) {
            writer1.println(entry.getKey() + "," + entry.getValue());
            writer2.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println("Merged IP tables written to both files.");
        } catch (Exception e) {
        System.out.println("Error writing merged tables: " + e.getMessage());
        }
        }
}
