package sprint_0;

import javax.swing.*;
import java.awt.*;

public class InitialGUI extends JFrame {

    private JPanel mainPanel;        
    private JPanel gridContainer; // container to center the grid
    private JPanel gridPanel;        
    private JPanel bottomPanel;      

    private JCheckBox recordGame;

    private JRadioButton btnSimpleGame;
    private JRadioButton btnGeneralGame;
    private ButtonGroup gameModeGroup;

    public InitialGUI() {
        setTitle("SOS Game");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout()); 

        buildTopPanel();
        buildGridPanel();
        buildBottomPanel();

        add(mainPanel, BorderLayout.NORTH);   
        add(gridContainer, BorderLayout.CENTER);  
        add(bottomPanel, BorderLayout.SOUTH); 
    }

    private void buildTopPanel() {
        mainPanel = new JPanel(new FlowLayout());
        JLabel gameModeLabel = new JLabel("Game Mode:");
        mainPanel.add(gameModeLabel);

        btnSimpleGame = new JRadioButton("Simple Game");
        btnGeneralGame = new JRadioButton("General Game");
        gameModeGroup = new ButtonGroup();
        gameModeGroup.add(btnSimpleGame);
        gameModeGroup.add(btnGeneralGame);

        mainPanel.add(btnSimpleGame);
        mainPanel.add(btnGeneralGame);
    }

    private void buildGridPanel() {
        int gridSize = 8; // 8x8 grid

        // Create the actual grid panel
        gridPanel = new JPanel(new GridLayout(gridSize, gridSize, 2, 2));
        gridPanel.setPreferredSize(new Dimension(400, 400)); // smaller size

        for (int i = 0; i < gridSize * gridSize; i++) {
            JButton btn = new JButton(""); 
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            gridPanel.add(btn);
        }

        // Create a container panel to center the grid
        gridContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridContainer.add(gridPanel);
    }

    private void buildBottomPanel() {
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        recordGame = new JCheckBox("Record game");
        bottomPanel.add(recordGame);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialGUI gui = new InitialGUI();
            gui.setVisible(true);
        });
    }
}
