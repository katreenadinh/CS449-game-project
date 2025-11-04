package sprint3.product;

public class SimpleGameModel extends GameModel {
	
	private SOS moveMakesSOS = null;
    
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
    }

    @Override
    public boolean isGameOver() {
    	if (gameOver || isBoardFull()) {
            gameOver = true;
        }
        return gameOver;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    private SOS hasSOS(int row, int col) {
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
                	return new SOS(r1, c1, row, col, r2, c2);
                }
            }
        }

        if (letter == 'S') {
            int r1 = row + dRow, c1 = col + dCol;
            int r2 = row + 2*dRow, c2 = col + 2*dCol;
            if (r2 >= 0 && r2 < size && c2 >= 0 && c2 < size) {
                if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
                	return new SOS(row, col, r1, c1, r2, c2);
                }
            }

            r1 = row - dRow; c1 = col - dCol;
            r2 = row - 2*dRow; c2 = col - 2*dCol;
            if (r2 >= 0 && r2 < size && c2 >= 0 && c2 < size) {
                if (board[r1][c1] == 'O' && board[r2][c2] == 'S') {
                	return new SOS(r2, c2, r1, c1, row, col);
                }
            }
        }
        return null;
    }
    
}
