package com.networksimulator.datalink.protocols;

import java.util.Random;

public class AccessControl {

    private static final int MAX_BACKOFF = 10; // Maximum wait time for retransmission
    private static final Random random = new Random();
    private boolean channelBusy = false;

    // Simulates CSMA/CD (Carrier Sense Multiple Access with Collision Detection)
    public synchronized void sendData(String sender, byte[] data) {
        System.out.println(sender + " is trying to send data...");

        // Step 1: Carrier Sense - Check if channel is busy
        if (channelBusy) {
            System.out.println(sender + " detects channel is busy. Waiting...");
            waitForChannel();
        }

        // Step 2: Start Transmission
        System.out.println(sender + " is sending data...");
        channelBusy = true;

        // Step 3: Simulate Collision Detection
        if (random.nextDouble() < 0.3) { // 30% chance of collision
            System.out.println(sender + " detects a collision! Initiating backoff...");
            handleCollision(sender);
            return; // Abort transmission
        }

        // Step 4: Successful Transmission
        System.out.println(sender + " successfully sent the data!");
        channelBusy = false; // Free the channel after transmission
    }

    // Wait until the channel is free
    private void waitForChannel() {
        while (channelBusy) {
            try {
                Thread.sleep(100); // Wait before checking again
            } catch (InterruptedException e) {
                System.err.println("Error: Transmission interrupted.");
            }
        }
    }

    // Handles collision by applying exponential backoff
    private void handleCollision(String sender) {
        int backoffTime = random.nextInt(MAX_BACKOFF) * 100; // Random backoff time
        System.out.println(sender + " is waiting for " + backoffTime + " ms before retrying...");
        try {
            Thread.sleep(backoffTime); // Simulate backoff delay
        } catch (InterruptedException e) {
            System.err.println("Error: Backoff interrupted.");
        }
        sendData(sender, "Retrying after collision...".getBytes()); // Retry transmission
    }

    // Reset channel state (Used for debugging and simulations)
    public void resetChannel() {
        channelBusy = false;
    }

    public static void main(String[] args) {
        AccessControl csma = new AccessControl();

        // Simulate two devices trying to send data at the same time
        new Thread(() -> csma.sendData("Device 1", "Hello from Device 1".getBytes())).start();
        new Thread(() -> csma.sendData("Device 2", "Hello from Device 2".getBytes())).start();
    }
}
