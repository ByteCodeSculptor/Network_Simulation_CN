# Java Network Simulator

This project is a layered **Network Simulator** built in Java that simulates real-world network devices and behavior across the OSI model layers — including Physical, Data Link, and Network layers.

---

## 🌐 Features

### ✅ Phase 1 – Physical & Data Link Layer Simulation
- Support for multiple network topologies: **Star**, **Mesh**, and **Hybrid**
- Devices: **Hub**, **Switch**, **Bridge**, **Router**
- Inter-network connectivity based on user-selected device
- Device-to-device data transfer simulation
- Error Control, Access Control, Flow Control using Stop-and-Wait / Sliding Window
- DOT file + PNG topology image generation using Graphviz

### ✅ Phase 2 – Network Layer Simulation
- **Unique IP and MAC address assignment** to host devices
- **IP-based flooding and learning (Router)** using ARP-like logic
- **MAC-based learning (Switch/Bridge)** with broadcast and table update
- **Static routing table simulation** per router
- Simulation-ready structure to add dynamic routing protocols (e.g., RIP)

---

## 🗂️ Folder Structure

```
com/
└── networksimulator/
    ├── devices/         # Network devices like Router, Switch, Bridge, Hub
    ├── protocols/       # ErrorControl, AccessControl, FlowControl logic
    ├── topology/        # Network creation, DOT generation, image writing
    └── Main.java        # Central simulation runner
```

---

## 🛠 How to Run

### ✅ Prerequisites
- Java 8 or later
- Graphviz installed (for generating topology images)

### ✅ Steps

1. Clone or download the project
2. Compile and run `Main.java`
3. Follow the prompts to:
   - Enter number of networks
   - Choose topology for each
   - Select inter-network device (Bridge/Switch/Router/Hub)
   - Choose sender and receiver
4. View:
   - Device communication logs
   - IP and MAC assignments
   - ARP table construction and learning
   - Output files:
     - `topology.dot`
     - `topology.png`

---

## 📂 Sample Output (Console)

```
Assigning IPs for Network ID: 1
[INFO] Assigned IP 192.168.1.1 and MAC address AA:BB:CC:00:00:01 to device: Device1_N1
...
[IP Learning Table] Destination IP not found! Flooding frame...
[MAC Learning Table] Learning MAC of Device2_N1 with MAC: AA:BB:CC:00:00:02
```

---

## 📈 Future Enhancements
- Implement RIP protocol
- Add support for VLSM and CIDR-based routing
- Export logs to file (.csv or .json)
- GUI for live packet flow visualization

---

## 👩‍🏫 Use Case

Designed for **Computer Networks Lab (ITL355)** to simulate layered protocol behavior and demonstrate:
- Subnetting, ARP, and MAC flooding
- IP learning and static routing
- End-to-end communication under different device constraints

---

## 📄 License

