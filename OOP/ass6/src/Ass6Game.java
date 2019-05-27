import animations.AnimationRunner;
import biuoop.GUI;
import game.GameFlow;
import interfaces.LevelInformation;
import level.FirstLevel;
import level.FourthLevel;
import level.SecondLevel;
import level.ThirdLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for assignment 5.
 * The Ass5Game class initializes and runs the animations.Game.
 *
 * @author Amit Twito
 * @since 15.5.19
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
        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor(), gui);

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
			}
		}
		gameFlow.runLevels(levelInformationList);


        //levelInformationList.add(new FirstLevel());
        //levelInformationList.add(new SecondLevel());
        levelInformationList.add(new ThirdLevel());
        levelInformationList.add(new FourthLevel());
        gameFlow.runLevels(levelInformationList);


    }
}
