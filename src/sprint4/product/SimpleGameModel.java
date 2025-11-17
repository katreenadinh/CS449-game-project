package sprint4.product;

import java.util.List;

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
		if (board[row][col] == '\0' && !isGameOver()) {
			board[row][col] = letter;
			checkSOS(row, col);
			switchPlayer();
			return true;
		}
		return false;
	}
    
    public boolean makeComputerMove() {
    	if (isGameOver()) return false;
    	
    	List<int[]> emptyCells = getEmptyCells();
        if (emptyCells.isEmpty()) return false;
        
        int[] move = emptyCells.get((int)(Math.random() * emptyCells.size()));
        char letter = Math.random() < 0.5 ? 'S' : 'O';

        return makeMove(move[0], move[1], letter);
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
