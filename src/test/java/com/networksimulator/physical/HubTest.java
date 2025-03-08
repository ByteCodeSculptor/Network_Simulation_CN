package com.networksimulator.physical;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HubTest {

    private Hub hub;
    private EndDevice device1;
    private EndDevice device2;
    private EndDevice device3;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Hub and Devices
        hub = new Hub();
        device1 = new EndDevice("Device1");
        device2 = new EndDevice("Device2");
        device3 = new EndDevice("Device3");

        // Connect Devices to the Hub
        hub.addDevice(device1);
        hub.addDevice(device2);
        hub.addDevice(device3);
    }

    @Test
    void testDeviceConnections() {
        assertEquals(3, hub.getConnectedDevices().size(), "All devices should be connected to the Hub");
    }

    @Test
    void testBroadcastData() {
        // Device1 sends data via the Hub
        device1.sendData("Hello, Everyone!", hub);

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Hub: Broadcasting data from Device1"), "Hub should broadcast data");
        assertTrue(output.contains("Device2 received data: Hello, Everyone!"), "Device2 should receive the message");
        assertTrue(output.contains("Device3 received data: Hello, Everyone!"), "Device3 should receive the message");
        assertFalse(output.contains("Device1 received data"), "Sender should not receive its own message");
    }
}
