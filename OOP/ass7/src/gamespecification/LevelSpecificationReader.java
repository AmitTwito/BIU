package gamespecification;

import interfaces.LevelInformation;
import level.CustomLevel;
import movement.Velocity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelSpecificationReader {

    public List<LevelInformation> fromReader(java.io.Reader reader) {

        List<LevelInformation> levelInformationList = new ArrayList<>();

        //BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        String mainString = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                String newLine;
                if (!line.contains("level_name")) {
                    newLine = line.replaceAll("\\s+", "");
                } else {
                    newLine = line;
                }
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

        //String[] levelsString = mainString.split("START_LEVEL");
        String[] levelsString = Arrays.asList(mainString.split("START_LEVEL")).stream().
                filter(str -> !str.isEmpty()).collect(Collectors.toList()).toArray(new String[0]);
        for (String levelString : levelsString) {

            String[] properties = levelString.split("\n");
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
			CustomLevel customLevel = new CustomLevel(levelName, ballVelocities, background,
					paddleSpeed, paddleWidth);
			levelInformationList.add(customLevel);
        }
        return levelInformationList;
    }
}