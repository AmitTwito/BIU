import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {

	private List<Sprite> spritesList;
	public SpriteCollection () {
		this.spritesList = new ArrayList<>();
	}
	public void addSprite(Sprite s) {
		this.spritesList.add(s);
	}

	// call timePassed() on all sprites.
	public void notifyAllTimePassed() {

		for (Sprite s : this.spritesList) {
			s.timePassed();
		}
	}

	// call drawOn(d) on all sprites.
	public void drawAllOn(DrawSurface d) {

		for (Sprite s : this.spritesList) {
			s.drawOn(d);
		}

	}
}

