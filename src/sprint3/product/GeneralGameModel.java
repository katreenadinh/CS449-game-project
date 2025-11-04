package sprint3.product;

public class GeneralGameModel extends GameModel{
	private int player1Score;
    private int player2Score;

    public GeneralGameModel(int size) {
        super(size);
        player1Score = 0;
        player2Score = 0;
    }

	@Override
	public void checkSOS(int row, int col) {
		
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getWinner() {
		// TODO Auto-generated method stub
		return 0;
	}
}
