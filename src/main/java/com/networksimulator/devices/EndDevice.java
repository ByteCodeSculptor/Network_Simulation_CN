package com.networksimulator.devices;

/**
 * EndDevice represents a host or terminal in the network. It extends Device and
 * provides its own implementation of sendData.
 */
public class EndDevice extends Device {

    /**
     * Constructor to initialize the EndDevice with a unique name.
     *
     * @param name Unique identifier for the EndDevice.
     */
    public EndDevice(String name) { //this name is inherited from parent class
        super(name);
        //Calls super(name); to invoke the Device constructor, which sets the device's name and initializes the list of connected devices.
    }

    /**
     * Implements sending data from this EndDevice to the receiver device. For
     * simplicity, this method simulates sending by directly calling the
     * receiver's receiveData method.
     *
     * @param receiver The device to which data is sent.
     * @param data The message to send.
     */
    @Override  //as the parent class device is abstract so that method is intiated here so we are overriding it
    public void sendData(Device receiver, String data) {
        System.out.println("[FromDevice: " + name + "] Sent data to " + receiver.getName() + ": " + data); //name of the device is achived using super keyword as this file is child class of Device
        // Directly invoke receiveData on the receiver device to simulate data transmission.
        //
        receiver.receiveData(data, this);
    }

}
