package sprites;

import biuoop.DrawSurface;
import game.SpriteCollection;
import interfaces.Sprite;


public class LevelBackground implements Sprite {
	private SpriteCollection sprites;

	public LevelBackground() {
		this.sprites = new SpriteCollection();
	}


	public void addSprite(Sprite sprite) {
		this.sprites.addSprite(sprite);
	}

	@Override
	public void drawOn(DrawSurface d) {
		this.sprites.drawAllOn(d);
	}

	@Override
	public void timePassed() {

	}
}
