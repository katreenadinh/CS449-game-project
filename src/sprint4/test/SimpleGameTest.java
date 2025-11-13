package sprint4.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sprint3.product.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;

public class SimpleGameTest {

    private SimpleGameModel simpleModel;

    @BeforeEach
    void setup() {
        simpleModel = new SimpleGameModel(3);   // 3x3 board
    }
    
    @After
	public void tearDown() throws Exception {
	}

    // AC 4.1
    @Test
    void testValidMove() {
        boolean moveMade = simpleModel.makeMove(0, 0, 'S');
        assertTrue(moveMade, "Move should be valid");
        assertEquals('S', simpleModel.getBoard()[0][0]);
    }
    
    // AC 4.2
    @Test
    void testInvalidMove() {
        simpleModel.makeMove(0, 0, 'S');
        boolean moveMade = simpleModel.makeMove(0, 0, 'O');
        assertFalse(moveMade, "Cannot place on occupied square");
    }
    
    // AC 5.1
    @Test
    void testSOSDetection() {
        simpleModel.makeMove(0, 0, 'S');
        simpleModel.makeMove(1, 0, 'S'); 
        simpleModel.makeMove(0, 1, 'O');
        simpleModel.makeMove(1, 1, 'O');
        simpleModel.makeMove(0, 2, 'S');

        GameModel.SOS sos = simpleModel.getMoveMakesSOS();
        assertNotNull(sos, "Simple mode should detect SOS");
        assertEquals(0, sos.row1);
        assertEquals(0, sos.col1);
        assertEquals(0, sos.row3);
        assertEquals(2, sos.col3);
    }
    
    // AC 5.2: for tie
    @Test
    void testGameOver() {
        // Fill board with no SOS
        char[][] moves = {
                {'S', 'S', 'O'},
                {'O', 'O', 'S'},
                {'S', 'O', 'O'}
        };

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                simpleModel.makeMove(r, c, moves[r][c]);
            }
        }

        assertTrue(simpleModel.isGameOver(), "Game should detect game over when board is full");

        // AC 5.3: attempt move after game over
        boolean moveMade = simpleModel.makeMove(0, 0, 'S');
        assertFalse(moveMade, "No move allowed after game over");
    }
}
