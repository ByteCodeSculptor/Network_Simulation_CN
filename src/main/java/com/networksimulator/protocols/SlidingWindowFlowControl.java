package com.networksimulator.protocols;

/**
 * SlidingWindowFlowControl is an implementation of the FlowControl protocol
 * using a sliding window approach. The sender can send multiple frames (up to a
 * specified window size) before waiting for acknowledgments.
 */
public class SlidingWindowFlowControl implements FlowControl {

    // The maximum number of frames that can be sent without receiving an acknowledgment.
    private int windowSize;
    // The current number of outstanding (sent but not yet acknowledged) frames.
    private int outstandingFrames;

    /**
     * Constructor for SlidingWindowFlowControl.
     *
     * @param windowSize The size of the sliding window (i.e., the maximum
     * number of unacknowledged frames allowed).
     */
    public SlidingWindowFlowControl(int windowSize) {
        this.windowSize = windowSize;
        this.outstandingFrames = 0;
    }

    /**
     * Checks if a new frame can be sent. In the sliding window protocol, a new
     * frame can be sent if the number of outstanding frames is less than the
     * window size.
     *
     * @return true if a new frame can be sent; false otherwise.
     */
    @Override
    public boolean canSend() {
        return outstandingFrames < windowSize;
    }

    /**
     * Called when a frame is sent. This method increments the count of
     * outstanding frames.
     */
    @Override
    public void frameSent() {
        outstandingFrames++;
        System.out.println("[SlidingWindowFlowControl] Frame sent. Outstanding frames: " + outstandingFrames);
    }

    /**
     * Called when an acknowledgment is received. This method decrements the
     * count of outstanding frames.
     */
    @Override
    public void ackReceived() {
        if (outstandingFrames > 0) {
            outstandingFrames--;
            System.out.println("[SlidingWindowFlowControl] Acknowledgment received. Outstanding frames: " + outstandingFrames);
        }
    }

    /**
     * Resets the flow control state. This method resets the count of
     * outstanding frames, typically used after a timeout or error.
     */
    @Override
    public void reset() {
        outstandingFrames = 0;
        System.out.println("[SlidingWindowFlowControl] Flow control state reset. Outstanding frames cleared.");
    }
}
