package com.networksimulator.topology;

import java.io.File;
import java.io.IOException;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizException;

/**
 * DotImageGenerator generates an image from a DOT-format string. It uses the
 * Graphviz Java API to render the DOT data to a PNG image.
 */
public class DotImageGenerator {

    /**
     * Generates an image from the provided DOT data and saves it to the given
     * output path.
     *
     * @param dotData The DOT language representation of the network topology.
     * @param outputPath The file path where the generated image should be
     * saved.
     */
    public static void generateImage(String dotData, String outputPath) {
        try {
            Graphviz.fromString(dotData)
                    .render(Format.PNG)
                    .toFile(new File(outputPath));
            System.out.println("Image generated successfully at " + outputPath);
        } catch (IOException | GraphvizException e) {
            System.err.println("Error generating image from DOT data:");
            e.printStackTrace();
        }
    }
}
