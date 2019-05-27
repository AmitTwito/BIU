package animations;

import biuoop.DrawSurface;
import interfaces.Animation;

public class GameOverAnimation implements Animation {
	private int score;
	public GameOverAnimation(int score) {
		this.score = score;
	}
	public void doOneFrame(DrawSurface d) {
		d.drawText(10, d.getHeight() / 2, "Game Over :( Your score is: " + this.score, 32);
	}
	public boolean shouldStop() { return false; }

}
