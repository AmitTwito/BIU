package gamespecification;

import factory.BlocksFromSymbolsFactory;
import interfaces.LevelInformation;
import game.CustomLevel;
import movement.Velocity;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

/**
 * The LevelSpecificationReader reads a file with Level definitions and creates a List<LevelInformation> .
 *
 * @author Amit Twito
 */
public class LevelSpecificationReader {

    /**
     * Returns a List<LevelInformation> by a given level definitions file.
     *
     * @param reader Reader of files.
     * @return List<LevelInformation>.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {

        List<LevelInformation> levelInformationList = new ArrayList<>();

        String line = "";
        String mainString = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                java.lang.String newLine;
                if (!line.contains("level_name")) {
                    newLine = line.replaceAll("\\s+", "");
                }
                newLine = line;

                if (!newLine.isEmpty()) {
                    if (!newLine.contains("#") && !newLine.equals("END_LEVEL")) {
                        mainString = mainString + newLine + "\n";
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading from the level definitions file!");
        } finally {
            if (bufferedReader != null) { // Exception might have happened at constructor
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }

        String[] levelsString = mainString.split("START_LEVEL");
        for (String levelString : levelsString) {
            if (!levelString.isEmpty()) {
                if (!levelString.contains("level_name") || !levelString.contains("ball_velocities")
                        || !levelString.contains("background") || !levelString.contains("paddle_speed")
                        || !levelString.contains("paddle_width") || !levelString.contains("block_definitions")
                        || !levelString.contains("blocks_start_x") || !levelString.contains("blocks_start_y")
                        || !levelString.contains("row_height") || !levelString.contains("num_blocks")
                        || !levelString.contains("START_BLOCKS") || !levelString.contains("END_BLOCKS")) {
                    System.exit(0);
                }
                String[] allProperties = levelString.split("block_definitions:");

                String[] properties = allProperties[0].split("\n");
                String levelName = "";
                List<Velocity> ballVelocities = new ArrayList<>();
                String background = "";
                int paddleSpeed = 0;
                int paddleWidth = 0;
                for (String property : properties) {

                    if (!property.isEmpty()) {
                        if (property.contains("level_name:")) {
                            levelName = property.substring(property.indexOf(":") + 1);
                        }
                        if (property.contains("ball_velocities:")) {

                            String[] velocitiesStrings = property.substring(property.indexOf(":") + 1).split(" ");

                            for (String v : velocitiesStrings) {
                                String[] vParts = v.split(",");
                                ballVelocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(vParts[0]),
                                        Double.parseDouble(vParts[1])));
                            }
                        }
                        if (property.contains("background:")) {
                            background = property.substring(property.indexOf(":") + 1);
                        }
                        if (property.contains("paddle_speed:")) {
                            paddleSpeed = Integer.parseInt(property.substring(property.indexOf(":") + 1));
                        }
                        if (property.contains("paddle_width:")) {
                            paddleWidth = Integer.parseInt(property.substring(property.indexOf(":") + 1));
                        }
                    }
                }
                String blocksDefinitionPath = allProperties[1].substring(0, allProperties[1]
                        .indexOf("blocks_start_x"))
                        .replace("\n", "").replace("\r", "");
                String blockPropertiesString = allProperties[1].substring(blocksDefinitionPath.length(),
                        allProperties[1].indexOf("START_BLOCKS"));
                String blockSymbolsString =
                        allProperties[1].substring((blocksDefinitionPath + blockPropertiesString).length()
                                + ("START_BLOCKS").length(), allProperties[1].indexOf("END_BLOCKS"));
                String[] blocksProperties = blockPropertiesString.split("\n");
                InputStreamReader in = null;
                BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
                InputStream is = ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(blocksDefinitionPath);
                if (is == null) {
                    throw new RuntimeException("There was a problem reading the file: " + blocksDefinitionPath);
                }
                in = new InputStreamReader(is);
                blocksFromSymbolsFactory = BlockDefinitionReader.fromReader(in);
                Map<String, Integer> blockPropertiesMap = new TreeMap<>();
                for (String property : blocksProperties) {
                    if (!property.isEmpty()) {
                        String[] keyVal = property.split(":");
                        blockPropertiesMap.put(keyVal[0], Integer.parseInt(keyVal[1]));
                    }
                }
                CustomLevel customLevel = new CustomLevel(levelName, ballVelocities, background, paddleSpeed,
                        paddleWidth, blockPropertiesMap.get("row_height"), blockPropertiesMap.get("num_blocks"));
                customLevel.setBlocksFromSymbolsFactory(blocksFromSymbolsFactory);
                customLevel.setBlockStartX(blockPropertiesMap.get("blocks_start_x"));
                customLevel.setBlockStartY(blockPropertiesMap.get("blocks_start_y"));
                customLevel.setBlockSymbolsString(blockSymbolsString);
                levelInformationList.add(customLevel);
            }
        }
        return levelInformationList;
    }
}