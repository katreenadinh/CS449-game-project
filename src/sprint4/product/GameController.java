package sprint4.product;

import javafx.application.Platform;
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
        
        PlayerType blueType = view.getBlueTypeGroup().getSelectedToggle() != null &&
                ((RadioButton) view.getBlueTypeGroup().getSelectedToggle()).getText().equalsIgnoreCase("Computer")
                ? PlayerType.COMPUTER : PlayerType.HUMAN;

		PlayerType redType = view.getRedTypeGroup().getSelectedToggle() != null &&
		                ((RadioButton) view.getRedTypeGroup().getSelectedToggle()).getText().equalsIgnoreCase("Computer")
		                ? PlayerType.COMPUTER : PlayerType.HUMAN;
		
		model.setPlayerTypes(blueType, redType);
        
        if (model.getCurrentPlayerType() == PlayerType.COMPUTER) {
            computerMoveHandlers();
        } 
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
        
        view.setSquares(squares);
        view.setBoardGrid(newBoard);
        view.buildBoardStack(newBoard);
        mainPanel.setCenter(view.getBoardStack());
        
        if (model.getCurrentPlayerType() == PlayerType.COMPUTER && !model.isGameOver()) {
            computerMoveHandlers();
        }
        
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
    	
    	if (model.getCurrentPlayerType() != PlayerType.HUMAN) return;

    	char letter = getSelectedLetterForCurrentPlayer();
    	boolean moveMade = model.makeMove(row, col, letter);

    	if (moveMade) {
    		System.out.println("Move made at (" + row + ", " + col + ") with " + letter);
    		updateSquare(row, col, letter);
    		drawSOSLines();

    		if (model.isGameOver()) {
    			view.setCurrentTurnLabel(getGameOverText());
    			disableBoardClicks();
    		}
    		else {
    			view.setCurrentTurnLabel("Current Turn: " + 
    					(model.getCurrentPlayer() == 1 ? "Blue Player" : "Red Player"));
    			computerMoveHandlers();
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
    
    private void SOSLine(GameModel.SOS sos) {
        GameView.Square[][] sq = view.getSquares();
        GameView.Square start = sq[sos.row1][sos.col1];
        GameView.Square end = sq[sos.row3][sos.col3];
        
        double startX = start.getBoundsInParent().getMinX() + start.getWidth() / 2;
        double startY = start.getBoundsInParent().getMinY() + start.getHeight() / 2;
        double endX = end.getBoundsInParent().getMinX() + end.getWidth() / 2;
        double endY = end.getBoundsInParent().getMinY() + end.getHeight() / 2;

        Line line = new Line(startX, startY, endX, endY);
        Color lineColor = (sos.player == 1) ? Color.BLUE : Color.RED;
        
        line.setStroke(lineColor);
        line.setStrokeWidth(3);
        line.setMouseTransparent(true);

        view.getLinePane().getChildren().add(line);
        line.toFront();
    }
    
    private void drawSOSLines() {
        if (model.getGameMode() == GameMode.SIMPLE) {
            GameModel.SOS sos = model.getMoveMakesSOS();
            if (sos != null) SOSLine(sos);
        } 
        else {
            GeneralGameModel general = (GeneralGameModel) model;
            for (GameModel.SOS sos : general.getSOSList()) {
                SOSLine(sos);
            }
        }
    }
    
    private void computerMoveHandlers() {
        if (model.isGameOver()) return;

        Platform.runLater(() -> {
            boolean moveMade = false;

            if (model.getCurrentPlayerType() == PlayerType.COMPUTER) {
                if (model instanceof SimpleGameModel) {
                    moveMade = ((SimpleGameModel) model).makeComputerMove();
                } else if (model instanceof GeneralGameModel) {
                    moveMade = ((GeneralGameModel) model).makeComputerMove();
                }

                if (moveMade) {
                    refreshBoard();
                    drawSOSLines();

                    if (model.isGameOver()) {
                        view.setCurrentTurnLabel(getGameOverText());
                        disableBoardClicks();
                    } else {
                        view.setCurrentTurnLabel("Current Turn: " +
                                (model.getCurrentPlayer() == 1 ? "Blue Player" : "Red Player"));
                        computerMoveHandlers();
                    }
                }
            }
        });
    }
    
    private void refreshBoard() {
        char[][] boardData = model.getBoard();
        for (int r = 0; r < boardData.length; r++) {
            for (int c = 0; c < boardData[r].length; c++) {
                view.getSquares()[r][c].setLetter(boardData[r][c]);
            }
        }
    }
    
    private void disableBoardClicks() {
        GameView.Square[][] sq = view.getSquares();
        for (int r = 0; r < sq.length; r++) {
            for (int c = 0; c < sq[r].length; c++) {
                sq[r][c].setOnMouseClicked(null);
            }
        }
    }
}
