package sprint4.product;

import java.util.ArrayList;
import java.util.List;

public abstract class GameModel {

	protected char [][] board;
	protected int size;
	protected GameMode gameMode;
	protected int currentPlayer;
	protected boolean gameOver = false;
    protected int winner = 0;
    protected SOS moveMakesSOS = null;
    protected PlayerType bluePlayerType;
    protected PlayerType redPlayerType;
	
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
        public int player;

        public SOS(int r1, int c1, int r2, int c2, int r3, int c3, int player) {
            row1 = r1; col1 = c1;
            row2 = r2; col2 = c2;
            row3 = r3; col3 = c3;
            this.player = player;
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
	
	protected SOS hasSOS(int row, int col) {
        int[][] directions = {{1, 0},{0, 1},{1, 1},{1, -1}};
        for (int[] d : directions) {
            SOS found = checkDirection(row, col, d[0], d[1]);
            if (found != null) return found;
        }
        return null;
    }

    
    private SOS checkDirection(int row, int col, int dRow, int dCol) {
    	char letter = board[row][col];

        if (letter == 'O') {
            int r1 = row - dRow, c1 = col - dCol;
            int r2 = row + dRow, c2 = col + dCol;
            if (r1 >= 0 && r1 < size && c1 >= 0 && c1 < size &&
                r2 >= 0 && r2 < size && c2 >= 0 && c2 < size) {
                if (board[r1][c1] == 'S' && board[r2][c2] == 'S') {
                	return new SOS(r1, c1, row, col, r2, c2, currentPlayer);
                }
            }
        }

        if (letter == 'S') {
            int r1 = row + dRow, c1 = col + dCol;
            int r2 = row + 2*dRow, c2 = col + 2*dCol;
            if (r2 >= 0 && r2 < size && c2 >= 0 && c2 < size) {
                if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
                	return new SOS(row, col, r1, c1, r2, c2, currentPlayer);
                }
            }

            r1 = row - dRow; c1 = col - dCol;
            r2 = row - 2*dRow; c2 = col - 2*dCol;
            if (r2 >= 0 && r2 < size && c2 >= 0 && c2 < size) {
                if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
                	return new SOS(r2, c2, r1, c1, row, col, currentPlayer);
                }
            }
        }
        return null;
    }
    
    protected List<int[]> getEmptyCells() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '\0') emptyCells.add(new int[]{i, j});
            }
        }
        return emptyCells;
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
	
	public void setPlayerTypes(PlayerType blue, PlayerType red) {
		this.bluePlayerType = blue;
	    this.redPlayerType = red;
	}
	
	public PlayerType getCurrentPlayerType() {
	    return (currentPlayer == 1) ? bluePlayerType : redPlayerType;
	}
	
	public abstract void checkSOS(int row, int col);
	
	public abstract boolean isGameOver();
	
	public abstract int getWinner();
}
