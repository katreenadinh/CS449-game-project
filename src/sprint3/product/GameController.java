package sprint3.product;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class GameController {

    private GameModel model;
    private GameView view;
    private GameView.Square[][] squares;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.squares = view.getSquares();

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        modeSelectorListeners();
        boardSizeSelectorListeners();
        squareListeners();
    }

    private void modeSelectorListeners() {
        ToggleGroup modeGroup = view.getGameModeGroup();
        if (modeGroup != null) {
            modeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
                if (newToggle != null) {
                    RadioButton selected = (RadioButton) newToggle;
                    if (selected.getText().equalsIgnoreCase("Simple")) {
                        model.setGameMode(GameMode.SIMPLE);
                        System.out.println("Game mode set to SIMPLE");
                    } else {
                        model.setGameMode(GameMode.GENERAL);
                        System.out.println("Game mode set to GENERAL");
                    }
                }
            });
        }
    }

    private void boardSizeSelectorListeners() {
        ComboBox<Integer> sizeBox = view.getBoardSizeBox();
        if (sizeBox != null) {
            sizeBox.setOnAction(e -> {
                int newSize = sizeBox.getValue();
                model.setBoardSize(newSize);
                System.out.println("Board resized to " + newSize + "x" + newSize);
            });
        }
    }

    private void squareListeners() {
        if (squares == null) return;

        for (int r = 0; r < squares.length; r++) {
            for (int c = 0; c < squares[r].length; c++) {
                int row = r;
                int col = c;
                squares[r][c].setOnMouseClicked(e -> handleSquareClick(row, col));
            }
        }
    }

    public void rebuildBoard(int newSize) {
        GridPane newBoard = new GridPane();
        newBoard.setAlignment(Pos.CENTER);
        squares = new GameView.Square[newSize][newSize];

        for (int r = 0; r < newSize; r++) {
            for (int c = 0; c < newSize; c++) {
                squares[r][c] = view.new Square(r, c);
                int row = r, col = c;
                squares[r][c].setOnMouseClicked(e -> handleSquareClick(row, col));
                newBoard.add(squares[r][c], c, r);
            }
        }

        BorderPane mainPanel = view.getMainPanel();
        mainPanel.setCenter(newBoard);

        view.setBoardPane(newBoard);
        view.setSquares(squares);

        System.out.println("Board rebuilt to " + newSize + "x" + newSize);
    }

    public void updateSquare(int row, int col, char letter) {
        if (squares != null && row < squares.length && col < squares[row].length) {
            squares[row][col].setLetter(letter);
        }
    }

    public void handleSquareClick(int row, int col) {
    	
    	if (model.isGameOver()) {
            view.setCurrentTurnLabel(getGameOverText());
            return;
        }
    	
        char letter = getSelectedLetterForCurrentPlayer();
        boolean moveMade = model.makeMove(row, col, letter);

        if (moveMade) {
            System.out.println("Move made at (" + row + ", " + col + ") with " + letter);
            updateSquare(row, col, letter);
            drawSOSLine();
            
            if (model.isGameOver()) {
            	view.setCurrentTurnLabel(getGameOverText());
            	
	            GameView.Square[][] squares = view.getSquares();
	            for (int r = 0; r < squares.length; r++) {
	                for (int c = 0; c < squares[r].length; c++) {
	                    squares[r][c].setOnMouseClicked(null);
	                }
	            }
            }
	        else {
            	view.setCurrentTurnLabel("Current Turn: " + 
                (model.getCurrentPlayer() == 1 ? "Blue Player" : "Red Player"));
	        }
	    } 
        else {
            System.out.println("Invalid move at (" + row + ", " + col + ")");
        }
    }

    private char getSelectedLetterForCurrentPlayer() {
        ToggleGroup group = (model.getCurrentPlayer() == 1)
                ? view.getBlueGroup()
                : view.getRedGroup();

        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return selected.getText().charAt(0);
    }
    
    private String getGameOverText() {
        int winner = model.getWinner();
        if (winner == 1) return "Game Over! Blue Player Wins!";
        if (winner == 2) return "Game Over! Red Player Wins!";
        return "Game Over! Draw!";
    }
    
    private void drawSOSLine() {
    	GameModel.SOS sos = model.getMoveMakesSOS();
        if (sos == null) return;

        GameView.Square[][] sq = view.getSquares();
        GridPane boardPane = view.getBoardPane();

        // Center of first square in scene coordinates
        double x1Scene = sq[sos.row1][sos.col1].localToScene(
                sq[sos.row1][sos.col1].getWidth() / 2,
                sq[sos.row1][sos.col1].getHeight() / 2
        ).getX();
        double y1Scene = sq[sos.row1][sos.col1].localToScene(
                sq[sos.row1][sos.col1].getWidth() / 2,
                sq[sos.row1][sos.col1].getHeight() / 2
        ).getY();

        // Center of third square in scene coordinates
        double x2Scene = sq[sos.row3][sos.col3].localToScene(
                sq[sos.row3][sos.col3].getWidth() / 2,
                sq[sos.row3][sos.col3].getHeight() / 2
        ).getX();
        double y2Scene = sq[sos.row3][sos.col3].localToScene(
                sq[sos.row3][sos.col3].getWidth() / 2,
                sq[sos.row3][sos.col3].getHeight() / 2
        ).getY();

        // Convert scene coordinates to boardPane coordinates
        double x1 = boardPane.sceneToLocal(x1Scene, y1Scene).getX();
        double y1 = boardPane.sceneToLocal(x1Scene, y1Scene).getY();
        double x2 = boardPane.sceneToLocal(x2Scene, y2Scene).getX();
        double y2 = boardPane.sceneToLocal(x2Scene, y2Scene).getY();

        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);

        boardPane.getChildren().add(line);
    }
}
