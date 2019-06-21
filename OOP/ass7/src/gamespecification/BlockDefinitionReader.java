package gamespecification;

import collidables.Block;
import factory.BlocksFromSymbolsFactory;
import geometry.Point;
import geometry.Rectangle;
import interfaces.BlockCreator;
import utility.ColorParser;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The BlockDefinitionReader reads a file with block definitions and creates a BlocksFromSymbolsFactory.
 *
 * @author Amit Twito
 */
public class BlockDefinitionReader {

    /**
     * Returns a BlocksFromSymbolsFactory by a given block definitions file.
     *
     * @param reader Reader of files.
     * @return BlocksFromSymbolsFactory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

        String line;
        String mainString = "";
        String[] components;
        BufferedReader bufferedReader = null;


        try {
            bufferedReader = new BufferedReader(reader);

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("#")) {
                    mainString = mainString + line + "\n";
                }
            }

        } catch (IOException e) {
            System.out.println("Something went wrong while reading from the blocks definitions file!");
        } finally {
            if (bufferedReader != null) { // Exception might have happened at constructor
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }

        components = mainString.split("\n\\s*\n");
        Map<String, String> defaultPropsMap = new TreeMap<>();
        Map<String, Integer> spacerWidths = new TreeMap<>();
        Map<String, BlockCreator> blockCreators = new TreeMap<>();

        String[] defaultProps;
        String[] bdefStrings;
        String[] sdefStrings;
        for (String component : components) {
            if (component.contains("default ")) {
                defaultProps = component.substring("default ".length()).split(" ");
                for (String prop : defaultProps) {
                    String[] keyValue = prop.split(":");
                    defaultPropsMap.put(keyValue[0], keyValue[1]);
                }
            }

            if (component.contains("bdef ")) {
                bdefStrings = component.split("bdef ");
                for (String bdef : bdefStrings) {
                    if (!bdef.isEmpty()) {
                        final Map<String, String> properties = new TreeMap<>();
                        String[] props = bdef.split(" ");
                        for (String prop : props) {
                            if (!prop.isEmpty()) {
                                String[] keyVals = prop.split(":");
                                properties.put(keyVals[0], keyVals[1]);
                            }
                        }
                        BlockCreator blockCreator = new BlockCreator() {
                            /**
                             * Create a block at the specified location.
                             *
                             * @param xpos X position of the block.
                             * @param ypos Y position of the block.
                             * @return New Block.
                             */
                            @Override
                            public Block create(int xpos, int ypos) {
                                String width = properties.get("width");
                                if (width == null) {
                                    width = defaultPropsMap.get("width");
                                }
                                String height = properties.get("height");
                                if (height == null) {
                                    height = defaultPropsMap.get("height");
                                }
                                String hitPointsString = properties.get("hit_points");
                                if (hitPointsString == null) {
                                    hitPointsString = defaultPropsMap.get("hit_points");
                                }
                                int hitPoints = Integer.parseInt(hitPointsString);
                                String stroke = properties.get("stroke");
                                Color strokeColor = null;
                                if (stroke != null) {
                                    ColorParser colorParser = new ColorParser();
                                    String fillString = stroke.substring(stroke.indexOf("("), stroke.indexOf(")"));
                                    if (stroke.contains("RGB")) {
                                        String rgbString = stroke.substring(fillString.indexOf("("), fillString.indexOf(")"));

                                        String[] rgb = rgbString.split(",");
                                        int x = Integer.parseInt(rgb[0]);
                                        int y = Integer.parseInt(rgb[1]);
                                        int z = Integer.parseInt(rgb[2]);
                                        strokeColor = new Color(x, y, z);

                                    } else {
                                        strokeColor = colorParser.colorFromString(fillString);
                                    }
                                }
                                if (stroke == null) {
                                    stroke = defaultPropsMap.get("stroke");
                                    if (stroke != null) {
                                        ColorParser colorParser = new ColorParser();
                                        String fillString = stroke.substring(stroke.indexOf("(") + 1, stroke.indexOf(
                                                ")"));
                                        if (stroke.contains("RGB")) {
                                            String rgbString = stroke.substring(fillString.indexOf("("), fillString.indexOf(")"));

                                            String[] rgb = rgbString.split(",");
                                            int x = Integer.parseInt(rgb[0]);
                                            int y = Integer.parseInt(rgb[1]);
                                            int z = Integer.parseInt(rgb[2]);
                                            strokeColor = new Color(x, y, z);

                                        } else {
                                            strokeColor = colorParser.colorFromString(fillString);
                                        }
                                    }
                                }

                                List<String> fillingsList = new ArrayList<>();
                                String fill = properties.get("fill");
                                if (fill == null) {
                                    fill = defaultPropsMap.get("fill");
                                    if (fill == null) {
                                        fill = properties.get("fill-1");
                                        if (fill == null) {
                                            fill = defaultPropsMap.get("fill-1");
                                        }
                                    }
                                }
                                fillingsList.add(fill);
                                if (hitPoints > 1) {
                                    String fillKString = "fill-";
                                    for (int i = 2; i <= hitPoints; i++) {
                                        fill = properties.get(fillKString + i);
                                        if (fill == null) {
                                            fill = defaultPropsMap.get(fillKString + i);
                                        }
                                        fillingsList.add(fill);
                                    }
                                }
                                if (fillingsList == null || fillingsList.size() == 0) {
                                    throw new RuntimeException("fillinglist empty");
                                }
                                String[] fillings = fillingsList.toArray(new String[fillingsList.size()]);

                                Rectangle rectangle = new Rectangle(new Point(xpos, ypos), Double.parseDouble(width),
                                        Double.parseDouble(height));

                                return new Block(rectangle, hitPoints, fillings, strokeColor);
                            }
                        };
                        blockCreators.put(properties.get("symbol"), blockCreator);
                    }
                }
            }
            if (component.contains("sdef ")) {
                sdefStrings = component.split("sdef ");
                for (String spacer : sdefStrings) {
                    if (!spacer.isEmpty()) {
                        spacer = spacer.replace("\n", "")
                                .replace("\r", "");
                        String[] props = spacer.split(" ");
                        String symbol = "";
                        int width = 0;
                        for (String prop : props) {

                            if (prop.contains("symbol")) {
                                String[] keyValue = prop.split(":");
                                symbol = keyValue[1];
                            }
                            if (prop.contains("width")) {
                                String[] keyValue = prop.split(":");
                                width = Integer.parseInt(keyValue[1]);
                            }
                        }
                        spacerWidths.put(symbol, width);
                    }
                }
            }
        }


        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }
}
