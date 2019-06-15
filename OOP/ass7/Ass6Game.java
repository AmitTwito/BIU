import animations.HighScoresAnimation;
import animations.MenuAnimation;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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


        GUI gui = new GUI(AnimationRunner.GUI_TITLE, AnimationRunner.GUI_WIDTH, AnimationRunner.GUI_HEIGHT);
        AnimationRunner ar = new AnimationRunner(gui);
        HighScoresTable scoresTable = new HighScoresTable(8);
        File file = new File("highscores.ser");
        try {
			scoresTable.load(file);
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + file);
            return;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return;
        }

		Animation scores = new HighScoresAnimation(scoresTable);
        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor(), gui, scoresTable);

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
        menu.addSelection("s", "Start Game", new StartGameTask(gameFlow, levelInformationList));
        menu.addSelection("h", "Hi scores", new ShowHiScoresTask(ar, scores));
        menu.addSelection("q", "Exit", new ExitTask());

        while (true) {
            ar.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }

    }
}
