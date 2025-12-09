package sprint5.product;

public abstract class PlayerModel {
    protected int playerNum; 

    public PlayerModel(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getNumber() { return playerNum; }
    
    public abstract boolean isComputer();
    
    public abstract Move chooseMove(GameModel model);
    
    public static class Move {
        public int row;
        public int col;
        public char letter;

        public Move(int row, int col, char letter) {
            this.row = row;
            this.col = col;
            this.letter = letter;
        }
    }
}
