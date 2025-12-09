package sprint5.product;

public class SimpleGameModel extends GameModel {
    
    public SimpleGameModel(int size) {
        super(size);
        this.gameMode = GameMode.SIMPLE;
    }
    
    @Override
    public void checkSOS(int row, int col) {
    	moveMakesSOS = hasSOS(row, col);
        if (moveMakesSOS != null) {
            gameOver = true;
            winner = currentPlayer;
        }
        else if (isBoardFull()) {
        	gameOver = true;
        	winner = 0;
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
			
			if (!isGameOver()) {
				switchPlayer();
			}
			
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
