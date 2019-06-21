package sprites;

import biuoop.DrawSurface;
import game.SpriteCollection;
import interfaces.Sprite;
import utility.AnimationRunner;
import utility.ColorParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The LevelBackground class represents the background of a level.
 *
 * @author Amit Twito
 */
public class LevelBackground implements Sprite {
    private SpriteCollection sprites;
    private String colorOrImage;

    /**
     * A constructor of the LevelBackground class.
     * The class is built from SpriteCollection.
     */
    public LevelBackground() {
        this.sprites = new SpriteCollection();
        this.colorOrImage = null;
    }

    /**
     * A constructor for the LevelBackground class.
     *
     * @param colorOrImage Color or image string.
     */
    public LevelBackground(String colorOrImage) {
        this.colorOrImage = colorOrImage;
        this.sprites = null;
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

        Color color = null;
        if (this.sprites != null) {
            this.sprites.drawAllOn(d);
            this.colorOrImage = null;
        }
        if (this.colorOrImage != null) {
            this.sprites = null;
            if (this.colorOrImage.contains("image")) {
                String background = this.colorOrImage.substring(colorOrImage.indexOf("(") + 1, colorOrImage.indexOf(
                        ")"));
                Image image = null;
                try {
                    image = ImageIO.read(new File(background));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                d.drawImage(0, 0, image);
            }


        }
        ColorParser colorParser = new ColorParser();

        if (this.colorOrImage.contains("color")) {

            if (this.colorOrImage.contains("RGB")) {
                String fillString = this.colorOrImage.substring(colorOrImage.indexOf("(") + 1
                        , colorOrImage.indexOf(")") + 1);

                String rgbString = fillString.substring(fillString.indexOf("(") + 1, fillString.indexOf(")"));

                String[] rgb = rgbString.split(",");
                int x = Integer.parseInt(rgb[0]);
                int y = Integer.parseInt(rgb[1]);
                int z = Integer.parseInt(rgb[2]);
                color = new Color(x, y, z);

            } else {
                String fillString = this.colorOrImage.substring(colorOrImage.indexOf("(") + 1
                        , colorOrImage.indexOf(")"));

                color = colorParser.colorFromString(fillString);
            }

            d.setColor(color);
            d.fillRectangle(0, 0, AnimationRunner.GUI_WIDTH, AnimationRunner.GUI_HEIGHT);
        }
    }


    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
