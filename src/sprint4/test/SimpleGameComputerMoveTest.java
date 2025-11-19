package sprint4.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint4.product.*;

class SimpleGameComputerMoveTest {

	private SimpleGameModel simpleGame;

    @BeforeEach
    void setUp() {
        simpleGame = new SimpleGameModel(3);
    }
    
    @After
	public void tearDown() throws Exception {
	}

    // 9.1 AC: Valid move in simple game
    @Test
    void testComputerValidMoveSimple() {
        simpleGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);
        boolean moveMade = simpleGame.makeComputerMove();
        assertTrue(moveMade);

        char[][] board = simpleGame.getBoard();
        boolean found = false;

        for (char[] row : board) {
            for (char c : row) {
                if (c == 'S' || c == 'O') {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found);
    }

    // 9.2 AC: Invalid move on occupied cell
    @Test
    void testComputerInvalidMoveOccupiedCell() {
        simpleGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);
        simpleGame.makeMove(0, 0, 'S');
        boolean moveMade = simpleGame.makeMove(0, 0, 'O');
        assertFalse(moveMade);
    }

    // 9.3 AC: Invalid move outside board
    @Test
    void testComputerMoveOutsideSimpleGame() {
        simpleGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);

        assertFalse(simpleGame.makeMove(-1, 0, 'S'));
        assertFalse(simpleGame.makeMove(0, 3, 'O'));

        assertEquals(PlayerType.COMPUTER, simpleGame.getCurrentPlayerType());
    }

}
