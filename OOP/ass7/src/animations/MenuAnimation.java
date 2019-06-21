package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Menu;
import utility.AnimationRunner;
import utility.Selection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * The MenuAnimation represents a menu animation object.
 *
 * @param <T> type of menu.
 * @author Amit Twito
 */
public class MenuAnimation<T> implements Menu<T> {

    public static final int OPTION_NAME_POSITION_X = AnimationRunner.GUI_WIDTH / 9;
    public static final int OPTION_NAME_POSITION_Y = 150;

    private String menuTitle;
    private List<Selection<T>> selections;
    private KeyboardSensor keyboard;
    private T status;
    private boolean stop;

    /**
     * A constructor for MenuAnimation class.
     *
     * @param menuTitle      menu title.
     * @param keyboardSensor keyboard.
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor) {
        this.menuTitle = menuTitle;
        this.selections = new ArrayList<>();
        this.keyboard = keyboardSensor;
        this.stop = false;
    }

    /**
     * Adds a selection to the menu.
     *
     * @param key       Key to be pressed.
     * @param message   Message of the selection.
     * @param returnVal Return value of the selection.
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<>(key, message, returnVal));
    }

    /**
     * Returns the current status of the menu.
     *
     * @return Current status.
     */
    @Override
    public T getStatus() {
        this.stop = false;
        return this.status;
    }

    /**
     * Adds a sub menu to the menu.
     *
     * @param key     Key to be pressed.
     * @param message Message of the submenu.
     * @param subMenu The submenu animation.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
    }

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {

        d.setColor(AnimationRunner.SCREEN_TITLE_COLOR);
        d.drawText(AnimationRunner.SCREEN_TITLE_X, AnimationRunner.SCREEN_TITLE_Y, this.menuTitle, 30);

        d.setColor(Color.green);

        int optionY = OPTION_NAME_POSITION_Y;
        for (Selection selection : selections) {

            String text = "(" + selection.getKey() + ") " + selection.getMessage();
            d.drawText(OPTION_NAME_POSITION_X, optionY, text, 25);

            optionY += 40;
        }


        for (Selection selection : this.selections) {
            if (this.keyboard.isPressed(selection.getKey())) {

                this.status = (T) selection.getReturnVal();
                this.stop = true;
                break;
            }
        }
    }

    /**
     * Returns whether the animation has stopped.
     *
     * @return If the animation has been stopped.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }


}
