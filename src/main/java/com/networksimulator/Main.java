package com.networksimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.networksimulator.physical.EndDevice;
import com.networksimulator.physical.Hub;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Choose topology type
        System.out.println("Choose topology type (1: Star, 2: Mesh, 3: Dedicated Connection): ");
        int topologyChoice = scanner.nextInt();

        if (topologyChoice == 1) {
            // Star topology: All devices connected to a central hub
            List<EndDevice> devices = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                devices.add(new EndDevice("Device" + i));
            }
            Hub hub = new Hub();
            for (EndDevice device : devices) {
                hub.addDevice(device);
            }
            System.out.println("Devices connected in Star topology via Hub.");
            // Simulate sending data from Device1 to Device5
            devices.get(0).sendData("Hello, Device5!");
        } else if (topologyChoice == 2) {
            // Mesh topology: Connect all devices with each other
            List<EndDevice> devices = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                devices.add(new EndDevice("Device" + i));
            }
            for (int i = 0; i < devices.size(); i++) {
                for (int j = i + 1; j < devices.size(); j++) {
                    devices.get(i).connectTo(devices.get(j));
                    System.out.println(devices.get(i).getName() + " connected to " + devices.get(j).getName());
                }
            }
            System.out.println("Devices connected in Mesh topology.");
            // Simulate sending data from Device1 to Device5
            devices.get(0).sendData("Hello, Device5!");
        } else if (topologyChoice == 3) {
            // Dedicated connection between two user-defined devices
            System.out.println("How many devices would you like to create?");
            int numDevices = scanner.nextInt();

            // Create the devices dynamically based on user input
            List<EndDevice> devices = new ArrayList<>();
            for (int i = 1; i <= numDevices; i++) {
                devices.add(new EndDevice("Device" + i));
            }

            // Show the available devices
            System.out.println("Available devices:");
            for (int i = 0; i < devices.size(); i++) {
                System.out.println((i + 1) + ": " + devices.get(i).getName());
            }

            // Ask the user to select two devices to connect
            System.out.println("Choose the first device to connect (1 to " + numDevices + "): ");
            int device1Choice = scanner.nextInt();
            System.out.println("Choose the second device to connect (1 to " + numDevices + "): ");
            int device2Choice = scanner.nextInt();

            // Ensure the selected devices are valid and not the same
            if (device1Choice == device2Choice) {
                System.out.println("You cannot connect the same device. Please choose two different devices.");
            } else {
                // Get the selected devices
                EndDevice device1 = devices.get(device1Choice - 1);  // Subtract 1 for 0-based index3
                EndDevice device2 = devices.get(device2Choice - 1);

                // Create a dedicated connection between the two chosen devices
                device1.connectTo(device2);
                System.out.println(device1.getName() + " sending data to " + device2.getName());

                // Simulate data transmission from device1 to device2
                device1.sendData("Hello, " + device2.getName() + "!");
            }
        } else {
            System.out.println("Invalid topology choice.");
        }

        scanner.close();
    }
}
