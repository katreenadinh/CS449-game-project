package sprint5.product;

import java.util.ArrayList;
import java.util.List;

public class GeneralGameModel extends GameModel{
	
	private int[] playerScores;
    private List<SOS> sosList;

    public GeneralGameModel(int size) {
        super(size);
        this.gameMode = GameMode.GENERAL;
        playerScores = new int[2]; 
        sosList = new ArrayList<>();
    }
    
    public List<SOS> getSOSList() {
        return sosList;
    }
    
	@Override
	public void checkSOS(int row, int col) {
		SOS found = hasSOS(row, col);
        if (found != null) {
            sosList.add(found);
            playerScores[currentPlayer - 1]++;
        }
        if (isBoardFull()) {
            gameOver = true;
            if (playerScores[0] > playerScores[1]) winner = 1;
            else if (playerScores[1] > playerScores[0]) winner = 2;
            else winner = 0; // draw
        }
	}
	
	@Override
    public boolean makeMove(int row, int col, char letter) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
	        return false;
	    }
		
        if (board[row][col] == '\0' && !isGameOver()) {
            board[row][col] = letter;
            
            if (recorder != null && recorder.isRecording()) {
                recorder.recordMove(new PlayerModel.Move(row, col, letter));
            }
            
            checkSOS(row, col);
            SOS found = hasSOS(row, col);
            if (found == null && !isGameOver()) switchPlayer();
            return true;
        }
        return false;
    }

	@Override
	public boolean isGameOver() {
		return gameOver;
	}

	@Override
	public int getWinner() {
		return winner;
	}
}
