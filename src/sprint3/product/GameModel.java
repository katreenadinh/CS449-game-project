package sprint3.product;

public abstract class GameModel {

	protected char [][] board;
	protected int size;
	protected GameMode gameMode;
	protected int currentPlayer;
	protected boolean gameOver = false;
    protected int winner = 0;
    protected SOS moveMakesSOS = null;
	
	public GameModel(int size) {
		this.size = size;
		board = new char[size][size];
		currentPlayer = 1;
		gameMode = GameMode.SIMPLE;
		gameOver = false;
		winner = 0;
	}
	
	public static class SOS {
    	public int row1, col1;
        public int row2, col2;
        public int row3, col3;

        public SOS(int r1, int c1, int r2, int c2, int r3, int c3) {
            row1 = r1; col1 = c1;
            row2 = r2; col2 = c2;
            row3 = r3; col3 = c3;
        }
    }
	
	public int getCell(int row, int column) {
		return board[row][column];
	}
	
	public boolean makeMove(int row, int col, char letter) {
		if (board[row][col] == '\0' && !isGameOver()) {
			board[row][col] = letter;
			checkSOS(row, col);
			switchPlayer();
			return true;
		}
		return false;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean setBoardSize(int newSize) {
		if (newSize < 3) {
			return false;
		}
        this.size = newSize;
        this.board = new char[newSize][newSize];
        this.currentPlayer = 1;
        return true;
    }
	
	public boolean isBoardFull() {
		for (char[] r : board)
			for (char c : r)
				if (c == '\0')
					return false;
		return true;
	}
	
	public void setGameMode(GameMode mode) {
		this.gameMode = mode;
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void switchPlayer() {
		currentPlayer = 3 - currentPlayer;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public char[][] getBoard() {
		return board;
	}
	
	public SOS getMoveMakesSOS() {
        return moveMakesSOS;
    }
	
	public abstract void checkSOS(int row, int col);
	
	public abstract boolean isGameOver();
	
	public abstract int getWinner();
}
