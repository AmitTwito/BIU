package gamespecification;

import factory.BlocksFromSymbolsFactory;
import interfaces.LevelInformation;
import level.CustomLevel;
import movement.Velocity;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelSpecificationReader {

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
                } else {

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
        String[] levelsString = Arrays.asList(mainString.split("START_LEVEL")).stream().
                filter(str -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
        for (String levelString : levelsString) {
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
                        background = property.substring(property.indexOf("(") + 1, property.indexOf(")"));
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
            BlockDefinitionReader blockDefinitionReader = new BlockDefinitionReader();
            BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
            try {
                in = new InputStreamReader(
                        new FileInputStream(blocksDefinitionPath));
                blocksFromSymbolsFactory = blockDefinitionReader.fromReader(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (String property : blocksProperties) {


            }
            CustomLevel customLevel = new CustomLevel(levelName, ballVelocities, background,
                    paddleSpeed, paddleWidth, blocksFromSymbolsFactory);
            levelInformationList.add(customLevel);
        }
        return levelInformationList;
    }
}