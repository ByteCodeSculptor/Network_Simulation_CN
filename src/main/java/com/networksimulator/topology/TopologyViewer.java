package com.networksimulator.topology;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * TopologyViewer displays the network topology image generated from the DOT
 * file. It creates a simple Swing JFrame, loads the image from a file, and
 * displays it.
 */
public class TopologyViewer {

    public static void main(String[] args) {
        // Specify the path to the generated topology image.
        String imagePath = "topology.png";

        // Create an ImageIcon from the image file.
        ImageIcon icon = new ImageIcon(imagePath);

        // Create a JLabel to hold the image.
        JLabel imageLabel = new JLabel(icon);

        // Create the main application window (JFrame)
        JFrame frame = new JFrame("Network Topology Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);

        // Adjust the frame size to fit the image.
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
