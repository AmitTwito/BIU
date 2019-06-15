package tasks;

import interfaces.Animation;
import interfaces.Task;
import utility.AnimationRunner;

public class ShowHiScoresTask implements Task<Void> {
	private AnimationRunner runner;
	private Animation highScoresAnimation;
	public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
		this.runner = runner;
		this.highScoresAnimation = highScoresAnimation;
	}
	@Override
	public Void run() {
		this.runner.run(this.highScoresAnimation);
		return null;
	}
}
