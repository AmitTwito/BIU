public class BallRemover implements HitListener {

	private Game game;
	private Counter availableBalls;

	public BallRemover(Game game, Counter availableBalls) {
		this.game = game;
		this.availableBalls = availableBalls;
	}


    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
		hitter.removeFromGame(this.game);
		availableBalls.decrease(1);
    }
}
