package score;

import java.io.Serializable;

/**
 * The ScoreInfo represents a info of score class.
 */
public class ScoreInfo implements Serializable, Comparable<ScoreInfo> {

    private String name;
    private int score;

    /**
     * A constructor for the ScoreInfo class.
     *
     * @param name  The name of the player.
     * @param score The score of the player.
     */
    public ScoreInfo(String name, int score) {

        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the player.
     *
     * @return Name of player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the score.
     *
     * @return Score.
     */
    public Integer getScore() {
        return this.score;
    }
    /**
     * Compares scores.
     *
     * @param o other score.
     * @return Max of scores.
     */
    @Override
    public int compareTo(ScoreInfo o) {
        return this.getScore().compareTo(o.getScore());
    }



}
