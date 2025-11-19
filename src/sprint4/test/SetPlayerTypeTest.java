package sprint4.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint4.product.*;

class SetPlayerTypeTest {
	
	private SimpleGameModel simpleGame;

    @BeforeEach
    void setUp() {
        simpleGame = new SimpleGameModel(3);
    }
    
    @After
	public void tearDown() throws Exception {
	}

    // 8.1 AC: Valid player type selection
    @Test
    void testSetPlayerTypes() {
        simpleGame.setPlayerTypes(PlayerType.HUMAN, PlayerType.COMPUTER);
        assertEquals(PlayerType.HUMAN, simpleGame.getCurrentPlayerType());
        simpleGame.switchPlayer();
        assertEquals(PlayerType.COMPUTER, simpleGame.getCurrentPlayerType());
    }

    // 8.2 AC: Default player type selection
    @Test
    void testDefaultPlayerTypeSelection() {
        simpleGame.setPlayerTypes(PlayerType.HUMAN, PlayerType.HUMAN);
        assertEquals(PlayerType.HUMAN, simpleGame.getCurrentPlayerType());
    }

}
