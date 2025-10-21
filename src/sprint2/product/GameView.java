package sprint2.product;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView extends Application {

    private GameModel model;
    private GameController controller;
    private Square[][] squares;
    
    private GridPane boardPane;
    private ToggleGroup gameModeGroup;
    private ToggleGroup blueGroup;
    private ToggleGroup redGroup;
    private ComboBox<Integer> boardSizeBox;
    private Label currentTurnLabel;
    private BorderPane mainPanel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize model
        model = new GameModel(3); // Default 3x3 board

        mainPanel = new BorderPane();
        mainPanel.setPadding(new Insets(10));

        // --- Top: Game Mode & Board Size ---
        HBox modeBox = new HBox(20);
        modeBox.setAlignment(Pos.CENTER);
        modeBox.setPadding(new Insets(10));

        Label modeLabel = new Label("Game Mode:");
        RadioButton btnSimpleMode = new RadioButton("Simple");
        RadioButton btnGeneralMode = new RadioButton("General");
        gameModeGroup = new ToggleGroup();
        btnSimpleMode.setToggleGroup(gameModeGroup);
        btnGeneralMode.setToggleGroup(gameModeGroup);
        btnSimpleMode.setSelected(true);

        Label sizeLabel = new Label("Board Size:");
        boardSizeBox = new ComboBox<>();
        boardSizeBox.getItems().addAll(3, 4, 5, 6, 7, 8, 9);
        boardSizeBox.setValue(3);

        modeBox.getChildren().addAll(modeLabel, btnSimpleMode, btnGeneralMode, sizeLabel, boardSizeBox);
        mainPanel.setTop(modeBox);

        // --- Left: Blue Player ---
        VBox blueBox = new VBox(10);
        blueBox.setAlignment(Pos.CENTER);
        blueBox.setPadding(new Insets(10));
        Label blueLabel = new Label("Blue Player");
        blueLabel.setTextFill(Color.BLUE);
        RadioButton btnBlueS = new RadioButton("S");
        RadioButton btnBlueO = new RadioButton("O");
        blueGroup = new ToggleGroup();
        btnBlueS.setToggleGroup(blueGroup);
        btnBlueO.setToggleGroup(blueGroup);
        btnBlueS.setSelected(true);
        blueBox.getChildren().addAll(blueLabel, btnBlueS, btnBlueO);
        mainPanel.setLeft(blueBox);

        // --- Right: Red Player ---
        VBox redBox = new VBox(10);
        redBox.setAlignment(Pos.CENTER);
        redBox.setPadding(new Insets(10));
        Label redLabel = new Label("Red Player");
        redLabel.setTextFill(Color.RED);
        RadioButton btnRedS = new RadioButton("S");
        RadioButton btnRedO = new RadioButton("O");
        redGroup = new ToggleGroup();
        btnRedS.setToggleGroup(redGroup);
        btnRedO.setToggleGroup(redGroup);
        btnRedS.setSelected(true);
        redBox.getChildren().addAll(redLabel, btnRedS, btnRedO);
        mainPanel.setRight(redBox);

        // --- Center: Game Board ---
        boardPane = new GridPane();
        boardPane.setAlignment(Pos.CENTER);
        int size = model.getSize();
        squares = new Square[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                squares[row][col] = new Square(row, col);
                boardPane.add(squares[row][col], col, row);
            }
        }
        mainPanel.setCenter(boardPane);

        // --- Bottom: Current Turn ---
        currentTurnLabel = new Label("Current Turn: " + 
        	    (model.getCurrentPlayer() == 1 ? "Blue Player" : "Red Player"));
        currentTurnLabel.setFont(Font.font(16));
        currentTurnLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(currentTurnLabel, Pos.CENTER);
        mainPanel.setBottom(currentTurnLabel);
        
        controller = new GameController(model, this);
        
        Scene scene = new Scene(mainPanel, 700, 600);

        primaryStage.setTitle("SOS Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Inner class for the board cells
    public class Square extends Pane {
        private final int row;
        private final int col;
        private Label letterLabel;

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
            
            letterLabel = new Label("");
            letterLabel.setFont(Font.font(24));
            letterLabel.setAlignment(Pos.CENTER);
            letterLabel.setMaxWidth(Double.MAX_VALUE);
            letterLabel.setMaxHeight(Double.MAX_VALUE);
            
            // Use StackPane for better centering
            StackPane stack = new StackPane(letterLabel);
            stack.setPrefSize(80, 80);
            getChildren().add(stack);
            
            setStyle("-fx-border-color: black; -fx-background-color: white;");
            setPrefSize(80, 80);
            setMinSize(80, 80);
            setMaxSize(80, 80);
        }
        
        public void setLetter(char letter) {
        	letterLabel.setText(String.valueOf(letter));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square[][] squares) {
		this.squares = squares;
	}

	public ToggleGroup getGameModeGroup() {
		return gameModeGroup;
	}

	public void setGameModeGroup(ToggleGroup gameModeGroup) {
		this.gameModeGroup = gameModeGroup;
	}

	public ToggleGroup getBlueGroup() {
		return blueGroup;
	}

	public void setBlueGroup(ToggleGroup blueGroup) {
		this.blueGroup = blueGroup;
	}

	public ToggleGroup getRedGroup() {
		return redGroup;
	}

	public void setRedGroup(ToggleGroup redGroup) {
		this.redGroup = redGroup;
	}

	public ComboBox<Integer> getBoardSizeBox() {
		return boardSizeBox;
	}

	public void setBoardSizeBox(ComboBox<Integer> boardSizeBox) {
		this.boardSizeBox = boardSizeBox;
	}

	public Label getCurrentTurnLabel() {
		return currentTurnLabel;
	}

	public void setCurrentTurnLabel(Label currentTurnLabel) {
		this.currentTurnLabel = currentTurnLabel;
	}
	
	public void setCurrentTurnLabel(String text) {
	    if (currentTurnLabel != null) {
	        currentTurnLabel.setText(text);
	    }
	}

	public GridPane getBoardPane() {
		return boardPane;
	}

	public void setBoardPane(GridPane boardPane) {
		this.boardPane = boardPane;
	}

	public BorderPane getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(BorderPane mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public GameModel getModel() {
		return model;
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
}
