package com.networksimulator.physical;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndDeviceTest {

    private EndDevice device1;
    private EndDevice device2;
    private Hub hub;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Devices and Hub
        device1 = new EndDevice("Device1");
        device2 = new EndDevice("Device2");
        hub = new Hub();

        // Connect devices to the Hub
        hub.addDevice(device1);
        hub.addDevice(device2);
    }

    @Test
    void testDirectDeviceConnection() {
        // Connect two devices directly
        device1.connectTo(device2);
        
        // Send data from Device1 to Device2
        device1.sendData("Hello, Device2!");

        String output = outputStream.toString().trim();
        
        // Expected Output Check
        assertTrue(output.contains("Device1 connected to Device2"), "Devices should be connected.");
        assertTrue(output.contains("Device1 is sending data: Hello, Device2!"), "Device1 should send data.");
        assertTrue(output.contains("Device2 received data: Hello, Device2!"), "Device2 should receive data.");
    }

    @Test
    void testDeviceSendingDataViaHub() {
        // Device1 sends data via the Hub
        device1.sendData("Hello, Everyone!", hub);

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Hub: Broadcasting data from Device1"), "Hub should broadcast data.");
        assertTrue(output.contains("Device2 received data: Hello, Everyone!"), "Device2 should receive data.");
        assertFalse(output.contains("Device1 received data"), "Sender should not receive its own message.");
    }
}
