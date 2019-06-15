package sprites;

import biuoop.DrawSurface;
import game.SpriteCollection;
import interfaces.Sprite;

/**
 * The LevelBackground class represents the background of a level.
 *
 * @author Amit Twito
 */
public class LevelBackground implements Sprite {
    private SpriteCollection sprites;

    /**
     * A constructor of the LevelBackground class.
     * The class is built from SpriteCollection.
     */
    public LevelBackground() {
        this.sprites = new SpriteCollection();
    }


    /**
     * Adds a sprite to the SpriteCollection.
     *
     * @param sprite The sprite to add.
     */
    public void addSprite(Sprite sprite) {
        this.sprites.addSprite(sprite);
    }

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.sprites.drawAllOn(d);
    }

    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
