package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.SpriteCollection;
import interfaces.Animation;

import java.util.concurrent.TimeUnit;


// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.
public class CountdownAnimation implements Animation {
	private int countFrom;
	private SpriteCollection gameScreen;
	private boolean running;
	private long millisecondsPerCount;

	public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
		this.countFrom = countFrom;
		this.gameScreen = gameScreen;
		this.running = true;
		this.millisecondsPerCount = TimeUnit.SECONDS.toMillis((long) numOfSeconds) / countFrom;
	}

	public void doOneFrame(DrawSurface d) {
		gameScreen.drawAllOn(d);
		Sleeper sleeper = new Sleeper();
		if (this.countFrom == 0) {
			this.running = false;
		}
		if (this.countFrom > 0) {
			d.drawText(d.getWidth() / 2, 60, "" + this.countFrom, 60);
			this.countFrom = this.countFrom - 1;
		}
		sleeper.sleepFor(this.millisecondsPerCount);
	}

	public boolean shouldStop() {
		return !this.running;
	}
}
