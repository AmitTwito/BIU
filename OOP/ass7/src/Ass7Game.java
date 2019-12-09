import animations.HighScoresAnimation;
import animations.MenuAnimation;
import gamespecification.LevelSpecificationReader;
import interfaces.Animation;
import interfaces.Menu;
import interfaces.Task;
import score.HighScoresTable;
import tasks.ExitTask;
import tasks.ShowHiScoresTask;
import tasks.ShowSubMenuTask;
import tasks.StartLevelTask;
import utility.AnimationRunner;
import biuoop.GUI;
import game.GameFlow;
import interfaces.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The main class for assignment 6.
 * The Ass5Game class initializes and runs the animations.Game.
 *
 * @author Amit Twito
 * @since 29.5.19
 */
public class Ass7Game {

    /**
     * initializes and runs the animations.Game.
     *
     * @param args Array of String arguments from the command line.
     */
    public static void main(String[] args) {
        String path = "";
        if (args.length == 1) {
            path = args[0];
        } else {
            path = "level_sets.txt";
        }
        String line = "";
        String setOption = "";
        String[] options;
        String paths = "";
        Map<String, String> optionMessagesMap = new TreeMap<>();
        Map<String, String> optionPathesMap = new TreeMap<>();
        List<String> subMenuKeys = new ArrayList<>();

        String[] pathStrings;
        try {

            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.contains(":")) {
                        setOption = setOption + line + "\n";
                    } else {
                        paths = paths + line + "\n";

                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        options = setOption.split("\n");
        pathStrings = paths.split("\n");
        if (options.length != pathStrings.length) {
            throw new RuntimeException("Missing lines in level sets file.");
        } else {
            for (int i = 0; i < options.length; i++) {
                String[] keyVal = options[i].split(":");
                subMenuKeys.add(keyVal[0]);
                optionMessagesMap.put(keyVal[0], keyVal[1]);
                optionPathesMap.put(keyVal[0], pathStrings[i]);
            }
        }

        GUI gui = new GUI(AnimationRunner.GUI_TITLE, AnimationRunner.GUI_WIDTH, AnimationRunner.GUI_HEIGHT);
        AnimationRunner ar = new AnimationRunner(gui);
        HighScoresTable scoresTable = new HighScoresTable(8);
        File file = new File(HighScoresTable.FILE_NAME);
        try {
            scoresTable.load(file);
        } catch (FileNotFoundException e) { // Can't find file to open
            try {
                scoresTable.save(file);
            } catch (IOException el) {
                el.printStackTrace(System.err);
            }
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
        }

        Animation scores = new HighScoresAnimation(scoresTable);
        Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", gui.getKeyboardSensor());

        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor(), gui, scoresTable, scores, menu);

        Menu<Task<Void>> subMenu = new MenuAnimation<>("Level Sets", gui.getKeyboardSensor());
        for (String key : subMenuKeys) {
            List<LevelInformation> levelInformationList = null;
            LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
            InputStream is = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(optionPathesMap.get(key));
            if (is == null) {
                throw new RuntimeException("There was a problem reading the file: " + optionPathesMap.get(key));
            }
            InputStreamReader in = null;
            in = new InputStreamReader(is);

            levelInformationList = levelSpecificationReader.fromReader(in);


            subMenu.addSelection(key, optionMessagesMap.get(key), new StartLevelTask(gameFlow, levelInformationList));
        }
        menu.addSelection("s", "Start Game", new ShowSubMenuTask(ar, subMenu));
        menu.addSelection("h", "High Scores", new ShowHiScoresTask(ar, scores, gui.getKeyboardSensor()));
        menu.addSelection("q", "Exit", new ExitTask());
        while (true) {

            ar.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }

    }

}
