package sprint5.product;

import java.util.List;

public class ComputerPlayerModel extends PlayerModel {
	
	public ComputerPlayerModel(int playerNum) {
        super(playerNum);
    }
	
	@Override
    public boolean isComputer() {
        return true;
    }
	
	@Override
    public Move chooseMove(GameModel model) {
        List<int[]> emptyCells = model.getEmptyCells();
        if (emptyCells.isEmpty()) return null;

		int[] chosenSquare = emptyCells.get((int)(Math.random() * emptyCells.size()));
		char letter = Math.random() < 0.5 ? 'S' : 'O';
        
        return new Move(chosenSquare[0], chosenSquare[1], letter);
    }
}
