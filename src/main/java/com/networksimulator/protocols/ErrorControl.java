package com.networksimulator.protocols;

public class ErrorControl {

    // Method to simulate sending frames using Stop-and-Wait ARQ
    public void sendFrames(String[] frames) {
        int i = 0;
        while (i < frames.length) {
            System.out.println("Sending Frame: " + frames[i]);

            // Simulate ACK or timeout (pseudo-logic)
            boolean ackReceived = simulateAck(frames[i]);

            if (ackReceived) {
                System.out.println("ACK received for Frame: " + frames[i]);
                i++; // Move to next frame
            } else {
                System.out.println("Timeout/Error! Resending Frame: " + frames[i]);
                // i stays the same to resend the same frame
            }
        }
        System.out.println("All frames sent successfully.");
    }

    private boolean simulateAck(String frame) {
        // This randomly returns true or false to simulate success/failure
        double random = Math.random();
        return random > 0.2; // 80% chance of ACK received , chances of error
    }
}

