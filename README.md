# Network Simulator

A Java-based network simulation tool that visualizes and simulates network topologies using DOT files and Graphviz.

## 📁 Project Structure

```
network-simulator/
├── src/                  # Java source files
├── target/               # Maven build output
├── topology.dot          # Graphviz DOT file defining the network topology
├── topology.png          # Rendered image of the topology
├── pom.xml               # Maven configuration file
├── .vscode/              # VSCode settings (optional)
└── .git/                 # Git repository metadata
```

## ⚙️ Requirements

- Java 17+
- Maven
- Graphviz (for generating PNGs from `.dot` files)

## 📦 Dependencies

From `pom.xml`:
- `junit-jupiter-api` (for unit testing)
- `graphviz-java` (for Graphviz rendering)

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd network-simulator
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.network.Main"
   ```

   > Replace `com.network.Main` with your actual main class if different.

4. **View Topology**
   - Edit the `topology.dot` file to define network nodes and edges.
   - Use Graphviz to render it:
     ```bash
     dot -Tpng topology.dot -o topology.png
     ```

## 🧪 Running Tests

```bash
mvn test
```

## ✍️ Contributing

1. Fork this repository
2. Create your feature branch: `git checkout -b feature/AmazingFeature`
3. Commit your changes: `git commit -m 'Add some AmazingFeature'`
4. Push to the branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request

## 📄 License

This project is for educational purpose only.
