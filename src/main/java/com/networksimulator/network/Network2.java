package com.networksimulator.network;

public class Network2 {
    private String networkId;
    private String subnetMask;

    public Network2(String networkId, String subnetMask) {
        this.networkId = networkId;
        this.subnetMask = subnetMask;
    }

    public String getNetworkId() {
        return networkId;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    @Override
    public String toString() {
        return "Network{" +
                "networkId='" + networkId + '\'' +
                ", subnetMask='" + subnetMask + '\'' +
                '}';
    }
}
