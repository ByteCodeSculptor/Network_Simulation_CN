// LineCoding.java
public class LineCoding {
    public static String encodeNRZ(String data) {
        // Example: Non-return-to-zero encoding
        return data.replaceAll("0", "0000").replaceAll("1", "1111");
    }
}
