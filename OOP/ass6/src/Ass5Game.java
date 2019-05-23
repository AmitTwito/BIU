import animations.Game;

/**
 * The main class for assignment 5.
 * The Ass5Game class initializes and runs the animations.Game.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class Ass5Game {

    /**
     * initializes and runs the animations.Game.
     *
     * @param args Array of String arguments from the command line.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
