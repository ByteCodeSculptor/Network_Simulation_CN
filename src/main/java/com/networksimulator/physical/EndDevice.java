package com.networksimulator.physical;

public class EndDevice {
    private String name;
    private Connection connection;

    public EndDevice(String name) {
        this.name = name;
    }

    // Method to connect to another EndDevice
    public void connectTo(EndDevice device) {
        this.connection = new Connection(this, device);
    }

    // Method to send data through the connection
    public void sendData(String data) {
        connection.transmitData(data.getBytes());
    }

    // Method to receive data
    public void receiveData(byte[] data) {
        System.out.println(name + " received data: " + new String(data));
    }

    public String getName() {
        return name;
    }
}
