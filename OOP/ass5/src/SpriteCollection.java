import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class represents a sprite collection object.
 * A SpriteCollection object is built by a list of Sprites.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class SpriteCollection {

    //Members.
    private List<Sprite> spritesList; //The Sprites list.

    //Constructors.

    /**
     * Constructor for the SpriteCollection class.
     * Initialize a List of Sprites.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<>();
    }

    //Class methods.

    /**
     * Add a Sprite to the collection.
     *
     * @param s Right edge of the Region.
     */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**
     * Notifies every Sprite in the collection that the time has passed.
     */
    public void notifyAllTimePassed() {

        for (int i = 0; i < this.spritesList.size(); i++) {
            this.spritesList.get(i).timePassed();
        }
    }

    /**
     * Draws the Sprites on a given DrawSurface.
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

