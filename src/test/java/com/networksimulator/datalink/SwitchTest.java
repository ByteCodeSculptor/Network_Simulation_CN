package com.networksimulator.datalink;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.networksimulator.physical.EndDevice;

class SwitchTest {

    private Switch networkSwitch;
    private EndDevice device1;
    private EndDevice device2;
    private EndDevice device3;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Switch and Devices
        networkSwitch = new Switch();
        device1 = new EndDevice("Device1", "00:0A:95:9D:68:01", 1);
        device2 = new EndDevice("Device2", "00:0A:95:9D:68:02", 2);
        device3 = new EndDevice("Device3", "00:0A:95:9D:68:03", 3);

        // Connect Devices to Switch
        networkSwitch.connectDevice(device1);
        networkSwitch.connectDevice(device2);
        networkSwitch.connectDevice(device3);
    }

    @Test
    void testMacAddressLearning() {
        // Device1 sends data to Device2
        networkSwitch.receiveData("Hello, Device2!".getBytes(), device1.getMacAddress(), device2.getMacAddress(), device1);

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Switch: Forwarding data to Port 2"), "Switch should forward data to Device2");
    }

    @Test
    void testBroadcastingForUnknownMac() {
        // Device1 sends data to an unknown MAC address
        networkSwitch.receiveData("Hello, Unknown!".getBytes(), device1.getMacAddress(), "00:0A:95:9D:68:99", device1);

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Switch: Broadcasting data"), "Switch should broadcast to all ports");
    }

    @Test
    void testDisplayMacTable() {
        // Device1 communicates first, so switch learns MAC address
        networkSwitch.receiveData("Hello, Device2!".getBytes(), device1.getMacAddress(), device2.getMacAddress(), device1);

        // Display MAC table
        networkSwitch.displayMacTable();

        // Capture the output
        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("MAC: 00:0A:95:9D:68:01 -> Port: 1"), "Switch should store Device1's MAC");
    }
}
