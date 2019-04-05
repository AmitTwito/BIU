import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class Game {
    public static final String GUI_TITLE = "Title";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECONDS = 1000;
    public static final int PADDLE_WIDTH = 90;
    public static final int RADIUS = 10;
    public static final double BLOCK_WIDTH = 60;
    public static final double BLOCK_HEIGHT = 25;
    public static final int BLOCK_LINES_NUMBER = 5;
    public static final int BLOCKS_NUMBER = 12;
    public static final int BORDER_SIDE = 30;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {

        this.gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);
        DrawSurface d = this.gui.getDrawSurface();



        double upperLeftPointX = (WIDTH / 2) - (PADDLE_WIDTH / 2);
        double upperLeftPointY = HEIGHT - BORDER_SIDE - BLOCK_HEIGHT;
        Point upperLeft = new Point(upperLeftPointX, upperLeftPointY);
        Rectangle recPaddle = new Rectangle(upperLeft, PADDLE_WIDTH, BLOCK_HEIGHT);
        Paddle paddle = new Paddle(recPaddle, Color.ORANGE, this.gui.getKeyboardSensor());
        upperLeft = new Point(BORDER_SIDE, HEIGHT - BORDER_SIDE);
        Rectangle botRec = new Rectangle(upperLeft, WIDTH - 2 * BORDER_SIDE, BORDER_SIDE);
        paddle.setBordersFrame(botRec);
        paddle.addToGame(this);

        addBlocksToGame();

        int ballPointX = (int) paddle.getCollisionRectangle().getUpperLeft().getX() + PADDLE_WIDTH / 2;
        int ballPointY = (int) paddle.getCollisionRectangle().getUpperLeft().getY() - RADIUS;

        Ball ball1 = new Ball(ballPointX , ballPointY, RADIUS, Color.WHITE);
        Velocity v = Velocity.fromAngleAndSpeed(135, 3);
        ball1.setVelocity(v);
        ball1.addToGame(this);
        ball1.setGameEnvironment(this.environment);

        v = Velocity.fromAngleAndSpeed(125, 3);
        Ball ball2 = new Ball(ballPointX , ballPointY, RADIUS - 5, Color.red);
        ball2.setVelocity(v);
        ball2.addToGame(this);
        ball2.setGameEnvironment(this.environment);

    }

    // Run the game -- start the animation loop.
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = FRAMES_PER_SECOND;
        int millisecondsPerFrame = MILLISECONDS / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            //Background
            d.setColor(Color.BLUE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);
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


    private void addBlocksToGame() {
        Point upperLeft = new Point(0, 0);
        Rectangle topRec = new Rectangle(upperLeft, WIDTH,BORDER_SIDE);
        (new Block(topRec, Color.GRAY)).addToGame(this);

        upperLeft = new Point(0, BORDER_SIDE);
        Rectangle leftRec = new Rectangle(upperLeft, BORDER_SIDE, HEIGHT - BORDER_SIDE);
        (new Block(leftRec, Color.GRAY)).addToGame(this);

        upperLeft = new Point(BORDER_SIDE, HEIGHT - BORDER_SIDE);
        Rectangle botRec = new Rectangle(upperLeft, WIDTH - 2 * BORDER_SIDE, BORDER_SIDE);
        (new Block(botRec, Color.GRAY)).addToGame(this);

        upperLeft = new Point(WIDTH - BORDER_SIDE, BORDER_SIDE);
        Rectangle rightRec = new Rectangle(upperLeft, BORDER_SIDE, HEIGHT - BORDER_SIDE);
        (new Block(rightRec, Color.GRAY)).addToGame(this);

        int blocksNumber = BLOCKS_NUMBER;
        double yPosition = 5 * BLOCK_HEIGHT;
        for (int i = 1; i <= BLOCK_LINES_NUMBER; i++) {
            addColoredBlocks(blocksNumber, generateRandomColor(), yPosition);
            blocksNumber = blocksNumber - 1;
            yPosition = yPosition + BLOCK_HEIGHT;

        }
    }

    private void addColoredBlocks(int blocksNumber, Color color, double yPosition) {
        double startPositionX = WIDTH - BORDER_SIDE - BLOCK_WIDTH;
        for (int i = 1; i <= blocksNumber; i++) {
            Point upperLeft = new Point(startPositionX, yPosition);
            Rectangle rec = new Rectangle(upperLeft, BLOCK_WIDTH, BLOCK_HEIGHT);
            Block block = new Block(rec, color);
            block.addToGame(this);
            startPositionX = startPositionX - BLOCK_WIDTH;
        }
    }

    private Color generateRandomColor() {
        //Generate random numbers from 0 to 255 for a random color.
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
    }
}