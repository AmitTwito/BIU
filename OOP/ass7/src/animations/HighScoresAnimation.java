package animations;

import biuoop.DrawSurface;
import interfaces.Animation;
import score.HighScoresTable;
import score.ScoreInfo;
import utility.AnimationRunner;

import java.awt.Color;
import java.util.List;

/**
 * The HighScoresAnimation represents a high scores table animation object.
 *
 * @author Amit Twito
 */
public class HighScoresAnimation implements Animation {
    public static final int PLAYER_NAME_POSITION_X = AnimationRunner.GUI_WIDTH / 9;
    public static final int PLAYER_NAME_POSITION_Y = 150;
    public static final int SCORE_POSITION_X = AnimationRunner.GUI_WIDTH / 9 + 300;

    private HighScoresTable highScoresTable;

    /**
     * A constructor for the GameOveAnimation.
     *
     * @param scores The scores table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.highScoresTable = scores;
    }

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(AnimationRunner.SCREEN_TITLE_COLOR);
        d.drawText(AnimationRunner.SCREEN_TITLE_X, AnimationRunner.SCREEN_TITLE_Y, "High Scores", 30);
        List<ScoreInfo> scoreInfos = this.highScoresTable.getHighScores();

        d.setColor(Color.green);
        d.drawText(PLAYER_NAME_POSITION_X, PLAYER_NAME_POSITION_Y, "Player Name", 25);
        d.drawText(SCORE_POSITION_X, PLAYER_NAME_POSITION_Y, "Score", 25);

        d.drawLine(PLAYER_NAME_POSITION_X, PLAYER_NAME_POSITION_Y + 15, PLAYER_NAME_POSITION_X + 500,
                PLAYER_NAME_POSITION_Y + 15);

        d.setColor(Color.orange);

        int scoreInfoLineY = PLAYER_NAME_POSITION_Y + 45;
        if (scoreInfos.size() != 0) {
            for (int i = 0; i < Math.min(this.highScoresTable.size(), scoreInfos.size()); i++) {
                ScoreInfo scoreInfo = scoreInfos.get(i);
                d.drawText(PLAYER_NAME_POSITION_X, scoreInfoLineY, scoreInfo.getName(), 25);
                d.drawText(SCORE_POSITION_X, scoreInfoLineY, "" + scoreInfo.getScore(), 25);
                scoreInfoLineY += 30;
            }
        }

        d.setColor(Color.BLUE);
        d.drawText(AnimationRunner.GUI_WIDTH / 2 - 200, AnimationRunner.GUI_HEIGHT - 50,
                "Press space to continue", 40);
    }

    /**
     * Returns whether the animation has stopped.
     *
     * @return If the animation has been stopped.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }

}
