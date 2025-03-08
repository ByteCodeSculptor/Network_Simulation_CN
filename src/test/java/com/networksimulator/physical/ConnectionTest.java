package com.networksimulator.physical;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectionTest {

    private EndDevice device1;
    private EndDevice device2;
    private Connection connection;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Initialize Devices
        device1 = new EndDevice("Device1");
        device2 = new EndDevice("Device2");

        // Establish Connection (With Delay & No Errors)
        connection = new Connection(device1, device2, 200, 0.0);
    }

    @Test
    void testDirectConnection() {
        // Send Data from Device1 to Device2
        connection.transmitData("Hello, Device2!".getBytes());

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Connection: Transmitting data..."), "Connection should transmit data.");
        assertTrue(output.contains("Device2 received data: Hello, Device2!"), "Device2 should receive the data.");
    }

    @Test
    void testTransmissionDelay() {
        long startTime = System.currentTimeMillis();

        // Send Data from Device1 to Device2
        connection.transmitData("Hello, Device2!".getBytes());

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        // Check if the delay was applied
        assertTrue(elapsedTime >= 200, "Transmission should have a delay of at least 200ms.");
    }

    @Test
    void testPacketLoss() {
        // Establish a connection with a high error rate (100% loss)
        Connection faultyConnection = new Connection(device1, device2, 0, 1.0);

        // Send Data
        faultyConnection.transmitData("This will be lost".getBytes());

        String output = outputStream.toString().trim();

        // Expected Output Check
        assertTrue(output.contains("Connection: Data transmission error occurred!"), "Packet should be lost.");
        assertFalse(output.contains("Device2 received data"), "Device2 should not receive lost data.");
    }
}
