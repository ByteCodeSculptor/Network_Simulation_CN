package com.networksimulator.protocols;

/**
 * CSMA_CD is an implementation of the AccessControl interface that simulates
 * the Carrier Sense Multiple Access with Collision Detection (CSMA/CD)
 * protocol. It checks whether the medium is free, marks the medium as busy when
 * transmission starts, and frees the medium when transmission ends.
 */
public class Aloha extends AccessControl {

    // Boolean flag representing whether the medium is free (true) or busy (false)
    private boolean mediumFree;

    /**
     * Constructor for Aloha. Initially, the medium is assumed to be free.
     */
    public Aloha() {
        this.mediumFree = true;
    }

    
    

    /**
     * Simulates collision detection. In this simple simulation, the method
     * prints a collision message and resets the medium status.
     */
    public void detectCollision() {
        System.out.println("[Aloha] Collision detected! Initiating backoff and resetting medium status.");
        // In a real scenario, a random backoff algorithm would be applied here.
        mediumFree = true;
    }
}
