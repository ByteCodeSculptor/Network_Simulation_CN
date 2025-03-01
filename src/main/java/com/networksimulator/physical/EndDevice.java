// EndDevice.java
public class EndDevice {
    private String name;
    private Connection connection;

    public EndDevice(String name) {
        this.name = name;
    }

    public void connectTo(EndDevice device) {
        this.connection = new Connection(this, device);
    }

    public void sendData(String data) {
        connection.transmitData(data.getBytes());
    }

    public void receiveData(byte[] data) {
        // Process received data
    }
}
