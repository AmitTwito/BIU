package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collidables.Block;
import collidables.Paddle;
import game.GameEnvironment;
import game.SpriteCollection;
import geometry.Point;
import geometry.Rectangle;
import interfaces.*;
import sprites.*;
import utilities.Counter;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The animations.Game class represents a game object.
 * A animations.Game object is built with a collection of colliadble objects and a collections of sprites.
 * A animations.Game object handles a game: initializes and runs.
 * The game itself is a game where there are blocks on the screen, a ball and a movable paddle,
 * controlled by the player, and the ball can collied with the
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class GameLevel implements Animation {

    //Constants.

    public static final double BORDER_SIDE = 30;    //The shorter side of the borders blocks.
    public static final int PADDLE_WIDTH = 90;
    public static final int RADIUS = 6;
    public static final int MAX_BLOCKS_PER_ROW = 15;
    public static final double BLOCK_WIDTH = (AnimationRunner.GUI_WIDTH - 2 * BORDER_SIDE) / MAX_BLOCKS_PER_ROW ;
    public static final double BLOCK_HEIGHT = 25;
    public static final int INDICATORS_BLOCK_HEIGHT = 25;
    public static final double BALL_SPEED = 6;
    public static final double SECONDS_TO_COUNTDOWN = 2;
    //public static final Rectangle BACKGROUND_RECTANGLE = new Rectangle()


    //Members.

    private SpriteCollection sprites; // The sprites collection of the animations.Game.
    private GameEnvironment environment; // The environment of the animations.Game.
    private Counter remainingBlocks;
    private Counter availableBalls;
    private Counter scoreCounter;
    private Counter livesCounter;
    private Paddle gamePaddle;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;

    //Constructors.

    /**
     * A Constructor for the animations.Game class.
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner animationRunner,
                     KeyboardSensor keyboardSensor, Counter livesCounter, Counter scoreCounter) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBlocks.increase(levelInformation.numberOfBlocksToRemove());
        this.availableBalls = new Counter();
        this.scoreCounter = scoreCounter;
        this.livesCounter = livesCounter;
        this.runner = animationRunner;
        this.running = false;
        this.keyboard = keyboardSensor;
        this.levelInformation = levelInformation;

        //Indicators block at top.
        Point upperLeft = new Point(0, 0);
        Rectangle indicatorsRec = new Rectangle(upperLeft, AnimationRunner.GUI_WIDTH, INDICATORS_BLOCK_HEIGHT);
        Color c = new Color(237, 237, 237);
        addSprite(new ColoredRectangle(indicatorsRec,c));
        //Indicators.
        ScoreIndicator scoreIndicator = new ScoreIndicator(indicatorsRec, this.scoreCounter);
        addSprite(scoreIndicator);

        LivesIndicator livesIndicator = new LivesIndicator(indicatorsRec, this.livesCounter);
        addSprite(livesIndicator);

        LevelIndicator levelIndicator = new LevelIndicator(indicatorsRec, this.levelInformation.levelName());
        addSprite(levelIndicator);

        addSprite(this.levelInformation.getBackground());

    }

    //Getters.


    //Class Methods.

    /**
     * Adds a interfaces.Collidable to the game.
     *
     * @param c The interfaces.Collidable to add to the game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a interfaces.Sprite to the game.
     *
     * @param s The interfaces.Sprite to add to the game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    /**
     * Initializes a new animations.Game.
     *
     * @throws RuntimeException when adding blocks to the screen.
     */
    public void initialize() throws RuntimeException {


        List<HitListener> hitListenerList = new ArrayList<>();

        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        hitListenerList.add(blockRemover);

        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(scoreCounter);
        hitListenerList.add(scoreTrackingListener);


        //Bottom death block.
        Point upperLeft = new Point(BORDER_SIDE, AnimationRunner.GUI_WIDTH);
        Rectangle botRec = new Rectangle(upperLeft, AnimationRunner.GUI_WIDTH - 2 * BORDER_SIDE, BORDER_SIDE);
        Block bottomBlock = new Block(botRec, Color.GRAY);
        bottomBlock.addToGame(this);
        //Create ball remover hit listener.
        BallRemover ballRemover = new BallRemover(this, availableBalls);
        bottomBlock.addHitListener(ballRemover);

        //Add blocks to the game.
        addBlocksToGame(hitListenerList);

        //Add the paddle to the game.
        addPaddleToGame();

    }


    /**
     * Runs the Arkanoid game.
     */
    public void run() {

        while (this.livesCounter.getValue() != 0) {
            playOneTurn();
        }
        this.runner.getGui().close();
    }

    public boolean shouldStop() {
        return !this.running;
    }

    public void doOneFrame(DrawSurface d) {

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();


        //If there are no available blocks - the player won the game.
        if (this.remainingBlocks.getValue() == 0 && this.availableBalls.getValue() != 0) {
            this.scoreCounter.increase(100);

        }
        //If there are no available blocks - the player loses one life.
        if (this.remainingBlocks.getValue() != 0 && this.availableBalls.getValue() == 0) {
            this.livesCounter.decrease(1);
            //stop the turn.
            this.running = false;
        }

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }


    }

    /**
     * Runs one turn of the game, in each frame everything is drawn and being notified that time has passed.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle(); // or a similar method

        // countdown before turn starts.
        this.runner.run(new CountdownAnimation(SECONDS_TO_COUNTDOWN, 3, this.sprites));
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.running = true;
        this.runner.run(this);
    }

    private void createBallsOnTopOfPaddle() {
        //Remove the current paddle and 	add a new one to start from the center of the screen.
        this.gamePaddle.removeFromGame(this);
        addPaddleToGame();
        //Add the balls.
        addBallsToGame();
    }


    /**
     * Removes a collidable from the game.
     *
     * @param c The collidable to remove from the game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game.
     *
     * @param s The sprite to remove from the game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Add the blocks to the game - border blocks and inner blocks.
     *
     * @param hitListenersList hitListeners for the blocks.
     * @throws RuntimeException when a row of blocks of block a redrawn outside of the border blocks.
     */
    private void addBlocksToGame(List<HitListener> hitListenersList) throws RuntimeException {

        //Build the border blocks and add them to the game.

        //Top block.
        Point upperLeft = new Point(0, INDICATORS_BLOCK_HEIGHT);
        Rectangle topRec = new Rectangle(upperLeft, AnimationRunner.GUI_WIDTH, BORDER_SIDE);
        Block topBlock = new Block(topRec, Color.GRAY);
        topBlock.addToGame(this);

        //Left block.
        upperLeft = new Point(0, BORDER_SIDE + INDICATORS_BLOCK_HEIGHT);
        Rectangle leftRec = new Rectangle(upperLeft, BORDER_SIDE,
                AnimationRunner.GUI_HEIGHT - upperLeft.getY());
        Block leftBlock = new Block(leftRec, Color.GRAY);
        leftBlock.addToGame(this);

        //Right block.
        upperLeft = new Point(AnimationRunner.GUI_WIDTH - BORDER_SIDE, BORDER_SIDE + INDICATORS_BLOCK_HEIGHT);
        Rectangle rightRec = new Rectangle(upperLeft, BORDER_SIDE,
                AnimationRunner.GUI_HEIGHT - upperLeft.getY());
        Block rightBlock = new Block(rightRec, Color.GRAY);
        rightBlock.addToGame(this);

        List<Block> blocks = this.levelInformation.blocks();
        for (Block block : blocks) {
            addHitListenersToBlock(block, hitListenersList);
            block.addToGame(this);
        }

    }


    /**
     * Generates a random Color.
     *
     * @return Random Color.
     */
    public static Color generateRandomColor() {
        //Generate random numbers from 0 to 255 for a random color.
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
    }

    /**
     * Adds a list of listeners to a given block.
     *
     * @param block           The block to add the listeners to.
     * @param hitListenerList The listeners to add to the block.
     */
    private void addHitListenersToBlock(Block block, List<HitListener> hitListenerList) {

        for (HitListener hl : hitListenerList) {
            block.addHitListener(hl);
        }
    }

    /**
     * Creates and adds A paddle to the game.
     */
    private void addPaddleToGame() {

        //Build the paddle, to start in the middle of the screen -
        //the upper left point of the paddle will be in a distance of PADDLE_WIDTH/2 from
        //the middle of the GUI_WIDTH.
        //double upperLeftPointX = (GUI_WIDTH / 2) - (PADDLE_WIDTH / 2);
        //The paddle needs to be close to the bottom border block.

        Point PADDLE_UPPER_LEFT_POINT =
                new Point((AnimationRunner.GUI_WIDTH / 2) - (levelInformation.paddleWidth() / 2),
                        AnimationRunner.GUI_HEIGHT - BORDER_SIDE - BLOCK_HEIGHT);

        Rectangle recPaddle = new Rectangle(PADDLE_UPPER_LEFT_POINT, this.levelInformation.paddleWidth(),
                BLOCK_HEIGHT);
        Paddle paddle = new Paddle(recPaddle, Color.ORANGE, this.keyboard, this.levelInformation.paddleSpeed());

        //Set the moving region of the paddle to be between the left and the right border blocks.
        double x1 = BORDER_SIDE;
        double x2 = AnimationRunner.GUI_WIDTH - BORDER_SIDE;
        paddle.setMovingRegion(x1, x2);

        paddle.addToGame(this);

        this.gamePaddle = paddle;

    }

    /**
     * Creates and adds balls to the game.
     */
    private void addBallsToGame() {
        Point PADDLE_UPPER_LEFT_POINT =
                new Point((AnimationRunner.GUI_WIDTH / 2) - (levelInformation.paddleWidth() / 2),
                        AnimationRunner.GUI_HEIGHT - BORDER_SIDE - BLOCK_HEIGHT);

        //The height of the balls is above the paddle, in a RADIUS distance from it.
        int ballPointY = (int) PADDLE_UPPER_LEFT_POINT.getY() - RADIUS;

        int ballPointX = AnimationRunner.GUI_WIDTH / 2;

        for (int i = 0; i < this.levelInformation.numberOfBalls(); i++) {

            //Build the balls, and add them to the game.
            Ball ball = new Ball(ballPointX, ballPointY, RADIUS, Color.WHITE, this.environment);
            ball.setVelocity(this.levelInformation.initialBallVelocities().get(i));
            ball.addToGame(this);
            this.availableBalls.increase(1);
        }
    }

    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }
}