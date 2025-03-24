package com.networksimulator.topology;

import java.io.FileWriter;
import java.io.IOException;

/**
 * DotFileWriter is a helper class that writes a DOT description string to a file.
 * This file can then be processed by Graphviz to generate an image of the network topology.
 */
public class DotFileWriter {

    /**
     * Writes the provided DOT data to the specified file path.
     *
     * @param dotData  The DOT language representation of the topology.
     * @param filePath The file path where the DOT data should be saved.
     */
    public static void writeDotFile(String dotData, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(dotData);
            System.out.println("DOT file written successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing DOT file:");
            e.printStackTrace();
        }
    }
}
