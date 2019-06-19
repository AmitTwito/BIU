package tasks;

import game.GameFlow;
import interfaces.LevelInformation;
import interfaces.Task;


import java.util.List;

public class StartGameTask implements Task<Void> {
	private GameFlow gameFlow;
	private List<LevelInformation> levelInformations;
	public StartGameTask(GameFlow gameFlow, List<LevelInformation> levelInformations) {
		this.gameFlow = gameFlow;
		this.levelInformations = levelInformations;
	}
	@Override
	public Void run() {
		this.gameFlow.runLevels(this.levelInformations);
		return null;
	}
}
