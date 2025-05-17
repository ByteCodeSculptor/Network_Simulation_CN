package com.networksimulator.protocols;

import java.util.Random;

public class AccessControl {

    // Simulates ALOHA access control for sending data
    public void sendData(String[] devices) {
        
        Random rand = new Random();

        for (String device : devices) {
            System.out.println(device + " attempting to send data...");

            boolean collision = simulateCollision();

            if (!collision) {
                System.out.println("Data sent successfully by " + device);
            } else {
                System.out.println("Collision detected for " + device + "! Retrying...");
                int backoffTime = rand.nextInt(5) + 1; // Random backoff between 1-5 units
                System.out.println(device + " waiting for " + backoffTime + " time units.");
                // Simulate wait (not necessary to sleep in simulator)
            }
        }

        System.out.println("Access Control Simulation Completed.");
    }

    // Randomly decides whether collision occurred
    private boolean simulateCollision() {
        double probability = Math.random();
        return probability < 0.3; // 30% chances of collision
    }
}
