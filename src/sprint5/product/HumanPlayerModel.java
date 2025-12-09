package sprint5.product;

public class HumanPlayerModel extends PlayerModel{
	
	public HumanPlayerModel(int playerNum) {
        super(playerNum);
    }
	
	@Override
    public boolean isComputer() {
        return false;
    }

    @Override
    public Move chooseMove(GameModel model) {
    	return null;
    }
}
