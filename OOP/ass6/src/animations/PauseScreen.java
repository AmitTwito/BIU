package animations;

import biuoop.DrawSurface;
import interfaces.Animation;

public class PauseScreen implements Animation {

	public void doOneFrame(DrawSurface d) {
		d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
	}
	public boolean shouldStop() { return false;}
}
