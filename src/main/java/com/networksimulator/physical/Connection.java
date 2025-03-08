package com.networksimulator.physical;

import java.util.Random;

public class Connection {

    private EndDevice device1;
    private EndDevice device2;
    private int transmissionDelay; // Simulated delay in milliseconds
    private double errorRate; // Simulated error probability (0.0 - 1.0)
    private static final Random random = new Random();

    // Constructor for connection with delay and error rate
    public Connection(EndDevice device1, EndDevice device2, int transmissionDelay, double errorRate) {
        this.device1 = device1;
        this.device2 = device2;
        this.transmissionDelay = transmissionDelay;
        this.errorRate = errorRate;
    }

    // Overloaded constructor with default delay (0 ms) and error rate (0.0)
    public Connection(EndDevice device1, EndDevice device2) {
        this(device1, device2, 0, 0.0);
    }

    // Simulates data transmission between connected devices
    public void transmitData(byte[] data) {
        System.out.println("Connection: Transmitting data...");

        // Simulate transmission delay
        try {
            Thread.sleep(transmissionDelay);
        } catch (InterruptedException e) {
            System.err.println("Error: Transmission interrupted.");
        }

        // Simulate error occurrence
        if (random.nextDouble() < errorRate) {
            System.out.println("Connection: Data transmission error occurred!");
            return; // Drop the packet due to error
        }

        // Deliver data to the correct recipient
        if (random.nextBoolean()) {
            device1.receiveData(data);
        } else {
            device2.receiveData(data);
        }
    }

    // Getters and Setters for transmission parameters
    public int getTransmissionDelay() {
        return transmissionDelay;
    }

    public void setTransmissionDelay(int transmissionDelay) {
        this.transmissionDelay = transmissionDelay;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public EndDevice getDevice1() {
        return device1;
    }

    public EndDevice getDevice2() {
        return device2;
    }
}
