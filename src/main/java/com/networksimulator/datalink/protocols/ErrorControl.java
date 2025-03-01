package com.networksimulator.datalink.protocols;

public class ErrorControl {

    // Method to calculate CRC (simple example)
    public static boolean checkForErrors(byte[] data) {
        // A simple checksum or CRC implementation could go here
        // For this example, let's assume data integrity is checked and return true
        // In a real-world scenario, you'd calculate a CRC value here

        // Simulate error detection
        int checksum = 0;
        for (byte b : data) {
            checksum += b;  // Simple checksum calculation (sum of bytes)
        }

        // If checksum is zero, return true (no error), otherwise false (error detected)
        return checksum % 2 == 0;
    }

    // Method to handle data transmission with error checking
    public static void transmitWithErrorControl(byte[] data) {
        if (checkForErrors(data)) {
            System.out.println("Data transmitted successfully: " + new String(data));
        } else {
            System.out.println("Error detected, requesting retransmission.");
        }
    }
}
