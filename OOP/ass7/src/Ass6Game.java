import animations.HighScoresAnimation;
import animations.MenuAnimation;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import gamespecification.LevelSpecificationReader;
import interfaces.Animation;
import interfaces.Menu;
import interfaces.Task;
import score.HighScoresTable;
import tasks.ExitTask;
import tasks.ShowHiScoresTask;
import tasks.StartGameTask;
import utility.AnimationRunner;
import biuoop.GUI;
import game.GameFlow;
import interfaces.LevelInformation;
import level.FirstLevel;
import level.FourthLevel;
import level.SecondLevel;
import level.ThirdLevel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for assignment 6.
 * The Ass5Game class initializes and runs the animations.Game.
 *
 * @author Amit Twito
 * @since 29.5.19
 */
public class Ass6Game {

    /**
     * initializes and runs the animations.Game.
     *
     * @param args Array of String arguments from the command line.
     */
    public static void main(String[] args) {


        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(
                    new FileInputStream("level_definitions.txt"));
            levelSpecificationReader.fromReader(in);
        } catch (IOException e) {

        }


        GUI gui = new GUI(AnimationRunner.GUI_TITLE, AnimationRunner.GUI_WIDTH, AnimationRunner.GUI_HEIGHT);
        AnimationRunner ar = new AnimationRunner(gui);
        HighScoresTable scoresTable = new HighScoresTable(8);
        File file = new File(HighScoresTable.FILE_NAME);
        System.out.println(file.getAbsolutePath());
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


        LevelInformation[] levelInformations = new LevelInformation[4];
        levelInformations[0] = new FirstLevel();
        levelInformations[1] = new SecondLevel();
        levelInformations[2] = new ThirdLevel();
        levelInformations[3] = new FourthLevel();

        List<LevelInformation> levelInformationList = new ArrayList<>();

        for (String s : args) {
            try {
                int num = Integer.parseInt(s);
                levelInformationList.add(levelInformations[num - 1]);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        if (levelInformationList.size() == 0) {
            levelInformationList.add(new FirstLevel());
            levelInformationList.add(new SecondLevel());
            levelInformationList.add(new ThirdLevel());
            levelInformationList.add(new FourthLevel());
        }

        Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", gui.getKeyboardSensor());
        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor(), gui, scoresTable, levelInformationList, scores);
        menu.addSelection("s", "Start Game", new StartGameTask(gameFlow, levelInformationList));
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
