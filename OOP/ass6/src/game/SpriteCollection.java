package game;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The game.SpriteCollection class represents a sprite collection object.
 * A game.SpriteCollection object is built by a list of sprites.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class SpriteCollection {

    //Members.
    private List<Sprite> spritesList; //The sprites list.

    //Constructors.

    /**
     * Constructor for the game.SpriteCollection class.
     * Initialize a List of sprites.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<>();
    }

    //Class methods.

    /**
     * Add a interfaces.Sprite to the collection.
     *
     * @param s Right edge of the utilities.Region.
     */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**
     * Notifies every interfaces.Sprite in the collection that the time has passed.
     */
    public void notifyAllTimePassed() {

        for (int i = 0; i < this.spritesList.size(); i++) {
            this.spritesList.get(i).timePassed();
        }
    }

    /**
     * Draws the sprites on a given DrawSurface.
     *
     * @param d DrawSurface to draw the sprite on
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.spritesList.size(); i++) {
            this.spritesList.get(i).drawOn(d);
        }
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s The sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.spritesList.remove(s);
    }
}

