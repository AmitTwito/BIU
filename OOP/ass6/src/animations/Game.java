package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collidables.Block;
import collidables.Paddle;
import game.GameEnvironment;
import game.SpriteCollection;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Animation;
import utilities.Counter;
import interfaces.Collidable;
import interfaces.HitListener;
import interfaces.Sprite;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import movement.Velocity;
import sprites.Ball;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;

import javax.swing.JOptionPane;
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
public class Game implements Animation {

	//Constants.

	public static final String GUI_TITLE = "Arkanoid";
	public static final int GUI_WIDTH = 800;
	public static final int GUI_HEIGHT = 600;
	public static final double BORDER_SIDE = 30;    //The shorter side of the borders blocks.
	public static final int PADDLE_WIDTH = 90;
	public static final int RADIUS = 8;
	public static final double BLOCK_WIDTH = 60;
	public static final double BLOCK_HEIGHT = 25;
	public static final int BLOCK_LINES_NUMBER = 5; // Number of blocks lines.
	public static final int BLOCKS_NUMBER = 10; // Number of block in each line.
	public static final int SCORE_BLOCK_HEIGHT = 25;
	public static final int MAX_LIVES_NUMBER = 4;
	public static final int MAX_BALLS_NUMBER = 2;
	public static final double BALL_SPEED = 5;
	public static final Point PADDLE_UPPER_LEFT_POINT = new Point((GUI_WIDTH / 2) - (PADDLE_WIDTH / 2)
			, GUI_HEIGHT - BORDER_SIDE - BLOCK_HEIGHT);
	public static final double SECONDS_TO_COUNTDOWN = 3;



	//Members.

	private SpriteCollection sprites; // The sprites collection of the animations.Game.
	private GameEnvironment environment; // The environment of the animations.Game.
	//private GUI gui; //The GUI of the animations.Game
	private Counter remainingBlocks;
	private Counter availableBalls;
	private Counter scoreCounter;
	private Counter livesCounter;
	private Paddle gamePaddle;
	private AnimationRunner runner;
	private boolean running;
	private KeyboardSensor keyboard;


	//Constructors.

	/**
	 * A Constructor for the animations.Game class.
	 */
	public Game() {
		this.sprites = new SpriteCollection();
		this.environment = new GameEnvironment();
		this.remainingBlocks = new Counter();
		this.availableBalls = new Counter();
		this.scoreCounter = new Counter();
		this.livesCounter = new Counter();
		this.livesCounter.increase(MAX_LIVES_NUMBER);
		this.runner = new AnimationRunner();
		this.running = false;
		this.keyboard = this.runner.getGui().getKeyboardSensor();

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


		DrawSurface d = this.runner.getGui().getDrawSurface();


		//Indicators block at top.
		Point upperLeft = new Point(0, 0);
		Rectangle scoreRec = new Rectangle(upperLeft, GUI_WIDTH, SCORE_BLOCK_HEIGHT);
		Block indicatorsBlock = new Block(scoreRec, Color.WHITE);
		indicatorsBlock.addToGame(this);

		//Indicators.
		ScoreIndicator scoreIndicator = new ScoreIndicator(scoreRec, this.scoreCounter);
		this.addSprite(scoreIndicator);

		LivesIndicator livesIndicator = new LivesIndicator(scoreRec, this.livesCounter);
		this.addSprite(livesIndicator);


		//Bottom death block.
		upperLeft = new Point(BORDER_SIDE, GUI_HEIGHT);
		Rectangle botRec = new Rectangle(upperLeft, GUI_WIDTH - 2 * BORDER_SIDE, BORDER_SIDE);
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
		// the logic from the previous playOneTurn method goes here.
		// the `return` or `break` statements should be replaced with
		// this.running = false;

		//Add background to the gui, behind every sprite.
		Color color = new Color(42, 129, 21);
		d.setColor(color);
		d.fillRectangle(0, 0, GUI_WIDTH, GUI_HEIGHT);

		this.sprites.drawAllOn(d);
		this.sprites.notifyAllTimePassed();


		//If there are no available blocks - the player won the game.
		if (this.remainingBlocks.getValue() == 0 && this.availableBalls.getValue() != 0) {
			this.scoreCounter.increase(100);
			this.runner.getGui().close();
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
		Point upperLeft = new Point(0, SCORE_BLOCK_HEIGHT);
		Rectangle topRec = new Rectangle(upperLeft, GUI_WIDTH, BORDER_SIDE);
		Block topBlock = new Block(topRec, Color.GRAY);
		topBlock.addToGame(this);

		//Left block.
		upperLeft = new Point(0, BORDER_SIDE + SCORE_BLOCK_HEIGHT);
		Rectangle leftRec = new Rectangle(upperLeft, BORDER_SIDE, GUI_HEIGHT - upperLeft.getY());
		Block leftBlock = new Block(leftRec, Color.GRAY);
		leftBlock.addToGame(this);

		//Right block.
		upperLeft = new Point(GUI_WIDTH - BORDER_SIDE, BORDER_SIDE + SCORE_BLOCK_HEIGHT);
		Rectangle rightRec = new Rectangle(upperLeft, BORDER_SIDE, GUI_HEIGHT - upperLeft.getY());
		Block rightBlock = new Block(rightRec, Color.GRAY);
		rightBlock.addToGame(this);


		//Build the inner colored blocks (random color) and add them to the game.

		//Build lines of same-color blocks by a y position on the screen.
		int blocksNumber = BLOCKS_NUMBER;
		double yPosition = 5 * BLOCK_HEIGHT;

		try {
			//First set top row of blocks with 2 hit points.
			addColoredBlocks(blocksNumber, generateRandomColor(), yPosition, 2, hitListenersList);
			//Then for every other row (till BLOCK_LINES_NUMBER) add a row with 1 hit points.
			for (int i = 2; i <= BLOCK_LINES_NUMBER; i++) {
				//Starting from BLOCKS_NUMBER, each loop reduce the blocks number by one.
				blocksNumber = blocksNumber - 1;
				//Change the position of each row by adding a BLOCK_HEIGHT to the y position.
				yPosition = yPosition + BLOCK_HEIGHT;
				if (yPosition >= GUI_HEIGHT - BORDER_SIDE) {
					throw new RuntimeException("A row of blocks have gotten outside of the bottom border block "
							+ "and will not shown.");
				}
				//Add the row of blocks.
				addColoredBlocks(blocksNumber, generateRandomColor(), yPosition, 1, hitListenersList);
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
	 * @param blocksNumber     The number of blocks in the row.
	 * @param color            The color of the row.
	 * @param yPosition        The y position of the row.
	 * @param hitPoints        The hit points of the row.s
	 * @param hitListenersList hitListeners for the blocks.
	 * @throws RuntimeException when blocks where drawn out of the left border block.
	 */
	private void addColoredBlocks(int blocksNumber, Color color, double yPosition, int hitPoints,
								  List<HitListener> hitListenersList)
			throws RuntimeException {
		//Start the adding the blocks at point of startPositionX.
		double startPositionX = GUI_WIDTH - BORDER_SIDE - BLOCK_WIDTH;
		for (int i = 1; i <= blocksNumber; i++) {
			//build the block.
			Point upperLeft = new Point(startPositionX, yPosition);
			Rectangle rec = new Rectangle(upperLeft, BLOCK_WIDTH, BLOCK_HEIGHT);
			Block block = new Block(rec, color, hitPoints);
			block.addToGame(this);

			addHitListenersToBlock(block, hitListenersList);
			this.remainingBlocks.increase(1);

			//Change the current startPositionX to startPositionX - BLOCK_WIDTH :
			// the next block will be in the left to the previous block.
			startPositionX = startPositionX - BLOCK_WIDTH;
			if (startPositionX < BORDER_SIDE) {
				throw new RuntimeException("Blocks have gotten out of the left border block and will not be shown.");
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
		//double upperLeftPointY = GUI_HEIGHT - BORDER_SIDE - BLOCK_HEIGHT;
		//geometry.Point upperLeft = new geometry.Point(upperLeftPointX, upperLeftPointY);
		Rectangle recPaddle = new Rectangle(PADDLE_UPPER_LEFT_POINT, PADDLE_WIDTH, BLOCK_HEIGHT);
		Paddle paddle = new Paddle(recPaddle, Color.ORANGE, this.keyboard);

		//Set the moving region of the paddle to be between the left and the right border blocks.
		double x1 = BORDER_SIDE;
		double x2 = GUI_WIDTH - BORDER_SIDE;
		paddle.setMovingRegion(x1, x2);

		paddle.addToGame(this);

		this.gamePaddle = paddle;

	}

	/**
	 * Creates and adds balls to the game.
	 */
	private void addBallsToGame() {

		//The height of the balls is above the paddle, in a RADIUS distance from it.
		int ballPointY = (int) PADDLE_UPPER_LEFT_POINT.getY() - RADIUS;
		//I wanted to create even distances between the start of tha paddle, the balls between the,
		// and the end of the paddle : for 2 balls the paddle needs to have 3 parts,for 3 : 4 parts, and so on.
		// number_of_balls + 1.
		int ballPointX = (int) (PADDLE_UPPER_LEFT_POINT.getX() + PADDLE_WIDTH / (MAX_BALLS_NUMBER + 1));

		for (int i = 1; i <= MAX_BALLS_NUMBER; i++) {

			//Build the balls, and add them to the game.
			Ball ball = new Ball(ballPointX, ballPointY, RADIUS, generateRandomColor(), this.environment);
			Velocity v = Velocity.fromAngleAndSpeed(0, BALL_SPEED);
			ball.setVelocity(v);
			ball.addToGame(this);
			this.availableBalls.increase(1);

			ballPointX += PADDLE_WIDTH / (MAX_BALLS_NUMBER + 1);
		}
	}
}