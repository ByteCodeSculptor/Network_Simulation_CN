// ErrorControlTest.java (as a test example)
public class ErrorControlTest {
    @Test
    public void testSendDataBetweenEndDevices() {
        EndDevice device1 = new EndDevice("Device1");
        EndDevice device2 = new EndDevice("Device2");
        
        device1.connectTo(device2);
        device1.sendData("Hello, Device2!");

        // Assert that device2 receives the data
    }
}
