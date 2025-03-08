package com.networksimulator.datalink;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.networksimulator.physical.EndDevice;

class BridgeTest {

    private Bridge networkBridge;
    private EndDevice device1;
    private EndDevice device2;
    private EndDevice device3;
    private EndDevice device4;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Bridge and Devices
        networkBridge = new Bridge();
        device1 = new EndDevice("Device1", "00:0A:95:9D:68:01", 1);
        device2 = new EndDevice("Device2", "00:0A:95:9D:68:02", 2);
        device3 = new EndDevice("Device3", "00:0A:95:9D:68:03", 3);
        device4 = new EndDevice("Device4", "00:0A:95:9D:68:04", 4);

        // Connect Devices to Bridge (Different Segments)
        networkBridge.connectDevice(device1, 1);
        networkBridge.connectDevice(device2, 1);
        networkBridge.connectDevice(device3, 2);
        networkBridge.connectDevice(device4, 2);
    }

    @Test
    void testMacAddressLearning() {
        // Device1 sends data to Device2 in the same segment
        networkBridge.receiveData("Hello, Device2!".getBytes(), device1.getMacAddress(), device2.getMacAddress(), device1);

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Bridge: Forwarding data within Segment 1 to Port 2"), "Bridge should forward data within the same segment");
    }

    @Test
    void testFilteringWithinSegment() {
        // Device1 sends data to Device2 (same segment), Bridge should NOT forward it to Segment 2
        networkBridge.receiveData("Hello, Device2!".getBytes(), device1.getMacAddress(), device2.getMacAddress(), device1);

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertFalse(output.contains("Bridge: Forwarding data to the other segment"), "Bridge should not forward within the same segment");
    }

    @Test
    void testForwardingBetweenSegments() {
        // Device1 (Segment 1) sends data to Device3 (Segment 2)
        networkBridge.receiveData("Hello, Device3!".getBytes(), device1.getMacAddress(), device3.getMacAddress(), device1);

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Bridge: Forwarding data to the other segment"), "Bridge should forward data across segments");
    }

    @Test
    void testDisplayMacTable() {
        // Device1 communicates first, so Bridge learns MAC address
        networkBridge.receiveData("Hello, Device2!".getBytes(), device1.getMacAddress(), device2.getMacAddress(), device1);

        // Display MAC table
        networkBridge.displayMacTable();

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("MAC: 00:0A:95:9D:68:01 -> Port: 1"), "Bridge should store Device1's MAC");
    }
}
