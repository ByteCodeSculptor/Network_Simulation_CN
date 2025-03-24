package com.networksimulator.topology;

import java.util.ArrayList;
import java.util.List;

import com.networksimulator.devices.Bridge;
import com.networksimulator.devices.Device;
import com.networksimulator.devices.EndDevice;
import com.networksimulator.devices.Hub;
import com.networksimulator.devices.Router;
import com.networksimulator.devices.Switch;

/**
 * Network represents a single LAN or subnet in the simulator. It manages a
 * collection of devices and supports various topology types (Star, Mesh,
 * Hybrid). It also provides helper methods required by Router, such as
 * getNetworkId() and containsDevice(Device device).
 */
public class Network {

    private int networkId;
    private List<Device> devices = new ArrayList<>();
    private Hub hub;
    private String topologyType; // New field to store the topology type ("Star", "Mesh", or "Hybrid")

    /**
     * Constructor that initializes the network with a unique identifier.
     *
     * @param id Unique network identifier.
     */
    public Network(int id) {
        this.networkId = id;
    }

    /**
     * Creates a Star topology within this network. A central hub is used to
     * connect 5 EndDevices.
     */
    public void createStarTopology() {
        topologyType = "Star";
        hub = new Hub("Hub_N" + networkId);
        for (int i = 1; i <= 5; i++) {
            EndDevice device = new EndDevice("Device" + i + "_N" + networkId);
            devices.add(device);
            hub.addDevice(device);
        }
    }

    /**
     * Creates a Mesh topology within this network. All 5 EndDevices are
     * directly connected to each other.
     */
    public void createMeshTopology() {
        topologyType = "Mesh";
        for (int i = 1; i <= 5; i++) {
            devices.add(new EndDevice("Device" + i + "_N" + networkId));
        }
        for (int i = 0; i < devices.size(); i++) {
            for (int j = i + 1; j < devices.size(); j++) {
                devices.get(i).connectTo(devices.get(j));
            }
        }
    }

    /**
     * Creates a Hybrid topology. For this simulation, it simply calls both
     * createStarTopology and createMeshTopology.
     */
    public void createHybridTopology() {
        topologyType = "Hybrid";
        // Hybrid: First create a Star topology, then add additional mesh connections
        createStarTopology();
        // Add mesh connections among all devices (if not already connected)
        for (int i = 0; i < devices.size(); i++) {
            for (int j = i + 1; j < devices.size(); j++) {
                devices.get(i).connectTo(devices.get(j));
            }
        }
    }

    /**
     * Returns the list of devices in this network.
     *
     * @return List of devices.
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     * Returns the unique identifier of this network. This method is required by
     * the Router to identify the network.
     *
     * @return The network's unique ID.
     */
    public int getNetworkId() {
        return networkId;
    }

    /**
     * Returns the topology type of this network ("Star", "Mesh", or "Hybrid").
     *
     * @return The topology type.
     */
    public String getTopologyType() {
        return topologyType;
    }

    /**
     * Checks if a given device is part of this network. This method is used by
     * the Router to determine which network a device belongs to.
     *
     * @param device The device to check.
     * @return true if the device is present in this network; false otherwise.
     */
    public boolean containsDevice(Device device) {
        return devices.contains(device);
    }

    /**
     * Connects the devices in this network with a Bridge.
     *
     * @param bridge The Bridge to connect with.
     */
    public void connectWithBridge(Bridge bridge) {
        if (bridge == null) {
            System.out.println("Error: Bridge is null.");
            return;
        }
        for (Device device : devices) {
            bridge.addConnectedDevice(device);
        }
    }

    /**
     * Connects the devices in this network with a Switch.
     *
     * @param networkSwitch The Switch to connect with.
     */
    public void connectWithSwitch(Switch networkSwitch) {
        if (networkSwitch == null) {
            System.out.println("Error: Switch is null.");
            return;
        }
        for (Device device : devices) {
            networkSwitch.addConnectedDevice(device);
        }
    }

    /**
     * Connects this network with a Router.
     *
     * @param router The Router to connect with.
     */
    public void connectWithRouter(Router router) {
        if (router == null) {
            System.out.println("Error: Router is null.");
            return;
        }
        router.connectNetwork(this);
    }

    //
    public void connectWithHub(Hub hub) {
        if (hub == null) {
            System.out.println("Error: Hub is null.");
        }
        int numNetworks;
        // if (!connectedDevices.contains(device)) {
        //     connectedDevices.add(device);
        //     device.connectTo(this);
        //     System.out.println("[Switch: " + name + "] Device " + device.getName() + " connected.");
        // }

        for (Device device : devices) {
            // hub.addConnect
            System.out.println("[Device: " + device.getName() + "] connected to " + " NetworkHub");
            System.out.println("[Hub: " + "NetworkHub" + "] Device " + device.getName() + " connected.");
        }
    }
}
