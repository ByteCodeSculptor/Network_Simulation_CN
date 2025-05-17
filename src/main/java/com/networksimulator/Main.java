package com.networksimulator;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;


import com.networksimulator.devices.Bridge;
import com.networksimulator.devices.Device;
import com.networksimulator.devices.Hub;
import com.networksimulator.devices.Router;
import com.networksimulator.devices.Switch;
import com.networksimulator.protocols.AccessControl;
import com.networksimulator.protocols.ErrorControl;
import com.networksimulator.protocols.FlowControl;
import com.networksimulator.protocols.SlidingWindowFlowControl;
import com.networksimulator.topology.DotFileGenerator;
import com.networksimulator.topology.DotFileWriter;
import com.networksimulator.topology.DotImageGenerator;
import com.networksimulator.topology.Network;
import com.networksimulator.topology.TopologyManager;



public class Main {

    // Write MAC Learning Table to CSV
public static void writeMacTableToCSV(Map<String, String> macLearningTable, String filePath) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
        writer.println("MAC Address,Device");
        for (Map.Entry<String, String> entry : macLearningTable.entrySet()) {
            writer.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println("MAC Learning Table saved to " + filePath);
    } catch (Exception e) {
        System.out.println("Error writing MAC table: " + e.getMessage());
    }
}

// Write IP Learning Table to CSV
public static void writeIpTableToCSV(Map<String, String> ipLearningTable, String filePath) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
        writer.println("Device,IP Address");
        for (Map.Entry<String, String> entry : ipLearningTable.entrySet()) {
            writer.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println("IP Learning Table saved to " + filePath);
    } catch (Exception e) {
        System.out.println("Error writing IP table: " + e.getMessage());
    }
}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TopologyManager topologyManager = new TopologyManager();
        int connectionChoice = 0;
        int choice = 0;

        // Step 1: Get number of networks
        System.out.print("Enter the number of networks: ");
        int numNetworks = scanner.nextInt();

        for (int i = 1; i <= numNetworks; i++) {
            System.out.println("\nChoose topology for Network " + i + ":");
            System.out.println("1) Star");
            System.out.println("2) Mesh");
            System.out.println("3) Hybrid");
            choice = scanner.nextInt();
            topologyManager.createNetwork(i, choice);
        }

        // Step 2: Connect networks if there is more than one network
        if (numNetworks > 1) {
            System.out.println("\nChoose inter-network connection:");
            System.out.println("1) Bridge");
            System.out.println("2) Switch");
            System.out.println("3) Router");
            System.out.println("4) Hub");
            connectionChoice = scanner.nextInt();
            topologyManager.connectNetworks(connectionChoice);

        }

        // Step 3: Display the complete network topology
        System.out.println("\nDisplaying Network Topology:");
        topologyManager.displayTopology();

        // Step 3.5: Generate DOT file and image
        System.out.println("\nGenerating DOT file and topology image...");
        String dotData = DotFileGenerator.generateDot(topologyManager);//topologyManager object is created in this file itself
        //Step 3.6:  Write the DOT data to a file (e.g., topology.dot)
        DotFileWriter.writeDotFile(dotData, "topology.dot");
        //Step 3.7:  Generate an image (PNG) from the DOT data (e.g., topology.png)
        DotImageGenerator.generateImage(dotData, "topology.png");

        // Step 4: Data transfer simulation
        System.out.println("");
        System.out.println("\n--- Phase 1: Physical Layer and Data Link layer Simulation ---");
        System.out.println("\nAvailable Devices:");
        topologyManager.listAllDevices();
        System.out.print("\nChoose sender device: ");
        String senderId = scanner.next();
        System.out.print("Choose receiver device: ");
        String receiverId = scanner.next();

        //need some updates from here if required
        if (connectionChoice == 1) {
            Bridge bridge1 = new Bridge("InterNetworkBridge");
            bridge1.sendData(bridge1, dotData);
        }
        if (connectionChoice == 2) {
            Switch switch1 = new Switch("InterNetworkSwitch");
            switch1.printMACTable(receiverId);
            switch1.sendData(switch1, dotData);
        }
        if (connectionChoice == 3) {
            Router router = new Router("InterNetworkRouter");
            System.out.println("This is Layer3 Device need some updates");
        }
        if (connectionChoice == 4 || choice == 1) {
            Hub hub = new Hub("InterNetworkHub");
            hub.sendData(hub, "Hello");
        }

        String word = "Hello";
        String[] frames = word.split(""); // Splits into individual characters
        ErrorControl errorControl = new ErrorControl();
        errorControl.sendFrames(frames);

        String[] devCol = topologyManager.listAllDevices();
        AccessControl accessControl = new AccessControl();
        accessControl.sendData(devCol);
        //need some updates to here if required       
        topologyManager.sendData(senderId, receiverId, "Hello, " + receiverId + "!");

        FlowControl flowControl = new SlidingWindowFlowControl(3);
        // Or: FlowControl flowControl = new StopAndWaitFlowControl();

        // Simulate sending frames
        for (int i = 0; i < 5; i++) {
            if (flowControl.canSend()) {
                flowControl.frameSent();
            } else {
                System.out.println("[Main] Cannot send frame at index " + i + ". Waiting for ACK...");
            }
        }

        // Simulate receiving acknowledgments
        for (int i = 0; i < 3; i++) {
            flowControl.ackReceived();
        }

        // Try sending again
        for (int i = 0; i < 2; i++) {
            if (flowControl.canSend()) {
                flowControl.frameSent();
            }
        }

        // Reset the flow control
        flowControl.reset();

// Step 5: Phase 2: Network Layer simulation begins
        System.out.println("");
        System.out.println("\n--- Phase 2: Network Layer Simulation ---");

        
// 5.1 Assign IP addresses to devices
        // 5.1 Assign IP addresses and MAC addresses to devices
System.out.println("Assigning IP addresses and MAC addresses to routers and devices...");

Map<String, String> deviceIPMap = new HashMap<>();
Map<String, String> deviceMacMap = new HashMap<>();
int startingNetworkID = 1; // Start from Network 1
int baseOctet = 1;         // 192.168.<baseOctet>.x changes per network
int globalDeviceCounter = 1; // Used for MAC address uniqueness

/**
 * Generates a unique MAC address based on a device index.
 */



for (Map.Entry<Integer, Network> networkEntry : topologyManager.getNetworks().entrySet()) {
    Network currentNetwork = networkEntry.getValue();
    String networkBase = "192.168." + baseOctet + ".0";  // 192.168.1.0/29, 192.168.2.0/29 etc.

    int hostCounter = 1; // Host part starts from .1

    System.out.println("\nAssigning IPs for Network ID: " + networkEntry.getKey());

    for (Device device : currentNetwork.getDevices()) {
        if (!(device.getName().toLowerCase().contains("router")
                || device.getName().toLowerCase().contains("hub")
                || device.getName().toLowerCase().contains("switch")
                || device.getName().toLowerCase().contains("bridge"))) {

            // Assign IP inside this network block
            String assignedIP = "192.168." + baseOctet + "." + hostCounter;
            deviceIPMap.put(device.getName(), assignedIP);

            // Assign MAC address
            String assignedMAC = Hub.generateUniqueMacAddress(globalDeviceCounter++);
            deviceMacMap.put(device.getName(), assignedMAC);

            System.out.println("[INFO] Assigned IP " + assignedIP + " and MAC address " + assignedMAC +
                               " to device: " + device.getName());

            hostCounter++;

            if (hostCounter > 5) {  // Maximum 5 devices per network
                break;
            }
        }
    }
    baseOctet++; // Move to next network
}


if (connectionChoice == 3) {
    System.out.println("\nBuilding ARP tables and IP Learning from CSV...");

    Map<String, String> IPLearningTable = new HashMap<>();

    // Read the IP_Learning_Table.csv
    try (BufferedReader br = new BufferedReader(new FileReader("IP_Learning_Table.csv"))) {
        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
            if (firstLine) { // skip header
                firstLine = false;
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String deviceName = parts[0].trim();
                String ip = parts[1].trim();
                IPLearningTable.put(deviceName, ip);
            }
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Failed to read IP_Learning_Table.csv: " + e.getMessage());
        return;
    }

    // Check if destination IP is known
    if (IPLearningTable.containsKey(receiverId)) {
        String knownIP = IPLearningTable.get(receiverId);
        System.out.println("[IP Learning Table] Destination IP found! Forwarding frame directly to " + knownIP);
    } else {
        System.out.println("[IP Learning Table] Destination IP not found! Flooding frame to all devices...");

        for (Map.Entry<String, String> entry : IPLearningTable.entrySet()) {
            String deviceName = entry.getKey();
            String deviceIP = entry.getValue();
            String deviceNetworkID = deviceIP.split("\\.")[2];

            if (deviceName.equals(receiverId)) {
                System.out.println("Frames accepted by device : " + deviceName + " at IP: " + deviceIP);
            }
            if (deviceName.charAt(deviceName.length() - 1) == receiverId.charAt(receiverId.length() - 1)) {
                System.out.println("[IP Learning Table] Learning IP of " + deviceName + " at IP: " + deviceIP);
            }
        }
    }
}

else if (connectionChoice == 1 || connectionChoice == 2) {
    System.out.println("\nBuilding ARP tables and MAC Learning from CSV...");

    Map<String, String> MACLearningTable = new HashMap<>();

    // Read the MAC_Learning_Table.csv
    try (BufferedReader br = new BufferedReader(new FileReader("MAC_Learning_Table.csv"))) {
        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // skip header
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String mac = parts[0].trim();
                String deviceName = parts[1].trim();
                MACLearningTable.put(mac, deviceName);
            }
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Failed to read MAC_Learning_Table.csv: " + e.getMessage());
        return;
    }

    // Try to get MAC address of the receiver from the table
    String receiverMAC = null;
    for (Map.Entry<String, String> entry : MACLearningTable.entrySet()) {
        if (entry.getValue().equals(receiverId)) {
            receiverMAC = entry.getKey();
            break;
        }
    }

    if (receiverMAC != null && MACLearningTable.containsKey(receiverMAC)) {
        String knownDevice = MACLearningTable.get(receiverMAC);
        System.out.println("[MAC Learning Table] Destination MAC found! Forwarding frame directly to " + knownDevice);
    } else {
        System.out.println("[MAC Learning Table] Destination MAC not found! Flooding frame to all devices...");

        for (Map.Entry<String, String> entry : MACLearningTable.entrySet()) {
            String deviceName = entry.getValue();
            String mac = entry.getKey();

            if (deviceName.equals(receiverId)) {
                System.out.println("Frames accepted by device: " + deviceName + " with MAC: " + mac);
            }

            if (deviceName.charAt(deviceName.length() - 1) == receiverId.charAt(receiverId.length() - 1)) {
                System.out.println("[MAC Learning Table] Learning MAC of " + deviceName + " with MAC: " + mac);
            }
        }
    }
}

        


// Call this near the end of main(), before scanner.close()
writeMacTableToCSV(deviceMacMap, "MAC_Learning_Table.csv");
writeIpTableToCSV(deviceIPMap, "IP_Learning_Table.csv");


// 5.5 (Optional later) Run RIP dynamic updates
            scanner.close(); //its not mandatory but good practice to release the resources and clean the memory.
        
    }
}
//
