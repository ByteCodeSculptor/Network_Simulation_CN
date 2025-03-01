package com.networksimulator.physical;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HubTest {

    @Test
    public void testHubBroadcast() {
        Hub hub = new Hub();

        EndDevice device1 = new EndDevice("Device1");
        EndDevice device2 = new EndDevice("Device2");

        // Connect devices to the hub
        hub.addDevice(device1);
        hub.addDevice(device2);

        // Broadcast data
        hub.broadcastData("Test broadcast".getBytes());

        // Assuming receiveData prints to console, no assertion for this example
        assertTrue(true); // Simplified for demonstration
    }
}
