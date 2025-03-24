package com.networksimulator;

import java.util.Scanner;

import com.networksimulator.devices.Bridge;
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
import com.networksimulator.topology.TopologyManager;

public class Main {

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
            switch1.printMACTable();
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


        scanner.close(); //its not mandatory but good practice to release the resources and clean the memory.
    }
}
//

