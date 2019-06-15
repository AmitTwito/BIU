package tasks;

import biuoop.GUI;
import interfaces.Task;

public class ExitTask implements Task<Void> {

	@Override
	public Void run() {
		System.exit(0);
		return null;
	}
}
