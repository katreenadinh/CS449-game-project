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
	
	public void setSize(int size) {
		this.size = size;
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

	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public char[][] getBoard() {
		return board;
	}
	
}
