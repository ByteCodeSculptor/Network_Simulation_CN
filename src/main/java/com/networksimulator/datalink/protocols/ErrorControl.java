package com.networksimulator.datalink.protocols;

import java.util.Random;

public class ErrorControl {

    private static final int POLYNOMIAL = 0x07; // CRC-8 Polynomial (x^8 + x^2 + x + 1)

    // Compute the CRC-8 checksum for a given byte array
    public static byte computeCRC(byte[] data) {
        byte crc = 0x00;
        for (byte b : data) {
            crc ^= b; // XOR with byte
            for (int i = 0; i < 8; i++) { // Process each bit
                if ((crc & 0x80) != 0) { // If MSB is 1, shift and XOR with polynomial
                    crc = (byte) ((crc << 1) ^ POLYNOMIAL);
                } else {
                    crc <<= 1;
                }
            }
        }
        return crc;
    }

    // Verify CRC-8: Returns true if data is correct
    public static boolean verifyCRC(byte[] data, byte receivedCRC) {
        return computeCRC(data) == receivedCRC;
    }

    // Introduce random bit errors (for testing)
    public static byte[] introduceError(byte[] data, double errorProbability) {
        Random random = new Random();
        byte[] corruptedData = data.clone();
        for (int i = 0; i < data.length; i++) {
            if (random.nextDouble() < errorProbability) {
                corruptedData[i] ^= (1 << random.nextInt(8)); // Flip a random bit
            }
        }
        return corruptedData;
    }

    public static void main(String[] args) {
        // Test the CRC Implementation
        String message = "Hello";
        byte[] data = message.getBytes();
        
        // Compute CRC
        byte crc = computeCRC(data);
        System.out.println("Original CRC: " + String.format("0x%02X", crc));

        // Verify Data
        System.out.println("Verification Passed: " + verifyCRC(data, crc));

        // Introduce Error
        byte[] corruptedData = introduceError(data, 0.3); // 30% probability of error
        System.out.println("Verification Passed After Corruption: " + verifyCRC(corruptedData, crc));
    }
}
