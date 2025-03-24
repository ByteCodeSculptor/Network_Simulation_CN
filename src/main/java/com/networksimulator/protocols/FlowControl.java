package com.networksimulator.protocols;

/**
 * FlowControl defines the contract for flow control protocols.
 * It includes methods to determine if a sender can transmit a new frame,
 * and to update the flow control state when frames are sent or acknowledged.
 */
public interface FlowControl {

    /**
     * Checks if the sender is allowed to send a new frame.
     *
     * @return true if the sender can send, false otherwise.
     */
    boolean canSend();

    /**
     * Notifies the flow control mechanism that a frame has been sent.
     * This method should be called immediately after sending a frame.
     */
    void frameSent();

    /**
     * Notifies the flow control mechanism that an acknowledgment has been received.
     * This should be called when the receiver confirms successful delivery.
     */
    void ackReceived();

    /**
     * Resets the flow control state, typically used after a timeout, error,
     * or when reinitializing the connection.
     */
    void reset();
}
 