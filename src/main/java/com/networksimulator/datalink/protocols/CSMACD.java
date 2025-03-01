package com.networksimulator.datalink.protocols;

public class CSMACD {
    private boolean channelBusy;

    public CSMACD() {
        this.channelBusy = false;
    }

    // Method to simulate checking if the channel is clear
    public boolean isChannelClear() {
        return !channelBusy;
    }

    // Method to simulate data transmission
    public void transmitData(byte[] data) {
        if (isChannelClear()) {
            System.out.println("Transmitting data: " + new String(data));
            // Simulate the transmission process
            simulateTransmission();
        } else {
            System.out.println("Channel busy, retrying...");
            simulateBackoff();
        }
    }

    // Simulate the process of checking the channel and handling backoff
    private void simulateTransmission() {
        channelBusy = true;  // Assume the channel is occupied during transmission
        // After transmission, the channel becomes free
        channelBusy = false;
    }

    // Simulate the backoff process in case of collision
    private void simulateBackoff() {
        try {
            // Simulate random backoff time
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        transmitData("Retrying after backoff".getBytes());  // Retry transmission
    }
}
