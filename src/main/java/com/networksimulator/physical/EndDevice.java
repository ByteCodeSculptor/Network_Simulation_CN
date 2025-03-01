package com.networksimulator.physical;

import java.util.ArrayList;
import java.util.List;

public class EndDevice {
    private String name;
    private List<Connection> connections;

    // Initialize the connections list
    public EndDevice(String name) {
        this.name = name;
        this.connections = new ArrayList<>();  // Initialize the list
    }

    // Method to establish a connection to another EndDevice
    public void connectTo(EndDevice device) {
        // Create a new connection between this device and the other device
        Connection connection = new Connection(this, device);
        connections.add(connection);  // Add this connection to the list of connections
        System.out.println(name + " connected to " + device.getName());  // Log the connection
    }

    // Method to send data to all connected devices
    public void sendData(String data) {
        for (Connection connection : connections) {
            connection.transmitData(data.getBytes());  // Send data to connected device(s)
        }
    }

    // Method to receive data from another device
    public void receiveData(byte[] data) {
        System.out.println(name + " received data: " + new String(data));  // Display the received data
    }

    // Get the name of the device
    public String getName() {
        return name;
    }
}
