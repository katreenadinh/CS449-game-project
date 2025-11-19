package sprint4.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sprint4.product.*;

class GeneralGameComputerMoveTest {

	private GeneralGameModel generalGame;

    @BeforeEach
    void setUp() {
        generalGame = new GeneralGameModel(3);
    }
    
    @After
	public void tearDown() throws Exception {
	}

    // 10.1 AC: Valid move in general game
    @Test
    void testComputerValidMoveGeneral() {
        generalGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);
        boolean moveMade = generalGame.makeComputerMove();
        assertTrue(moveMade);

        char[][] board = generalGame.getBoard();
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

    // 10.2 AC: Invalid move on occupied cell
    @Test
    void testComputerInvalidMoveOccupiedCellGeneral() {
        generalGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);
        generalGame.makeMove(0, 0, 'S');
        assertFalse(generalGame.makeMove(0, 0, 'O'));
    }

    // 10.3 AC: Invalid move outside board
    @Test
    void testComputerMoveOutsideGeneralGame() {
        generalGame.setPlayerTypes(PlayerType.COMPUTER, PlayerType.HUMAN);

        assertFalse(generalGame.makeMove(3, 0, 'S'));
        assertFalse(generalGame.makeMove(0, -1, 'O'));

        assertEquals(PlayerType.COMPUTER, generalGame.getCurrentPlayerType());
    }

}
