package com.networksimulator.integration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.networksimulator.datalink.Bridge;
import com.networksimulator.datalink.Switch;
import com.networksimulator.datalink.protocols.AccessControl;
import com.networksimulator.datalink.protocols.ErrorControl;
import com.networksimulator.datalink.protocols.GoBackN;
import com.networksimulator.physical.EndDevice;
import com.networksimulator.physical.Hub;

class NetworkIntegrationTest {

    private Hub hub;
    private Switch networkSwitch;
    private Bridge networkBridge;
    private EndDevice device1, device2, device3, device4;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Devices
        device1 = new EndDevice("Device1", "00:0A:95:9D:68:01", 1);
        device2 = new EndDevice("Device2", "00:0A:95:9D:68:02", 2);
        device3 = new EndDevice("Device3", "00:0A:95:9D:68:03", 3);
        device4 = new EndDevice("Device4", "00:0A:95:9D:68:04", 4);

        // Initialize Network Components
        hub = new Hub();
        networkSwitch = new Switch();
        networkBridge = new Bridge();

        // Connect Devices to the Hub (Star Topology)
        hub.addDevice(device1);
        hub.addDevice(device2);

        // Connect Devices to the Switch
        networkSwitch.connectDevice(device3);
        networkSwitch.connectDevice(device4);

        // Connect Devices to the Bridge (Segment 1 & 2)
        networkBridge.connectDevice(device1, 1);
        networkBridge.connectDevice(device2, 1);
        networkBridge.connectDevice(device3, 2);
        networkBridge.connectDevice(device4, 2);
    }

    @Test
    void testHubCommunication() {
        // Device1 sends data via Hub
        device1.sendData("Hello, Hub Network!", hub);

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Hub: Broadcasting data from Device1"), "Hub should broadcast data.");
        assertTrue(output.contains("Device2 received data: Hello, Hub Network!"), "Device2 should receive data.");
        assertFalse(output.contains("Device1 received data"), "Sender should not receive its own message.");
    }

    @Test
    void testSwitchForwarding() {
        // Device3 sends data to Device4 via Switch
        networkSwitch.receiveData("Hello, Device4!".getBytes(), device3.getMacAddress(), device4.getMacAddress(), device3);

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Switch: Forwarding data to Port 4"), "Switch should forward data to Device4.");
    }

    @Test
    void testBridgeForwarding() {
        // Device1 (Segment 1) sends data to Device3 (Segment 2)
        networkBridge.receiveData("Hello, Device3!".getBytes(), device1.getMacAddress(), device3.getMacAddress(), device1);

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Bridge: Forwarding data to the other segment"), "Bridge should forward data across segments.");
    }

    @Test
    void testCSMACollisionHandling() {
        AccessControl csma = new AccessControl();

        // Simulate two devices trying to send data at the same time
        new Thread(() -> csma.sendData("Device1", "CSMA/CD Test".getBytes())).start();
        new Thread(() -> csma.sendData("Device2", "CSMA/CD Test".getBytes())).start();

        try {
            Thread.sleep(500); // Allow threads to complete execution
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("detects a collision"), "CSMA/CD should detect a collision.");
        assertTrue(output.contains("Initiating backoff"), "CSMA/CD should initiate backoff.");
    }

    @Test
    void testErrorControl() {
        byte[] data = "Test Message".getBytes();
        byte crc = ErrorControl.computeCRC(data);

        assertTrue(ErrorControl.verifyCRC(data, crc), "CRC Verification should pass for correct data.");

        byte[] corruptedData = ErrorControl.introduceError(data, 0.5); // 50% chance of corruption
        assertFalse(ErrorControl.verifyCRC(corruptedData, crc), "CRC Verification should fail for corrupted data.");
    }

    @Test
    void testGoBackNFlowControl() {
        GoBackN goBackN = new GoBackN();
        goBackN.sendFrames(); // Simulate Go-Back-N Transmission

        String output = outputStream.toString().trim();
        assertTrue(output.contains("Go-Back-N: All frames transmitted successfully!"), "Go-Back-N should complete transmission.");
    }
}
