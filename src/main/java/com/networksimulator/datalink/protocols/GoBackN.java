package com.networksimulator.datalink.protocols;

import java.util.Random;

public class GoBackN {

    private static final int WINDOW_SIZE = 4; // Number of frames in the sliding window
    private static final int TOTAL_FRAMES = 10; // Total frames to be sent
    private static final double LOSS_PROBABILITY = 0.2; // Probability of frame loss

    private int base = 0; // Oldest unacknowledged frame
    private int nextSeqNum = 0; // Next frame to send
    private Random random = new Random();

    // Simulates sending frames using Go-Back-N
    public void sendFrames() {
        System.out.println("Go-Back-N: Starting frame transmission...");

        while (base < TOTAL_FRAMES) {
            // Send frames within window size
            while (nextSeqNum < base + WINDOW_SIZE && nextSeqNum < TOTAL_FRAMES) {
                sendFrame(nextSeqNum);
                nextSeqNum++;
            }

            // Wait for Acknowledgment (ACK)
            boolean ackReceived = receiveAcknowledgment();

            if (!ackReceived) {
                System.out.println("Timeout! Retransmitting from Frame " + base);
                nextSeqNum = base; // Retransmit all unacknowledged frames
            } else {
                base++; // Move window forward
            }
        }

        System.out.println("Go-Back-N: All frames transmitted successfully!");
    }

    // Simulates sending a frame
    private void sendFrame(int frameNumber) {
        System.out.println("Sending Frame " + frameNumber);
    }

    // Simulates receiving an acknowledgment (ACK) with a chance of loss
    private boolean receiveAcknowledgment() {
        boolean lost = random.nextDouble() < LOSS_PROBABILITY; // Simulate frame loss
        if (lost) {
            System.out.println("ACK lost! Resending frames...");
            return false;
        }
        System.out.println("ACK received for Frame " + base);
        return true;
    }

    public static void main(String[] args) {
        GoBackN goBackN = new GoBackN();
        goBackN.sendFrames();
    }
}
