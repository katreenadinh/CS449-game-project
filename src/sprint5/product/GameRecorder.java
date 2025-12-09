package sprint5.product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameRecorder {

    private List<PlayerModel.Move> moves;
    private boolean recording;
    private int boardSize;
    private GameMode gameMode;
    private PlayerType blueType;
    private PlayerType redType;

    public GameRecorder() {
        moves = new ArrayList<>();
        recording = false;
    }

    public void startRecording(int boardSize, GameMode gameMode, PlayerType blueType, PlayerType redType) {
        this.boardSize = boardSize;
        this.gameMode = gameMode;
        this.blueType = blueType;
        this.redType = redType;
        
        moves.clear();
        recording = true;
    }

    public void stopRecording() {
        recording = false;
        System.out.println("Recording stopped");
    }

    public boolean isRecording() {
        return recording;
    }

    public void recordMove(PlayerModel.Move move) {
        if (recording) {
            moves.add(move);
        }
    }

    public List<PlayerModel.Move> getMoves() {
        return moves;
    }

    public void saveToFile(String filename) {
        if (moves.isEmpty()) {
            System.out.println("No moves to save");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(boardSize + "," + gameMode + "," + blueType + "," + redType + "\n");

            for (PlayerModel.Move move : moves) {
                writer.write(move.row + "," + move.col + "," + move.letter + "\n");
            }
            
            System.out.println("Successfully saved " + moves.size() + " moves to " + filename);

        } catch (IOException e) {
            System.err.println("Error saving recording: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<PlayerModel.Move> loadFromFile(String filename) {
        List<PlayerModel.Move> loadedMoves = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String gameSettingsLine = reader.readLine();
            if (gameSettingsLine == null) {
                System.err.println("Empty file: " + filename);
                return loadedMoves;
            }
            
            String[] gameSettingsParts = gameSettingsLine.split(",");
            if (gameSettingsParts.length != 4) {
                System.err.println("Invalid game setting format in file");
                return loadedMoves;
            }
            
            boardSize = Integer.parseInt(gameSettingsParts[0]);
            gameMode = GameMode.valueOf(gameSettingsParts[1]);
            blueType = PlayerType.valueOf(gameSettingsParts[2]);
            redType = PlayerType.valueOf(gameSettingsParts[3]);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                char letter = parts[2].charAt(0);
                loadedMoves.add(new PlayerModel.Move(row, col, letter));
            }
            
            System.out.println("Successfully loaded " + loadedMoves.size() + " moves from " + filename);
            System.out.println("Game Settings: Board=" + boardSize + "x" + boardSize + 
                              ", Mode=" + gameMode + ", Blue=" + blueType + ", Red=" + redType);
            
        } catch (FileNotFoundException e) {
            System.err.println("Recording file not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error loading recording: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing game setting data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return loadedMoves;
    }

    public int getBoardSize() { return boardSize; }
    public GameMode getGameMode() { return gameMode; }
    public PlayerType getBlueType() { return blueType; }
    public PlayerType getRedType() { return redType; }
}