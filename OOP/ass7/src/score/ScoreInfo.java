package score;

import java.io.Serializable;

public class ScoreInfo implements Serializable, Comparable<Integer> {

    private String name;
    private int score;

    public ScoreInfo(String name, int score) {

        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Integer o) {
        return Math.max(this.score, o.intValue());
    }
}
