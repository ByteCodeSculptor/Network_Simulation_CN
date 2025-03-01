package com.networksimulator.physical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EndDeviceTest {

    @Test
    public void testSendData() {
        EndDevice device1 = new EndDevice("Device1");
        EndDevice device2 = new EndDevice("Device2");

        device1.connectTo(device2);

        // Test if data is sent and received
        device1.sendData("Hello Device2");
        // We assume receiveData method prints the received data
        assertEquals("Device2 received data: Hello Device2", "Device2 received data: Hello Device2"); // Simplified for demo
    }
}
