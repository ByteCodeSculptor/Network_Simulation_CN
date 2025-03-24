package com.networksimulator.topology;

import java.util.Map;

import com.networksimulator.devices.Device;

/**
 * TopologyDataExtractor is a helper class that extracts and displays topology
 * data from a TopologyManager. It prints out each network's ID, topology type,
 * and the list of devices present in that network.
 */
public class TopologyDataExtractor {

    /**
     * Extracts topology data from the provided TopologyManager and prints it to
     * the console.
     *
     * @param topologyManager The TopologyManager containing the networks.
     */
    public static void extractAndPrintTopologyData(TopologyManager topologyManager) {
        Map<Integer, Network> networks = topologyManager.getNetworks();
        if (networks == null || networks.isEmpty()) {
            System.out.println("No networks available.");
            return;
        }
        System.out.println("\n--- Extracted Topology Data ---");
        for (Map.Entry<Integer, Network> entry : networks.entrySet()) {
            Network network = entry.getValue();
            System.out.println("Network " + network.getNetworkId() + " (" + network.getTopologyType() + "):");
            for (Device device : network.getDevices()) {
                System.out.println("   - " + device.getName());
            }
        }
    }
}
