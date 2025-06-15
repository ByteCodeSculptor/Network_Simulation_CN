package com.networksimulator.network;
import java.util.*;

public class TransportLayer {
    private Map<Integer, String> portProcessMap = new HashMap<>();
    private Map<Integer, Queue<String>> portBuffers = new HashMap<>();
    private int nextEphemeralPort = 1024; // Start of ephemeral ports
    private static final int[] WELL_KNOWN_PORTS = {23, 7}; // 23: Telnet, 7: Echo

    // Assign a port to a process (well-known or ephemeral)
    public int assignPort(String processName, boolean isWellKnown) {
        int port = isWellKnown ? WELL_KNOWN_PORTS[portProcessMap.size() % WELL_KNOWN_PORTS.length] : nextEphemeralPort++;
        portProcessMap.put(port, processName);
        portBuffers.put(port, new LinkedList<>());
        System.out.println("[PortManager] Assigned port " + port + " to process " + processName);
        return port;
    }

    // Print current port mapping
    public void printPortMapping() {
        System.out.println("[PortManager] Current port mapping: " + portProcessMap);
    }

    // Simulate sending data from one process to another (direct, no protocol)
    public void sendData(int srcPort, int destPort, String data) {
        if (!portBuffers.containsKey(destPort)) {
            System.out.println("[Transport] Destination port not found.");
            return;
        }
        String message = "[From port " + srcPort + " to port " + destPort + "] " + data;
        portBuffers.get(destPort).add(message);
        System.out.println("[Transport] Sent: " + message);
    }

    // Simulate receiving data at a port
    public String receiveData(int port) {
        Queue<String> buffer = portBuffers.get(port);
        if (buffer == null || buffer.isEmpty()) {
            System.out.println("[Transport] No data at port " + port);
            return null;
        }
        String data = buffer.poll();
        System.out.println("[Transport] Received at port " + port + ": " + data);
        return data;
    }

    // Simulate Go-Back-N sliding window protocol
    public void sendWithGoBackN(int srcPort, int destPort, String data, int windowSize) {
        System.out.println("[Go-Back-N] Sending data from port " + srcPort + " to port " + destPort + " with window size " + windowSize);
        // For simplicity, treat each character as a "packet"
        int n = data.length();
        int base = 0;
        int nextSeq = 0;
        while (base < n) {
            // Send packets in window
            while (nextSeq < base + windowSize && nextSeq < n) {
                String packet = data.charAt(nextSeq) + "";
                System.out.println("[Go-Back-N] Sent packet seq#" + nextSeq + ": " + packet);
                portBuffers.get(destPort).add("[GBN-Packet-" + nextSeq + "] " + packet);
                nextSeq++;
            }
            // Simulate ACK (no actual loss or retransmission here)
            System.out.println("[Go-Back-N] ACK received for packet seq#" + base);
            base++;
        }
        System.out.println("[Go-Back-N] All data sent using Go-Back-N protocol.");
    }

    // ---- Application Layer Services (as inner classes for simplicity) ----
    public static class TelnetService {
        private final TransportLayer tl;
        private final int myPort;

        public TelnetService(TransportLayer tl, int myPort) {
            this.tl = tl;
            this.myPort = myPort;
        }

        // Simulate sending a command (as Telnet client)
        public void sendCommand(int destPort, String command) {
            System.out.println("[TelnetService] Sending command: " + command);
            tl.sendData(myPort, destPort, "TELNET-CMD: " + command);
        }

        // Simulate receiving a command (as Telnet server)
        public void receiveCommand() {
            String data = tl.receiveData(myPort);
            if (data != null) {
                System.out.println("[TelnetService] Received at Telnet: " + data);
            }
        }
    }

    public static class EchoService {
        private final TransportLayer tl;
        private final int myPort;

        public EchoService(TransportLayer tl, int myPort) {
            this.tl = tl;
            this.myPort = myPort;
        }

        // Echo whatever data is sent to this port
        public void echoBack(int destPort, String message) {
            System.out.println("[EchoService] Echoing message: " + message);
            tl.sendData(myPort, destPort, "ECHO: " + message);
        }

        public void receiveEcho() {
            String data = tl.receiveData(myPort);
            if (data != null) {
                System.out.println("[EchoService] Received at Echo: " + data);
            }
        }
    }
}
//----------------------------------------------------------------------------------------
// package com.networksimulator.network;
// import java.util.*;

// public class TransportLayer {
//     private Map<Integer, String> portProcessMap = new HashMap<>();
//     private int nextEphemeralPort = 1024; // Start of ephemeral ports
//     private static final int[] WELL_KNOWN_PORTS = {21, 23}; // FTP, Telnet, etc.

//     // Assign port (well-known or ephemeral)
//     public int assignPort(String processName, boolean isWellKnown) {
//         int port = isWellKnown ? WELL_KNOWN_PORTS[portProcessMap.size() % WELL_KNOWN_PORTS.length] : nextEphemeralPort++;
//         portProcessMap.put(port, processName);
//         System.out.println("Assigned port " + port + " to " + processName);
//         return port;
//     }

//     public void printPortMapping() {
//         System.out.println("Port Mapping: " + portProcessMap);
//     }

//     public void sendData(int srcPort, int destPort, String data) {
//         System.out.println("[Transport] Sending from port " + srcPort + " to " + destPort + ": " + data);
//         // Simulate encapsulation and delivery
//     }

//     public String receiveData(int port) {
//         // Simulate receiving data
//         System.out.println("[Transport] Receiving data on port " + port);
//         return "Simulated Data"; // For demo
//     }

//     public void sendWithGoBackN(int srcPort, int destPort, String data, int windowSize) {
//         System.out.println("[Go-Back-N] Sending '" + data + "' from " + srcPort + " to " + destPort + " with window size " + windowSize);
//         // Simulate sliding window protocol
//     }
// }
