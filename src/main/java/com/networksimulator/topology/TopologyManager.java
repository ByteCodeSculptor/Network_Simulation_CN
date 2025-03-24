package com.networksimulator.topology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.networksimulator.devices.Bridge;
import com.networksimulator.devices.Device;
import com.networksimulator.devices.Router;
import com.networksimulator.devices.Switch;
import com.networksimulator.devices.Hub;

/**
 * TopologyManager is responsible for managing multiple networks. It creates
 * networks with specified topology types (Star, Mesh, Hybrid), manages
 * inter-network connections (via Bridge, Switch, or Router), and provides
 * methods to list devices, send data, and display the entire topology.
 */
public class TopologyManager {

    private Map<Integer, Network> networks = new HashMap<>();
    private List<Device> allDevices = new ArrayList<>();

    // New field to store the inter-network connection type.
    // 0 = none, 1 = Bridge, 2 = Switch, 3 = Router.
    private int interConnectionType = 0;

    public String[] devCol;

    /**
     * Creates a network with a specific topology.
     *
     * @param networkId Unique network identifier.
     * @param topologyChoice Topology choice: 1 for Star, 2 for Mesh, 3 for
     * Hybrid.
     */
    public void createNetwork(int networkId, int topologyChoice) {
        Network network = new Network(networkId);

        switch (topologyChoice) {
            case 1:
                network.createStarTopology();
                break;
            case 2:
                network.createMeshTopology();
                break;
            case 3:
                network.createHybridTopology();
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Star Topology.");
                network.createStarTopology();
        }

        networks.put(networkId, network);
        allDevices.addAll(network.getDevices());
    }

    /**
     * Connects the networks using an inter-network device.
     *
     * @param connectionType Connection type: 1 for Bridge, 2 for Switch, 3 for
     * Router 4 for Hub.
     */
    public void connectNetworks(int connectionType) {
        // Store the chosen inter-network connection type.
        interConnectionType = connectionType;

        if (networks.size() < 2) {
            System.out.println("At least two networks are required for inter-network connection.");
            return;
        }

        switch (connectionType) {
            case 1:
                System.out.println("Connecting networks using a Bridge...");
                Bridge bridge = new Bridge("Bridge1");
                for (Network net : networks.values()) {
                    net.connectWithBridge(bridge);
                }
                break;
            case 2:
                System.out.println("Connecting networks using a Switch...");
                Switch networkSwitch = new Switch("InterNetworkSwitch");
                for (Network net : networks.values()) {
                    net.connectWithSwitch(networkSwitch);
                }
                break;
            case 3:
                System.out.println("Connecting networks using a Router...");
                Router router = new Router("Router1");
                for (Network net : networks.values()) {
                    net.connectWithRouter(router);
                }
                break;
            case 4:
                System.out.println("Connecting networks using a Hub...");
                Hub hub = new Hub("NetworkHub");
                for (Network net : networks.values()) {
                    net.connectWithHub(hub);
                }
                //update here for Hub
                break;
            default:
                System.out.println("Invalid connection type.");
        }
    }

    /**
     * Lists all devices across all networks.
     */
    public String[] listAllDevices() {
        if (allDevices.isEmpty()) {
            System.out.println("No devices found.");
        }
        for (Device device : allDevices) {
            System.out.println(device.getName());
            // Assuming allDevices is a Collection or List of Device objects

        }
        String[] devCol = allDevices.stream()
                .map(Device::getName)
                .toArray(String[]::new);
        return devCol;
    }
//

    public void accessControl(String[] devCol) {

    }

    /**
     * Sends data from one device to another.
     *
     * @param fromDevice The sender device name.
     * @param toDevice The receiver device name.
     * @param message The message to send.
     */
    public void sendData(String fromDevice, String toDevice, String message) {
        Device sender = findDeviceByName(fromDevice);
        Device receiver = findDeviceByName(toDevice);
        if (sender != null && receiver != null) {
            sender.sendData(receiver, message);
        } else {
            System.out.println("Invalid device names provided.");
        }
    }

    /**
     * Finds a device by its name.
     *
     * @param name The device name.
     * @return The Device if found; otherwise, null.
     */
    private Device findDeviceByName(String name) {
        for (Device device : allDevices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    /**
     * Returns the map of networks.
     *
     * @return Map of networks with network IDs as keys.
     */
    public Map<Integer, Network> getNetworks() {
        return networks;
    }

    /**
     * Displays the topology of all networks. For each network, it prints the
     * network ID, topology type, and the list of devices.
     */
    public void displayTopology() {
        System.out.println("\n--- Network Topology ---");
        if (networks.isEmpty()) {
            System.out.println("No networks available.");
            return;
        }
        for (Map.Entry<Integer, Network> entry : networks.entrySet()) {
            Network net = entry.getValue();
            System.out.println("Network " + net.getNetworkId() + " (" + net.getTopologyType() + "):");
            for (Device dev : net.getDevices()) {
                System.out.println("   - " + dev.getName());
            }
        }
    }

    /**
     * Returns the inter-network connection type.
     *
     * @return 1 for Bridge, 2 for Switch, 3 for Router, or 0 if none.
     */
    public int getInterConnectionType() {
        return interConnectionType;
    }
}
