package com.networksimulator.topology;

import java.util.Map;

import com.networksimulator.devices.Device;

/**
 * DotFileGenerator creates a DOT language description of the current network
 * topology. It iterates over each Network, generates subgraphs for each
 * network, and includes nodes for devices (and hubs for Star topologies). It
 * also conditionally adds a Bridge, Switch, or Router node if multiple networks
 * exist.
 */
public class DotFileGenerator {

    /**
     * Generates a DOT description string for the topology stored in the given
     * TopologyManager.
     *
     * @param topologyManager The TopologyManager containing all networks.
     * @return A String in DOT language representing the network topology.
     */
    public static String generateDot(TopologyManager topologyManager) { //static method
        StringBuilder dot = new StringBuilder();
        dot.append("digraph NetworkTopology {\n"); //its a directed graph
        dot.append("  rankdir=LR;\n"); // Left-to-right layout

        // Get all networks from the TopologyManager
        Map<Integer, Network> networks = topologyManager.getNetworks();
        if (networks == null || networks.isEmpty()) {
            dot.append("  // No networks available\n"); //if no networks are available in the collection
        } else {
            // Iterate over each network
            for (Network network : networks.values()) {
                dot.append("  subgraph cluster_N").append(network.getNetworkId()).append(" {\n");
                dot.append("    label=\"Network ").append(network.getNetworkId())
                        .append(" (").append(network.getTopologyType()).append(")\";\n");

                // Create nodes for each device in the network
                for (Device device : network.getDevices()) {
                    dot.append("    \"").append(device.getName()).append("\";\n"); //iterates the each device in the network and add to the dot file.
                }

                // If the network is Star, create a hub node and connect each device to it.
                if ("Star".equalsIgnoreCase(network.getTopologyType())) {
                    String hubName = "Hub_N" + network.getNetworkId();
                    dot.append("    \"").append(hubName).append("\" [shape=box];\n");
                    for (Device device : network.getDevices()) {
                        dot.append("    \"").append(device.getName()).append("\" -> \"")
                                .append(hubName).append("\";\n");
                    }
                }
                dot.append("  }\n");
            }
        }

        if (networks.size() >= 2) {
            int connType = topologyManager.getInterConnectionType();
            switch (connType) {
                case 1:
                    // Bridge
                    dot.append("  \"Bridge1\" [shape=box, style=filled, color=grey];\n");
                    connectInterNetworkDevice(dot, networks, "Bridge1");
                    break;
                case 2:
                    // Switch
                    dot.append("  \"InterNetworkSwitch\" [shape=box, style=filled, color=yellow];\n");
                    connectInterNetworkDevice(dot, networks, "InterNetworkSwitch");
                    break;
                case 3:
                    // Router
                    dot.append("  \"Router1\" [shape=ellipse, style=filled, color=lightblue];\n");
                    connectInterNetworkDevice(dot, networks, "Router1");
                    break;
                case 4:
                    // Hub
                    dot.append("  \"NetworkHub\" [shape=ellipse, style=filled, color=lightblue];\n");
                    connectInterNetworkDevice(dot, networks, "NetworkHub");
                    break;
                default:
                    System.out.println("// no valid inter-network device");// no valid inter-network device
                    break;
            }
        }

        dot.append("}\n");
        return dot.toString(); //return string for representing dot language for plotting he graph
    }

    /**
     * Helper method that connects the inter-network device to the first device
     */
    private static void connectInterNetworkDevice(StringBuilder dot, Map<Integer, Network> networks, String deviceName) { //to handle InterNetwork connection device
        for (Network net : networks.values()) {
            if (!net.getDevices().isEmpty()) {
                // Connect the inter-network device to the first device in this network
                Device firstDevice = net.getDevices().get(0);
                dot.append("  \"").append(deviceName).append("\" -> \"")
                        .append(firstDevice.getName()).append("\";\n");
                dot.append("  \"").append(firstDevice.getName()).append("\" -> \"")
                        .append(deviceName).append("\";\n");//adding the first device of each network to inter connection device.
            }
        }
    }
}
