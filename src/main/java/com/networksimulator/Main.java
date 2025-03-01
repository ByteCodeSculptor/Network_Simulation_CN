package com.networksimulator;  // Correct package declaration

import com.networksimulator.physical.EndDevice;

public class Main {

    public static void main(String[] args) {
        // Create EndDevices
        EndDevice device1 = new EndDevice("Device1");
        EndDevice device2 = new EndDevice("Device2");

        // Connect devices
        device1.connectTo(device2);

        // Send data
        device1.sendData("Hello, Device2!");
    }
}
