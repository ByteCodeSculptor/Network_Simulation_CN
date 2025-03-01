package com.networksimulator.physical;

public class LineCoding {

    // Example of Non-Return-to-Zero (NRZ) line coding
    public static String encodeNRZ(String data) {
        StringBuilder encodedData = new StringBuilder();
        for (char bit : data.toCharArray()) {
            if (bit == '0') {
                encodedData.append("0000"); // Simulating the encoding of '0' as '0000'
            } else if (bit == '1') {
                encodedData.append("1111"); // Simulating the encoding of '1' as '1111'
            }
        }
        return encodedData.toString();
    }
}
