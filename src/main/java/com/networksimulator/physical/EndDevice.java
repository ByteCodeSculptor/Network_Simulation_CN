package com.networksimulator.physical;

import java.util.ArrayList;
import java.util.List;

public class EndDevice {

    private String name;
    private String macAddress;
    private int port;
    private List<Connection> connections;

    // Constructor with MAC Address & Port Number for Data Link Layer
    public EndDevice(String name, String macAddress, int port) {
        this.name = name;
        this.macAddress = macAddress;
        this.port = port;
        this.connections = new ArrayList<>();
    }

    // Overloaded constructor for automatic MAC Address and Port assignment
    public EndDevice(String name) {
        this.name = name;
        this.macAddress = generateMacAddress(); // Generate a unique MAC
        this.port = (int) (Math.random() * 100) + 1; // Assign a random port (1-100)
        this.connections = new ArrayList<>();
    }

    // Generates a random MAC Address for simulation
    private String generateMacAddress() {
        return "00:0A:95:9D:68:" + String.format("%02X", (int) (Math.random() * 256));
    }

    // Method to connect this EndDevice to another device (Hub, Switch, etc.)
    public void connectTo(EndDevice device) {
        Connection connection = new Connection(this, device);
        connections.add(connection);
        System.out.println(name + " connected to " + device.getName());
    }

    // Method to send data to all connected devices
    public void sendData(String data) {
        System.out.println(name + " is sending data: " + data);
        for (Connection connection : connections) {
            connection.transmitData(data.getBytes()); // Convert data to bytes and transmit
        }
    }

    // Overloaded sendData() method for Hub support
    public void sendData(String data, Hub hub) {
        System.out.println(name + " is sending data to Hub: " + data);
        hub.broadcastData(data.getBytes(), this); // Send data to the Hub for broadcast
    }

    // Method to receive data
    public void receiveData(byte[] data) {
        System.out.println(name + " received data: " + new String(data)); // Convert bytes to string
    }

    // Getters for necessary properties
    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getPort() {
        return port;
    }
}
