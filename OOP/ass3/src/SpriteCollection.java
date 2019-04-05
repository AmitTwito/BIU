import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class represents a SpriteCollection object.
 * A SpriteCollection object is built by a list of Sprites.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class SpriteCollection {

    //Members.
    private List<Sprite> spritesList;

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
        for (Sprite s : this.spritesList) {
            s.timePassed();
        }
    }

    /**
     * Draws the Sprites on a given DrawSurface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.spritesList) {
            s.drawOn(d);
        }
    }
}

