import animations.AnimationRunner;
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
        AnimationRunner ar = new AnimationRunner();
        GameFlow gameFlow = new GameFlow(ar, ar.getGui().getKeyboardSensor());

        List<LevelInformation> levelInformationList = new ArrayList<>();

        //levelInformationList.add(new FirstLevel());
        levelInformationList.add(new SecondLevel());
        levelInformationList.add(new ThirdLevel());
        levelInformationList.add(new FourthLevel());

        gameFlow.runLevels(levelInformationList);
    }
}
