import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.Random;

/**
 * The Game class represents a game object.
 * A Game object is built with a collection of colliadble objects and a collections of Sprites.
 * A Game object handles a game: initializes and runs.
 * The game itself is a game where there are blocks on the screen, a ball and a movable paddle,
 * controlled by the player, and the ball can collied with the
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class Game {

    //Constants.

    public static final String GUI_TITLE = "Title";
    public static final int GUI_WIDTH = 1000;
    public static final int GUI_HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECONDS = 1000;
    public static final int PADDLE_WIDTH = 90;
    public static final int RADIUS = 8;
    public static final double BLOCK_WIDTH = 60;
    public static final double BLOCK_HEIGHT = 25;
    public static final int BLOCK_LINES_NUMBER = 6; // Number of blocks lines.
    public static final int BLOCKS_NUMBER = 12; // Number of block in each line.

    //The shorter side of the borders blocks.
    public static final double BORDER_SIDE = 30;


    //Members.

    private SpriteCollection sprites; // The Sprites collection of the Game.
    private GameEnvironment environment; // The environment of the Game.
    private GUI gui; //The GUI of the Game


    //Constructors.

    /**
     * A Constructor for the Game class.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }


    //Class Methods.

    /**
     * Adds a Collidable to the game.
     *
     * @param c The Collidable to add to the game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a Sprite to the game.
     *
     * @param s The Sprite to add to the game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    /**
     * Initializes a new Game.
     *
     * @throws RuntimeException when adding blocks to the screen.
     */
    public void initialize() throws RuntimeException {

        this.gui = new GUI(GUI_TITLE, GUI_WIDTH, GUI_HEIGHT);
        DrawSurface d = this.gui.getDrawSurface();

        //Build the paddle, to start in the middle of the screen -
        //the upper left point of the paddle will be in a distance of PADDLE_WIDTH/2 from
        //the middle of the GUI_WIDTH.
        double upperLeftPointX = (GUI_WIDTH / 2) - (PADDLE_WIDTH / 2);
        //The paddle needs to be close to the bottom border block.
        double upperLeftPointY = GUI_HEIGHT - BORDER_SIDE - BLOCK_HEIGHT;
        Point upperLeft = new Point(upperLeftPointX, upperLeftPointY);
        Rectangle recPaddle = new Rectangle(upperLeft, PADDLE_WIDTH, BLOCK_HEIGHT);
        Paddle paddle = new Paddle(recPaddle, Color.ORANGE, this.gui.getKeyboardSensor());

        //Set the moving region of the paddle to be between the left and the right border blocks.
        double x1 = BORDER_SIDE;
        double x2 = GUI_WIDTH - BORDER_SIDE;
        paddle.setMovingRegion(x1, x2);
        paddle.addToGame(this);

        //Add blocks to the game.
        addBlocksToGame();

        //Set the balls position to be the middle of the screen : middle of the paddle.
        int ballPointX = (int) paddle.getUpperLeft().getX() + PADDLE_WIDTH / 2;
        //The height of the balls is above the paddle, in a RADIUS distance from it.
        int ballPointY = (int) paddle.getUpperLeft().getY() - RADIUS;

        //Build the balls, and add them to the game.
        Ball ball1 = new Ball(ballPointX, ballPointY, RADIUS, Color.WHITE, this.environment);
        Velocity v = Velocity.fromAngleAndSpeed(0, 4);
        ball1.setVelocity(v);
        ball1.addToGame(this);

        v = Velocity.fromAngleAndSpeed(0, 6);
        Ball ball2 = new Ball(ballPointX, ballPointY, RADIUS - 3, Color.red, this.environment);
        ball2.setVelocity(v);
        ball2.addToGame(this);

    }

    /**
     * Runs the game, in each frame everything is drawn and being notified that time has passed.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = FRAMES_PER_SECOND;
        int millisecondsPerFrame = MILLISECONDS / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            //Add background to the gui, behind every sprite.
            Color blue = new Color(0, 0, 128);
            d.setColor(blue);
            d.fillRectangle(0, 0, GUI_WIDTH, GUI_HEIGHT);

            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Add the blocks to the game - border blocks and inner blocks.
     *
     * @throws Exception when a row of blocks of block a redrawn outside of the border blocks.
     */
    private void addBlocksToGame() throws RuntimeException {

        //Build the border blocks and add them to the game.

        //Top block.
        Point upperLeft = new Point(0, 0);
        Rectangle topRec = new Rectangle(upperLeft, GUI_WIDTH, BORDER_SIDE);
        (new Block(topRec, Color.GRAY, 1)).addToGame(this);

        //Left block.
        upperLeft = new Point(0, BORDER_SIDE);
        Rectangle leftRec = new Rectangle(upperLeft, BORDER_SIDE, GUI_HEIGHT - BORDER_SIDE);
        (new Block(leftRec, Color.GRAY, 1)).addToGame(this);

        //Bottom block.
        upperLeft = new Point(BORDER_SIDE, GUI_HEIGHT - BORDER_SIDE);
        Rectangle botRec = new Rectangle(upperLeft, GUI_WIDTH - 2 * BORDER_SIDE, BORDER_SIDE);
        (new Block(botRec, Color.GRAY, 1)).addToGame(this);

        //Right block.
        upperLeft = new Point(GUI_WIDTH - BORDER_SIDE, BORDER_SIDE);
        Rectangle rightRec = new Rectangle(upperLeft, BORDER_SIDE, GUI_HEIGHT - BORDER_SIDE);
        (new Block(rightRec, Color.GRAY, 1)).addToGame(this);


        //Build the inner colored blocks (random color) and add them to the game.

        //Build lines of same-color blocks by a y position on the screen.
        int blocksNumber = BLOCKS_NUMBER;
        double yPosition = 5 * BLOCK_HEIGHT;

        try {
            //First set top row of blocks with 2 hit points.
            addColoredBlocks(blocksNumber, generateRandomColor(), yPosition, 2);
            //Then for every other row (till BLOCK_LINES_NUMBER) add a row with 1 hit points.
            for (int i = 2; i <= BLOCK_LINES_NUMBER; i++) {
                //Starting from BLOCKS_NUMBER, each loop reduce the blocks number by one.
                blocksNumber = blocksNumber - 1;
                //Change the position of each row by adding a BLOCK_HEIGHT to the y position.
                yPosition = yPosition + BLOCK_HEIGHT;
                if (yPosition >= GUI_HEIGHT - BORDER_SIDE) {
                    throw new Exception("A row of blocks have gotten outside of the bottom border block and will " +
                            "not shown.");
                }
                //Add the row of blocks.
                addColoredBlocks(blocksNumber, generateRandomColor(), yPosition, 1);
            }
        } catch (Exception e) {
            //Pop a message window on the screen with the error.
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Adds a row of colored blocks to the game.
     *
     * @param blocksNumber The number of blocks in the row.
     * @param color        The color of the row.
     * @param yPosition    The y position of the row.
     * @param hitPoints    The hit points of the row.s
     * @throws Exception when blocks where drawn out of the left border block.
     */
    private void addColoredBlocks(int blocksNumber, Color color, double yPosition, int hitPoints) throws Exception {
        //Start the adding the blocks at point of startPositionX.
        double startPositionX = GUI_WIDTH - BORDER_SIDE - BLOCK_WIDTH;
        for (int i = 1; i <= blocksNumber; i++) {
            //build the block.
            Point upperLeft = new Point(startPositionX, yPosition);
            Rectangle rec = new Rectangle(upperLeft, BLOCK_WIDTH, BLOCK_HEIGHT);
            Block block = new Block(rec, color, hitPoints);
            block.addToGame(this);
            //Change the current startPositionX to startPositionX - BLOCK_WIDTH :
            // the next block will be in the left to the previous block.
            startPositionX = startPositionX - BLOCK_WIDTH;
            if (startPositionX < BORDER_SIDE) {
                throw new Exception("Blocks have gotten out of the left border block and will not be shown.");
            }
        }
    }

    /**
     * Genertaes a random Color.
     *
     * @return Random Color.
     */
    private Color generateRandomColor() {
        //Generate random numbers from 0 to 255 for a random color.
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
    }
}