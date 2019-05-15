/**
 * The main class for assignment 5.
 * The Ass5Game class initializes and runs the Game.
 *
 * @author Amit Twito
 * @since 4.4.19
 */
public class Ass5Game {

    /**
     * initializes and runs the Game.
     *
     * @param args Array of String arguments from the command line.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
