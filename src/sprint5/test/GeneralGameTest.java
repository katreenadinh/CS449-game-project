package sprint5.test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sprint3.product.*;

class GeneralGameTest {

	private GeneralGameModel generalModel;

    @BeforeEach
    void setup() {
        generalModel = new GeneralGameModel(3); // 3x3 board
    }
    
    @After
	public void tearDown() throws Exception {
	}
    
    // AC 6.1
    @Test
    void testValidMove() {
        boolean moveMade = generalModel.makeMove(1, 1, 'O');
        assertTrue(moveMade);
        assertEquals('O', generalModel.getBoard()[1][1]);
    }
    
    // AC 6.1
    @Test
    void testMultipleSOSDetection() {
        // Create multiple SOS lines
        generalModel.makeMove(0, 0, 'S'); 
        generalModel.makeMove(0, 1, 'O'); 
        generalModel.makeMove(0, 2, 'S'); // SOS 1

        generalModel.makeMove(1, 0, 'S'); 
        generalModel.makeMove(1, 1, 'O'); 
        generalModel.makeMove(1, 2, 'S'); // SOS 2

        assertEquals(2, generalModel.getSOSList().size(), "Should detect multiple SOS lines");
    }
    
    // AC 7.1
    @Test
    void testGameOverDetection() {
        char[][] moves = {
                {'S', 'O', 'S'},
                {'O', 'S', 'O'},
                {'S', 'O', 'S'}
        };

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                generalModel.makeMove(r, c, moves[r][c]);
            }
        }

        assertTrue(generalModel.isGameOver(), "Game should detect game over when board is full");
        
        // AC 7.3: attempt move after game over
        boolean moveMade = generalModel.makeMove(0, 0, 'S');
        assertFalse(moveMade, "No move allowed after game over");
    }

}
