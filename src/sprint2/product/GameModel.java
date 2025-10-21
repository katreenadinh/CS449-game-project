package sprint2.product;

public class GameModel {

	private char [][] board;
	private int size;
	private GameMode gameMode;
	private int currentPlayer;
	
	public GameModel(int size) {
		this.size = size;
		board = new char[size][size];
		currentPlayer = 1;
		gameMode = GameMode.SIMPLE;
	}
	
	public int getCell(int row, int column) {
		return board[row][column];
	}
	
	public boolean makeMove(int row, int col, char letter) {
		if (board[row][col] == '\0') {
			board[row][col] = letter;
			currentPlayer = 3 - currentPlayer;
			return true;
		}
		return false;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setBoardSize(int newSize) {
        this.size = newSize;
        this.board = new char[newSize][newSize];
        this.currentPlayer = 1;
    }
	
	public void setGameMode(GameMode mode) {
		this.gameMode = mode;
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	
}
