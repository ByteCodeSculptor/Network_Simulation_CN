package com.networksimulator.protocols;

/**
 * StopAndWaitFlowControl is an implementation of the FlowControl protocol using
 * the Stop-and-Wait method. In this approach, the sender transmits one frame
 * and then waits for an acknowledgment before sending the next frame.
 */
public class StopAndWaitFlowControl implements FlowControl {

    // Flag indicating whether the sender is currently waiting for an acknowledgment.
    private boolean waitingForAck;

    /**
     * Constructor initializes the flow control state.
     */
    public StopAndWaitFlowControl() {
        waitingForAck = false;
    }

    /**
     * Determines if the sender can send a new frame. In Stop-and-Wait, a new
     * frame can be sent only if the sender is not waiting for an
     * acknowledgment.
     *
     * @return true if not waiting for an acknowledgment; false otherwise.
     */
    @Override
    public boolean canSend() {
        return !waitingForAck;
    }

    /**
     * Called when a frame is sent. This method sets the state to waiting for an
     * acknowledgment.
     */
    @Override
    public void frameSent() {
        waitingForAck = true;
        System.out.println("[StopAndWaitFlowControl] Frame sent. Waiting for acknowledgment...");
    }

    /**
     * Called when an acknowledgment is received. This method resets the state,
     * allowing the sender to transmit the next frame.
     */
    @Override
    public void ackReceived() {
        waitingForAck = false;
        System.out.println("[StopAndWaitFlowControl] Acknowledgment received. Ready to send next frame.");
    }

    /**
     * Resets the flow control state, for example after a timeout.
     */
    @Override
    public void reset() {
        waitingForAck = false;
        System.out.println("[StopAndWaitFlowControl] Flow control state reset.");
    }
}
